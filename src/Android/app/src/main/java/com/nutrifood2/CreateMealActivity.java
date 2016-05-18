package com.nutrifood2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nutrifood2.Adapter.CustomListAdapter;
import com.nutrifood2.Utils.Client;
import com.nutrifood2.Utils.DataHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import cz.msebera.android.httpclient.Header;

public class CreateMealActivity extends AppCompatActivity implements View.OnClickListener  {

    static final int REQUEST_CAMERA = 1;
    static final int SELECT_FILE = 2;
    private File mImage = null;
    private ListView mIngredients;
    private ListView mInstructions;
    private EditText mIngredient;
    private EditText mInstruction;
    private EditText mMealName;
    private EditText mCookTime;
    private EditText mDescription;
    private Spinner mDifficulty;
    private Spinner mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectImage();
                }
            });
        }

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.title_create_meal);
        }

        mIngredient = (EditText) findViewById(R.id.ingredient);

        mInstruction = (EditText) findViewById(R.id.instruction);

        mIngredients = (ListView) findViewById(R.id.ingredient_list);
        mIngredients.setAdapter(new CustomListAdapter(this));

        mInstructions = (ListView) findViewById(R.id.instruction_list);
        mInstructions.setAdapter(new CustomListAdapter(this));

        mMealName = (EditText) findViewById(R.id.mealname);
        mDescription = (EditText) findViewById(R.id.description);
        mCookTime = (EditText) findViewById(R.id.cooktime);
        mCategory = (Spinner) findViewById(R.id.category);
        mDifficulty = (Spinner) findViewById(R.id.difficulty);

        ImageButton addIngredient = (ImageButton) findViewById(R.id.add_ingredient);
        addIngredient.setOnClickListener(this);
        ImageButton addInstruction = (ImageButton) findViewById(R.id.add_instruction);
        addInstruction.setOnClickListener(this);
        Button createButton = (Button) findViewById(R.id.create_button);
        createButton.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            //NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectImage() {
        try {
            final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Photo");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Take Photo")
                            && checkWriteExternalPermission("android.permission.CAMERA")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        CreateMealActivity.this.startActivityForResult(intent, REQUEST_CAMERA);
                    } else if (items[item].equals("Choose from Library")) {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        CreateMealActivity.this.startActivityForResult(
                                Intent.createChooser(intent, "Select File"),
                                SELECT_FILE);
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    } else {
                        Log.d("selectImage","ERROR");
                    }
                }
            });
            builder.show();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private boolean checkWriteExternalPermission(String permission)
    {
        int res = checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private File savebitmap(String filename, Bitmap bitmap) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;

        File file = new File(getFilesDir(), filename + ".png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, filename + ".png");
            Log.e("file exist", "" + file + ",Bitmap= " + filename);
        }
        try {
            Log.d("savebitmap","in");
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("file", "" + file);
        return file;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mImage = savebitmap("tmp", imageBitmap);
                ImageView image = (ImageView) findViewById(R.id.meal_image);
                image.setImageBitmap(imageBitmap);
            }
            else if (requestCode == SELECT_FILE) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.d("PATH", picturePath);
                try {
                    mImage = new File(new URI(picturePath));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onCreateMeal()
    {
        JSONArray ingredients = new JSONArray();
        JSONArray instructions = new JSONArray();
        CustomListAdapter ingredientAdapter = (CustomListAdapter)mIngredients.getAdapter();
        CustomListAdapter instructionAdapter = (CustomListAdapter)mInstructions.getAdapter();
        try {
            ingredients = DataHolder.getJsonArray(ingredientAdapter.getList(), ingredients);
            instructions = DataHolder.getJsonArray(instructionAdapter.getList(), instructions);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String mealName = String.valueOf(mMealName.getText());
        String cookTime = String.valueOf(mCookTime.getText());
        String description = String.valueOf(mDescription.getText());
        String difficulty = mDifficulty.getSelectedItem().toString();
        String category = mCategory.getSelectedItem().toString();

        RequestParams params = new RequestParams();
        params.put(getString(R.string.name_key), mealName);
        if (DataHolder.user != null)
            params.put(getString(R.string.author_key), DataHolder.user.Username());
        params.put(getString(R.string.cooktime_key), cookTime);
        params.put(getString(R.string.description_key), description);
        params.put(getString(R.string.difficulty_key), difficulty);
        params.put(getString(R.string.category_key), category);
        params.put(getString(R.string.ingredients_key), ingredients);
        params.put(getString(R.string.instruction_key), instructions);
        try {
            params.put(getString(R.string.image_key), mImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Client.post(getString(R.string.meals_URL), params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("create", "SUCCESS");
                finish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                Log.d("create", "SUCCESS");
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("create", responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("create", "FAILURE");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add_ingredient:
                String ingredient = String.valueOf(mIngredient.getText());
                ((CustomListAdapter)mIngredients.getAdapter()).addItem(ingredient);
                mIngredient.setText("");
                break;
            case R.id.add_instruction:
                String instruction = String.valueOf(mInstruction.getText());
                ((CustomListAdapter)mInstructions.getAdapter()).addItem(instruction);
                mInstruction.setText("");
                break;
            case R.id.create_button:
                onCreateMeal();
                break;
        }
    }
}

package com.nutrifood.cisner_d.nutrifood.Main.Pages.CreateMeal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nutrifood.cisner_d.nutrifood.Main.Client;
import com.nutrifood.cisner_d.nutrifood.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CreateDishes extends Fragment implements View.OnClickListener {
    private Button _createbutton;
    private Button _addbutton;
    private Button _imagebutton;
    private EditText _mealname;
    private EditText _author;
    private EditText _category;
    private EditText _ingredient;
    private EditText _cooktime;
    private EditText _difficulty;
    private EditText _description;
    private EditText _instruction;
    private ArrayList<String> _ingredients = new ArrayList<String>();
    private TextView error;
    private Bitmap _image;
    private View createdishe_view;
    static final int REQUEST_CAMERA = 1;
    static final int SELECT_FILE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        createdishe_view = inflater.inflate(R.layout.fragment_create_dishes, container, false);

        _createbutton = (Button)createdishe_view.findViewById(R.id.register);
        _addbutton = (Button)createdishe_view.findViewById(R.id.add_ingredient);
        _imagebutton = (Button)createdishe_view.findViewById(R.id.add_image);
        _mealname = (EditText)createdishe_view.findViewById(R.id.mealname);
        _author = (EditText)createdishe_view.findViewById(R.id.author);
        _category = (EditText)createdishe_view.findViewById(R.id.category);
        _ingredient = (EditText)createdishe_view.findViewById(R.id.ingredients);
        _difficulty = (EditText)createdishe_view.findViewById(R.id.difficulty);
        _cooktime = (EditText)createdishe_view.findViewById(R.id.cooktime);
        _description = (EditText)createdishe_view.findViewById(R.id.description);
        _instruction = (EditText)createdishe_view.findViewById(R.id.instruction);

        _createbutton.setOnClickListener(this);
        _addbutton.setOnClickListener(this);
        _imagebutton.setOnClickListener(this);
        _mealname.addTextChangedListener(textWatcher);
        _author.addTextChangedListener(textWatcher);
        _category.addTextChangedListener(textWatcher);
        _ingredient.addTextChangedListener(textWatcher);
        _difficulty.addTextChangedListener(textWatcher);
        _cooktime.addTextChangedListener(textWatcher);
        _description.addTextChangedListener(textWatcher);
        _instruction.addTextChangedListener(textWatcher);
        return createdishe_view;
    }

    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void onCreateMeal()
    {
        RequestParams params = new RequestParams();
        String mealname = _mealname.getText().toString();
        String author = _author.getText().toString();
        String category = _category.getText().toString();
        String difficulty = _difficulty.getText().toString();
        String cooktime = _cooktime.getText().toString();
        String description = _description.getText().toString();
        String instruction = _instruction.getText().toString();
        String image;

        if (_image != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            _image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            //image = DataHolder.encodeImage(byteArray);
            params.put(getString(R.string.image_key), new ByteArrayInputStream(byteArray), mealname + ".png");
        }

        params.put(getString(R.string.name_key), mealname);
        params.put(getString(R.string.author_key), author);
        params.put(getString(R.string.category_key), category);
        params.put(getString(R.string.difficulty_key), difficulty);
        params.put(getString(R.string.description_key), description);
        params.put(getString(R.string.instruction_key), instruction);
        params.put(getString(R.string.ingredients_key), _ingredients);
        params.put(getString(R.string.cooktime_key), cooktime);

        Client.post(getString(R.string.meals_URL), params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("SUCCESS", "ONE");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                Log.d("SUCCESS", "TWO");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String string, Throwable throwable) {
                Log.e("ERROR", String.valueOf(statusCode));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ERROR", String.valueOf(statusCode));
            }
        });

        _ingredients.clear();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register:
                Log.d("REGISTER", "clicked");
                ConnectivityManager connMgr = (ConnectivityManager)
                        getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    onCreateMeal();
                } else {
                    error.setText("ERROR : no connection");
                }
                break;
            case R.id.add_ingredient:
                Log.d("add_ingredient", "clicked");
                String ingredient = _ingredient.getText().toString();
                Log.d("INGREDIENT", ingredient);
                if (ingredient.length() > 0)
                    _ingredients.add(ingredient);
                    _ingredient.setText("");
                break;
            case R.id.add_image:
                selectImage();
            default:
                Log.d("WTF", "clicked");
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    CreateDishes.this.startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    CreateDishes.this.startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    _image = bitmap;

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.d("PATH", picturePath);
                _image = thumbnail;
            }
        }
    }
}

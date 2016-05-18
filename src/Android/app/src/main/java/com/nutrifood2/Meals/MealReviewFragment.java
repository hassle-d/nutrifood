package com.nutrifood2.Meals;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nutrifood2.Adapter.ListRecyclerViewAdapter;
import com.nutrifood2.Adapter.SimpleRecyclerViewAdapter;
import com.nutrifood2.Models.Comment;
import com.nutrifood2.Models.CommentContent;
import com.nutrifood2.Models.Meal;
import com.nutrifood2.Models.MealContent;
import com.nutrifood2.R;
import com.nutrifood2.Utils.CallBack;
import com.nutrifood2.Utils.Client;
import com.nutrifood2.Utils.DataHolder;
import com.nutrifood2.Utils.RecyclerViewDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MealReviewFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_ITEM_ID = "item_id";

    private Meal mItem;
    private RecyclerView mRecyclerView;
    private Button mSubmit;
    private EditText mComment;
    private TextView mNbView;
    private RatingBar mRating;

    public MealReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the meal content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = MealContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_meal_review, container, false);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.detail_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new ListRecyclerViewAdapter(R.integer.COMMENT_VIEW));

        mSubmit = (Button) rootView.findViewById(R.id.submit_button);
        mSubmit.setOnClickListener(this);

        mComment = (EditText) rootView.findViewById(R.id.comment);

        mNbView = (TextView) rootView.findViewById(R.id.nb_review);

        mRating = (RatingBar)rootView.findViewById(R.id.rating);

        getListComments();

        return rootView;
    }

    private Comment newComment(JSONObject obj)
    {
        Comment comment = null;
        try {
            String id = obj.getString(getString(R.string.id_key));
            if (CommentContent.ITEM_MAP.containsKey(id))
                comment = CommentContent.ITEM_MAP.get(id);
            else
                comment = new Comment();
            comment.Author(obj.getString(getString(R.string.author_key)));
            comment.Name(obj.getString(getString(R.string.author_key)));
            comment.Id(obj.getString(getString(R.string.id_key)));
            comment.MealId(obj.getString(getString(R.string.mealid_key)));

            //Tmp debug - Fix the buggy date on the API
            comment.Date(obj.getString(getString(R.string.date_key)).substring(0, 10));
            comment.Content(obj.getString(getString(R.string.content_key)));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return comment;

    }

    private void getListComments() {

        String url =  "/" + getString(R.string.comment_key) + "/" + mItem.Id();
        Client.get(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                int length = response.length();
                JSONObject obj;
                ListRecyclerViewAdapter adapter;
                adapter = (ListRecyclerViewAdapter) mRecyclerView.getAdapter();
                mNbView.setText(String.valueOf(length) + " Comments");
                Log.d("SUCCESS", response.toString());

                adapter.clearList();

                try {
                    for (int i = 0; i < length; ++i) {
                        obj = response.getJSONObject(i);
                        if (obj != null) {
                            Comment item = newComment(obj);
                            adapter.addItem(item);
                            CommentContent.addItem(item);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String string, Throwable throwable) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });
    }

    private void onRating(double value)
    {
        String url = "/" + getString(R.string.rating_key) + "/" + mItem.Id();
        RequestParams params = new RequestParams();
        params.add(getString(R.string.value_key), String.valueOf(value));

        Client.post(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("SUCCESS", "ONE");
                mRating.setRating(0);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                Log.d("SUCCESS", "TWO");
                mRating.setRating(0);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String string, Throwable throwable) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });
    }

    private void onSubmit()
    {
        String url = "/" + getString(R.string.comment_key) + "/" + mItem.Id();
        RequestParams params = new RequestParams();
        params.add(getString(R.string.author_key), DataHolder.user.Username());
        params.add(getString(R.string.comment_key), String.valueOf(mComment.getText()));

        Client.post(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("SUCCESS", "ONE");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                Log.d("SUCCESS", "TWO");
                mComment.setText("");
                getListComments();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String string, Throwable throwable) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });

        double rating = mRating.getRating();
        if (rating > 0)
        {
            onRating(rating);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.submit_button:
                Log.d("SUBMIT", "CLICKED");
                onSubmit();
                break;
        }
    }
}

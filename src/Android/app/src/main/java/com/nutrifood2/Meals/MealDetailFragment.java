package com.nutrifood2.Meals;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nutrifood2.Adapter.ListRecyclerViewAdapter;
import com.nutrifood2.Models.BasicItem;
import com.nutrifood2.Models.Meal;
import com.nutrifood2.Models.MealContent;
import com.nutrifood2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a single Meal detail screen.
 * This fragment is either contained in a {@link MealListActivity}
 * in two-pane mode (on tablets) or a {@link MealDetailActivity}
 * on handsets.
 */
public class MealDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The meal content this fragment is presenting.
     */
    private Meal mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MealDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the meal content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = MealContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            ImageView imageParalax = (ImageView) activity.findViewById(R.id.image_paralax);

            if (appBarLayout != null)
                appBarLayout.setTitle(mItem.Name());
            if (imageParalax != null)
            {
                Bitmap bitmap = mItem.Bitmap();
                if (bitmap != null)
                    imageParalax.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.meal_detail, container, false);

        // Show the meal content
        if (mItem != null)
        {
            List<BasicItem> list = new ArrayList<BasicItem>();

            list.add(new BasicItem("Author", mItem.Author()));
            list.add(new BasicItem("Difficulty", mItem.Difficulty()));
            list.add(new BasicItem("Category", mItem.Category()));
            list.add(new BasicItem("Description", mItem.Description()));
            list.add(new BasicItem("Cooktime", mItem.Cooktime()));
            list.add(new BasicItem("Ingredients", mItem.Ingredients().toString()));
            list.add(new BasicItem("Instruction", mItem.Instruction()));

            RecyclerView detail_recyclerview = (RecyclerView)rootView.findViewById(R.id.detail_recyclerview);
            detail_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            detail_recyclerview.setAdapter(new ListRecyclerViewAdapter(list, R.integer.CARD_VIEW));
        }

        return rootView;
    }
}

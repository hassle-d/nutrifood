package com.nutrifood2.Meals;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nutrifood2.Adapter.ListRecyclerViewAdapter;
import com.nutrifood2.Adapter.ViewPagerAdapter;
import com.nutrifood2.Categories.CategoryFragment;
import com.nutrifood2.Models.BasicItem;
import com.nutrifood2.Models.Meal;
import com.nutrifood2.Models.MealContent;
import com.nutrifood2.Profil.ProfilFragment;
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
            TextView author = (TextView) activity.findViewById(R.id.author);
            ImageView imageParalax = (ImageView) activity.findViewById(R.id.image_paralax);
            RatingBar ratingBar = (RatingBar) activity.findViewById(R.id.ratingBar);

            if (appBarLayout != null)
                appBarLayout.setTitle(mItem.Name());

            if (author != null)
                author.setText(mItem.Author());

            if (ratingBar != null)
                ratingBar.setRating((float) mItem.Rating());

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

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        Bundle arguments = new Bundle();
        arguments.putString(MealDetailFragment.ARG_ITEM_ID, mItem.Id());

        MealDirectionFragment directionFragment = new MealDirectionFragment();
        MealIngredientsFragment ingredientsFragment = new MealIngredientsFragment();
        MealReviewFragment reviewFragment = new MealReviewFragment();
        MealInformationFragment infoFragment = new MealInformationFragment();

        infoFragment.setArguments(arguments);
        directionFragment.setArguments(arguments);
        ingredientsFragment.setArguments(arguments);
        reviewFragment.setArguments(arguments);

        adapter.addFragment(infoFragment, "INFORMATION");
        adapter.addFragment(directionFragment, "INSTRUCTION");
        adapter.addFragment(ingredientsFragment, "INGREDIENT");
        adapter.addFragment(reviewFragment, "REVIEW COMMENT");

        viewPager.setAdapter(adapter);
    }
}

package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mAlsoKnownAsTv;
    TextView mAlsoKnownAsLabelTv;
    TextView mPlaceofOrigin;
    TextView mDescriptionTv;
    TextView mIngredientsTv;
    int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownAsTv = findViewById(R.id.also_known_tv);
        mAlsoKnownAsLabelTv = findViewById(R.id.also_known_as_label);
        mPlaceofOrigin = findViewById(R.id.origin_tv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);
        mDescriptionTv = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        if (savedInstanceState != null) {
            mPosition = savedInstanceState.getInt(EXTRA_POSITION);
        } else {
            mPosition = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
            if (mPosition == DEFAULT_POSITION) {
                // EXTRA_POSITION not found in intent
                closeOnError();
                return;
            }
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[mPosition];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.placeholder_image)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        final String SEPERATOR1 = ", ";
        final String SEPERATOR2 = ",\n";
        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            StringBuilder alsoKnownAsBuider = new StringBuilder();
            for (String othername : sandwich.getAlsoKnownAs()) {
                alsoKnownAsBuider.append(othername);
                alsoKnownAsBuider.append(SEPERATOR1);
            }
            String alsoKnownAs = alsoKnownAsBuider.toString();
            alsoKnownAs = alsoKnownAs.substring(0, alsoKnownAs.length() - SEPERATOR1.length());
            mAlsoKnownAsTv.setText(alsoKnownAs);
        } else {
            mAlsoKnownAsTv.setVisibility(View.GONE);
            mAlsoKnownAsLabelTv.setVisibility(View.GONE);
        }
        mPlaceofOrigin.setText(sandwich.getPlaceOfOrigin());
        if (!sandwich.getIngredients().isEmpty()) {
            StringBuilder ingredientBuider = new StringBuilder();
            for (String ingredient : sandwich.getIngredients()) {
                ingredientBuider.append(ingredient);
                ingredientBuider.append(SEPERATOR2);
            }
            String ingredient = ingredientBuider.toString();
            ingredient = ingredient.substring(0, ingredient.length() - SEPERATOR2.length());
            mIngredientsTv.setText(ingredient);
        }
        mDescriptionTv.setText(sandwich.getDescription());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_POSITION, mPosition);
        super.onSaveInstanceState(outState);
    }
}

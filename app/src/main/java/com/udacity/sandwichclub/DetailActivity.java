package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //UI Elements
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);
        LinearLayout alsoKnownLl = findViewById(R.id.also_known_ll);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView placeOfOriginTv = findViewById(R.id.origin_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);

        //If the also know as details are not available, hide the relative fields
        String alsoKnownAs = TextUtils.join(", ", sandwich.getAlsoKnownAs());
        if (alsoKnownAs.trim().isEmpty()){
            alsoKnownLl.setVisibility(View.GONE);
        }else{
            alsoKnownLl.setVisibility(View.VISIBLE);
            alsoKnownTv.setText(alsoKnownAs);

        }

        String ingredients = TextUtils.join(", ", sandwich.getIngredients());
        ingredientsTv.setText(ingredients);

        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());

        descriptionTv.setText(sandwich.getDescription());
    }


}

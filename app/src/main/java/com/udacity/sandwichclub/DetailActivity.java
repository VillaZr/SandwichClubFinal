package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        TextView alsoKnownAs = (TextView)findViewById(R.id.also_known_tv);
        TextView placeOfOrigin = (TextView)findViewById(R.id.origin_tv);
        TextView description = (TextView)findViewById(R.id.description_tv);
        TextView ingredients = (TextView)findViewById(R.id.ingredients_tv);


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

        StringBuilder sAlsoKnownAs = new StringBuilder();
        sAlsoKnownAs.setLength(0);
        List<String> lAlsoKnownAs = sandwich.getAlsoKnownAs();
        for (int i = 0; i < lAlsoKnownAs.size(); i++) {
            sAlsoKnownAs.append(lAlsoKnownAs.get(i));
        }

        StringBuilder sIngredients = new StringBuilder();
        sIngredients.setLength(0);
        List<String> lIngredients = sandwich.getIngredients();
        for (int i = 0; i < lIngredients.size(); i++) {
            sIngredients.append(lIngredients.get(i));
        }

        populateUI(sandwich, alsoKnownAs, sAlsoKnownAs,
                placeOfOrigin, description, ingredients,
                sIngredients);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());



        //description.setText(sandwich.getDescription());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich, TextView alsoKnownAs, StringBuilder sAlsoKnownAs,
                            TextView placeOfOrigin, TextView description, TextView ingredients,
                            StringBuilder sIngredients) {

        alsoKnownAs.setText(sAlsoKnownAs);
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        description.setText(sandwich.getDescription());
        ingredients.setText(sIngredients);
    }
}

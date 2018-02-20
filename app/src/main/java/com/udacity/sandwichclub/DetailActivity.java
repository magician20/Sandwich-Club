package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

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
        TextView alsoKnowAs = findViewById(R.id.also_known_tv);
        TextView placeOfOrigin = findViewById(R.id.origin_tv);
        TextView description = findViewById(R.id.description_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);



        //set alsoKnowAs
        if (sandwich.getAlsoKnownAs().size() > 0) {
            alsoKnowAs.setText(getStringFromList(sandwich.getAlsoKnownAs()));

        } else {
            alsoKnowAs.setText(R.string.data_unavailable_message);
        }

        //set placeOfOrigin

        placeOfOrigin.setText(!sandwich.getPlaceOfOrigin().isEmpty() ?
                sandwich.getPlaceOfOrigin() : getString(R.string.data_unavailable_message));

        //set description
        description.setText(sandwich.getDescription());

        //set ingredients
        ingredients.setText(getStringFromList(sandwich.getIngredients()));

    }

//i didn't use stringBuilder from begining because i thought it's overhead to create it
    public String getStringFromList(List<String> list) {
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < list.size(); i++) {
//            builder.append(list.get(i));
//            if (i != list.size() - 1) {
//                builder.append(", ");
//            }
//        }
//        return builder.toString();

        return  TextUtils.join(", ", list);
    }
}

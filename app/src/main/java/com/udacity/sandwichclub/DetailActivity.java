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

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //Views
    private TextView mAlsoKnownTextView;
    private TextView mIngredientsTextView;
    private TextView mOriginTextView;
    private TextView mDescriptionTextView;
    private TextView mLabelAlsoKnownAsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownTextView = findViewById(R.id.also_known_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mOriginTextView = findViewById(R.id.origin_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mLabelAlsoKnownAsTextView = findViewById(R.id.label_also_known_tv);

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
        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        if (alsoKnownList.isEmpty()) {
            mLabelAlsoKnownAsTextView.setVisibility(View.GONE);
            mAlsoKnownTextView.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < alsoKnownList.size(); i++) {
                mAlsoKnownTextView.append(alsoKnownList.get(i));
                if (i == alsoKnownList.size() - 1) {
                    mAlsoKnownTextView.append(".");
                } else {
                    mAlsoKnownTextView.append(", ");
                }
            }
        }
        mDescriptionTextView.setText(sandwich.getDescription());
        mOriginTextView.setText(sandwich.getPlaceOfOrigin());
        List<String> ingredients = sandwich.getIngredients();
        for (int i = 0; i < ingredients.size(); i++) {
            mIngredientsTextView.append(ingredients.get(i));
            if (i == ingredients.size() - 1) {
                mIngredientsTextView.append(".");
            } else {
                mIngredientsTextView.append(", ");
            }
        }
    }
}

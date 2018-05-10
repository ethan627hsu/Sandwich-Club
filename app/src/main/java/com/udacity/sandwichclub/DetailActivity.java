package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    //The sandwich that stores the current sandwich parsed
    Sandwich currentSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Finding the toolbar and enabling the back button
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //Adding up navigation to the application
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        currentSandwich = JsonUtils.parseSandwichJson(json);
        if (currentSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(currentSandwich.getImage())
                .into(ingredientsIv);

        setTitle(currentSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        //Getting the ids of the textviews that display sandwich data
        TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        TextView originTextView = findViewById(R.id.origin_tv);

        //If the item is empty, then set the text to be "N/A", else make the text a sandwich obj value

        //Comparision for the alsoKnownAs field
        if (currentSandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownAsTextView.setText(R.string.empty_field);
        } else {
            //Creating a string called cutString which will remove the two brackets that
            //the toString() method creates
            String cutString = currentSandwich.getAlsoKnownAs().toString();
            cutString = cutString.substring(1, cutString.length() - 1);
            alsoKnownAsTextView.setText(cutString);
        }

        //Comparision for the description field
        if (currentSandwich.getDescription().isEmpty()) {
            descriptionTextView.setText(R.string.empty_field);
        } else {
            descriptionTextView.setText(currentSandwich.getDescription().toString());
        }

        //Comparision for the ingredients field
        if (currentSandwich.getIngredients().isEmpty()) {
            ingredientsTextView.setText(R.string.empty_field);
        } else {

            //Creating a string called cutString which will remove the two brackets that
            //the toString() method creates
            String cutString = currentSandwich.getIngredients().toString();
            cutString = cutString.substring(1, cutString.length() - 1);
            ingredientsTextView.setText(cutString);
        }

        //Comparision for the origin field
        if (currentSandwich.getPlaceOfOrigin().isEmpty()) {
            originTextView.setText(R.string.empty_field);
        } else {
            originTextView.setText(currentSandwich.getPlaceOfOrigin());
        }
    }

    //Overriding onOptionsItemSelected() so we can go back to the list screen
    //when the back button is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //When the back button is pressed, we navigate to the main parent activity
            case android.R.id.home:
                launchMainActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Providing system back button navigation
    @Override
    public void onBackPressed() {
        launchMainActivity();
    }
    private void launchMainActivity(){
        //Creating a new intent with the current activity and the acitivity with the main list
        Intent launchIntent = new Intent(DetailActivity.this, MainActivity.class);
        //Then starting it
        startActivity(launchIntent);

    }
}

package com.example.tle.bakingapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Recipe> recipies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetJsonAsyncTask().execute();
    }

    private class GetJsonAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                return NetworkUtils.getResponseFromHttpUrl();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Main Activity", "Failed to get JSON", e);
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            recipies = parseJsonToRecipe(s);
        }
    }

    private List<Recipe> parseJsonToRecipe(String s) {
        List<Recipe> recipies = new ArrayList<>();
        return recipies;
    }
}

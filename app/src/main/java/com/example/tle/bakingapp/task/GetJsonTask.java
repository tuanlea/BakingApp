package com.example.tle.bakingapp.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tle.bakingapp.NetworkUtils;

import java.io.IOException;

public class GetJsonTask extends AsyncTask<Void, Void, String> {

    private final GetJsonTaskHandler getJsonTaskHandler;

    public GetJsonTask(GetJsonTaskHandler getJsonTaskHandler) {
        this.getJsonTaskHandler = getJsonTaskHandler;
    }

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
    protected void onPostExecute(String json) {
        super.onPostExecute(json);
        getJsonTaskHandler.handleJson(json);
    }
}

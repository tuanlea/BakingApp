package com.example.tle.bakingapp;

import android.net.Uri;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    final private static String BASE_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    /**
     *
     */
    public static String getResponseFromHttpUrl() throws IOException {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .build();
        URL url = new URL(builtUri.toString());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestMethod("GET");
        urlConnection.setInstanceFollowRedirects(true);
        urlConnection.connect();

        try {
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = urlConnection.getInputStream();
                return readStream(inputStream);
            }
        } finally {
            urlConnection.disconnect();
        }
        return "";
    }

    /**
     *
     */
    private static String readStream(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append('\n');
        }
        bufferedReader.close();

        return stringBuilder.toString();
    }

}

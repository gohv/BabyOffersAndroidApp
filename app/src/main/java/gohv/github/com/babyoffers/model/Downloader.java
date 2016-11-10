
package gohv.github.com.babyoffers.model;


import android.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import gohv.github.com.babyoffers.ui.MainActivity;


public class Downloader {
     private final String OFFERS_URL = "http://95.111.32.73:12345/offers";

    private final String test = "";

    private InputStream getStream(String urlString) {
        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(1000);
            return urlConnection.getInputStream();
        } catch (Exception ex) {


            throw new RuntimeException(ex);
        }
    }

    public Result getOffers(int start, int end) {
        try {
            String url = String.format("%s?s=%s&e=%s", OFFERS_URL, start, end);
            InputStream inputStream = getStream(url);
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            String out = "";
            String line = "";

            while (true) {
                line = bf.readLine();

                if (line == null) break;

                out += line;
            }

            return new Gson().fromJson(out, Result.class);

        } catch (IOException e) {
            Log.d("Connection", "Connection Error");
            e.printStackTrace();
        }

        return null;
    }

    public class Result {
        public List<Offer> offers;
        public int size;
    }


}

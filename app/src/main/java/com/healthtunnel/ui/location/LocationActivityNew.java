package com.healthtunnel.ui.location;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.healthtunnel.MainActivity;
import com.healthtunnel.R;
import com.healthtunnel.ui.location.util.PlaceDetailsJSONParser;
import com.healthtunnel.ui.location.util.PlaceJSONParser;
import com.healthtunnel.ui.utility.ClsGeneral;
import com.healthtunnel.ui.utility.Constant;
import com.healthtunnel.MainActivity;
import com.healthtunnel.ui.utility.ClsGeneral;
import com.healthtunnel.ui.utility.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocationActivityNew extends AppCompatActivity {

    private EditText searchLocation;
    private PlacesTask placesTask;
    private ParserTask parserTask;
    private ParserTask placeDetailsParserTask, placesParserTask;
    private final int PLACES = 0;
    final int PLACES_DETAILS = 1;
    private ArrayList<String> reference_id_list = new ArrayList<String>();
    private ArrayList<String> address_list = new ArrayList<String>();
    private RecyclerView locationRv;
    private DownloadTask placeDetailsDownloadTask;
    private LinearLayout currentLocationCL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        searchLocation = findViewById(R.id.searchLocation);
        locationRv = findViewById(R.id.locationRv);
        currentLocationCL = findViewById(R.id.currentLocationCL);

        currentLocationCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.Companion.setNewLocationClickedd(false);
                finish();
            }
        });


        searchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                placesTask = new PlacesTask();
                placesTask.execute(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private class PlacesTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... place) {
            // For storing data from web service
            String data = "";


            String key = "";
            if (Constant.serverKey != null && Constant.serverKey.length() > 0) {
                key = "key=" + Constant.serverKey;
            }


            String input = "";

            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            String types = "establishment|geocode&location=" + ClsGeneral.getPreferences(Constant.LATITUTE) + "," + ClsGeneral.getPreferences(Constant.LONGITUTE) + "&radius=500po&language=en";

            // Building the parameters to the web service
            String parameters = input + "&" + types + "&" + key;

            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/" + output + "?" + parameters;

            try {
                // Fetching the data from web service in background
                data = downloadUrl(url);
            } catch (Exception e) {
                // Log.d("Background Task",e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Creating ParserTask
            parserTask = new ParserTask(PLACES);

            // Starting Parsing the JSON string returned by Web Service
            parserTask.execute(result);
        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
        JSONObject jObject;

        int parserType = 0;

        public ParserTask(int type) {
            this.parserType = type;
        }

        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {
            List<HashMap<String, String>> places = null;

            try {

                if (jsonData[0] != null) {
                    jObject = new JSONObject(jsonData[0]);
                    switch (parserType) {
                        case PLACES:
                            PlaceJSONParser placeJsonParser = new PlaceJSONParser();
                            // Getting the parsed data as a List construct
                            places = placeJsonParser.parse(jObject);
                            break;
                        case PLACES_DETAILS:
                            PlaceDetailsJSONParser placeDetailsJsonParser = new PlaceDetailsJSONParser();
                            // Getting the parsed data as a List construct
                            places = placeDetailsJsonParser.parse(jObject);
                    }

                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                //Utilities.printLog("ParserTask Exception: "+e.toString());
            }

            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {
            // progressBar.setVisibility(View.GONE);

            switch (parserType) {
                case PLACES:

                    if (result != null) {

                        reference_id_list.clear();
                        address_list.clear();

                        for (int i = 0; i < result.size(); i++) {
                            address_list.add(result.get(i).get("description"));
                            reference_id_list.add(result.get(i).get("reference"));
                        }


                        locationRv.setAdapter(new AddressAdapterNew(address_list, pos -> {
                            placeDetailsDownloadTask = new DownloadTask(PLACES_DETAILS);

                            if (reference_id_list.size() > 0 && reference_id_list.get(pos) != null) {
                                String url = getPlaceDetailsUrl(reference_id_list.get(pos));
                                placeDetailsDownloadTask.execute(url);
                                return;
                            }
                        }));
                    }

                    break;
                case PLACES_DETAILS:

                    if (result != null && result.size() > 0) {
                        MainActivity.Companion.setNewLocationClickedd(true);
                        String latitude = result.get(0).get("lat");
                        String longitude = result.get(0).get("lng");

                        ClsGeneral.setPreferences(Constant.LATITUTE, latitude);
                        ClsGeneral.setPreferences(Constant.LONGITUTE, longitude);
                        finish();
                        break;
                    }
            }
        }

    }

    private String getPlaceDetailsUrl(String ref) {
        String key = "";
        if (Constant.serverKey != null && Constant.serverKey.length() > 0) {
            key = "key=" + Constant.serverKey;
        }

        String reference = "reference=" + ref;

        String sensor = "sensor=false";

        String parameters = reference + "&" + sensor + "&" + key;

        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/place/details/" + output + "?" + parameters;

        return url;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        private int downloadType = 0;

        // Constructor
        public DownloadTask(int type) {
            this.downloadType = type;
        }

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            switch (downloadType) {
                case PLACES:
                    // Creating ParserTask for parsing Google Places
                    placesParserTask = new ParserTask(PLACES);

                    placesParserTask.execute(result);

                    break;

                case PLACES_DETAILS:
                    placeDetailsParserTask = new ParserTask(PLACES_DETAILS);

                    placeDetailsParserTask.execute(result);
            }
        }
    }

}

package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    public static final String TAG = "QueryUtils :- ";



    public static URL createURL(String stringURL){
        URL url=null;
        try {
             url = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.i(TAG, "Malformed URL - Unable to retrive data");
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String JSONResponse = "";

        if(url == null)
            return JSONResponse;

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10 * 1000);
            httpURLConnection.setConnectTimeout(15 * 1000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                JSONResponse = readFromStream(inputStream);
            }else{
                Log.e(TAG,"Error Response Code : " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problems Retreving JSON Data from the internet - ", e);
        }finally {
            if(httpURLConnection !=null)
                httpURLConnection.disconnect();

            if(inputStream != null)
                inputStream.close();
        }
        return JSONResponse;
    }

    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream !=null){

            InputStreamReader reader = new InputStreamReader(inputStream, Charset.defaultCharset());
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = bufferedReader.readLine();
            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }

        }
        return output.toString();
    }


    private QueryUtils() {
    }


    public static List<EqData> extractFeatureFromJson(String earthquakeJson){
        if(TextUtils.isEmpty(earthquakeJson))
            return null;

        List<EqData> earthquakes = new ArrayList<>();

        try {
            JSONObject baseJsonObject = new JSONObject(earthquakeJson);
            JSONArray earthquakeArray = baseJsonObject.getJSONArray("features");

            for(int i=0; i<earthquakeArray.length(); i++){
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);

                JSONObject properties = currentEarthquake.getJSONObject("properties");

                Double magnitude = properties.getDouble("mag");

                String location = properties.getString("place");

                long time = properties.getLong("time");

                String url = properties.getString("url");

                EqData earthquake = new EqData(magnitude,location,time,url);

                earthquakes.add(earthquake);

            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "problems parsing JSON results", e);
        }

        return earthquakes;
    }

    public static List<EqData> fetchEarthquakeData(String requestUrl){
        URL url = createURL(requestUrl);
        String JsonResponse = null;

        try {
            JsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("QueryUtils", "Problem in making HTTP request",e);
        }

        List<EqData> earthquakes = extractFeatureFromJson(JsonResponse);

        return earthquakes;
    }

}
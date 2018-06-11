package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EqData>> {


    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private EarthQuakeAdapter adapter;
    private ArrayList<EqData> EqList;
    private ListView earthquakeListView;
    private TextView emptyView;

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&limit=50&minmag=12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Earthquake", "OnCreate of mainactivity is called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        ProgressBar loading_indicator = (ProgressBar) findViewById(R.id.loading);

        emptyView = (TextView) findViewById(R.id.emptyView);


        earthquakeListView = (ListView) findViewById(R.id.list);

        adapter = new EarthQuakeAdapter(EarthquakeActivity.this,new ArrayList<EqData>());

        if(networkInfo !=null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, this);
            Log.i("Earthquake","initLoader is called");
            earthquakeListView.setAdapter(adapter);
        }else{
            loading_indicator.setVisibility(View.GONE);
             emptyView.setText(R.string.NoInternet);
             earthquakeListView.setEmptyView(emptyView);
        }


        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                EqData currentEarthquake = adapter.getItem(position);
                Toast.makeText(EarthquakeActivity.this,"Opening in Browser ...", Toast.LENGTH_LONG).show();
                Intent openInBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(currentEarthquake.getUrl()));
                startActivity(openInBrowser);


            }
        });

    }
    @Override
    public Loader<List<EqData>> onCreateLoader(int i, Bundle bundle) {
        Log.i("Earthquake", "onCreateLoader() has been invoked");
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<EqData>> loader, List<EqData> eqData) {
        findViewById(R.id.loading).setVisibility(View.GONE);
        emptyView.setText(R.string.NoEarthquakes);

        earthquakeListView.setEmptyView(emptyView);

        Log.i("Earthquake", "onLoadFinished has been invoked");
        adapter.clear();
        if(eqData !=null && !eqData.isEmpty()) {
            adapter.addAll(eqData);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<EqData>> loader) {
        Log.i("Earthquake","onLoaderReset Has been invoked");
        adapter.clear();
    }

    @Override
    protected void onPause() {
        Log.i("Earthquake", "onPause() has been invoked");
        super.onPause();
    }
}

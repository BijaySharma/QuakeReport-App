package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<EqData>> {
    private String mUrl;
    public EarthquakeLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    public List<EqData> loadInBackground() {
        Log.i("Earthquake","loadInBackgrund() has been invoked");
        if(mUrl == null)
            return null;

        List<EqData> result = QueryUtils.fetchEarthquakeData(mUrl);

        return result;
    }

    @Override
    protected void onStartLoading() {
        Log.i("Earthquake","onStartLoading() has been invoked");

        forceLoad();
        super.onStartLoading();
    }
}

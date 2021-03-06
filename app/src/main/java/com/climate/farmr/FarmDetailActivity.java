package com.climate.farmr;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.climate.farmr.domain.Farm;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public class FarmDetailActivity extends AppCompatActivity {

    JSONObject session = null;
    double lat, log;
    public Farm farm = null;
    LatLng currentLocation;
    public String acres = "100";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farm_details);

        try {
            session = new JSONObject((String) getIntent().getExtras().get("SessionToken"));
            lat = getIntent().getExtras().getDouble("Lat");
            log = getIntent().getExtras().getDouble("Long");
            farm = (Farm) getIntent().getSerializableExtra("Farm");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        WebView farmDetailWebView = (WebView)this.findViewById(R.id.farmDetailsWebView);
        farmDetailWebView.getSettings().setJavaScriptEnabled(true);
        farmDetailWebView.addJavascriptInterface(new FarmDetailsJavaScriptInterface(this, farmDetailWebView), "MyHandler");
        farmDetailWebView.loadUrl("file:///android_asset/webviews/farm_profile/index.html");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        //farmDetailWebView.loadUrl("javascript:loaded()");
    }

}

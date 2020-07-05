package com.mvp.vsar_as;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView wv;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        wv = (WebView) findViewById(R.id.webView);
        wv.setWebViewClient(new WebViewClient());
        wv.setWebChromeClient(new WebChromeClient());

        wv.addJavascriptInterface(new WebVRInterface(this), "Navigator");
        wv.addJavascriptInterface(new AndroidInterface(this), "Android");

        WebSettings websettings = wv.getSettings();
        websettings.setJavaScriptEnabled(true);
        websettings.setAllowUniversalAccessFromFileURLs(true);
        websettings.setMediaPlaybackRequiresUserGesture(false);
        websettings.setDomStorageEnabled(true);
        websettings.setPluginState(WebSettings.PluginState.ON);

        wv.setWebChromeClient(new WebChromeClient(){
            // Need to accept permissions to use the camera
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.grant(request.getResources());
                }
            }

        });

        String site = "https://visar.co.za/three.js/examples/css3d_molecules.html";
        Uri uri = Uri.parse(site);
        wv.loadUrl(site);
        // wv.loadUrl("file:///android_asset/index.html");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv.loadUrl("https://lvr.visar.co.za");
            }
        });
        //setupEvents();
    }
     /*private void setupEvents() {
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                wv.loadUrl("https://visar.co.za/lvr");
            }
        });
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (wv.canGoBack()) {
            wv.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public class WebVRInterface {
        Context mContext;

        WebVRInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public String getVRDevices() {
            return "{\"devices\": []}";
        }

        @JavascriptInterface
        public String getState() {
            String x = String.format("%.3f", Math.random());
            String y = String.format("%.3f", Math.random());
            String z = String.format("%.3f", Math.random());
            String w = String.format("%.3f", Math.random());
            return "{\"hmd\": { \"orientation\": ["+x+","+y+","+z+","+w+"]}}";
        }

        @JavascriptInterface
        public int answer() {
            return 42;
        }
    }

    /*
    Android interface
    */
    public class AndroidInterface {
        Context mContext;

        AndroidInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }
}



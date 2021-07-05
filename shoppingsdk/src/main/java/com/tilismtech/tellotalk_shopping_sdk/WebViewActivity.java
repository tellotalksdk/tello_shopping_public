package com.tilismtech.tellotalk_shopping_sdk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    WebView webView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView1 = findViewById(R.id.webView1);
        Intent intent = getIntent();
        String id = intent.getStringExtra("videoUrl");
        Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();
       // webView1.loadUrl(id);
      //  https://www.youtube.com/watch?v=xsU14eHgmBg&t=1s&ab_channel=Electrostore
        String str = id;
        String result = str.substring(str.indexOf("=") + 1, str.indexOf("&"));

        String myYouTubeVideoUrl = "https://www.youtube.com/embed/"+result;
        //String myYouTubeVideoUrl = "https://www.youtube.com/embed/bGkd90PIMcQ";

        String dataUrl =
                "<html>" +
                        "<body>" +
                        "<h2>Video From YouTube</h2>" +
                        "<br>" +
                        "<iframe width=\"100%\" height=\"100%\" src=\""+myYouTubeVideoUrl+"\" frameborder=\"0\" allowfullscreen/>" +
                        "</body>" +
                        "</html>";

        WebSettings webSettings = webView1.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webView1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView1.getSettings().setLoadWithOverviewMode(true);
        webView1.getSettings().setUseWideViewPort(true);
        webView1.loadData(dataUrl, "text/html", "utf-8");
    }
}
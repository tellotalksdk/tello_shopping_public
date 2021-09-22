package com.tilismtech.tellotalk_shopping_sdk_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

public class WebViewActivity extends AppCompatActivity {

    WebView webView1;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView1 = findViewById(R.id.webView1);
        Intent intent = getIntent();
        String id = intent.getStringExtra("videoUrl");
        back = findViewById(R.id.back);
        // Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();
        // webView1.loadUrl(id);
        //  https://www.youtube.com/watch?v=xsU14eHgmBg&t=1s&ab_channel=Electrostore
        String str = id;
        //Toast.makeText(this, "" + str, Toast.LENGTH_SHORT).show();
        //String result = str.substring(str.indexOf("=") + 1, str.indexOf("&"));

        String result = str.substring(str.indexOf("=") + 1, 43);
        String myYouTubeVideoUrl = "https://www.youtube.com/embed/" + result + "?autoplay=1";
        //String myYouTubeVideoUrl = "https://www.youtube.com/embed/bGkd90PIMcQ";

        String dataUrl =
                "<html>" +
                        "<body>" +
                        "<br>" +
                        "<iframe width=\"100%\" height=\"100%\" src=\"" + myYouTubeVideoUrl + "\" frameborder=\"0\" allowfullscreen/>" +
                        "</body>" +
                        "</html>";

        WebSettings webSettings = webView1.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webView1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView1.getSettings().setLoadWithOverviewMode(true);
        webView1.getSettings().setUseWideViewPort(true);
        webView1.loadData(dataUrl, "text/html", "utf-8");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
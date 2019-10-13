package com.nizhal.lynkhacks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmSelection extends AppCompatActivity {

    Button searchDoc;
    WebView webview3;

    private class MyWebViewClient3 extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    String stream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        TextView tv = new TextView(getApplicationContext());
        Typeface typeface = ResourcesCompat.getFont(this, R.font.merlo_regular);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText(R.string.title_name_colored); // ActionBar title text
        tv.setTextSize(22);
        tv.setTextColor(Color.BLACK);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setTypeface(typeface);
        actionBar.setCustomView(tv);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        setContentView(R.layout.activity_confirm_selection);

        Intent intent = getIntent();
        stream = intent.getExtras().getString("stream");

        TextView selectionContent = findViewById(R.id.selectionContent);
        selectionContent.setText(stream);

        webview3 = findViewById(R.id.webView3);
        webview3.setWebViewClient(new ConfirmSelection.MyWebViewClient3());

        searchDoc = findViewById(R.id.searchDoc);
        searchDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openURLCheck();
                Intent i = new Intent(getBaseContext(), SearchingDoctor.class);
                i.putExtra("stream",stream);
                startActivity(i);
            }
        });
    }
    private void openURLCheck()
    {
        webview3.loadUrl("https://lyflinehack.herokuapp.com/checkdoc?&name=Shyam&location=Mylapore&docspl="+stream);
        webview3.requestFocus();
    }
}

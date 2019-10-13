package com.lynkhack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WifiP2pManager wifiP2pManager;
    WifiP2pManager.Channel wifip2pChannel;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void goToRequest(View view) {

        startActivity(new Intent(this,RequestHelpActivity.class));

    }

    public void goToOfflineApp(View view) {
        Intent i = getPackageManager().getLaunchIntentForPackage("org.drulabs.localdash");
        i.putExtra("FROM_LYNK","Connect with Naveen:8903877274");
        if (i!=null){
            startActivity(i);
        }

    }

    public void dashboard(View view) {
        startActivity(new Intent(MainActivity.this,DashboardActivity.class));
    }
}

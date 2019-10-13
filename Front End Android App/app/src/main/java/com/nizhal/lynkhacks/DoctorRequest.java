package com.nizhal.lynkhacks;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class DoctorRequest extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;

    private double s_longitude;
    private double s_latitude;

    private double b_longitude;
    private double b_latitude;

    InputStream is = null;
    String line = null;
    String result = null;
    StringBuilder sb;

    WebView webview,webview2;

    TextView user_name, user_address;

    String user;

    LinearLayout checkStatus, lin_over;

    Handler h = new Handler();
    int delay = 3000; //3 seconds
    Runnable runnable;

    int flag = 0;

    Marker marker1, marker2;
    Button acceptInvite, declineInvite;

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    private class MyWebViewClient2 extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public DoctorRequest() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_doctor_request, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        checkStatus = rootView.findViewById(R.id.checkStatus);
        user_name = rootView.findViewById(R.id.user_name);
        user_address = rootView.findViewById(R.id.user_address);
        lin_over = rootView.findViewById(R.id.lin_over);

        h.postDelayed(new Runnable() {
            public void run() {
                if(flag==0)
                {
                    checkStatus();
                }
                runnable=this;
                h.postDelayed(runnable, delay);
            }
        }, delay);

        webview = rootView.findViewById(R.id.webView);
        webview2 = rootView.findViewById(R.id.webView2);
        webview.setWebViewClient(new MyWebViewClient2());
        webview2.setWebViewClient(new MyWebViewClient());

        acceptInvite = rootView.findViewById(R.id.acceptInvite);
        declineInvite = rootView.findViewById(R.id.declineInvite);

        acceptInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openURLAccept();
            }
        });

        declineInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openURLDecline();
                lin_over.setVisibility(View.VISIBLE);
            }
        });

        return rootView;
    }

    private void openURLAccept()
    {
        webview.loadUrl("https://lyflinehack.herokuapp.com/approvalstatus?&status=0&name="+user);
        webview.requestFocus();
    }
    private void openURLDecline()
    {
        webview2.loadUrl("https://be-her-change.herokuapp.com/approvalstatus?&status=1&name="+user);
        webview2.requestFocus();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.styling));

            if (!success) {
                Log.e("MapsActivityRaw", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivityRaw", "Can't find style.", e);
        }

    }

    public void checkStatus()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://lyflinehack.herokuapp.com/userdetails");
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
            httpPost.setParams(httpParams);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
            Toast.makeText(getActivity().getBaseContext(),"Server Timed out",Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
            is.close();
        } catch (Exception e) {
            Toast.makeText(getActivity().getBaseContext(),"Server Timed out",Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
        }

        JSONObject jsonObject;
        try {
            JSONArray jArray = new JSONArray(result);
            if(jArray.length() > 0)
            {
                lin_over.setVisibility(View.INVISIBLE);
                checkStatus.setVisibility(View.VISIBLE);
                String sp = "";
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);

                    b_latitude = Double.parseDouble(json_data.getString("lat"));
                    b_longitude = Double.parseDouble(json_data.getString("long"));
                    String userName = json_data.getString("name");
                    String address = json_data.getString("address");
                    user = userName;
                    user_name.setText("Name - "+userName);
                    user_address.setText("Address - "+address);
                    LatLng latLng_b = new LatLng(b_latitude, b_longitude);
                    marker2 = mMap.addMarker(
                            new MarkerOptions()
                                    .position(latLng_b)
                                    .draggable(false)
                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_destn_pin))
                                    .title("Visitor Location"));

                    s_latitude = 13.049055;
                    s_longitude = 80.267574;
                    LatLng latLng = new LatLng(s_latitude, s_longitude);
                    marker1 = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .draggable(true)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_source_pin))
                            .title("Your Location"));

                    moveMarkerTo(marker1,marker2,latLng_b);
                    flag++;
                }
            }
            else
            {
                checkStatus.setVisibility(View.INVISIBLE);
            }

        } catch (JSONException e) {
            Toast.makeText(getActivity().getBaseContext(),"Server Timed out",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void moveMarkerTo(Marker m1,Marker m2,LatLng bs)
    {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        builder.include(m1.getPosition());
        builder.include(m2.getPosition());

        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.35); // offset from edges of the map 30% of screen
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(bs));
        mMap.animateCamera(cu);
    }

}

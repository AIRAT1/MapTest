package de.android.mapstodelete;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;

public class MainActivity extends FragmentActivity {
    private final int START_MAP_STATE_VALUE = 1;
    private final String LOG = "log";
    private Button btnTest;
    SupportMapFragment mapFragment;
    GoogleMap map;
    int mapState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTest = (Button) findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapState++;
                setMapType();
            }
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        map = mapFragment.getMap();

        if (savedInstanceState != null) {
            mapState = savedInstanceState.getInt("mapState");
        }else {
            mapState = START_MAP_STATE_VALUE;
        }
        Log.d(LOG, "onCreate " + mapState);
        setMapType();


//        // проверка доступности GooglePlayServices
//        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(
//                getApplicationContext());
//        if (resultCode == ConnectionResult.SUCCESS) {
//            map = mapFragment.getMap();
//        }

        if (map == null) {
            finish();
            return;
        }
        settings();
        init();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("mapState", mapState);
        Log.d(LOG, "savedInstanceState " + mapState);
        super.onSaveInstanceState(outState);
    }

    private void settings() {
        UiSettings settings = map.getUiSettings();
        settings.setAllGesturesEnabled(true);
        settings.setCompassEnabled(true);
        settings.setMyLocationButtonEnabled(true);
    }

    private void init() {

    }


    public void setMapType() {
        if (mapState > 3) {
            mapState = 1;
        }
        switch (mapState) {
            case 1:
                Log.d(LOG, "satellite " + mapState);
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case 2:
                Log.d(LOG, "hybrid " + mapState);
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case 3:
                Log.d(LOG, "terrain " + mapState);
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            default:
                break;
        }
    }
}
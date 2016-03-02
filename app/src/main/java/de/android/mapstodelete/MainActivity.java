package de.android.mapstodelete;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends FragmentActivity {

    SupportMapFragment mapFragment;
    GoogleMap map;
    final String TAG = "myLogs";
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i = 1;
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        map = mapFragment.getMap();
        if (map == null) {
            finish();
            return;
        }
        init();
    }

    private void init() {

    }


    public void onClickTest(View view) {
        if (i > 3) {
            i = 1;
        }
        switch (i) {
            case 1:
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                i++;
                break;
            case 2:
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                i++;
                break;
            case 3:
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                i++;
                break;
        }
    }
}
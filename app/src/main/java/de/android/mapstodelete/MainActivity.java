package de.android.mapstodelete;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

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
        // добавить все жесты
        settings.setAllGesturesEnabled(true);
        // добавить компас
        settings.setCompassEnabled(true);
        // определение местоположения девайса
        map.setMyLocationEnabled(true);
        // добавления кнопки перехода на текущие координаты
        settings.setMyLocationButtonEnabled(true);
        // показывать этажи
        map.setIndoorEnabled(true);
        // показывать 3d здания
        map.setBuildingsEnabled(true);
        // добавить жесты вращения
        settings.setRotateGesturesEnabled(true);
        // добавить кнопки зума
        settings.setZoomControlsEnabled(true);
        // добавить жесты зума
        settings.setZoomGesturesEnabled(true);
    }

    private void init() {
        // координаты нажатия
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d(LOG, "onMapClick: "
                        + latLng.latitude + ", " + latLng.longitude);
//                Toast.makeText(MainActivity.this, "onMapClick: "
//                        + latLng.latitude + ", " + latLng.longitude,
//                        Toast.LENGTH_SHORT).show();
            }
        });
        // координаты долгого нажатия
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.d(LOG, "onMapLongClick: "
                        + latLng.latitude + ", " + latLng.longitude);
//                Toast.makeText(MainActivity.this, "onMapLongClick: "
//                                + latLng.latitude + ", " + latLng.longitude,
//                        Toast.LENGTH_SHORT).show();
            }
        });
        // даные с камеры: куда смотрит, угол поворота, угол наклона, уровень зума
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition camera) {
                Log.d(LOG, "onCameraChange: "
                        + camera.target.latitude + ", "
                        + camera.target.longitude + ", "
                        + camera.bearing + ", "
                        + camera.tilt + ", "
                        + camera.zoom);
            }
        });
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

    public void onCameraUpdate(View view) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(- 27, 133))
                .zoom(5)
                .bearing(0)
                .tilt(20)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate, 2000, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Good work!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Error(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onMoveTo(View view) {
        // перемещение в область с рамкой в 100 пикселов вокруг
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(
                new LatLngBounds(new LatLng(- 39, 112), new LatLng(- 11, 154)), 100);

        // перемещение в указанную точку с зумом
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
//                new LatLng(50, 0), 10);

        // перемещение камеры на указанное количество пикселов
//        CameraUpdate cameraUpdate = CameraUpdateFactory.scrollBy(10, 20);

        // изменение текущего зума на указанную величину + приближение - удаление
//        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomBy(3);


        map.animateCamera(cameraUpdate);
        // мгновенное перемещение
//        map.moveCamera(cameraUpdate);
    }
}
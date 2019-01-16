package com.example.myopenweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;


public class Map extends AppCompatActivity {
    private MapView mapView;
    private Button boto, boto2;
    String OPEN_WEATHER_MAP_API = "275d21f98ce6515e48e663fce25dd1f6";
    private MapboxMap mapboxMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            Mapbox.getInstance(this, getString(R.string.token));

            setContentView(R.layout.map);

            mapView = (MapView) findViewById(R.id.mapView);
            mapView.onCreate(savedInstanceState);

            boto=(Button) findViewById(R.id.boto);
            boto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CameraPosition position = new CameraPosition.Builder()
                            .target(new LatLng(6.9218335, 79.7861645))
                            .zoom(10)
                            .tilt(20)
                            .build();
//https://www.google.com/maps/place/Colombo,+Sri+Lanka/@6.9218335,79.7861645,12z
// /data=!3m1!4b1!4m5!3m4!1s0x3ae253d10f7a7003:0x320b2e4d32d3838d!8m2!3d6.9270786!4d79.861243
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 3000);
                }
            });
            boto2=(Button) findViewById(R.id.boto2);
            boto2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CameraPosition position = new CameraPosition.Builder()
                            .target(new LatLng(41.3890464, 2.1454964))
                            .zoom(10)
                            .tilt(20)
                            .build();
//https://www.google.com/maps/place/Colombo,+Sri+Lanka/@6.9218335,79.7861645,12z
// /data=!3m1!4b1!4m5!3m4!1s0x3ae253d10f7a7003:0x320b2e4d32d3838d!8m2!3d6.9270786!4d79.861243
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 4000);
                }
            });

            //       https://www.google.com/maps/place/Institut+Escola+del+Treball/
            // @41.3890464,2.1454964,17z/data=!3m1!4b1!4m5!3m4!1s0x12a4a2847eeed3b5:0xfcbfd60966182d80!8m2!3d41.3890464!4d2.1476851
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(MapboxMap mapboxMap) {
                    Map.this.mapboxMap=mapboxMap;
                    mapboxMap.addMarker(new MarkerViewOptions()
                            .position(new LatLng(41.3890464, 2.1454964))
                            .title("EdT,Barcelona"));
                    mapboxMap.addMarker(new MarkerViewOptions()
                            .position(new LatLng(41.3868488, 2.1506006))
                            .title("Retornino,Barcelona"));
                    //https://www.google.com/maps/place/O'Retorno/@41.3868488,2.1506006,17z/
                    // data=!3m1!4b1!4m5!3m4!1s0x12a4a285cdbcd6a3:0xdf0da38df2664c6c!8m2!3d41.3868488!4d2.1527893

                    mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(@NonNull LatLng point) {
                            try {
                                String myUrl = "http://api.openweathermap.org/data/2.5/forecast?lat=" + String.valueOf( point.getLatitude()) +
                                        "&lon=" + String.valueOf(point.getLongitude()) + "&appid=" + OPEN_WEATHER_MAP_API;

                                // Log.d("test", ": "+myUrl);

                                //Toast.makeText(getBaseContext(),myUrl, Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(getBaseContext(), MainActivity.class);
                                /*Bundle bundle=new Bundle();
                                bundle.putFloat("Lat",Float.parseFloat(String.valueOf(point.getLatitude())));
                                bundle.putFloat("Lon",Float.parseFloat(String.valueOf(point.getLongitude())));
                                intent.putExtras(bundle);
*/
                                intent.putExtra("Lat",Float.parseFloat(String.valueOf(point.getLatitude())));
                                intent.putExtra("Lon",Float.parseFloat(String.valueOf(point.getLongitude())));
                                setResult(RESULT_OK,intent);
                                Log.d("teste", ": "+myUrl);

                                finish();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("test", "onMapClick: "+e.getMessage());
                            }
                        }
                    }) ;

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "onCreate: "+e.getMessage());
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }


    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}
        return super.onOptionsItemSelected(item);
    }
}
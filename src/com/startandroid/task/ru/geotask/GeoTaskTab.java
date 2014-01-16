package com.startandroid.task.ru.geotask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class GeoTaskTab extends FragmentActivity{

    TabHost tabs;
    EditText from, to;
    Button buttonFrom, buttonTo;
    ListView listView1, listView2;
    LatLngSerializable latLangSerialFrom, latLangSerialTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Remove title bar */
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_tab);

        /* Add two tabs */
        tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        tabs.addTab(spec.setContent(R.id.tab1).setIndicator("Откуда"));

        spec = tabs.newTabSpec("tag2");
        tabs.addTab(spec.setContent(R.id.tab2).setIndicator("Куда"));

        tabs.setCurrentTab(0);

        findViewById(R.id.dummy_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (latLangSerialFrom != null && latLangSerialTo != null){
                    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                    intent.putExtra("latLangSerialFrom", latLangSerialFrom);
                    intent.putExtra("latLangSerialTo", latLangSerialTo);
                    startActivity(intent);
                }else {
                    Toast.makeText(getBaseContext(),
                            "Необходимо выбрать Откуда строть маршрут и Куда!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.dummy_button_To).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (latLangSerialFrom != null && latLangSerialTo != null){
                    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                    intent.putExtra("latLangSerialFrom", latLangSerialFrom);
                    intent.putExtra("latLangSerialTo", latLangSerialTo);
                    startActivity(intent);
                }else {
                    Toast.makeText(getBaseContext(),
                            "Необходимо выбрать Откуда строть маршрут и Куда!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    /* Geocoder initialization && get location */
        final Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        buttonFrom = (Button) findViewById(R.id.buttonFrom);
        buttonFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            List<Address> addresses = null;

            from = (EditText) findViewById(R.id.from);

            try {
                assert from.getText() != null;
                addresses = geocoder.getFromLocationName(from.getText().toString(), 7);
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<AddressObjectHelper> addrFrom = new ArrayList<AddressObjectHelper>();

            execute(addresses, addrFrom);

            listView1 = (ListView) findViewById(R.id.listView1);
            assert tabs.getContext() != null;
            ArrayAdapter<AddressObjectHelper> adapterFrom = new ArrayAdapter<AddressObjectHelper>(tabs.getContext(),
                    android.R.layout.simple_list_item_1, addrFrom);
            listView1.setAdapter(adapterFrom);

                final List<Address> finalAddresses = addresses;
                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        MarkerOptions markerOptions = new MarkerOptions();
                        assert finalAddresses != null;
                        Address obj = finalAddresses.get((int) id);
                        double lo = obj.getLongitude();
                        double la = obj.getLatitude();

                        LatLng lngFrom = new LatLng(la, lo);
                        markerOptions.position(lngFrom);

                        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
                        if (status == ConnectionResult.SUCCESS) {

                            SupportMapFragment mapFragment;
                            Marker map;

                            mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.map);
                            map = mapFragment.getMap().addMarker(markerOptions);
                            cameraDrawing(lngFrom, mapFragment, map);

                            latLangSerialFrom = new LatLngSerializable(la, lo); //object for MapActivity
                        }
                    }
                });
            }
        });

        buttonTo = (Button) findViewById(R.id.buttonTo);
        buttonTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Address> addresses = null;

                to = (EditText) findViewById(R.id.to);

                try {
                    assert to.getText() != null;
                    addresses = geocoder.getFromLocationName(to.getText().toString(), 7);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                List<AddressObjectHelper> addrTo = new ArrayList<AddressObjectHelper>();

                execute(addresses, addrTo);

                listView2 = (ListView) findViewById(R.id.listView2);
                assert tabs.getContext() != null;
                ArrayAdapter<AddressObjectHelper> adapterFrom = new ArrayAdapter<AddressObjectHelper>(tabs.getContext(),
                        android.R.layout.simple_list_item_1, addrTo);
                listView2.setAdapter(adapterFrom);

                final List<Address> finalAddresses = addresses;
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        MarkerOptions markerOptions = new MarkerOptions();
                        assert finalAddresses != null;
                        Address obj = finalAddresses.get((int) id);
                        double lo = obj.getLongitude();
                        double la = obj.getLatitude();

                        LatLng lngTo = new LatLng(la, lo);
                        markerOptions.position(lngTo);

                        int status= GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
                        if (status == ConnectionResult.SUCCESS){

                            SupportMapFragment mapFragment1;
                            Marker map1;

                            mapFragment1 = (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.mapTo);
                            map1 = mapFragment1.getMap().addMarker(markerOptions);
                            cameraDrawing(lngTo, mapFragment1, map1);

                            latLangSerialTo = new LatLngSerializable(la, lo); //object for MapActivity
                        }
                    }
                });
            }
        });

    }

    /*
    *Animate camera to marker
    **/
    private void cameraDrawing(LatLng lng, SupportMapFragment mapFragment1, Marker map1) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(lng)
                .zoom(15)
                .bearing(45)
                .tilt(20)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mapFragment1.getMap().animateCamera(cameraUpdate);
        if (map1 == null) {
            finish();
            return;
        }
        init();
    }

    private void init() {

    }

    /*
    *Create array of objects(addresses founded by geocoder)
     */
    public List<AddressObjectHelper> execute(List<Address> addresses, List<AddressObjectHelper> addr) {
        int i = 0;
        if (addresses.size() > 0) {
            while (i < addresses.size()) {
                addr.add(new AddressObjectHelper(addresses.get(i).getAddressLine(0),
                        addresses.get(i).getAddressLine(1),
                        addresses.get(i).getAddressLine(2),
                        addresses.get(i).getAddressLine(3),
                        addresses.get(i).getLatitude(),
                        addresses.get(i).getLongitude()));

                Log.i("my_LOCATION", "addresses: " + addresses.get(i).getAddressLine(0) + "\n"
                        + addresses.get(i).getAddressLine(1) + "\n"
                        + addresses.get(i).getAddressLine(2) + "\n"
                        + addresses.get(i).getAddressLine(3) + "\n"
                        + addresses.get(i).getLatitude() + "\n"
                        + addresses.get(i).getLongitude() + "\n");
                i++;
            }
        }
        return addr;
    }

}

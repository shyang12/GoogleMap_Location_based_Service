package com.example.googlemap;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // 20.2.2
        GoogleMap googleMap = mapFragment.getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*
        // 20.2.3 특정 위치(카페베네신논현점) 중심 지도
        double latitude = 37.507630;
        double longitude = 127.026648;
        LatLng LOC = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions()
            .position(LOC)
            .title("카페베네신논현점"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LOC));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOC, 16));
        // 20.2.3
        */

        /*
        // 20.2.5. 마커의 색 변경
        double latitude = 37.507630;
        double longitude = 127.026648;
        LatLng LOC = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions()
                .position(LOC)
                .title("카페베네신논현점")
                .snippet("아메리카노 10% 할인")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LOC));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOC, 16));
        // 20.2.5
        */

        /*
        // 20.2.6. 마커 아이콘 변경
        double latitude = 37.507630;
        double longitude = 127.026648;
        LatLng LOC = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions()
                .position(LOC)
                .title("카페베네신논현점")
                .snippet("아메리카노 10% 할인")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.coffee)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LOC));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOC, 16));
        // 20.2.6
        */

        /*
        // 20.2.7. 다수 마커
        double latitude;
        double longitude;
        LatLng LOC;

        // 카페베네신논현점
        latitude = 37.507630;
        longitude = 127.026648;
        LOC = new LatLng(latitude, longitude);
        Marker mk1 = mMap.addMarker(new MarkerOptions()
                .position(LOC)
                .title("카페베네신논현점")
                .snippet("아메리카노 10% 할인")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.coffee)));

        // 카페베네강남대로점
        latitude = 37.499485;
        longitude = 127.026433;
        LOC = new LatLng(latitude, longitude);
        Marker mk2 = mMap.addMarker(new MarkerOptions()
                .position(LOC)
                .title("카페베네강남대로점")
                .snippet("대학생 20% 할인")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.coffee)));

        mk2.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LOC));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOC, 16));
        // 20.2.7
        */

        // 20.2.8
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                updateMap(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                alertStatus(provider);
            }

            public void onProviderEnabled(String provider) {
                alertProvider(provider);
            }

            public void onProviderDisabled(String provider) {
                checkProvider(provider);
            }
        };

        String locationProvider = LocationManager.NETWORK_PROVIDER;

        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        // 20.2.8
    }

    public void updateMap(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        final LatLng LOC = new LatLng(latitude, longitude);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LOC, 16));
        Marker mk = mMap.addMarker(new MarkerOptions()
                .position(LOC)
                .title("현재 위치"));
        mk.showInfoWindow();
    }

    public void checkProvider(String provider) {
        Toast.makeText(this, provider + "에 의한 위치서비스가 꺼져 있습니다. 켜주세요...", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    public void alertProvider(String provider) {
        Toast.makeText(this, provider + "서비스가 켜졌습니다!", Toast.LENGTH_LONG).show();
    }

    public void alertStatus(String provider) {
        Toast.makeText(this, "위치서비스가 "+provider+"로 변경되었습니다!", Toast.LENGTH_LONG).show();
    }
}

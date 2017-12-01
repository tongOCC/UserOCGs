package dreyna.tong.userocgs;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Connection;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String OCCRECYCLING="Orange Coast College Recycling Center";

    private Location mMyLocation;

    private GoogleApiClient mGoogleApiClient;
    //Last Location is the last latitude and longitude reported
    private Location mLastLocation;
    //Location requests are made every x seconds (we configure this)
    private LocationRequest mLocationRequest;

    private static final Double LATITUDE= 33.675513,LONGITUDE =-117.910844;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.MapFragment);
        mapFragment.getMapAsync(this);

    }

    public void toContactButton(View view) {
        Intent intent = new Intent(this,ContactActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap= googleMap;



        LatLng myPosition= new LatLng(LATITUDE, LONGITUDE);


        //ADD a custom maekr
        mMap.addMarker(new MarkerOptions().position(myPosition)
                .title(OCCRECYCLING+"\n2701 Fairview Rd, Costa Mesa, CA 92626").icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.map_marker)));
        CameraPosition cameraPosition= new CameraPosition.Builder()
                .target(myPosition).target(myPosition)
                .zoom(15.0f).build();
        CameraUpdate cameraUpdate= CameraUpdateFactory.newCameraPosition(cameraPosition);

        mMap.moveCamera(cameraUpdate);

    }
}

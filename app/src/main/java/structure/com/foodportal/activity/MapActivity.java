package structure.com.foodportal.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import structure.com.foodportal.R;
import structure.com.foodportal.databinding.ActivityMapBinding;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{
    ActivityMapBinding binding;
    SupportMapFragment mapFragment;
    private GoogleMap mMap;

    double latitude, longitude;
    String title, label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);

        if (getIntent().getExtras() != null) {
            title = getIntent().getExtras().getString("title");
            label = getIntent().getExtras().getString("label");
            latitude = getIntent().getExtras().getDouble("latitude");
            longitude = getIntent().getExtras().getDouble("longitude");

            binding.tvLocationName.setText(latitude + ", " + longitude + " - " + label);
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        binding.titlebar.resetView();
        binding.titlebar.setTitle(title);
        binding.titlebar.showBackButton(this);
        binding.titlebar.showTitlebar();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng(latitude, longitude);
//        googleMap.addMarker(new MarkerOptions().position(latLng).title(label));
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(label)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setMinZoomPreference(10);
    }
}

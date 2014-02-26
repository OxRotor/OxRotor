package edu.usna.oxcontroller;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class ManualActivity extends Activity {
	GoogleMap map;
	LatLng myLocation = new LatLng(38.978445,-76.492183);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        
        FragmentManager myFragmentManager = getFragmentManager();
        MapFragment myMapFragment = (MapFragment)myFragmentManager.findFragmentById(R.id.map);
        map = myMapFragment.getMap();
        
        updatePosition(myLocation);
    }
    public void updatePosition(LatLng pos){
    	CameraUpdate center= CameraUpdateFactory.newLatLng(pos);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(16);
        map.moveCamera(center);
        map.animateCamera(zoom);
    }
}
package edu.usna.oxcontroller;

import java.util.ArrayList;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AutoActivity extends Activity implements OnMapClickListener, OnMapLongClickListener, OnClickListener {
	
	GoogleMap map;
	Circle circle;
	ArrayList<LatLng> points;
	Button compute;
	CircleCalc c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);
        
        compute = (Button)findViewById(R.id.compute);
        compute.setOnClickListener(this);
        
        FragmentManager myFragmentManager = getFragmentManager();
        MapFragment myMapFragment = (MapFragment)myFragmentManager.findFragmentById(R.id.map);
        map = myMapFragment.getMap();
        
        points = new ArrayList<LatLng>();

        //map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.setOnMapClickListener(this);
        map.setOnMapLongClickListener(this);
    } 
    
    private static CircleCalc findSec(int n, PointCalc[] p, int m, PointCalc[] b)
	{
    	CircleCalc sec = new CircleCalc();

		// Compute the Smallest Enclosing CircleCalc defined by B
		if(m == 1)
		{
			sec = new CircleCalc(b[0]);
		}
		else if(m == 2)
		{
			sec = new CircleCalc(b[0], b[1]);
		}
		else if(m == 3)
		{
			return new CircleCalc( b[0], b[1], b[2]);
		}

		// Check if all the points in p are enclosed
		for(int i=0; i<n; i++)
		{
			if(sec.contain(p[i]) == 1)
			{
				// Compute B <--- B union P[i].
				b[m] = new PointCalc(p[i]);	
				// Recurse
				sec = findSec(i, p, m+1, b);
			}
		}
		return sec;
	}

	public void onMapClick(LatLng point) {
			map.addMarker(new MarkerOptions()
			.position(point));
			points.add(point);
		 }
	
	 @Override
	 public void onMapLongClick(LatLng point) {
		 CircleOptions circleOptions = new CircleOptions()
		  .center(point)   //set center
		  .radius(4000)   //set radius in meters
		  .fillColor(Color.TRANSPARENT)  //default
		  .strokeColor(Color.BLUE)
		  .strokeWidth(5);
		  
		  circle = map.addCircle(circleOptions);
	 }

	@Override
	public void onClick(View v) {
		if(v == compute){
			int size = points.size();
			
			PointCalc[] pts = new PointCalc[size];
			for(int i = 0; i < size; i++){
				pts[i] = new PointCalc(points.get(i));
			}
	
			PointCalc[] b = new PointCalc[3];
	
			CircleCalc c = findSec(size,pts,0,b);
			System.out.println("Center is located at: " + c.getCenter());
			System.out.println("Area is: " + c.getArea());
			System.out.println("Diameter is: " + c.getDiameter());
			System.out.println("Radius is: " + c.getRadius());
	
			  CircleOptions circleOptions = new CircleOptions()
		      		.center(c.getCenter())   //set center
		      		.radius(c.getRadius())   //set radius in meters
		      		.fillColor(Color.TRANSPARENT)  //default
		      		.strokeColor(Color.BLUE)
		      		.strokeWidth(5);
		      circle = map.addCircle(circleOptions);
		}		
	}
}
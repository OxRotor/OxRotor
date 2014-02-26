package edu.usna.oxcontroller;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AutoActivity extends Activity implements OnMapClickListener, OnMapLongClickListener, OnClickListener {
	final double earthRadius = 6378;
	GoogleMap map;
	Circle circle;
	ArrayList<LatLng> points;
	Button compute;
	CircleCalc c;
	Polygon polygon;
	double squareLength;
	LatLng myLocation = new LatLng(38.978445,-76.492183);

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

        map.setOnMapClickListener(this);
        map.setOnMapLongClickListener(this);
        
        updatePosition(myLocation);
    } 
    public void updatePosition(LatLng pos){
    	CameraUpdate center= CameraUpdateFactory.newLatLng(pos);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(16);
        map.moveCamera(center);
        map.animateCamera(zoom);
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
    private static LatLng[] SER(CircleCalc circ){
		LatLng[] answer = new LatLng[5];
		double radius = circ.getRadius()/1000;
		LatLng center = circ.getCenter();
		System.out.println("Center should be after: " + center);
		
		double diff = newPoint(center,90,radius);
		double cl = circ.getCenter().latitude;
		double clo = circ.getCenter().longitude;		
		diff = Math.toDegrees(diff);
		
		LatLng upright = new LatLng(cl+diff,clo+diff);
		LatLng downright = new LatLng(cl-diff,clo+diff);
		LatLng downleft = new LatLng(cl-diff,clo-diff);
		LatLng upleft = new LatLng(cl+diff,clo-diff);

		answer[0] = upright;
		answer[1] = downright;
		answer[2] = downleft;
		answer[3] = upleft;
		answer[4] = center;
		return answer;	
	}
    public static double newPoint(LatLng x, double b, double d){
    	double latitude = Math.toRadians(x.latitude);
    	double earthRadius = 6378;
    	
    	System.out.println("x.lat: " + latitude);
    	
    	double rad = Math.toRadians(d/earthRadius);
    	b = Math.toRadians(b);
    	
        double lat = Math.asin(Math.sin(latitude)*Math.cos(rad) + Math.cos(latitude)*Math.sin(rad)*Math.cos(b));       
        double secondpart = Math.toDegrees(Math.atan2( Math.sin(b) * Math.sin(rad) * Math.cos(latitude),Math.cos(rad) - Math.sin(latitude) * Math.sin(lat)));
        	
        return secondpart;
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
	 public double distanceTo(LatLng current, LatLng dest){
			double dLat = Math.toRadians(dest.latitude-current.latitude);
			double dLon = Math.toRadians(dest.longitude-current.longitude);
			double lat1 = Math.toRadians(current.latitude);
			double lat2 = Math.toRadians(dest.latitude);
			double R = earthRadius;
			
			double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	    	        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
	    	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
	    	return R * c;

	 }
	@Override
	public void onClick(View v) {
		if(v == compute){
			int size = points.size();
			boolean tooBig = false;
			boolean FOVparam = false;
			
			PointCalc[] pts = new PointCalc[size];
			for(int i = 0; i < size; i++){
				pts[i] = new PointCalc(points.get(i));
			}
	
			PointCalc[] b = new PointCalc[3];
	
			CircleCalc c = findSec(size,pts,0,b);
				/*
			  CircleOptions circleOptions = new CircleOptions()
		      		.center(c.getCenter())   //set center
		      		.radius(c.getRadius())   //set radius in meters
		      		.fillColor(Color.TRANSPARENT)  //default
		      		.strokeColor(Color.BLUE)
		      		.strokeWidth(5);
		      circle = map.addCircle(circleOptions);
		      */
		               
		      LatLng[] square = SER(c);
		    if(c.getRadius() > 5000){
		    	tooBig = true;
		    }
			PolygonOptions rectOptions = new PolygonOptions()
					.add(square[0],
						(square[1]),
						(square[2]),
						(square[3]),
						(square[0]));
				
				squareLength = distanceTo(square[0],square[1]);
				double FOV = squareLength/10;
				if(FOV > squareLength){
					FOVparam = true;
				}
				Search searcher = new Search(square[4],squareLength,FOV);
				ArrayList<LatLng> points = searcher.SquareSearch();
				
				PolylineOptions line = new PolylineOptions();
				for(int i = 0;i<points.size();i++){
					line.add(points.get(i));
				}
				
				rectOptions.strokeColor(Color.RED);
				rectOptions.fillColor(Color.TRANSPARENT);
				rectOptions.strokeWidth(2);
				
				if(tooBig == true){		
					Toast toast = Toast.makeText(getBaseContext(), "Make Search Area Smaller, KThanks Bye", Toast.LENGTH_LONG);
		            toast.show();
				}
				if(FOVparam == true){
					Toast toast = Toast.makeText(getBaseContext(), "The Field of View is larger than the search area. This is bad", Toast.LENGTH_LONG);
		            toast.show();
				}
				if(tooBig == false && FOVparam == false){
					Polyline polyline = map.addPolyline(line);
					polyline.setColor(Color.BLACK);
					polygon = map.addPolygon(rectOptions);
				}

		}		
	}
}
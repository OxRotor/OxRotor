package edu.usna.oxcontroller;
import java.util.ArrayList;

import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class Search {
        /** Performs a search of a square.  The square is regularized so
          * demensions are in terms of the field of view diameter.  The search
          * is a counterclockwise square spiral begining at the center of the
          * square and begins by moving right 1 F.O.V. diameter.
          *
          * @param sideLength the length of the square
          * @return the multiples of the field of view the search should take */
		/*
		 * These directions are right, up, left, right in radians
		 */
		public final static double[] directions = {Math.toRadians(90),Math.toRadians(0),Math.toRadians(270),Math.toRadians(180)};
		private LatLng center;
		private double sideLength;
		private double FOV;
		public Search(LatLng center,double sideLength,double FOV){
			this.center = center;
			this.sideLength = sideLength;
			this.FOV = FOV;
		}	
        public ArrayList<LatLng> SquareSearch() {
                ArrayList<LatLng> moves = new  ArrayList<LatLng>();
                moves.add(center); //Add the center of the square as the starting point
                int counter = 0; //This counter keeps tabs of the available moves
                LatLng current = center; //The current LatLng is where we currently are in the search
                /*
                 * Each iteration through the loop will add two points to the search
                 * These two points will have the same distance
                 * This is a characteristic of a "roomba search"
                 * 
                 *    D          C
                 * 
                 * 
                 *         A     B
                 * 
                 *         
                 *    E                    F  
                 *    
                 * A: The starting point
                 * B: The 1st iteration first point
                 * C: The 1st iteration second point
                 * D: The 2nd iteration first point
                 * E: The 2nd iteration second point
                 * F: The 3rd iteration first point
                 */
                LatLng first = new LatLng(0,0); //initialize the variable
                LatLng second = new LatLng(0,0); //initialize
                
                for(int i = 1;(i*FOV) <= sideLength;i++){
                	if(counter > 3){
                		counter = 0;
                	}
                	first = searchHelper(current,directions[counter],i*FOV);
                	second = searchHelper(first,directions[counter+1],i*FOV);
                	counter = counter + 2;            	
                	current = second;
                	
                	moves.add(first);
                	moves.add(second);
                }
                return moves;
        }
        public LatLng searchHelper(LatLng x, double bearing, double d){
        	double latitude = Math.toRadians(x.latitude);
        	double earthRadius = 6378;     	
        	double b = Math.toRadians(90);
        	double rad = Math.toRadians(d/earthRadius);
   	
            double lat = Math.asin(Math.sin(latitude)*Math.cos(rad) + Math.cos(latitude)*Math.sin(rad)*Math.cos(b));        
            double secondpart = Math.toDegrees(Math.atan2( Math.sin(b) * Math.sin(rad) * Math.cos(latitude),Math.cos(rad) - Math.sin(latitude) * Math.sin(lat)));        
            secondpart = Math.toDegrees(secondpart);
            LatLng dest = new LatLng(0,0);

            if(bearing == Math.toRadians(90)){	
        		dest = new LatLng(x.latitude,x.longitude+secondpart);
            }
            if(bearing == Math.toRadians(180)){	
        		dest = new LatLng(x.latitude-secondpart,x.longitude);
            }
            if(bearing == Math.toRadians(270)){	
        		dest = new LatLng(x.latitude,x.longitude-secondpart);
            }
            if(bearing == Math.toRadians(0)){	
            	dest = new LatLng(x.latitude+secondpart,x.longitude);
            }         
            return dest;
        }
}

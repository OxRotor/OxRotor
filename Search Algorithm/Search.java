import java.util.ArrayList;

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
		
        public static ArrayList<Point> SquareSearch(double sideLength,double FOV,Point center) {
                ArrayList<Point> moves = new  ArrayList<Point>();
                moves.add(center);
                int multiple = 1; // multiple of F.O.V. used in movements

                // Each addition to 'moves' represents the scale of a movement
                /*
                while (multiple <= sideLength) {
                        moves.add(multiple);
                        moves.add(multiple);
                        multiple++;
                }
                */
                int counter = 0;
                Point current = center;
                Point first = new Point();
                Point second = new Point();
                for(int i = 1;(i*FOV) <= sideLength;i++){
                	if(counter > 3){
                		counter = 0;
                	}
                	first = newPoint(current,directions[counter],i*FOV);
                	second = newPoint(first,directions[counter+1],i*FOV);
                	counter = counter + 2;            	
                	current = second;
                	
                	moves.add(first);
                	moves.add(second);
                }

                // ensures the entire search area is scanned
                //multiple--;
                //moves.add(multiple);

                return moves;
        }
        public static Point newPoint(Point x, double bearing, double d){
        	double latitude = Math.toRadians(x.getX());
        	double earthRadius = 6378;
        	System.out.println("Distance traveling is: " + d);
        	
        	//System.out.println("x.lat: " + latitude);
        	
        	double b = Math.toRadians(90);
        	double rad = Math.toRadians(d/earthRadius);
        	//b = Math.toRadians(b);      	
            double lat = Math.asin(Math.sin(latitude)*Math.cos(rad) + Math.cos(latitude)*Math.sin(rad)*Math.cos(b));        
            double secondpart = Math.toDegrees(Math.atan2( Math.sin(b) * Math.sin(rad) * Math.cos(latitude),Math.cos(rad) - Math.sin(latitude) * Math.sin(lat)));
            //double lon = Math.toDegrees(longitude) + Math.toDegrees(secondpart);
            
            secondpart = Math.toDegrees(secondpart);
            Point dest = new Point();
            System.out.println("Current Bearing is: " + Math.toDegrees(b));
            if(bearing == Math.toRadians(90)){	
        		dest = new Point(x.getX(),x.getY()+secondpart);
            }
            if(bearing == Math.toRadians(180)){	
        		dest = new Point(x.getX()-secondpart,x.getY());
            }
            if(bearing == Math.toRadians(270)){	
        		dest = new Point(x.getX(),x.getY()-secondpart);
            }
            if(bearing == Math.toRadians(0)){	
            	dest = new Point(x.getX()+secondpart,x.getY());
            }         
            return dest;
        }
        public static void main(String[] args){
        	Point current = new Point(30,40);
        	ArrayList<Point> moves = SquareSearch(20,1,current);
        	for(int i = 0;i<moves.size();i++){
        		System.out.println(moves.get(i));
        	}
        }
}

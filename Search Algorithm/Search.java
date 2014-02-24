import java.util.ArrayList;

public class Search {
        /** Performs a search of a square.  The square is regularized so
          * demensions are in terms of the field of view diameter.  The search
          * is a counterclockwise square spiral begining at the center of the
          * square and begins by moving right 1 F.O.V. diameter.
          *
          * @param sideLength the length of the square
          * @return the multiples of the field of view the search should take */
        public static ArrayList<Integer> SquareSearch(int sideLength) {
                ArrayList<Integer> moves = new  ArrayList<Integer>();
                int multiple = 1; // multiple of F.O.V. used in movements

                // Each addition to 'moves' represents the scale of a movement
                while (multiple <= sideLength) {
                        moves.add(multiple);
                        moves.add(multiple);
                        multiple++;
                }

                // ensures the entire search area is scanned
                multiple--;
                moves.add(multiple);

                return moves;
        }
}

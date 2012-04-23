import java.util.*;

class GrowingTree
{

    static Random rand = new Random();

    private static final char WALL = '#'; //indicates a wall in the maze
    private static final char PATH = ' '; //indicates a path in the maze

    int rows;      //number of rows in the representative feild
    int cols;      //cols in the repres. feild

    int act_cols;  //actual cols in the maze
    int act_rows;  //actual rows in the maze

    ArrayList<Integer[]> visited = new ArrayList<Integer[]>(); /* set of
                                                                  visited cells
                                                                */

    char[][] feild; //representative feild of the maze

    
    public GrowingTree(int nRows, int nCols)
    {
        act_cols = nCols;
        act_rows = nRows;

        rows = 2*nRows+1;
        cols = 2*nCols+1;

        feild = new char[rows][cols];

        /* set the feild to filled */
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                feild[i][j] = WALL;
            }
        }

    }
    
    public void makeMaze()
    {
        Integer[] location;
        int[]     cuttable;

        setInitialLocation();

        while(visited.size() != 0){


            /* get a random location from the visited list */
            location = visited.get(rand.nextInt(visited.size()));

            cuttable = canCut(location);
            cuttable = mix(cuttable);

            if(cuttable[0] == 0)
                visited.remove(location);

            else{
                switch(cuttable[0]){
                    case 1:
                        cutNextUp(location);
                        break;
                    case 2:
                        cutNextRight(location);
                        break;
                    case 3:
                        cutNextDown(location);
                        break;
                    case 4:
                        cutNextLeft(location);
                        break;
                }
            }
        }

        makeOpenings();
    }

    private void setInitialLocation()
    {
        Integer[] temp = new Integer[2];    //to add to the list

        /*picks a random starting location.  only odd locations are valid */
        int startRowIndex = rand.nextInt(act_rows-1) * 2 + 1;
        int startColIndex = rand.nextInt(act_cols-1) * 2 + 1;

        temp[0] = startRowIndex;
        temp[1] = startColIndex;

        /* adds the starting index to the list */
        visited.add(temp);

        /* set the starting index to a path */
        feild[startRowIndex][startColIndex] = PATH;
    }

    private int[] canCut(Integer[] temp)
    {
        int[] cut = new int[4];   //and array of the directions able to be cut
        int place =0;             //number of directions that can be cut into

        //check to see if up is a valid direction
        if(canUp(temp) != 0){
            cut[place] = canUp(temp);
            place++;
        }
        //check to see if right is a valid direction
        if(canRight(temp) != 0){
            cut[place] = canRight(temp);
            place++;
        }
        //check to see if down is a valid direction
        if(canDown(temp) != 0){
            cut[place] = canDown(temp);
            place++;
        }
        //check to see if left is a valid direction
        if(canLeft(temp) != 0){
            cut[place] = canLeft(temp);
            place++;
        }

        //return array full of 0 is there are no valid directions
        if(place == 0){
            for(int i = 0; i<4; i++){
                cut[i] = 0;
            }
            return cut;

        } else { //otherwise trim the array to the right length and return it
            int[] cancut = new int[place];
            for(int i=0; i<place; i++){
                cancut[i] = cut[i];
            }

            return cancut;
        }
    }

    private int canUp(Integer[] current)
    {

        int nxt_row = current[0]-2;  //next location
        int nxt_col = current[1];

        //if next location is in the array and not already cut, can cut it
        if(nxt_row < 0 || feild[nxt_row][nxt_col] == PATH){
            return 0;
        }else{

            return 1; //1 corresponds to up
        }
    }

    private int canDown(Integer[] current)
    {


        int nxt_row = current[0]+2;  //next location
        int nxt_col = current[1];

        //if the next location is in the array and not already cut, can cut it
        if(nxt_row > rows-1 || feild[nxt_row][nxt_col] == PATH){
            return 0;
        }else{
            return 3; //3 corresponds to down
        }
    }

    private int canRight(Integer[] current)
    {

        int nxt_row = current[0];   //next location
        int nxt_col = current[1]+2;

        //if the next location is in the array and not already cut, can cut it
        if(nxt_col > cols-1 || feild[nxt_row][nxt_col] == PATH){
            return 0;
        }else{
            return 2; //2 corresponds to right
        }
    }

    private int canLeft(Integer[] current)
    {

        int nxt_row = current[0];   //next location
        int nxt_col = current[1]-2;

        //if next location is in the array and not already cut, can cut it
        if(nxt_col < 0 || feild[nxt_row][nxt_col] == PATH){
            return 0;
        }else{
            return 4; //4 corresponds to left
        }
    }

    private int cutNextUp(Integer[] current)
    {
        Integer[] loc = new Integer[2];     //temp var to access the stack

        int nxt_row = current[0]-2;   //the location of the next row index
        int nxt_col = current[1];     //locaiton of next col index


        //sets the next index and the wall between it to blank
        feild[current[0]-1][current[1]] = PATH;
        feild[current[0]-2][current[1]] = PATH;

        loc[0] = nxt_row;
        loc[1] = nxt_col;

        //adds the new index to the stack
        visited.add(loc);

        return 1;
    }


    private int cutNextDown(Integer[] current)
    {
        Integer[] loc = new Integer[2];   //temp variable to access stack

        int nxt_row = current[0]+2; //locaiton of next row and col
        int nxt_col = current[1];



        //clears the next index and the wall between it
        feild[current[0]+1][current[1]] = PATH;
        feild[current[0]+2][current[1]] = PATH;

        loc[0] = nxt_row;
        loc[1] = nxt_col;

        //adds new index to stack
        visited.add(loc);

        return 1;
    }


    private int cutNextRight(Integer[] current)
    {
        Integer[] loc = new Integer[2]; //dummy variable to access stack

        int nxt_row = current[0];   //location of next row and col
        int nxt_col = current[1]+2;



        //clears the necessary locaitons
        feild[current[0]][current[1]+1] = PATH;
        feild[current[0]][current[1]+2] = PATH;

        loc[0] = nxt_row;
        loc[1] = nxt_col;

        //adds index to stack
        visited.add(loc);

        return 1;
    }


    private int cutNextLeft(Integer[] current)
    {
        Integer[] loc = new Integer[2];  //temp varaible to access stack

        int nxt_row = current[0];   //location of next row and col
        int nxt_col = current[1]-2;


        //clears the necessary locaitons
        feild[current[0]][current[1]-1] = PATH;
        feild[current[0]][current[1]-2] = PATH;

        loc[0] = nxt_row;
        loc[1] = nxt_col;

        //adds new index to stack
        visited.add(loc);

        return 1;
    }

    private static int[] mix(int[] arr)
    {

        int temp;                   //a temp variable for swapping
        int place1;                 //one location to be swapped
        int place2;

        //shuffle seven times
        for(int i=0; i<7; i++){

            //pick two random indices of the array
            place1 = rand.nextInt(arr.length);
            place2 = rand.nextInt(arr.length);

            //swap the two random indices
            temp = arr[place1];
            arr[place1] = arr[place2];
            arr[place2] = temp;
        }

        return arr;
    }

    //getter
    public char[][] getMaze()
    {
        return feild;
    } //end getter

    public void printMaze()
    {

        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                System.out.print(feild[i][j]);
            }
            System.out.println();
        }

    }

    public void makeOpenings(){


        //a random location for the entrance and exit
        int entrance_row = rand.nextInt(act_rows-1) * 2 +1;
        int exit_row = rand.nextInt(act_rows-1) * 2 +1;

        //clear the location
        feild[entrance_row][0] = PATH;
        feild[exit_row][cols-1] = PATH;

    }



}

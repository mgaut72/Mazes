import java.util.*;

class Backtracker
{

    /* constants */

    static final char MAZE_WALL = '#';
    static final char MAZE_PATH = ' ';


    /*instance variables*/

    private char[][] feild;   //The feild we are cutting into

    private ArrayDeque<Integer[]> tracker; //stack to trace location


    private int rows;         //number of rows in the representative 2r+1 array
    private int cols;         //number of cols in the representative 2c+1 array
    private int act_rows;      //number of rows in the real maze
    private int act_cols;      // ""       cols    ""


    //constructor
    public Backtracker(int row, int col)
    {
        act_rows = row;
        act_cols = col;
        rows = 2*row+1;
        cols = 2*col+1;

        feild = new char[rows][cols];   //initializes the feild to proper size

        tracker = new ArrayDeque<Integer[]>(rows*cols);
        //initialize tracker to ample size


        //setting the inside to filled
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                feild[i][j] = MAZE_WALL;
            }
        }

    }//end constructor


    //actual implementation of the algorithm
    public void makeMaze()
    {


        int[] cut_order;//an array that gets all the possible motions
                        //to which can be cut into

        int not_done;     /*a variable that is positive when there are items
                           *in the stack, and negative when the stack of
                           *locations is empty
                           */


        start();     //picks a random location to start building the maze

        not_done = stackPeek();  /*initializes the looping condition now
                                  *that there is a location (the start)
                                  *in the stack
                                  */

        //loops while there are locations in the stack
        while(not_done > 0){
            cut_order = canCut();  //gets the availible locations



            //if there are directions to cut into
            if(cut_order[0] != 0){

                cut_order = mix(cut_order); //shuffle the availible directions

                //take the first direction to be cut into after shuffling and
                //cut into it.  like shuffling a deck of cards and drawing the
                //top card
                if(cut_order[0] == 1){
                    cutNextUp();
                }else if(cut_order[0] == 2){
                    cutNextRight();
                }else if(cut_order[0] == 3){
                    cutNextDown();
                }else if(cut_order[0] == 4){
                    cutNextLeft();
                }
            }else{  //if there are no directions to cut, back up one locaiton
                back();
            }


            not_done = stackPeek();  //are the more locations in stack?
        }

        makeOpenings();

    }


    //getter
    public char[][] getMaze()
    {
        return feild;
    } //end getter



    //starts making the maze at a random location
    private void start()
    {

        Random rand = new Random();     //initialize random number generator
        Integer[] loc = new Integer[2]; //a temp array to access the stack

        //pick a random start location.  the location must be odd to be valid
        int start_row_index = rand.nextInt(act_rows-1) * 2 + 1;
        int start_col_index = rand.nextInt(act_cols-1) * 2 + 1;

        loc[0] = start_row_index;
        loc[1] = start_col_index;

        tracker.addFirst(loc);

        //clears the start point
        feild[start_row_index][start_col_index] = MAZE_PATH;

    }


    //check to see if there are more locations in the tracker
    private int stackPeek()
    {
        if(tracker.peekFirst() == null)
            return -1;

        return 1;
    }



    private int[] canCut()
    {
        int[] cut = new int[4];   //and array of the directions able to be cut
        int place =0;             //number of directions that can be cut into

        //check to see if up is a valid direction
        if(canUp() !=   0){
            cut[place] = canUp();
            place++;
        }
        //check to see if right is a valid direction
        if(canRight() != 0){
            cut[place] = canRight();
            place++;
        }
        //check to see if down is a valid direction
        if(canDown() != 0){
            cut[place] = canDown();
            place++;
        }
        //check to see if left is a valid direction
        if(canLeft() != 0){
            cut[place] = canLeft();
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

    private int canUp()
    {
        Integer[] current = tracker.peekFirst(); //current location

        int nxt_row = current[0]-2;  //next location
        int nxt_col = current[1];

        //if next location is in the array and not already cut, can cut it
        if(nxt_row < 0 || feild[nxt_row][nxt_col] == MAZE_PATH){
            return 0;
        }else{

            return 1; //1 corresponds to up
        }
    }

    private int canDown()
    {

        Integer[] current = tracker.peekFirst(); //current location

        int nxt_row = current[0]+2;  //next location
        int nxt_col = current[1];

        //if the next location is in the array and not already cut, can cut it
        if(nxt_row > rows-1 || feild[nxt_row][nxt_col] == MAZE_PATH){
            return 0;
        }else{
            return 3; //3 corresponds to down
        }
    }

    private int canRight()
    {
        Integer[] current = tracker.peekFirst(); //current location

        int nxt_row = current[0];   //next location
        int nxt_col = current[1]+2;

        //if the next location is in the array and not already cut, can cut it
        if(nxt_col > cols-1 || feild[nxt_row][nxt_col] == MAZE_PATH){
            return 0;
        }else{
            return 2; //2 corresponds to right
        }
    }

    private int canLeft()
    {
        Integer[] current = tracker.peekFirst(); //current location

        int nxt_row = current[0];   //next location
        int nxt_col = current[1]-2;

        //if next location is in the array and not already cut, can cut it
        if(nxt_col < 0 || feild[nxt_row][nxt_col] == MAZE_PATH){
            return 0;
        }else{
            return 4; //4 corresponds to left
        }
    }


    private int cutNextUp()
    {
        Integer[] current = tracker.peekFirst(); //gets the current location
        Integer[] loc = new Integer[2];     //temp var to access the stack

        int nxt_row = current[0]-2;   //the location of the next row index
        int nxt_col = current[1];     //locaiton of next col index


        //sets the next index and the wall between it to blank
        feild[current[0]-1][current[1]] = MAZE_PATH;
        feild[current[0]-2][current[1]] = MAZE_PATH;

        loc[0] = nxt_row;
        loc[1] = nxt_col;

        //adds the new index to the stack
        tracker.addFirst(loc);

        return 1;
    }


    private int cutNextDown()
    {
        Integer[] current = tracker.peekFirst(); //gets the current locaiton
        Integer[] loc = new Integer[2];   //temp variable to access stack

        int nxt_row = current[0]+2; //locaiton of next row and col
        int nxt_col = current[1];



        //clears the next index and the wall between it
        feild[current[0]+1][current[1]] = MAZE_PATH;
        feild[current[0]+2][current[1]] = MAZE_PATH;

        loc[0] = nxt_row;
        loc[1] = nxt_col;

        //adds new index to stack
        tracker.addFirst(loc);

        return 1;
    }


    private int cutNextRight()
    {
        Integer[] current = tracker.peekFirst(); //gets the current locaiton
        Integer[] loc = new Integer[2]; //dummy variable to access stack

        int nxt_row = current[0];   //location of next row and col
        int nxt_col = current[1]+2;



        //clears the necessary locaitons
        feild[current[0]][current[1]+1] = MAZE_PATH;
        feild[current[0]][current[1]+2] = MAZE_PATH;

        loc[0] = nxt_row;
        loc[1] = nxt_col;

        //adds index to stack
        tracker.addFirst(loc);

        return 1;
    }


    private int cutNextLeft()
    {
        Integer[] current = tracker.peekFirst(); //gets current locaiton
        Integer[] loc = new Integer[2];  //temp varaible to access stack

        int nxt_row = current[0];   //location of next row and col
        int nxt_col = current[1]-2;


        //clears the necessary locaitons
        feild[current[0]][current[1]-1] = MAZE_PATH;
        feild[current[0]][current[1]-2] = MAZE_PATH;

        loc[0] = nxt_row;
        loc[1] = nxt_col;

        //adds new index to stack
        tracker.addFirst(loc);

        return 1;
    }



    private void back()
    {
        tracker.removeFirst();
    }




    public void makeOpenings(){

        Random rand = new Random(); //two different random number generators
        Random rand2 = new Random();//just in case

        //a random location for the entrance and exit
        int entrance_row = rand.nextInt(act_rows-1) * 2 +1;
        int exit_row = rand2.nextInt(act_rows-1) * 2 +1;

        //clear the location
        feild[entrance_row][0] = MAZE_PATH;
        feild[exit_row][cols-1] = MAZE_PATH;

    }



    private static int[] mix(int[] arr)
    {

        Random rand = new Random(); //a random number generator
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

    public void printMaze()
    {

        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                System.out.print(feild[i][j]);
            }
            System.out.println();
        }

    }

}

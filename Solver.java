import java.util.*;

public class Solver
{

    char[][] maze;  //the 2D array that represents the maze

    ArrayDeque<Integer[]> path;  //stack used to track the location

    int rows;  //the rows in the maze
    int cols;  //the cols in the maze


    public Solver(char[][] feild)
    {
        //initializes the maizesolver array to the length of the inputted array
        maze = new char[feild.length][feild[1].length];

        //copies the contents of the input array to the maizesolver array
        for(int i=0; i<feild.length; i++){
            for(int j=0; j<feild[i].length; j++){
                maze[i][j] = feild[i][j];
            }
        }

        //sets the rows and cols instance variables
        rows = feild.length;
        cols = feild[0].length;

        //initializes the stack that tracks the path of the maze to a big size
        path = new ArrayDeque<Integer[]>(rows*cols);

    }//end constructor



    /*
     *  getter
     *
     *  returns the maizesolver 2D array
     */

    public char[][] getMaze()
    {
        return maze;
    }

    public void setInitialLocation()
    {

        Integer[] temp = new Integer[2]; //a temp array to access the Deque


        //loops through looking for the entrance in the 0th column
        for(int i=0; i<maze.length; i++)
        {
            //if it finds a blank location, its the entrance
            if(maze[i][0] == ' '){

                //set temp to the current location
                temp[0] = i;
                temp[1] = 0;

                //changes the location to '-' to signify the tracker was there
                maze[temp[0]][temp[1]] = '-';

                //push the location to the Deque
                path.addFirst(temp);

            }
        }
    }


    public int canSolveUp()
    {
        Integer[] temp = new Integer[2]; //temp array to access the stack
        temp = path.peekFirst();

        int nRow = temp[0]-1; //sets the next location
        int nCol = temp[1];

        //if the next locaiton is a valid path of the maze, return 1
        if(nRow > 0){
            if( maze[nRow][nCol] == ' '){
                return 1;
            }
        }

        return 0; //else return 0

    }

    public int canSolveRight()
    {
        Integer[] temp = new Integer[2]; //temp to access stack
        temp = path.peek();

        int nRow = temp[0];   //sets the next locaiton
        int nCol = temp[1]+1;

        //if the next locaiton is a valid path of the maze, return 2
        if(nCol < cols){
            if( maze[nRow][nCol] == ' '){
                return 2;
            }
        }

        return 0; //else return 0

    }

    public int canSolveDown()
    {
        Integer[] temp = new Integer[2]; //for accessing the stack
        temp = path.peek();

        int nRow = temp[0]+1;  //sets next locaiton
        int nCol = temp[1];

        //if the next locaiton is a valid path of the maze, return 3
        if(nRow < rows){
            if( maze[nRow][nCol] == ' '){
                return 3;
            }
        }

        return 0;  //else return 0

    }

    public int canSolveLeft()
    {
        Integer[] temp = new Integer[2]; //for accessing the stack
        temp = path.peek();

        int nRow = temp[0];   //sets next direction
        int nCol = temp[1]-1;


        //if the next locaiton is a valid path of the maze, return 4
        if(nCol > 0){
            if( maze[nRow][nCol] == ' '){
                return 4;
            }
        }

        return 0; //else return 0

    }



    public int[] canSolve()
    {
        int[] cut = new int[4];
        int place =0;
        //check to see if up is a valid direction
        if(canSolveUp() !=   0){
            cut[place] = canSolveUp();
            place++;
        }
        //check to see if right is a valid direction
        if(canSolveRight() != 0){
            cut[place] = canSolveRight();
            place++;
        }
        //check to see if down is a valid direction
        if(canSolveDown() != 0){
            cut[place] = canSolveDown();
            place++;
        }
        //check to see if left is a valid direction
        if(canSolveLeft() != 0){
            cut[place] = canSolveLeft();
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



    public void solveUp()
    {
        Integer[] current = path.peek(); //gets the current location
        Integer[] temp = new Integer[2]; //for adding the next location

        int nRow = current[0]-1;  //sets the next location
        int nCol = current[1];


        maze[nRow][nCol] = '-'; //sets next to '-' to indicate solver presence

        temp[0] = nRow;
        temp[1] = nCol;

        path.addFirst(temp);  //pushes the next location to the stack

    }

    public void solveRight()
    {
        Integer[] current = path.peek(); //gets the current location
        Integer[] temp = new Integer[2]; //for adding the next location

        int nRow = current[0];   //adds the next location
        int nCol = current[1]+1;
        maze[nRow][nCol] = '-';  //sets next to '-' to indicate solver presence

        temp[0] = nRow;
        temp[1] = nCol;

        path.addFirst(temp); //pushes the next location to the stack

    }

    public void solveDown()
    {
        Integer[] temp = new Integer[2]; //gets the current location
        Integer[] current = path.peek(); //for adding the next location

        int nRow = current[0]+1;   //adds the next location
        int nCol = current[1];
        maze[nRow][nCol] = '-';  //sets next to '-' to indicate solver presence

        temp[0] = nRow;
        temp[1] = nCol;

        path.addFirst(temp); //pushes the next location to the stack

    }

    public void solveLeft()
    {
        Integer[] temp = new Integer[2]; //gets the current location
        Integer[] current = path.peek(); //for adding the next location

        int nRow = current[0];   //adds the next location
        int nCol = current[1]-1;
        maze[nRow][nCol] = '-';  //sets next to '-' to indicate solver presence

        temp[0] = nRow;
        temp[1] = nCol;

        path.addFirst(temp); //pushes the next location to the stack

    }

    public boolean nearExit()
    {
        Integer[] temp = new Integer[2];
        temp = path.peek();

        if(temp[1] == maze[0].length-2){
            if(maze[temp[0]][temp[1]+1] == ' '){
                return true;
            }
        }

        return false;
    }

    public void goToExit()
    {
        Integer[] temp = new Integer[2]; //used to access the stack
        Integer[] current = path.peek(); //gets the current location

        temp[0] = current[0];   //exit is to the right of the current location
        temp[1] = current[1]+1;

        maze[temp[0]][temp[1]] = '-'; //sets the exit to '-'

        path.push(temp); //pushes the exit onto the stack

    }

    public void backTrack()
    {
        path.removeFirst();

    }


    public void solvePath()
    {
        Integer[] temp = new Integer[2]; //used to access the stack


        //while there are objects in the stack
        while(path.peek() != null){

            temp = path.pop(); //get the current location

            maze[temp[0]][temp[1]] = '+'; //change the location to a '+'
        }

        //cleans up the maze
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                if(maze[i][j] == '-')
                    maze[i][j] = ' ';
            }
        }

    }

    public void solveMaze()
    {


        int[] solve_order; //stores the possible moves for each location

        setInitialLocation(); //finds the start of the maze

        while( ! nearExit() ){

            solve_order = canSolve(); /*gets the possible directions for
                                              the current location */

            if(solve_order[0] == 0){  //if you hit a dead end
                backTrack();    //go back to find a new direction to take

            }else{ //pick a direction to try to find the solution
                if(solve_order[0] == 1){
                    solveUp();
                }else if(solve_order[0] == 2){
                    solveRight();
                }else if(solve_order[0] == 3){
                    solveDown();
                }else if(solve_order[0] == 4){
                    solveLeft();
                }
            }

        }

        goToExit(); //after the loop exits, the location will be directly
                          //next to the exit, so we must go to the exit

        solvePath(); //now that the path from the begining to the end is
                           //determined, denote the path as '+' in the feild


    }

    public char[][] getSolution()
    {
        return maze;
    }

    public void printSolution()
    {
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }

}










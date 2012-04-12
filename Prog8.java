import java.util.*;
import acm.program.*;
import acm.graphics.*;
import java.awt.*;

public class Prog8 extends GraphicsProgram
{

    private static int rows,cols;

    public void run ()
    {


        /* THE MATH PART */

        MaizeBuilder test = new MaizeBuilder(rows,cols);
                                            //a MaizeBilder object that
                                            //will be used to make the
                                            //maze

        int[] cut_order;//an array that gets all the possible motions
                        //to which can be cut into


        char[][] tFeild;
        int not_done;     //a variable that is positive when there are items
                          //in the stack, and negative when the stack of
                          //locations is empty

        tFeild = test.getFeild();  //the feild that the maze is being
                                   //made in


        test.start();     //picks a random location to start building the maze

        not_done = test.stackPeek();  //initializes the looping condition now
                                      //that there is a location (the start)
                                      //in the stack

        //loops while there are locations in the stack
        while(not_done > 0){
            cut_order = test.canCut();  //gets the availible locations



            /*
            //used to print the cut options (for debugging)

            for(int i=0; i<cut_order.length; i++){
            System.out.print(cut_order[i]);
            }
            System.out.println();
            */



            //if there are directions to cut into
            if(cut_order[0] != 0){

                cut_order = mix(cut_order); //shuffle the availible directions

                //take the first direction to be cut into after shuffling and
                //cut into it.  like shuffling a deck of cards and drawing the
                //top card
                if(cut_order[0] == 1){
                    test.cutNextUp();
                }else if(cut_order[0] == 2){
                    test.cutNextRight();
                }else if(cut_order[0] == 3){
                    test.cutNextDown();
                }else if(cut_order[0] == 4){
                    test.cutNextLeft();
                }
            }else{  //if there are no directions to cut, back up one locaiton
                test.back();
            }



            /*
            //prints the maze after each step (for debugging)

            tFeild = test.getFeild();
            printMaize(tFeild);
            */


            not_done = test.stackPeek();  //are the more locations in stack?
        }

        test.makeOpenings();  //make the openings for the maze


        /*
        //prints the final maze
        tFeild = test.getFeild();
        printMaize(tFeild);
        */

        /* THE SOLVER */

        //solver object for the generated maze
        MaizeSolver test2 = new MaizeSolver(test.getFeild());
        int[] solve_order; //stores the possible moves for each location

        test2.setInitialLocation(); //finds the start of the maze

        while( ! test2.nearExit() ){

            solve_order = test2.canSolve(); /*gets the possible directions for
                                              the current location */

            //prints the possible moves for each locattion (for debugging)
            /*
            for(int i=0; i<solve_order.length; i++){
                System.out.print(solve_order[i]);
            }
            System.out.println();
            */

            if(solve_order[0] == 0){  //if you hit a dead end
                test2.backTrack();    //go back to find a new direction to take

            }else{ //pick a direction to try to find the solution
                if(solve_order[0] == 1){
                    test2.solveUp();
                }else if(solve_order[0] == 2){
                    test2.solveRight();
                }else if(solve_order[0] == 3){
                    test2.solveDown();
                }else if(solve_order[0] == 4){
                    test2.solveLeft();
                }
            }

        //printMaize(test2.getMaze());
        }

        test2.goToExit(); //after the loop exits, the location will be directly
                          //next to the exit, so we must go to the exit

        test2.solvePath(); //now that the path from the begining to the end is
                           //determined, denote the path as '+' in the feild

        char[][] solved_maze = test2.getMaze(); //get the solved maze



        //printMaize(test2.getMaze());











        /* THE GRAPHICS PART */




        /* ---------   FOR THE UNSOLVED MAZE  -------- */


        //an attempt at a decent scale of the maze
        final double delta = (double)2*getHeight()/(5*rows);


        double x = delta;  //coordinates in the maze
        double y = delta;

        /*
         * loop through the feild.  if there needs to be a line drawn between
         * the current point and the point either to the right and/or below,
         * draw the line.
         */
        for(int i=0; i<tFeild.length; i++){

            x = delta; //always start at the first column

            //loop through the index of each row, and if the index right or
            //below it connects to make a wall, draw the wall
            for(int j=0; j<tFeild[i].length; j++){

                //if the current index is a '#' there might be a wall
                if(tFeild[i][j] == '#'){

                    //if the index below is in the array and a '#'
                    //there is a wall
                    if(i != tFeild.length-1){
                        if(tFeild[i+1][j] == '#'){
                            GLine line = new GLine(x,y,x,y+delta);
                            line.setColor(Color.BLUE);
                            add(line);
                        }
                    }

                    //if the index right is in the array and a '#'
                    //there is a wall
                    if(j != tFeild[i].length-1){
                        if(tFeild[i][j+1] == '#'){
                            GLine line = new GLine(x,y,x+delta,y);
                            line.setColor(Color.BLUE);
                            add(line);
                        }
                    }
                }

                x+=delta; //go to the next column to draw in the right place
            }
            y+=delta; //go to the next row to draw in the right place
        }


        /* -------   FOR THE SOLVED MAZE  ------- */

        //  THE MAZE AGAIN



        //an attempt at a decent scale of the maze

        double xs = x + (2*delta);  //coordinates in the maze
        y = delta;

        /*
         * loop through the feild.  if there needs to be a line drawn between
         * the current point and the point either to the right and/or below,
         * draw the line.
         */
        for(int i=0; i<solved_maze.length; i++){

            xs = x+(2*delta);
            for(int j=0; j<solved_maze[i].length; j++){

                if(solved_maze[i][j] == '#'){

                    if(i != solved_maze.length-1){
                        if(solved_maze[i+1][j] == '#'){
                            GLine line = new GLine(xs,y,xs,y+delta);
                            line.setColor(Color.BLUE);
                            add(line);
                        }
                    }

                    if(j != solved_maze[i].length-1){
                        if(solved_maze[i][j+1] == '#'){
                            GLine line = new GLine(xs,y,xs+delta,y);
                            line.setColor(Color.BLUE);
                            add(line);
                        }
                    }
                }

                xs+=delta;
            }
            y+=delta;
        }

        // FOR THE SOLUTION LINE


        y = delta;

        //loop through the maze and connect all the plus signs with a red line
        for(int i=0; i<solved_maze.length; i++){

            xs = x+(2*delta);
            for(int j=0; j<solved_maze[i].length; j++){

                if(solved_maze[i][j] == '+'){

                    if(i != solved_maze.length-1){
                        if(solved_maze[i+1][j] == '+'){
                            GLine line = new GLine(xs,y,xs,y+delta);
                            line.setColor(Color.RED);
                            add(line);
                        }
                    }

                    if(j != solved_maze[i].length-1){
                        if(solved_maze[i][j+1] == '+'){
                            GLine line = new GLine(xs,y,xs+delta,y);
                            line.setColor(Color.RED);
                            add(line);
                        }
                    }
                }

                xs+=delta;
            }
            y+=delta;
        }

    }




    //main method, for the purpose of getting the argumets from the command
    //line.  if the command line arguments are not integers greater than 1
    //for both the
    //row and the column, then prints an error message and instructions,
    //otherwise it calls the run function
    public static void main(String [] args)
    {
        int error = 1;
        int matches1 = 0;
        int matches2 = 0;
        if(args.length == 2){

            //check the first argument to make sure every character is a number
            for(int i=0; i<args[0].length(); i++){
                if(args[0].substring(i, i+1).matches("[0-9]"))
                    matches1++;
            }
            //check the second argument to make sure every char is a number
            for(int i=0; i<args[1].length(); i++){
                if(args[1].substring(i,i+1).matches("[0-9]"))
                    matches2++;
            }


            //if both arguments had only numbers, and the numbers were
            //greater than 1, allow them, otherwise quit the program after
            //sending an error message

            if(matches1 == args[0].length() && matches2 == args[1].length()){
                int temp_rows = Integer.valueOf(args[0]);
                int temp_cols = Integer.valueOf(args[1]);

                if(temp_rows > 1 && temp_cols > 1){
                    error = 0;
                    rows = temp_rows;
                    cols = temp_cols;
                    new Prog8().start(args);
                }
            }
        }
        if(error == 1){
            System.out.println("This program generates a maze. Enter two"
                + " integers, as command line args, greater than 1.  The first"
                + " will correspond with the number of rows in the"
                + " maze, and the second will correspond with the"
                + " number of columns.  Please try again.");
        }
    }





    /*-------------------------------------------------------------------------
     *
     *  Method:  printMaze(char[][])
     *
     *  Descrption: a function that loops through the feild representation of
     *              the maze and prints them.  mostly for convienence than
     *              functionality, so that I dont have to type a bunch of loops
     *
     *  Pre-condition: a 2D array
     *
     *  Post-condition: no changes to the 2D array, but it is printed to the
     *                  console
     *
     *  Parameters: char[][] the array to be printed
     *
     *  Returns: none
     *
     *-----------------------------------------------------------------------*/
    private static void printMaize(char[][] feild)
    {

        //nested loops to print the contents of the 2D array
        for(int i=0; i<feild.length ; i++){
            for(int j=0; j<feild[i].length ; j++){
                System.out.print(feild[i][j]);
            }
            System.out.print("\n");
        }
        System.out.println();
    }


    /*-------------------------------------------------------------------------
     *
     *  Method:  mix(int[])
     *
     *  Descrption: takes in an array of ints, and swaps two numbers
     *              at random locations within the array.  this shuffle takes
     *              place 7 times (mostly because ive always heard you have
     *              to shuffle a deck of cards 7 times to really mix it up)
     *
     *  Pre-condition: an array of ints
     *
     *  Post-condition: the array of ints with the same numbers but with their
     *                  locations randomly swapped
     *
     *  Parameters: an array of ints
     *
     *  Returns: the mixed up array of ints
     *
     *-----------------------------------------------------------------------*/
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

}





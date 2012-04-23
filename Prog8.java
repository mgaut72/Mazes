import java.util.*;
import acm.program.*;
import acm.graphics.*;
import java.awt.*;

public class Prog8 extends GraphicsProgram
{

    private static int rows;
    private static int cols;

    private static final char MAZE_WALL = '#';

    public void run ()
    {

        rows=cols=20;

        int numMazes = 0;

        Ellers ell = new Ellers(rows,cols);
        ell.makeMaze();
        numMazes++;

        Backtracker bTrack = new Backtracker(rows, cols);
        bTrack.makeMaze();
        numMazes++;

        RecursiveDivision rDiv = new RecursiveDivision(rows, cols);
        rDiv.makeMaze();
        numMazes++;

        GrowingTree gTree = new GrowingTree(rows,cols);
        gTree.makeMaze();
        numMazes++;






        /* THE GRAPHICS PART */





        //an attempt at a decent scale of the maze
        final double delta = (double)1.3*getWidth()/(7*cols);


        double x = delta;  //coordinates in the maze
        double y = delta;
        char[][] maze=null;

        for(int k=0; k<numMazes; k++){
            if(k==0)
                maze = ell.getMaze();
            if(k==1)
                maze = bTrack.getMaze();
            if(k==2)
               maze = rDiv.getMaze();
            if(k==3)
                maze = gTree.getMaze();



            y=delta;
        /*
         * loop through the feild.  if there needs to be a line drawn between
         * the current point and the point either to the right and/or below,
         * draw the line.
         */
            for(int i=0; i<maze.length; i++){

            x = delta+(delta*2.2*cols*k); //always start at the first column

            //loop through the index of each row, and if the index right or
            //below it connects to make a wall, draw the wall
            for(int j=0; j<maze[i].length; j++){

                //if the current index is a MAZE_WALL there might be a wall
                if(maze[i][j] == MAZE_WALL){

                    //if the index below is in the array and a MAZE_WALL
                    //there is a wall
                    if(i != maze.length-1){
                        if(maze[i+1][j] == MAZE_WALL){
                            GLine line = new GLine(x,y,x,y+delta);
                            line.setColor(Color.BLUE);
                            add(line);
                        }
                    }

                    //if the index right is in the array and a MAZE_WALL
                    //there is a wall
                    if(j != maze[i].length-1){
                        if(maze[i][j+1] == MAZE_WALL){
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
        }

    }
}


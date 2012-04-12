import java.util.*;

class RecursiveDivision
{
    int rows;
    int cols;
    int act_rows;
    int act_cols;

    char[][] board;

    public RecursiveDivision(int row, int col)
    {

        //initialize instance variables
        rows = row*2+1;
        cols = col*2+1;
        act_rows = row;
        act_cols = col;
        board = new char[rows][cols];



        //set the maze to empty
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                board[i][j] = ' ';
            }
        }

        //make the outter walls
        for(int i=0; i<rows; i++){
            board[i][0] = '#';
            board[i][cols-1] = '#';
        }

        for(int i=0; i<cols; i++){
            board[0][i] = '#';
            board[rows-1][i] = '#';
        }


    }

    //storefront method to make the maze
    public void makeMaze()
    {
        makeMaze(0,cols-1,0,rows-1);
    }


    //behind the scences actual mazemaking
    private void makeMaze(int left, int right, int top, int bottom)
    {
        int width = right-left;
        int height = bottom-top;

        //makes sure there is still room to divide, then picks the best
        //direction to divide into
        if(width > 2 && height > 2){

            if(width > height)
                divideVertical(left, right, top, bottom);

            else if(height > width)
                divideHorizontal(left, right, top, bottom);

            else if(height == width){
                Random rand = new Random();
                boolean pickOne = rand.nextBoolean();

                if(pickOne)
                    divideVertical(left, right, top, bottom);
                else
                    divideHorizontal(left, right, top, bottom);
            }
        }else if(width > 2 && height <=2){
            divideVertical(left, right, top, bottom);
        }else if(width <=2 && height > 2){
            divideHorizontal(left, right, top, bottom);
        }
    }


    private void divideVertical(int left, int right, int top, int bottom)
    {
        //find the halfway point
        int half = (right+left)/2;
        if(half%2 == 1)
            half++;

        //draw a line at the halfway point
        for(int i=top; i<bottom; i++){
            board[i][half] = '#';
        }

        //get a random odd integer between top and bottom and clear it
        Random rand = new Random();
        int clearSpace = top + rand.nextInt((bottom-top)/2) * 2 + 1;

        board[clearSpace][half] = ' ';

        makeMaze(left, half, top, bottom);
        makeMaze(half, right, top, bottom);
    }

    private void divideHorizontal(int left, int right, int top, int bottom)
    {
        //find the halfway point
        int half = (top+bottom)/2;
        if(half%2 == 1)
            half++;

        //draw a line at the halfway point
        for(int i=left; i<right; i++){
            board[half][i] = '#';
        }

        //get a random odd integer between left and right and clear it
        Random rand = new Random();
        int clearSpace = left + rand.nextInt((right-left)/2) * 2 + 1;

        board[half][clearSpace] = ' ';

        //recur for both parts of the newly split section
        makeMaze(left, right, top, half);
        makeMaze(left, right, half, bottom);
    }

    public void makeOpenings(){

        Random rand = new Random(); //two different random number generators
        Random rand2 = new Random();//just in case

        //a random location for the entrance and exit
        int entrance_row = rand.nextInt(act_rows-1) * 2 +1;
        int exit_row = rand2.nextInt(act_rows-1) * 2 +1;

        //clear the location
        board[entrance_row][0] = ' ';
        board[exit_row][cols-1] = ' ';

    }

    public void printMaze()
    {
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public char[][] getMaze()
    {
        return board;
    }

}




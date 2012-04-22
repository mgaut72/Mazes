import java.util.*;

class Ellers
{
    int       rows;           //the rows in the representative feild
    int       cols;           //the cols in the representative feild

    int       act_rows;       //the actual number of rows in the maze
    int       act_cols;       //the actual number of cols in the maze

    static char[][]  feild;          //the feild where the maze is being made
    int[]     current;
    int[]     next;

    int       numSet;         //track set numbers to make sure not to duplicate


    /* constructor */
    public Ellers (int nRows, int nCols)
    {
        act_rows = nRows;
        act_cols = nCols;

        rows = act_rows*2+1;
        cols = act_cols*2+1;

        feild   = new char[rows][cols];
        current = new int[act_cols*2-1];
        next    = new int[act_cols*2-1];


        /* Sets the borders of the maze */
        for(int i=0; i<cols; i++){
            feild[0][i] = '#';
            feild[rows-1][i] = '#';
        }

        for(int i=0; i<rows; i++){
            feild[i][0] = '#';
            feild[i][cols-1] = '#';
        }

        for(int i =0; i<feild[0].length; i++){
            for(int j=0; j<feild.length; j++){
                feild[i][j] = '#';
            }
        }


        for(int i=0; i<current.length; i++){
            next[i] = -2;
        }



        /* initialize the first row to unique sets */
        for(int i=0; i<current.length; i+=2){
            current[i] = i/2+1;
            if(i != current.length-1)
                current[i+1] = -1;
        }
        numSet = current[current.length-1];
    }


    public void makeMaze()
    {

        Random rand = new Random();


        for(int q=0; q<act_rows-1; q++){   //for all rows but the last one

            if(q != 0){

                /* get the current row from the last iteration */
                for(int i=0; i<current.length; i++){
                    current[i] = next[i];
                    next[i] = -2;           /* set next to blank, indicated
                                             * by -2
                                             */
                }
            }


            joinSets();
            makeVerticalCuts();


            /* populate the rest of the next row */

            for(int j=0; j<current.length; j+=2){

                if(next[j] == -2)
                    next[j] = ++numSet;
                if(j != current.length-1)
                    next[j+1] = -1;
            }


            /* record the current row onto the feild */
            for(int k=0; k<current.length; k++){

                if(current[k] == -1){
                    feild[2*q+1][k+1] = '#';
                    feild[2*q+2][k+1] = '#';
                }else{
                    feild[2*q+1][k+1] = ' ';

                    if(current[k] == next[k]){
                        feild[2*q+2][k+1] = ' ';
                    }
                }

            }

        }

        makeLastRow();

    }

    private void joinSets()
    {
        Random rand = new Random();

        /* Randomly join sets together */
        for(int i=1; i<current.length-1; i+=2){ //checks only at wall locations

            /* make sure they are eligible to be combined:
             *      they have wall between then
             *      they are not part of the same set
             *
             * then get a random boolean to pick if they actually get combine
             */
            if(current[i] == -1 && current[i-1] != current[i+1]
                    && rand.nextBoolean()){


                current[i] = 0; //take away the barrier

                int old  = Math.max(current[i-1],current[i+1]);
                int next = Math.min(current[i-1],current[i+1]);

                /* combine the two sets into 1 (the smallest numbered
                 * set)
                 */
                for(int j=0; j<current.length; j++){

                    if(current[j] == old)
                        current[j] = next;
                }
                    }
        }
    }


    /* Randomly pick vertical paths for each set, making sure there
     * is at least 1 vertical path per set
     */
    private void makeVerticalCuts()
    {
        Random   rand          = new Random();

        int      begining;     //the begining of the section (inclusive)
        int      end;          //the end of teh section (inclusive)

        boolean madeVertical;  /* tracks if a vertical path has been made
                                * in the section
                                */

        int i;
        begining = 0;
        do{

            /* find the end of this section */
            i=begining;
            while(i<current.length-1 && current[i] == current[i+2]){
                i+=2;
            }
            end = i;

            /* loop trying to cut a vertical path in the section until it
             * is sucessful at least 1 time in the section
             */
            madeVertical = false;
            do{
                for(int j=begining; j<=end; j+=2){

                    if(rand.nextBoolean()){
                        next[j] = current[j];
                        madeVertical = true;
                    }
                }
            }while(!madeVertical);

            begining = end+2;  //go to the next section in the row

        }while(end != current.length-1);
    }




        private void makeLastRow()
        {

            /* get the current row from the last iteration */
            for(int i=0; i<current.length; i++){
                current[i] = next[i];
            }

            for(int i=1; i<current.length-1; i+=2){
                if(current[i] == -1){
                    if(current[i-1] != current[i+1]){
                        current[i] = 0;
                        int old = Math.max(current[i-1],current[i+1]);
                        int cur = Math.min(current[i-1],current[i+1]);
                        current[i+1] = current[i-1] = cur;
                        for(int r=0; r<current.length; r++){
                            if(current[r] == old)
                                current[r] = cur;
                        }
                    }
                }
            }


            for(int k=0; k<current.length; k++){

                if(current[k] == -1){
                    feild[rows-2][k+1] = '#';
                }else{
                    feild[rows-2][k+1] = ' ';
                }
            }

        }




        public static void main(String [] args)
        {

            Ellers test = new Ellers(5,5);
            test.makeMaze();

            for(int i=0; i<feild[0].length; i++){
                for(int j=0; j<feild.length; j++){
                    System.out.print(feild[i][j]);
                }
                System.out.println();
            }
        }

    }





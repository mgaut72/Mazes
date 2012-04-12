import java.util.*;

class Mazes
{
    public static void main(String [] args)
    {

        final int rows = 12;
        final int cols = 12;



        Backtracker recBack = new Backtracker(rows,cols);
        recBack.makeMaze();
        recBack.makeOpenings();
        recBack.printMaze();

        System.out.println();

        Solver recBackSol = new Solver(recBack.getMaze() );
        recBackSol.solveMaze();
        recBackSol.printSolution();

        System.out.println();

        RecursiveDivision recDiv = new RecursiveDivision(rows,cols);
        recDiv.makeMaze();
        recDiv.makeOpenings();
        recDiv.printMaze();

        System.out.println();

        Solver recDivSol = new Solver(recDiv.getMaze() );
        recDivSol.solveMaze();
        recDivSol.printSolution();
    }
}

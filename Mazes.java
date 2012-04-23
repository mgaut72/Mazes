import java.util.*;

class Mazes
{
        static final int ROWS = 12;
        static final int COLS = 12;

    public static void main(String [] args)
    {




        
        System.out.println("Recursive Backtracker Maze and Solution:");

        Backtracker recBack = new Backtracker(ROWS,COLS);
        recBack.makeMaze();
        recBack.printMaze();

        System.out.println();

        Solver recBackSol = new Solver(recBack.getMaze() );
        recBackSol.solveMaze();
        recBackSol.printSolution();

        System.out.println("\n");
       



        System.out.println("Recursive Division Maze and Solution:");

        RecursiveDivision recDiv = new RecursiveDivision(ROWS,COLS);
        recDiv.makeMaze();
        recDiv.printMaze();

        System.out.println();

        Solver recDivSol = new Solver(recDiv.getMaze() );
        recDivSol.solveMaze();
        recDivSol.printSolution();

        System.out.println("\n");



        
        System.out.println("Eller's Algorithm Maze and Solution:");

        Ellers ell = new Ellers(ROWS,COLS);
        ell.makeMaze();
        ell.printMaze();
        
        System.out.println();

        Solver ellSol = new Solver(ell.getMaze());
        ellSol.solveMaze();
        ellSol.printSolution();

        System.out.println("\n");



        System.out.println("Growing Tree Algorithm Maze and Solution:");

        GrowingTree gTree = new GrowingTree(ROWS, COLS);
        gTree.makeMaze();
        gTree.printMaze();

        System.out.println();

        Solver gTreeSol = new Solver(gTree.getMaze());
        gTreeSol.solveMaze();
        gTreeSol.printSolution();

        System.out.println("\n");
        
    }
}

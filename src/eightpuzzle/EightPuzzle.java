/*
Brandon Pauly
CSC 380 - Assignment 1
*/
package eightpuzzle;

import eightpuzzle.node.Node;
import eightpuzzle.search.AStar;
import eightpuzzle.search.BFS;
import eightpuzzle.search.DFS;
import eightpuzzle.search.GreedyBestFirstSearch;
import eightpuzzle.search.Search;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/*
A simple User Interface that houses the runnable for the EightPuzzle program.
This displays the text based data to the User and acts as a control board for the puzzle.
Simply run this program and follow the output prompts.  Enjoy!
*/
public class EightPuzzle {
    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    private static final int RIGHT = 4;    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner responseReader = new Scanner(System.in);
        Search search;
        ArrayList<Integer> initialState;
        System.out.println("Would you like to...");
        System.out.println("    1 - run the easy problem.");
        System.out.println("    2 - run the medium problem.");
        System.out.println("    3 - run the hard problem.");
        System.out.println("    4 - input a new problem.\n>>>");
        int response = responseReader.nextInt();
        if (response == 1){
            initialState = new ArrayList<>(Arrays.asList(1, 3, 4, 8, 6, 2, 7, 0, 5));
        }
        else if (response == 2){
            initialState = new ArrayList<>(Arrays.asList(2, 8, 1, 0, 4, 3, 7, 6, 5));
        }
        else if (response == 3){
            initialState = new ArrayList<>(Arrays.asList(5, 6, 7, 4, 0, 8, 3, 2, 1));
        }
        else {
            initialState = new ArrayList<>();
            System.out.println("Please enter a sequence of integers to represent the initial game state.");
            for (int i = 0; i < 9; i++){
                System.out.println("Please enter integer number " + (i + 1));
                int t = responseReader.nextInt();
                initialState.add(t);
            }
        }
        System.out.println("Please indicate which search algorithm you would like to use...");
        System.out.println("    1 - DFS");
        System.out.println("    2 - BFS");
        System.out.println("    3 - Greedy Best First");
        System.out.println("    4 - A*\n>>>");
        response = responseReader.nextInt();
        if (response == 1){
            search = new DFS();
        }
        else if (response == 2){
            search = new BFS();
        }
        else if (response == 3){
            search = new GreedyBestFirstSearch();
        }
        else {
            search = new AStar();
        }
        try{
            long start = System.currentTimeMillis();
            LinkedList<Node> solution = search.findSolution(new Node(initialState));
            long stop = System.currentTimeMillis();
            long totalMillis = stop - start;
            int solutionLength = solution.size();
            long maxSpace = search.getMaxMemory();
            System.out.println("\n\n\n\nSolution found.  Here are the moves to solve:");
            for (Node n : solution){
                int j = 0;
                List<Integer> step = n.getState();
                if (n.getParent() != null){
                    System.out.print("The next move is to move the empty space ");
                    if (n.getPriorMove() == UP) System.out.println("UP");
                    else if (n.getPriorMove() == DOWN) System.out.println("DOWN");
                    else if (n.getPriorMove() == LEFT) System.out.println("LEFT");
                    else if (n.getPriorMove() == RIGHT) System.out.println("RIGHT");
                }
                System.out.println("\n\n-------------");
                System.out.println("| " + step.get(j++) + " | " + step.get(j++) + " | " + step.get(j++) + " |");
                System.out.println("-------------");
                System.out.println("| " + step.get(j++) + " | " + step.get(j++) + " | " + step.get(j++) + " |");
                System.out.println("-------------");
                System.out.println("| " + step.get(j++) + " | " + step.get(j++) + " | " + step.get(j++) + " |");
                System.out.println("-------------");
                System.out.println("");
            }
            System.out.println("The time the algorithm took to come up with a solution was " + totalMillis + " milliseconds.");
            System.out.println("The length of this solution is " + solutionLength + " moves.");
            System.out.println("The largest number of nodes in memory at any given time was " + maxSpace + " nodes.");
        }
        catch(NullPointerException npe){
            System.out.println("No solution found.");
        }
    }
    
}

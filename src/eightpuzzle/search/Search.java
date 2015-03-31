/*
Brandon Pauly
CSC 380 - Assignment 1
*/
package eightpuzzle.search;

import eightpuzzle.node.Node;
import java.util.LinkedList;


public interface Search {
    
    /*
    Takes a Node representing the initial state of the problem.  Returns a LinkedList of Nodes representing
    the path to the solution, or a winning state.
    */
    public LinkedList<Node> findSolution(Node initialState);
    
    /*
    Takes no arguments and returns the maximum number of Nodes that were held in memory during the search.
    */
    public long getMaxMemory();
    
    public void removeNode(Node n);
    
}

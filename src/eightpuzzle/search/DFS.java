/*
Brandon Pauly
CSC 380 - Assignment 1
*/
package eightpuzzle.search;

import eightpuzzle.node.Node;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/*
This is a search class implementing the Search interface.  The DFS visits Nodes in the stack by a LIFO
system.  If a solution is found, the Nodes that led to the solution are back tracked and placed into a stack.
The proper order is acheived by popping off the stack into an alternate list.
*/
public class DFS implements Search{
    
    /*
    A LinkedList holding the solution to the problem.
    */
    private LinkedList<Node> solution;   
    
    /*
    The maximum number of Nodes that were in memory during the search.
    */
    private long maxMemory = 0;
    
    private Stack<Node> stack;
    
    /*
    findSolution is the work horse of the class.  Takes one Node parameter representing the starting
    state of the problem.  This function utilizes a LIFO stack in order to search the depth of the state 
    space before the depth.
    */
    @Override
    public LinkedList<Node> findSolution(Node initialState) {
        initialState.setSearch(this);
        solution = new LinkedList<>();
        if (initialState.isGoal()) { solution.add(initialState); return solution; };
        stack = new Stack<>();
        stack.add(initialState);
        while (!stack.isEmpty()){
            Node current = stack.pop();
            List<Node> children = current.successor();
            for (Node child : children){
                if (child.isGoal()){
                    Stack<Node> backwardsSolution = new Stack<>();
                    Node temp = child;
                    while (temp.getParent() != null){
                        backwardsSolution.push(temp);
                        temp = temp.getParent();
                    }
                    backwardsSolution.push(temp);
                    while (!backwardsSolution.isEmpty()){
                        solution.add(backwardsSolution.pop());
                    }
                    return solution;
                    }
                    stack.add(child);
                    if (maxMemory < stack.size()){
                        maxMemory++;
                    }
                }
            }
        return null;
    }

    
    /*
    getMaxMemory is an accessor method to retreive the maximum number of nodes on the queue at any given time during the search.
    */
    @Override
    public long getMaxMemory() {
        return maxMemory;
    }
    
    @Override
    public void removeNode(Node n){
        stack.remove(n);
    }
}

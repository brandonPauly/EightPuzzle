/*
Brandon Pauly
CSC 380 - Assignment 1
*/
package eightpuzzle.search;

import eightpuzzle.node.Node;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

/*
This is a search class implementing the Search interface.  The GreedyBestFirstSearch uses the heuristic to prioritize
the queue in order to maximize efficiency.  If a solution is found, the Nodes that led to the solution are back tracked and placed into a stack.
The proper order is acheived by popping off the stack into an alternate list.
*/
public class GreedyBestFirstSearch implements Search{
    
    private PriorityQueue<Node> queue;
    
    /*
    A LinkedList holding the solution to the problem.
    */
    private LinkedList<Node> solution;
    
    /*
    The maximum number of Nodes that were in memory during the search.
    */
    private long maxMemory;
    
    
    
    /*
    findSolution is the work horse of the class.  Takes one Node parameter representing the starting
    state of the problem.  This function utilizes the fact that all Nodes are instantiated with a heuristic
    where f(n) = h(n).
    */
    @Override
    public LinkedList<Node> findSolution(Node initialState) {
        solution = new LinkedList<>();
        if (initialState.isGoal()) { solution.add(initialState); return solution; };
        queue = new PriorityQueue<>(10, initialState);
        queue.add(initialState);
        while (!queue.isEmpty()){
            Node current = queue.poll();
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
                queue.add(child);
                if (maxMemory < queue.size()){
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
        queue.remove(n);
    }
}

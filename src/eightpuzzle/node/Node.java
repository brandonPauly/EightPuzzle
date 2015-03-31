/*
Brandon Pauly
CSC 380 - Assignment 1
*/
package eightpuzzle.node;

import eightpuzzle.search.Search;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/*
This class represents the encapsulated data that is needed to determine a proper path to a goal state in the 8 puzzle game.
*/
public class Node implements Comparator{
    
    private static Search search;
    
    /*
    A static HashMap of all of the visited states thus far as the Key and the cost to reach that state as the Value.
    */
    private static HashMap<List<Integer>, Integer> costTo = new HashMap<>();
    
    /*
    A static HashMap of all of the visited states thus far as the Key and a reference back to that state as the Value.
    */
    private static HashMap<List<Integer>, Node> backRef = new HashMap<>();
    
    /*
    An int representing the heuristic, or the number of missplaced tiles in this scenario.
    */
    private int heuristic;
    
    /*
    A boolean value representing f(n) = g(n) + h(n) when set to true
    */
    private static boolean fOfNFlag = false;
    
    /*
    An int representing the prior move that led to this state.
    */
    private int priorMove;
    
    /*
    A constant representing the direction UP.
    */
    private static final int UP = 1;
    
    /*
    A constant representing the direction DOWN
    */
    private static final int DOWN = 2;
    
    /*
    A constant representing the direction LEFT.
    */
    private static final int LEFT = 3;
    
    /*
    A constant representing the direction RIGHT.
    */
    private static final int RIGHT = 4;    
    
    /*
    A list of Nodes that are children of this Node.
    */
    private List<Node> successors;
    
    /*
    An int representing the cost, in tiles moved, to reach the current state.
    */
    private int cost;
    
    /*
    Reference to the parent node of this node.
    */
    private Node parent;
    
    /*
    The state of the 8-puzzle board that is the win state.
    */
    private final static ArrayList<Integer> goalState = new ArrayList<>(Arrays.asList(1, 2, 3, 8, 0, 4, 7, 6, 5));
    
    /*
    The state of the 8-puzzle board that this Node represents.
    */
    private final List<Integer> currentState;
    
    /*
    The child Nodes representing states that can be reached from the current state.
    */
    private Node childA;
    private Node childB;
    private Node childC;
    private Node childD;
    
    /*
    Constructor takes a List of Integers that is the current state of the board.  Positions
    are represented as such:
    
    -------------
    | 0 | 1 | 2 |
    -------------
    | 3 | 4 | 5 |
    -------------
    | 6 | 7 | 8 |
    -------------
    
    Each position then holds and Integer value representing the number on the tile of the game board.
    The position that hold the Integer zero represents the empty square on the game board that an adjacent
    tile can be moved into.
    */
    public Node(List<Integer> cS){
        currentState = cS;
        cost = 0;
        costTo.put(cS, cost);
        backRef.put(cS, this);
        initHeuristic();
    }
    
    /*
    Overloaded constructor to take a parent Node p.  This is to make it easier to travel back up
    the path to find the solution.  Initializes the parent for a reference back, initiaizes cost to 
    one more than the parent cost, decides the move that was required to reach this state, and calls
    the function to initiate the heuristic value.
    */
    public Node(List<Integer> cS, Node p, int pm){
        currentState = cS;
        parent = p;
        cost = parent.getCost() + 1;
        initHeuristic();
        priorMove = pm;
    }
    
    /*
    Takes no arguments.  Returns a list of new child nodes that can be reached 
    from the current state of the board.
     */
    public List<Node> successor(){
        successors = new ArrayList<>();
        // empty space is in position 0
        if (currentState.get(0) == 0){
            List<Integer> copyCurrentStateA = swap(0, 1, currentState);
            List<Integer> copyCurrentStateB = swap(0, 3, currentState);
            if (!costTo.containsKey(copyCurrentStateA) || ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA)))){
                if ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA))){
                    search.removeNode(backRef.get(copyCurrentStateA));
                }
                childA = new Node(copyCurrentStateA, this, RIGHT);
                costTo.put(childA.getState(), childA.getCost());
                backRef.put(childA.getState(), childA);
                successors.add(childA);
            }
            if (!costTo.containsKey(copyCurrentStateB) || ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB)))){
                if ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB))){
                    search.removeNode(backRef.get(copyCurrentStateB));
                }
                childB = new Node(copyCurrentStateB, this, DOWN);
                backRef.put(childB.getState(), childB);
                costTo.put(childB.getState(), childB.getCost());
                successors.add(childB);
            }
        }
        // empty space is in position 1
        else if (currentState.get(1) == 0){
            List<Integer> copyCurrentStateA = swap(0, 1, currentState);
            List<Integer> copyCurrentStateB = swap(1, 2, currentState);
            List<Integer> copyCurrentStateC = swap(1, 4, currentState);
            if (!costTo.containsKey(copyCurrentStateA) || ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA)))){
                if ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA))){
                    search.removeNode(backRef.get(copyCurrentStateB));
                }
                childA = new Node(copyCurrentStateA, this, LEFT);
                backRef.put(childA.getState(), childA);
                costTo.put(childA.getState(), childA.getCost());
                successors.add(childA);
            }
            if (!costTo.containsKey(copyCurrentStateB) || ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB)))){
                if ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB))){
                    search.removeNode(backRef.get(copyCurrentStateB));
                }
                childB = new Node(copyCurrentStateB, this, RIGHT);
                backRef.put(childB.getState(), childB);
                costTo.put(childB.getState(), childB.getCost());
                successors.add(childB);
            }
            if (!costTo.containsKey(copyCurrentStateC) || ((costTo.containsKey(copyCurrentStateC) && (cost + 1) < costTo.get(copyCurrentStateC)))){
                if ((costTo.containsKey(copyCurrentStateC) && (cost + 1) < costTo.get(copyCurrentStateC))){
                    search.removeNode(backRef.get(copyCurrentStateC));
                }
                childC = new Node(copyCurrentStateC, this, DOWN);
                backRef.put(childC.getState(), childC);
                costTo.put(childC.getState(), childC.getCost());
                successors.add(childC);
            }
        }
        // empty space is in position 2
        else if (currentState.get(2) == 0){
            List<Integer> copyCurrentStateA = swap(1, 2, currentState);
            List<Integer> copyCurrentStateB = swap(2, 5, currentState);
            if (!costTo.containsKey(copyCurrentStateA) || ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA)))){
                if ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA))){
                    search.removeNode(backRef.get(copyCurrentStateA));
                }
                childA = new Node(copyCurrentStateA, this, LEFT);
                backRef.put(childA.getState(), childA);
                costTo.put(childA.getState(), childA.getCost());
                successors.add(childA);
            }
            if (!costTo.containsKey(copyCurrentStateB) || ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB)))){
                if ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB))){
                    search.removeNode(backRef.get(copyCurrentStateB));
                }
                childB = new Node(copyCurrentStateB, this, DOWN);
                backRef.put(childB.getState(), childB);
                costTo.put(childB.getState(), childB.getCost());
                successors.add(childB);
            }
        }
        // empty space is in position 3
        else if (currentState.get(3) == 0){
            List<Integer> copyCurrentStateA = swap(0, 3, currentState);
            List<Integer> copyCurrentStateB = swap(3, 4, currentState);
            List<Integer> copyCurrentStateC = swap(3, 6, currentState);
            if (!costTo.containsKey(copyCurrentStateA) || ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA)))){
                if ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA))){
                    search.removeNode(backRef.get(copyCurrentStateA));
                }
                childA = new Node(copyCurrentStateA, this, UP);
                backRef.put(childA.getState(), childA);
                costTo.put(childA.getState(), childA.getCost());
                successors.add(childA);
            }
            if (!costTo.containsKey(copyCurrentStateB) || ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB)))){
                if ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB))){
                    search.removeNode(backRef.get(copyCurrentStateB));
                }
                childB = new Node(copyCurrentStateB, this, RIGHT);
                backRef.put(childB.getState(), childB);
                costTo.put(childB.getState(), childB.getCost());
                successors.add(childB);
            }
            if (!costTo.containsKey(copyCurrentStateC) || ((costTo.containsKey(copyCurrentStateC) && (cost + 1) < costTo.get(copyCurrentStateC)))){
                if ((costTo.containsKey(copyCurrentStateC) && (cost + 1) < costTo.get(copyCurrentStateC))){
                    search.removeNode(backRef.get(copyCurrentStateC));
                }
                childC = new Node(copyCurrentStateC, this, DOWN);
                backRef.put(childC.getState(), childC);
                costTo.put(childC.getState(), childC.getCost());
                successors.add(childC);
            }
        }
        // empty space is in position 4
        else if (currentState.get(4) == 0){
            List<Integer> copyCurrentStateA = swap(1, 4, currentState);
            List<Integer> copyCurrentStateB = swap(3, 4, currentState);
            List<Integer> copyCurrentStateC = swap(4, 5, currentState);
            List<Integer> copyCurrentStateD = swap(4, 7, currentState);
            if (!costTo.containsKey(copyCurrentStateA) || ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA)))){
                if ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA))){
                    search.removeNode(backRef.get(copyCurrentStateA));
                }
                childA = new Node(copyCurrentStateA, this, UP);
                backRef.put(childA.getState(), childA);
                costTo.put(childA.getState(), childA.getCost());
                successors.add(childA);
            }
            if (!costTo.containsKey(copyCurrentStateB) || ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB)))){
                if ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB))){
                    search.removeNode(backRef.get(copyCurrentStateB));
                }
                childB = new Node(copyCurrentStateB, this, LEFT);
                backRef.put(childB.getState(), childB);
                costTo.put(childB.getState(), childB.getCost());
                successors.add(childB);
            }
            if (!costTo.containsKey(copyCurrentStateC) || ((costTo.containsKey(copyCurrentStateC) && (cost + 1) < costTo.get(copyCurrentStateC)))){
                if ((costTo.containsKey(copyCurrentStateC) && (cost + 1) < costTo.get(copyCurrentStateC))){
                    search.removeNode(backRef.get(copyCurrentStateC));
                }
                childC = new Node(copyCurrentStateC, this, RIGHT);
                backRef.put(childC.getState(), childC);
                costTo.put(childC.getState(), childC.getCost());
                successors.add(childC);
            }
            if (!costTo.containsKey(copyCurrentStateD) || ((costTo.containsKey(copyCurrentStateD) && (cost + 1) < costTo.get(copyCurrentStateD)))){
                if ((costTo.containsKey(copyCurrentStateD) && (cost + 1) < costTo.get(copyCurrentStateD))){
                    search.removeNode(backRef.get(copyCurrentStateD));
                }
                childD = new Node(copyCurrentStateD, this, DOWN);
                backRef.put(childD.getState(), childD);
                costTo.put(childD.getState(), childD.getCost());
                successors.add(childD);
            }
        }
        // empty space is in position 5
        else if (currentState.get(5) == 0){
            List<Integer> copyCurrentStateA = swap(2, 5, currentState);
            List<Integer> copyCurrentStateB = swap(4, 5, currentState);
            List<Integer> copyCurrentStateC = swap(5, 8, currentState);
            if (!costTo.containsKey(copyCurrentStateA) || ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA)))){
                if ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA))){
                    search.removeNode(backRef.get(copyCurrentStateA));
                }
                childA = new Node(copyCurrentStateA, this, UP);
                backRef.put(childA.getState(), childA);
                costTo.put(childA.getState(), childA.getCost());
                successors.add(childA);
            }
            if (!costTo.containsKey(copyCurrentStateB) || ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB)))){
                if ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB))){
                    search.removeNode(backRef.get(copyCurrentStateB));
                }
                childB = new Node(copyCurrentStateB, this, LEFT);
                backRef.put(childB.getState(), childB);
                costTo.put(childB.getState(), childB.getCost());
                successors.add(childB);
            }
            if (!costTo.containsKey(copyCurrentStateC) || ((costTo.containsKey(copyCurrentStateC) && (cost + 1) < costTo.get(copyCurrentStateC)))){
                if ((costTo.containsKey(copyCurrentStateC) && (cost + 1) < costTo.get(copyCurrentStateC))){
                    search.removeNode(backRef.get(copyCurrentStateC));
                }
                childC = new Node(copyCurrentStateC, this, DOWN);
                backRef.put(childC.getState(), childC);
                costTo.put(childC.getState(), childC.getCost());
                successors.add(childC);
            }
        }
        // empty space is in position 6
        else if (currentState.get(6) == 0){
            List<Integer> copyCurrentStateA = swap(3, 6, currentState);
            List<Integer> copyCurrentStateB = swap(6, 7, currentState);
            if (!costTo.containsKey(copyCurrentStateA) || ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA)))){
                if ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA))){
                    search.removeNode(backRef.get(copyCurrentStateA));
                }
                childA = new Node(copyCurrentStateA, this, UP);
                backRef.put(childA.getState(), childA);
                costTo.put(childA.getState(), childA.getCost());
                successors.add(childA);
            }
            if (!costTo.containsKey(copyCurrentStateB) || ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB)))){
                if ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB))){
                    search.removeNode(backRef.get(copyCurrentStateB));
                }
                childB = new Node(copyCurrentStateB, this, RIGHT);
                backRef.put(childB.getState(), childB);
                costTo.put(childB.getState(), childB.getCost());
                successors.add(childB);
            }
        }
        // empty space is in position 7
        else if (currentState.get(7) == 0){
            List<Integer> copyCurrentStateA = swap(4, 7, currentState);
            List<Integer> copyCurrentStateB = swap(6, 7, currentState);
            List<Integer> copyCurrentStateC = swap(7, 8, currentState);
            if (!costTo.containsKey(copyCurrentStateA) || ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA)))){
                if ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA))){
                    search.removeNode(backRef.get(copyCurrentStateA));
                }
                childA = new Node(copyCurrentStateA, this, UP);
                backRef.put(childA.getState(), childA);
                costTo.put(childA.getState(), childA.getCost());
                successors.add(childA);
            }
            if (!costTo.containsKey(copyCurrentStateB) || ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB)))){
                if ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB))){
                    search.removeNode(backRef.get(copyCurrentStateB));
                }
                childB = new Node(copyCurrentStateB, this, LEFT);
                backRef.put(childB.getState(), childB);
                costTo.put(childB.getState(), childB.getCost());
                successors.add(childB);
            }
            if (!costTo.containsKey(copyCurrentStateC) || ((costTo.containsKey(copyCurrentStateC) && (cost + 1) < costTo.get(copyCurrentStateC)))){
                if ((costTo.containsKey(copyCurrentStateC) && (cost + 1) < costTo.get(copyCurrentStateC))){
                    search.removeNode(backRef.get(copyCurrentStateC));
                }
                childC = new Node(copyCurrentStateC, this, RIGHT);
                backRef.put(childC.getState(), childC);
                costTo.put(childC.getState(), childC.getCost());
                successors.add(childC);
            }
        }
        // empty space is in position 8
        else if (currentState.get(8) == 0){
            List<Integer> copyCurrentStateA = swap(5, 8, currentState);
            List<Integer> copyCurrentStateB = swap(7, 8, currentState);
            if (!costTo.containsKey(copyCurrentStateA) || ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA)))){
                if ((costTo.containsKey(copyCurrentStateA) && (cost + 1) < costTo.get(copyCurrentStateA))){
                    search.removeNode(backRef.get(copyCurrentStateA));
                }
                childA = new Node(copyCurrentStateA, this, UP);
                backRef.put(childA.getState(), childA);
                costTo.put(childA.getState(), childA.getCost());
                successors.add(childA);
            }
            if (!costTo.containsKey(copyCurrentStateB) || ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB)))){
                if ((costTo.containsKey(copyCurrentStateB) && (cost + 1) < costTo.get(copyCurrentStateB))){
                    search.removeNode(backRef.get(copyCurrentStateB));
                }
                childB = new Node(copyCurrentStateB, this, LEFT);
                backRef.put(childB.getState(), childB);
                costTo.put(childB.getState(), childB.getCost());
                successors.add(childB);
            }
        }
        return successors;
    }
    
    /*
    Returns true if the currentState is equal to the goalState.  Meaning the winning state.
    */
    public boolean isGoal(){
        return currentState.equals(goalState);
    }
    
    /*
    Accessor to return the cost of the moves to reach this Node.
    */
    public int getCost(){
        return cost;
    }
    
    /*
    Accessor that returns the parent Node of this Node.
    */
    public Node getParent(){
        return parent;
    }
    
    /*
    Accessor that returns a List representing the current state of the board.
    */
    public List<Integer> getState(){
        return currentState;
    }
    
    /*
    Swaps the values in cS at  int position posA and int position posB 
    and returns a new List with the swapped values.
    */
    private List<Integer> swap(int posA, int posB, List<Integer> cS){
        List<Integer> swappedList = new ArrayList(cS.size());
        for (Integer i : cS) swappedList.add(i);
        Integer temp = swappedList.get(posA);
        swappedList.set(posA, swappedList.get(posB));
        swappedList.set(posB, temp);
        return swappedList;
    }
    
    /*
    Calculates the heuristic for the node by determining how many tiles are misplaces other than the empty tile.
    */
    public void initHeuristic(){
        for (int i = 0; i < 9; i++){
            if ((goalState.get(i).equals(currentState.get(i))) && (goalState.get(i).equals(0))){
                heuristic++;
            }
        }
    }
    
    /*
    Accessor to retrieve the heuristic value.
    */
    public int getHeuristic(){
        return heuristic;
    }
    
    /*
    Modifier for the fOfNFlag, which determines how Nodes will be compared in the Comparator's compare function
    (See below)
    */
    public void set_fOfNFlag(boolean b){
        fOfNFlag = b;
    }

    /*
    Comparator's compare function to evaluate which Node takes priority between two Nodes.
    When fOfNFlag is set, the Nodes are compared by f(n) where f(n) = g(n) + h(n).  Otherwise Nodes are
    compared by f(n) = h(n).
    */
    @Override
    public int compare(Object t, Object t1) {
        Node a = (Node) t;
        Node b = (Node) t1;
        if (fOfNFlag){
            return (a.getHeuristic() + a.getCost()) - (b.getHeuristic() + b.getCost());
        }
        return a.getHeuristic() - b.getHeuristic();
    }
    
    /*
    Accessor method to retrieve the int representation of the previous move that led to this state.
    */
    public int getPriorMove(){
        return priorMove;
    }
    
    public void setSearch(Search s){
        search = s;
    }

}

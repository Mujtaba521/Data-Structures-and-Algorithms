
import java.util.Iterator;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mujtaba Khan
 */
public class DepthFirstSearch {
    
    RouteGraph inputGraph;
    Stack<Intersection> stack;
    
    public DepthFirstSearch(RouteGraph graph){
        this.inputGraph = graph;
        this.stack = new Stack();
    }
    
    public Stack<Intersection> path(Intersection startVertex, Intersection endVertex) throws GraphException{
        this.stack = new Stack<>();
        pathRec(startVertex, endVertex);
        return this.stack;
    }
    
    public boolean pathRec(Intersection startVertex, Intersection endVertex) throws GraphException{
        Iterator<Road> roads = inputGraph.incidentRoads(startVertex);
        startVertex.setMark(true);
        stack.push(startVertex);
        
        if(startVertex == endVertex){
            return true;
        }
        while(roads.hasNext()){
            if (!roads.next().getFirstEndpoint().getMark()){
                if(pathRec(roads.next().getFirstEndpoint(), endVertex)){
                    return true;
                }
            }
        }
        stack.pop();
        return false;
    }
}

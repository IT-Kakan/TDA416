
import java.util.*;

public class DirectedGraph<E extends Edge> {

	LinkedList<E>[] adjacencyList; //We expect the graph to have a low density
	
	/**
	 * Creates a DirectedGraph containing the given number of nodes.
	 * @param noOfNodes The number of nodes the graph should contain
	 */
	public DirectedGraph(int noOfNodes) {
		adjacencyList = (LinkedList<E>[]) new LinkedList[noOfNodes];
	}

	/**
	 * Adds an edge to the graph
	 * @param e The edge to be added
	 */
	public void addEdge(E e) {
		if (adjacencyList[e.getSource()] == null) {
			adjacencyList[e.getSource()] = new LinkedList<>();
		}
		adjacencyList[e.getSource()].add(e);
	}

	/**
	 * Calculates the shortest path between two nodes.
	 * @param from The start node
	 * @param to The destination node
	 * @return An iterator containing the shortest path from the start node to the destination node. 
	 */
	public Iterator<E> shortestPath(int from, int to) {
		return CompDijkstraPath.calculateShortestPath(from, to, adjacencyList);
	}
		
	/**
	 * Calculates the minimum spanning tree
	 * @return An iterator containing the minimum spanning tree
	 */
	public Iterator<E> minimumSpanningTree() {
		return CompKruskalEdge.calculateMST(adjacencyList);
	}
}
  

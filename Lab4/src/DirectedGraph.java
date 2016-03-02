
import java.util.*;

public class DirectedGraph<E extends Edge> {

	LinkedList<ListNode>[] adjacencyList; //We expect the graph to have a low density
	
	/**
	 * Creates a DirectedGraph containing the given number of nodes.
	 * @param noOfNodes The number of nodes the graph should contain
	 */
	public DirectedGraph(int noOfNodes) {
		adjacencyList = (LinkedList<ListNode>[])new Object[noOfNodes];
	}

	/**
	 * Adds an edge to the graph
	 * @param e The edge to be added
	 */
	public void addEdge(E e) {
		ListNode connection = new ListNode(e.getDest(), e.getWeight());
		LinkedList<ListNode> previousEdges = adjacencyList[e.getSource()];
		
		if (!previousEdges.isEmpty()) {
			previousEdges.getLast().setNext(connection);
		}
		
		previousEdges.add(connection);
	}

	/**
	 * Calculates the shortest path between two nodes.
	 * @param from The start node
	 * @param to The destination node
	 * @return An iterator containing the shortest path from the start node to the destination node. 
	 */
	public Iterator<E> shortestPath(int from, int to) {
		return null;
	}
		
	/**
	 * Calculates the minimum spanning tree
	 * @return An iterator containing the minimum spanning tree
	 */
	public Iterator<E> minimumSpanningTree() {
		return null; //(CompKruskalEdge.kruskalsAlgorithm(this)).iterator();
	}
	
	class ListNode {
		int destination;
		double weight;
		ListNode next;
		
		private ListNode(int destination, double weight) {
			this(destination, weight, null);
		}
		
		private ListNode(int destination, double weight, ListNode next) {
			this.destination = destination;
			this.weight = weight;
			this.next = next;
		}
		
		private void setNext(ListNode newNext) {
			this.next = newNext;
		}
		
		ListNode getNext() {
			return next;
		}
		
		double getWeight() {
			return weight;
		}
	}
}
  

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;


public class CompKruskalEdge {
	
	
	/*public static <E extends DirectedGraph<Edge>.ListNode> Edge compareEdges(LinkedList<E> edges) {
		
		if (edges.isEmpty()) {
			return null;
		}
		DirectedGraph<E>.ListNode current = edges.getFirst();
		DirectedGraph<E>.ListNode minimumEdge = null;
		double minimumWeight = Double.POSITIVE_INFINITY;
		
		while (current.getNext() != null) {
			if (current.getWeight() < minimumWeight) {
				minimumEdge = current;
				minimumWeight = minimumEdge.getWeight();
			}
			current = current.getNext();
		}
		return minimumEdge;
	}*/
	
	
	public static <E extends Edge> Iterator<E> calculateMST (List<E>[] adjacencyList) {
		//Calculates a minimum spanning tree using an implementation of Kruskal's algorithm
		/*
		 * 0
			skapa ett f�lt cc som f�r varje nod 
			inneh�ller en egen tom lista (som skall
			inneh�lla b�gar s� sm�ningom)
			(dvs varje nod �r i en egen komponent)
			
			L�gg in alla b�gar i en prioritetsk�
			S� l�nge pq, ej �r tom && |cc| < n
				h�mta e = (from, to, weight) fr�n k�n
				
				om from och to inte refererar till samma lista i cc
					flytta �ver alla elementen fr�n den
					kortare listan till den andra och se till 
					att alla ber�rda noder i cc refererar 
					till den p�fyllda listan
					
				l�gg slutligen e i den p�fyllda listan
		 */
		
		int numberOfNodes = adjacencyList.length;
		
		List<E>[] connectedComponents = initComponents(numberOfNodes);
		
		PriorityQueue<E> weightedEdges = new PriorityQueue<>(new WeightComparator<E>());
		
		addAllEdges(weightedEdges, adjacencyList);
		
		while (!weightedEdges.isEmpty() && isLargestListSmaller(connectedComponents, numberOfNodes)) {
			E currentEdge = weightedEdges.poll();
			int from = currentEdge.getSource();
			int to = currentEdge.getDest();
			List<E> fromEdges = connectedComponents[from];
			List<E> toEdges = connectedComponents[to];
			
			if (fromEdges != toEdges) {
				if (fromEdges.size() <= toEdges.size()) {
					transferEdges(fromEdges, toEdges);
					
					relink(connectedComponents, fromEdges, toEdges);
				} else {
					transferEdges(toEdges, fromEdges);
					relink(connectedComponents, toEdges, fromEdges);
				}
			}
			connectedComponents[from].add(currentEdge);
			System.out.println("from: " + from + " | " + "to: " + to);
			for (int i = 0; i < numberOfNodes; i++) {
				System.out.println(i + ": " + connectedComponents[i]);
			}
		}

		//TODO: REMOVE
		List<E> reference = connectedComponents[0];
		/*for(List<E> edges: connectedComponents) {
			if (edges != reference) {
				throw new InternalError("Error in method kruskalsAlgorithm(): not all components references the same list.");
			}
		}
		//------------------
*/
		return reference.listIterator();
	}
	
	private static<E extends Edge> void relink(List<E>[] connectedComponents, List<E> oldList, List<E> newList) {
		List<Integer> index = new ArrayList<>();
		
		for (int i = 0; i < connectedComponents.length; i++) {
			if (oldList == connectedComponents[i]) {
				index.add(i);
			}
		}
		
		for (Integer i : index) {
			connectedComponents[i] = newList;
		}
		
		
		
		
		/*
		List<E> reference = component;
		
		for (int i = 0; i < connectedComponents.length; i++) {
			if (reference == connectedComponents[i]) {
				connectedComponents[i] = toEdges;
			}
		}*/
	}

	private static <E extends Edge> List<E>[] initComponents(int numberOfNodes) {
		 List <E>[] temp = (List<E>[]) new List[numberOfNodes];
		 for (int i = 0; i < numberOfNodes; i++) {
			 temp[i] = new LinkedList<E>();
		 }
		 return temp;
	}

	/**
	 * Checks whether the largest list is smaller than the specified value.
	 * @param components The lists to check
	 * @param value The value to compare to
	 * @return True if the size of the largest list is strictly less than the specified value. False otherwise
	 */
	private static <E extends Edge> boolean isLargestListSmaller(List<E>[] components, int value) {
		int currentLargest = -1;
		for (List<E> component: components) {
			if (component != null) {
				if (component.size() > currentLargest) {
					currentLargest = component.size();
				}
			}
		}
		return currentLargest < value-1;
	}

	/**
	 * Removes all elements in from and append these to to
	 * @param from The list to transfer elements from
	 * @param to The list to transfer elements to
	 */
	private static <E extends Edge> void transferEdges(List<E> from, List<E> to) {		
		while (!from.isEmpty()) {
			E edge = from.remove(0);
			to.add(edge);
		}
	}

	/**
	 * Adds all edges from an array of lists to a prioriy queue.
	 * @param queue The priority queue
	 * @param lists The list
	 */
	private static <E extends Edge> void addAllEdges(PriorityQueue<E> queue, List<E>[] lists) {
		for (List list: lists) {
			if (list != null) {
				queue.addAll(list);
			}
		}
	}

	/**
	 * Comparator class for comparing Edges by weight.
	 *
	 * @param <T>
	 */
	private static class WeightComparator<T extends Edge> implements Comparator<T> {

		/**
		 * {@inheritDoc}}
		 */
		@Override
		public int compare(T o1, T o2) {
			double firstWeight = o1.getWeight();
			double secondWeight = o2.getWeight();
			
			if (firstWeight < secondWeight) {
				return -1;
			} else if (firstWeight > secondWeight) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}

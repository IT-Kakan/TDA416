import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class CompDijkstraPath {
/*lägg (startnod, 0, tom väg) i en p-kö
while kön inte är tom
	(nod, cost, path) = första elementet i p-kön
	if nod ej är besökt
		if nod är slutpunkt returnera path
		else 
			markera nod besökt
			for every v on EL(nod)
				if v ej är besökt
					lägg in nytt köelement för v i p-kön*/	
	
	public static <E extends Edge> Iterator<E> calculateShortestPath(int start, int end, List<E>[] adjacencyList) {
		boolean[] visitedNodes = new boolean[adjacencyList.length];
		Set<E> visitedEdges = new HashSet<E>(); 
		//Edges are stored differently due to unknown amount of edges
		
		PriorityQueue<QueueElement<E>> queue = new PriorityQueue<>(new CostComparator<CompDijkstraPath.QueueElement<E>>());
		queue.add(new QueueElement<E>(start, 0, new LinkedList<E>())); //TODO 0 is start node?
		
		while (!queue.isEmpty()) {
			QueueElement<E> element = queue.poll();
			if (!visitedNodes[element.node]) { //if node not visited
				if (element.node == end) { 
					return element.path.listIterator();
				} else {
					visitedNodes[element.node] = true;
					
					for (E edge : adjacencyList[element.node]) {
						if (!visitedEdges.contains(edge)) {
							visitedEdges.add(edge);
							
							int node = edge.to;
							double cost = element.cost + edge.getWeight();
							LinkedList<E> path = (LinkedList<E>)element.path.clone();
							path.add(edge);
							queue.add(new QueueElement<>(node, cost, path));
						}
					}
				}
			}
		}
		return null;
	}
	
	private static class QueueElement<E extends Edge> {
		/*Ett köelement skall innehålla (to, cost, path):
		- noden man kommit till,
		- kostnaden för vägen dit från startnoden och
		- vägen dit från startpunkten */
		
		/* The node arrived at */
		int node;
		/* The cost of getting there from the start node */
		double cost;
		/* The path from the start node */
		LinkedList<E> path;
		
		private QueueElement(int node, double cost, LinkedList<E> path) {
			this.node = node;
			this.cost = cost;
			this.path = path;
		}
	}
	
	private static class CostComparator<T extends QueueElement<? extends Edge>> implements Comparator<T> {

		@Override
		public int compare(T o1, T o2) {
			if (o1.cost < o2.cost) {
				return -1;
			} else if (o1.cost > o2.cost) {
				return 1;
			} else {
				return 0;
			}
		}
		
	}
}
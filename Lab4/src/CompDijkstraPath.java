import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

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
	
	public static <E extends Edge> Iterator<E> calculateShortestPath(List<E>[] adjacencyList) {
		int[] visitedNodes = new int[adjacencyList.length];
		
		PriorityQueue<QueueElement<E>> queue = new PriorityQueue<>();
		queue.add(new QueueElement<E>(0, 0, new LinkedList<E>())); //TODO 0 is start node?
		
		while (!queue.isEmpty()) {
			QueueElement<E> element = queue.poll();
			//if (node !visited) {
			if (adjacencyList[element.node].isEmpty()) {
				return element.path.listIterator();
			} else {
				//mark node as visited
				for (E edge : adjacencyList[element.node]) {
					//if (edge !visited) {
					int node = edge.to;
					double cost = element.cost + edge.getWeight(); //TODO Correct way?
					LinkedList<E> path = (LinkedList<E>)element.path.clone();
					path.add(edge);
					queue.add(new QueueElement<>(node, cost, path));
					//}
				}
			}
			//}
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
}
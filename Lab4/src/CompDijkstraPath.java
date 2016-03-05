import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class CompDijkstraPath {
/*l�gg (startnod, 0, tom v�g) i en p-k�
while k�n inte �r tom
	(nod, cost, path) = f�rsta elementet i p-k�n
	if nod ej �r bes�kt
		if nod �r slutpunkt returnera path
		else 
			markera nod bes�kt
			for every v on EL(nod)
				if v ej �r bes�kt
					l�gg in nytt k�element f�r v i p-k�n*/	
	
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
		/*Ett k�element skall inneh�lla (to, cost, path):
		- noden man kommit till,
		- kostnaden f�r v�gen dit fr�n startnoden och
		- v�gen dit fr�n startpunkten */
		
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
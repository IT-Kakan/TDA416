import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import DirectedGraph.ListNode;


public class CompKruskalEdge {
	
	public static Edge compareEdges(LinkedList<ListNode> edges) {
		if (edges.isEmpty()) {
			return null;
		}
		ListNode current = edges.getFirst();
		ListNode minimumEdge = null;
		double minimumWeight = Double.POSITIVE_INFINITY;
		
		while (current.getNext() != null) {
			if (current.getWeight() < minimumWeight) {
				minimumEdge = current;
				minimumWeight = minimumEdge.getWeight();
			}
			current = current.getNext();
		}
		return minimumEdge;
	}
	

	
	private class WeightComparator<T extends Edge> implements Comparator<T> {

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

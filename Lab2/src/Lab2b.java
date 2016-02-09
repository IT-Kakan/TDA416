import java.util.Comparator;
import java.util.PriorityQueue;

public class Lab2b {

	/**
	 * Method to simplify a given shape by removing the least significant points until only k points remains.
	 * Pre: poly contains coordinates such that poly[0] is the x-coordinate of the first point, poly[1] is the
	 * y-coordinate of the first point, poly[2] is the x-coordinate of the second point and so on.
	 * Pre: poly has a length of at least 4.
	 * Pre: each element in poly is >= 0.
	 * Post: poly is unaltered.
	 * @param poly The points containing the shape to simplify.
	 * @param k the number of points that the simplified shape should contain.
	 * @return An array containing the simplified shape.
	 */
	public static double[] simplifyShape(double[] poly, int k) {
		DLList<Double> list = new DLList<Double>();
		PriorityQueue<DLList<Double>.Node> queue = new PriorityQueue<>(new NodeComparator());
		
		//Add all x- and y-coordinates to a DLList with nodes
		for (double d : poly) {
			list.addLast(d);
		}
		
		//Add all x-coordinates to the priority queue,
		//except for the very first and last points (should not be removed).
		DLList<Double>.Node node = list.getFirst().next.next;
		while (node.next.next != null) {
			queue.add(node);
			node = node.next.next;	
		}
		
		//while (first point + queue.size() + last point < k)
		while (queue.size()+2 > k){
			DLList<Double>.Node toRemove = queue.poll();
			
			//Remove x- and y-coordinates from list
			list.remove(toRemove);
			list.remove(toRemove.next);
			
			//Remove and re-add previous point from queue
			if (toRemove.prev.prev != list.getFirst()) {
				queue.remove(toRemove.prev.prev);
				queue.add(toRemove.prev.prev);
			}
			
			if (toRemove.next.next != list.getLast().prev) {
				queue.remove(toRemove.next.next);
				queue.add(toRemove.next.next);
			}
		}
		
		double[] toReturn = new double[2*k];
		//Add first element in list to array, and then remove it from the list
		for (int i = 0; i < toReturn.length; i++) {
			toReturn[i] = list.getFirst().elt;
			list.remove(list.getFirst());
		}
		
		return toReturn;
	}
	
	/**
	 * Compares Nodes on their significance value.
	 */
	private static class NodeComparator implements Comparator<DLList<Double>.Node> {
		
		/**
		 * @inheritDoc
		 */
		@Override
		public int compare(DLList<Double>.Node arg0, DLList<Double>.Node arg1) {
			double difference = calculateSignificance(arg0) - calculateSignificance(arg1);
			
			if (difference < 0) {
				return -1;
			} else if (difference > 0) {
				return 1;
			} else {
				return 0;
			}
		}

		/**
		 * Calculates the significance of the given Node by adding the distance to the previous and next node
		 * and subtracting the distance between the left and the right node.
		 * @param node The Node to calculate the significance of.
		 * @return the significance
		 */
		private double calculateSignificance(DLList<Double>.Node node) {
			double leftX = node.prev.prev.elt;
			double leftY = node.prev.elt;
			double middleX = node.elt;
			double middleY = node.next.elt;
			double rightX = node.next.next.elt;
			double rightY = node.next.next.next.elt;			
			
			//L-P = sqrt( (x_L - x_P)^2 + (y_L - y_P)^2 )
			double l1 = Math.sqrt(Math.pow(leftX - middleX, 2) + 
					Math.pow(leftY - middleY, 2));
			
			//P-R = sqrt( (x_P - x_R)^2 + (y_P - y_R)^2 )
			double l2 = Math.sqrt(Math.pow(middleX - rightX, 2) + 
					Math.pow(middleY - rightY, 2));
			
			//L-R = sqrt( (x_L - x_R)^2 + (y_L - y_R)^2 )
			double l3 = Math.sqrt(Math.pow(leftX - rightX, 2) + 
					Math.pow(leftY - rightY, 2));
			
			return l1 + l2 - l3;
		}
	}
}

import java.util.Comparator;
import java.util.PriorityQueue;

public class Lab2b {

	public static double[] simplifyShape(double[] poly, int k) {
		DLList<Double> list = new DLList<Double>();
		PriorityQueue<DLList<Double>.Node> queue = new PriorityQueue<>(new NodeComparator());
		
		for (double d : poly) {
			list.addLast(d);
		}
		
		DLList<Double>.Node node = list.getFirst().next.next;
		while (node.next.next != null) {
			queue.add(node);
			node = node.next.next;	
		}
		
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
		for (int i = 0; i < toReturn.length; i++) {
			DLList<Double>.Node toAdd = list.getFirst();
			toReturn[i] = toAdd.elt;
			list.remove(toAdd);
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

import java.util.Comparator;
import java.util.PriorityQueue;

public class Lab2b {

	public static double[] simplifyShape(double[] poly, int k) { 		
		DLList<Double> list = new DLList<Double>();
		PriorityQueue<DLList<Double>.Node> queue = new PriorityQueue<>(new NodeComparator());
		
		for (int i = 0; i < poly.length; i++) {
			list.addLast(poly[i]);
			
			//If x-coordinate, add to queue
			if (i % 2 == 0) {
				queue.add(list.getLast());
			}
		}
		
		for (; k > 0; k--) {
			DLList<Double>.Node toRemove = queue.poll();
			
			//Remove x- and y-coordinates from list
			list.remove(toRemove);
			list.remove(toRemove.next);
			
			//Remove and re-add previous point from queue
			queue.remove(toRemove.prev);
			queue.add(toRemove.prev);
			queue.remove(toRemove.next);
			queue.add(toRemove.next);
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
			return (int)(calculateSignificance(arg0) - calculateSignificance(arg1));
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
			
			//L-P
			double l1 = Math.sqrt(Math.pow(leftX - middleX, 2) + 
					Math.pow(leftY - middleY, 2));
			
			//P-R
			double l2 = Math.sqrt(Math.pow(middleX - rightX, 2) + 
					Math.pow(middleY - rightY, 2));
			
			//L-R
			double l3 = Math.sqrt(Math.pow(leftX - rightX, 2) + 
					Math.pow(leftY - rightY, 2));
			
			return l1 + l2 - l3;
		}
		
	}
}

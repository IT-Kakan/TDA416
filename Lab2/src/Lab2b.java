import java.util.Comparator;
import java.util.PriorityQueue;

public class Lab2b {

	public static double[] simplifyShape(double[] poly, int k) { 		
		DLList list = new DLList<Double>();
		for (double d : poly) {
			list.addLast(d);
		}
		
		PriorityQueue<DLList<Double>.Node> queue = new PriorityQueue<>(new NodeComparator());
		
		
		return new double[8];
	}

	private static class NodeComparator implements Comparator<DLList<Double>.Node> {
		@Override
		public int compare(DLList<Double>.Node arg0, DLList<Double>.Node arg1) {
			return (int)(calculateSignificance(arg0) - calculateSignificance(arg1));
		}

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


public class Lab2a {
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
		double[] simplified = poly.clone();
		
		while (simplified.length / 2 > k) {
			//Calculate second point, since the first point is not allowed to be removed
			int indexToRemove = 2;
			double toRemoveSignificance = calculateSignificance(simplified, indexToRemove);
			
			//calculate the least significant point
			//ends before the last point since it is not allowed to be removed
			for (int i = 4; i < simplified.length-2; i+=2) {
				double currentSignificance = calculateSignificance(simplified, i);
				
				if (currentSignificance < toRemoveSignificance) {
					indexToRemove = i;
					toRemoveSignificance = currentSignificance;
				}
			}
			
			//remove least significant point.
			double[] temp = new double[simplified.length-2];
			int newIndex = 0;
			for (int i = 0; i < simplified.length; i+=2) {
				if (i != indexToRemove) {
					temp[newIndex] = simplified[i]; //Copy x
					temp[newIndex+1] = simplified[i+1]; //Copy y
					newIndex += 2;
				}
			}
			simplified = temp.clone();
		}
		return simplified;
	}
	
	/**
	 * Calculates the significance of the given index.
	 * @param arr the array which contains the index.
	 * @param index The index to calculate the significance for
	 * @return the significance, always >= 0.
	 */
	private static double calculateSignificance(double[] arr, int index) {
		
		//L-P = sqrt( (x_L - x_P)^2 + (y_L - y_P)^2 )
		double l1 = Math.sqrt(Math.pow(arr[index-2] - arr[index], 2) +
				Math.pow(arr[index-1] - arr[index+1], 2));  
				
		//P-R = sqrt( (x_P - x_R)^2 + (y_P - y_R)^2 )
		double l2 = Math.sqrt(Math.pow(arr[index] - arr[index+2], 2) +
				Math.pow(arr[index+1] - arr[index+3], 2));
		
		//L-R = sqrt( (x_L - x_R)^2 + (y_L - y_R)^2 )
		double l3 = Math.sqrt(Math.pow(arr[index-2] - arr[index+2], 2) +
				Math.pow(arr[index-1] - arr[index+3], 2));
		
		return l1 + l2 - l3;
		
	}
}

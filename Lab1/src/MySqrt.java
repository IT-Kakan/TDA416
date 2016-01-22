
public class MySqrt {
	private static final double EPSILON = 10e-6;
	
	/**
	 * Iteratively calculates the square root of x.
	 * @param x The value to take the square root of.
	 * @param epsilon The allowed margin of error.
	 * @return The square root of x, approximated to within the margin of error.
	 */
	public static double mySqrtLoop(double x, double epsilon) {
		double min;
		double max;
		
		if (x < 0) {
			return Double.NaN;
		} else if (x <= 1) {
			min = x;
			max = 1;
		} else {
			min = 1;
			max = x;
		}
		
		double mid = (min + max)/2;
		//Shrink interval while difference bigger than epsilon
		while (Math.abs(mid * mid - x) >= epsilon) {
			mid = (min + max)/2;
						
			if (mid * mid > x) {
				//keep lower bound
				max = mid;
			} else {
				//keep upper bound
				min = mid;
			}
		}		
		return mid;
	}

	/**
	 * Recursively calculates the square root of x.
	 * @param x The value to take the square root of.
	 * @param epsilon The allowed margin of error.
	 * @return The square root of x, approximated to within the margin of error.
	 */
	public static double mySqrtRecurse(double x, double epsilon) {
		double min;
		double max;
		
		if (x < 0) {
			return Double.NaN;
		} else if (x <= 1) {
			min = x;
			max = 1;
		} else {
			min = 1;
			max = x;
		}
		return mySqrtRecurse(x, epsilon, min, max);
	}
	
	/**
	 * Helper method for mySqrtRecurse.
	 * @param x The value to take the square root of.
	 * @param epsilon The allowed margin of error.
	 * @param min Lower bound of the interval which contains the correct value.
	 * @param max Upper bound of the interval which contains the correct value.
	 * @return The square root of x, approximated to within the margin of error.
	 */
	private static double mySqrtRecurse(double x, double epsilon, double min, double max) {
		double mid = (min + max)/2;
		
		if (Math.abs(mid * mid - x) < epsilon) {
			return mid;
		} else {
			if (mid * mid > x) {
				//keep lower bound
				return mySqrtRecurse(x, epsilon, min, mid);
			} else {
				//keep upper bound
				return mySqrtRecurse(x, epsilon, mid, max);
			}
		}
	}

	public static void main(String[] args) {
		final double[] TEST_VALUES = {
			//Test edge cases
			0, 1,
			//Test negative value
			-10,
			//Test known root in x > 1
			81,
			//Test known value in 0 < x < 1
			0.81
		};
		
		
		for (double value : TEST_VALUES) {
			double correctValue = Math.sqrt(value);
			double loopValue = mySqrtLoop(value, EPSILON);
			double recurseValue = mySqrtRecurse(value, EPSILON);
			
			System.out.println("\nTesting value " + value + ". Correct root is " + correctValue + ".");
			
			//Test mySqrtLoop()
			if (!(loopValue * loopValue - value >= EPSILON)) {
				System.out.println("mySqrtLoop passed, with result: " + loopValue);
			} else {
				System.out.println("########## mySqrtLoop failed, with result: " + loopValue);	
			}
			
			//Test mySqrtRecurse()
			if (!(Math.abs(recurseValue * recurseValue - value) >= EPSILON)) {
				System.out.println("mySqrtRecurse passed, with result: " + recurseValue);
			} else {
				System.out.println("########## mySqrtRecurse failed, with result: " + recurseValue);	
			}
		}
	}
}

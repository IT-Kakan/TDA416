import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class TestSetCorrectness {

	public static void test(int implementationNbr, int nbrOfOperations, int nbrOfIntegers) {
		
		Random rand = new Random();
		Set<Integer> reference = new TreeSet<>();
		SimpleSet<Integer> implementation;
		switch (implementationNbr) {
		case 1:
			implementation = new SortedLinkedListSet<>();
			break;

		case 2:
			implementation = new SplayTreeSet<>();
			break;
			
		default:
			throw new IllegalArgumentException("Invalid implementation number.");
		}
		
		for (int i = 0; i < nbrOfOperations; i++) {
			switch (rand.nextInt(4)) {
			//Test size
			case 0:
				if (implementation.size() != reference.size()) {
					System.out.println("Error in method size, implementation: " + implementation.getClass().toString());
					System.out.println("Implementation size: " + implementation.size() + ". Reference size: " + reference.size());
					System.exit(1);
				}
				break;

			//Test add
			case 1:
				int toAdd = rand.nextInt(nbrOfIntegers);
				boolean implementationAdd = implementation.add(toAdd);
				boolean referenceAdd = reference.add(toAdd);
				if (implementationAdd) {
					//Assume contains is correct (is tested below)
					if (!implementation.contains(toAdd)) {
						System.out.println("Error in method add, implementation: " + implementation.getClass().toString());
						System.out.println("Returned true but did not add element.");
						System.exit(1);
					}
				}
				
				if (implementationAdd != referenceAdd) {
					System.out.println("Error in method add, implementation: " + implementation.getClass().toString());
					System.out.println("Value:" + toAdd);
					System.out.println("Implementation add: " + implementationAdd + ". Reference add: " + referenceAdd);
					System.exit(1);
				}
				break;
				
				
			//Test remove
			case 2:
				int toRemove = rand.nextInt(nbrOfIntegers);
				boolean implementationRemove = implementation.remove(toRemove);
				boolean referenceRemove = reference.remove(toRemove);
				
				if (implementationRemove) {
					if (implementation.contains(toRemove)) {
						System.out.println("Error in method remove, implementation: " + implementation.getClass().toString());
						System.out.println("Returned true but did not remove element.");
						System.exit(1);
					}
				}
				
				if (implementationRemove != referenceRemove) {
					System.out.println("Error in method remove, implementation: " + implementation.getClass().toString());
					System.out.println("Value:" + toRemove);
					System.out.println("Implementation remove: " + implementationRemove + ". Reference remove: " + referenceRemove);
					System.exit(1);
				}
				break;
				
			//Test contains
			case 3:
				int toCheck = rand.nextInt(nbrOfIntegers);
				boolean implementationContains= implementation.contains(toCheck);
				boolean referenceContains = reference.contains(toCheck);
				
				if (implementationContains != referenceContains) {
					System.out.println("Error in method contains, implementation: " + implementation.getClass().toString());
					System.out.println("Value:" + toCheck);
					System.out.println("Implementation contains: " + implementationContains + ". Reference contains: " + referenceContains);
					System.exit(1);
				}
				break;
				
			default:
				break;
			} 
		}
		
		
	}
	
	public static void main(String[] args) {
		int implementationNbr = Integer.parseInt(args[0]);
		int nbrOfRuns = Integer.parseInt(args[1]);
		int nbrOfOperations = Integer.parseInt(args[2]);
		int nbrOfIntegers = Integer.parseInt(args[3]);
		for (int i = 0; i < nbrOfRuns; i++) {
			test(implementationNbr, nbrOfOperations, nbrOfIntegers);
		}
		
	}

}

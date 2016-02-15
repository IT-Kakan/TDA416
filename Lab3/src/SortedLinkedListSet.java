import java.util.LinkedList;
import java.util.ListIterator;

public class SortedLinkedListSet<E extends Comparable<? super E>> implements SimpleSet<E> {
	
	LinkedList<E> sortedList = new LinkedList<>();


	/**
	 * Returns the number of elements in the set.
	 * @return The number of elements in the set.
	 */
	@Override
	public int size() {
		return sortedList.size();
	}

	/**
	 * Adds the element to the set. If the element already exists, nothing is altered.
	 * @param x The element to be added.
	 * @return True if the element did not exist. False otherwise.
	 */
	@Override
	public boolean add(E x) {
		
		//Only add an element if it is not already in the list.
		if (contains(x)) {
			return false;
		} else {
			
			/*
			//If the element is larger than the largest value in the list, insert the element last (time is O(1)).
			if (x.compareTo(sortedList.getLast()) > 0) {
				sortedList.addLast(x);
				return true;
			}
			
			//If the element is smaller than the smallest value in the list, insert the element first (time is O(1)).
			if (x.compareTo(sortedList.getFirst()) < 0) {
				sortedList.addFirst(x);
				return true;
			}	
			
			*/
 
			
			//Insert the element between the least lesser element and the least greater element.
			//Captures the case where the element is less than all other elements.
			ListIterator<E> iterator = sortedList.listIterator();
			while (iterator.hasNext()) {
				if (x.compareTo(iterator.next()) < 0) {
					sortedList.add(iterator.previousIndex(), x);
					return true;
				}
			}
			//If the element is larger than all elements, insert last.
			//Captures the case where the list is empty.
			sortedList.addLast(x);
			return true;
		}
	}

	/**
	 * Removes the element from the set. If it does not exist, nothing is altered.
	 * @param x The element to remove.
	 * @return True if the element was in the set. False otherwise.
	 */
	@Override
	public boolean remove(E x) {

		ListIterator<E> iterator = sortedList.listIterator();
		while (iterator.hasNext()) {
			if (x.compareTo(iterator.next()) == 0) {
				sortedList.remove(iterator.previousIndex());
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether or not the element is part of the set.
	 * @param x The element to be checked.
	 * @return True is the element is part of the set. False otherwise.
	 */
	@Override
	public boolean contains(E x) {
		
		ListIterator<E> iterator = sortedList.listIterator();
		while (iterator.hasNext()) {
			if (x.compareTo(iterator.next()) == 0) {
				return true;
			}
		}
		return false;
	}
}
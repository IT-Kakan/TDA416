/**
 * A set which maintains elements in ascending order.
 * 
 * @param <E> The type which the set should contain.
 */
public class SortedLinkedListSet<E extends Comparable<? super E>> implements SimpleSet<E> {
	
	SortedLinkedList sortedList;

	/**
	 * Constructs a new empty SortedLinkedListSet.
	 */
	public SortedLinkedListSet() {
		sortedList = new SortedLinkedList();		
	}
	
	/**
	 * Returns the number of elements in the set.
	 * @return The number of elements in the set.
	 */
	@Override
	public int size() {
		return sortedList.getSize();
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
			return sortedList.add(x);
		}
	}

	/**
	 * Removes the element from the set. If it does not exist, nothing is altered.
	 * @param x The element to remove.
	 * @return True if the element was in the set. False otherwise.
	 */
	@Override
	public boolean remove(E x) {
		return sortedList.remove(x);
	}

	/**
	 * Checks whether or not the element is part of the set.
	 * @param x The element to be checked.
	 * @return True is the element is part of the set. False otherwise.
	 */
	@Override
	public boolean contains(E x) {
		return sortedList.contains(x);
	}
	
	private class SortedLinkedList {
		
		private Node head;
		private int size;
		
		private SortedLinkedList() {
			head = null;
			size = 0;
		}
		
		private int getSize() {
			return size;
		}
		
		/**
		 * Adds a new element to the list in its proper place
		 * @param toAdd The value to add
		 * @post The list is sorted
		 * @return True
		 */
		private boolean add(E toAdd) {
			Node newNode = new Node(toAdd);

			//Handles the case of an empty list
			if (head == null) {
				head = newNode;
				size++;
				return true;
			} 
			
			int difference = toAdd.compareTo(head.getData());

			//Handles the case where the element should be the new head
			if (difference < 0) {
				newNode.setNext(head);
				head = newNode;
				size++;
				return true;
			}
			
			Node current = head;
			
			//Handles remaining cases
			while (current.getNext() != null && difference >= 0) {
				difference = toAdd.compareTo(current.getNext().getData());
				current = current.getNext();
			}
			newNode.setNext(current.getNext());
			current.setNext(newNode);
			size++;
			return true;
		}
		
		/**
		 * Removes the first occurrence of the element
		 * @param toRemove The value to remove
		 * @return True if an element was removed, false otherwise
		 */
		private boolean remove(E toRemove) {
			if (head == null) {
				return false;
			}
			
			if (toRemove.compareTo(head.getData()) == 0) {
				head = head.getNext();
				size--;
				return true;
			}
			
			Node current = head;
			int difference;
			
			
			while (current.getNext() != null) {
				difference = toRemove.compareTo(current.getNext().getData());
				if (difference == 0) {
					current.setNext(current.getNext().getNext());
					size--;
					return true;
				} else {
					current = current.getNext();
				}
			}
			
			return false;
		}
		
		/**
		 * Checks whether or not the element exists 
		 * @param toCheck The value to check
		 * @return True if the element exists, false otherwise
		 */
		private boolean contains(E toCheck) {
			Node current = head;
			while (current != null) {
				if (toCheck.compareTo(current.getData()) == 0) {
					return true;
				} else {
					current = current.getNext();
				}
			}
			return false;
		}
		
		private class Node {
			E data;
			Node next;
			
			private Node(E data) {
				this(data, null);
			}
			
			private Node(E data, Node next) {
				this.data = data;
				this.next = next;
			}
			
			private Node getNext() {
				return next;
			}
			
			private void setNext(Node newNode) {
				next = newNode;
			}
			
			private E getData() {
				return data;
			}
		}
	}
}
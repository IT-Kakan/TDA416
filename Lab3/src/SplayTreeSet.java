import com.sun.xml.internal.ws.api.pipe.NextAction;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

/**
 * 
 * A set which maintains elements in splay tree order.
 *
 * @param <E> The type which the set should contain.
 */
public class SplayTreeSet<E extends Comparable<? super E>> implements SimpleSet<E>{

	private SplayTree tree;

	/**
	 * Constructs a new empty SplayTreeSet
	 */
	public SplayTreeSet() {
		tree = new SplayTree();
	}
	
	/**
	 * Returns the number of elements in the set.
	 * @return The number of elements in the set.
	 */
	@Override
	public int size() {
		return tree.getSize();
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
			tree.add(x);
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
		return tree.remove(x);
	}

	/**
	 * Checks whether or not the element is part of the set.
	 * @param x The element to be checked.
	 * @return True is the element is part of the set. False otherwise.
	 */
	@Override
	public boolean contains(E x) {
		if (tree.find(x) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Inner class used for managing the set.
	 *
	 */
	private class SplayTree {
		
		private Node root;
		private int size;
		
		/**
		 * creates an empty splay tree.
		 */
		private SplayTree() {
			root = null;
			size = 0;
		}
		
		/**
		 * Adds a node containing the parameterized value to the tree.
		 * @param x The value to be added.
		 */
		private void add(E x) {
			if (size == 0) {
				root = new Node(x);				
			} else {
				
				Node current = root;
				Node parent;
				int difference;
				//add as for a binary search tree
				do {
					difference = x.compareTo(current.getData());
					parent = current;
					if (difference < 0) {
						current = current.getLeft();
					} else if (difference > 0) {
						current = current.getRight();
					}				
				} while (current != null && difference != 0);
				current = new Node(x, parent);
				splay(current);
			}
			size++;
		}
		
		private boolean remove(E x) {
			//There are three cases to consider:
			//1. The tree is empty
			//2. The element does not exist
			//3. The element exists and, since it was found, is in root.
			
			//Handles the case where the tree is empty.
			if (size == 0) {
				return false;
			}
			
			//Handles the case where the element does not exist.
			if (find(x) == null) {
				return false;
			} else { //Handles the case where the element was found.
				//Assert: the node to remove is in root
				
				//Check if the node to remove has two children
				if (root.getLeft() != null && root.getRight() != null) {
					//traverse to find the next higher value
					Node nextHigher = root.getRight();
					while (nextHigher.getLeft() != null) {
						nextHigher = nextHigher.getLeft();
					}
					
					//nextHigher is now the node containing the next higher value.
					
					//Replace the data in the root with the next higher value
					root.setData(nextHigher.getData());
					
					//Remove the nextHigher node, as its data is duplicated
					Node parent = nextHigher.getParent();
					if (nextHigher.getRight() != null) {
						parent.setLeft(nextHigher.getRight());
					} else {
						parent.setLeft(null);
					}
					splay(parent);


				} else if (root.getLeft() != null && root.getRight() == null) {
					//root has only a left child
					
					root = root.getLeft();
				} else if (root.getRight() != null && root.getLeft() == null) {
					//root has only a right child
					
					root = root.getRight();
				} else { //root has no children
					root = null;
				}
				
				size--;
				return true;				
			}
		}

		
		/**
		 * Finds the next higher value in the tree. 
		 * Side effect: updates the parameter to point to the parent of the returned node.
		 * @param start The starting position. The reference will be updated to point to the parent of the returned node.
		 * @return The node containing the value.
		 */
		private Node getNextHigher(Node start) {
			//Find the next higher key by traversing right once, then left as far as possible.
			//Update parent with each traversing.
			Node nextHigher = start.getRight();
			while (nextHigher.getLeft() != null) {
				start = nextHigher;
				nextHigher = nextHigher.getLeft();
			}
			return nextHigher;
		}
		
		/**
		 * Locates and returns the first node containing the parameterized value.
		 * @param x The value that the node to be found should contain.
		 * @return The first node containing the parameterized value. Null if no such node exists.
		 */
		private Node find(E x) {
			Node next = root;
			int difference = x.compareTo(next.getData());
			while (next != null && difference != 0) {
				if (difference < 0) {
					next = next.getLeft();
				} else if (difference > 0) {
					next = next.getRight();
				}
			}
			if (next == null) {
				return null;
			}
			splay(next);
			return next;
		}		
		
		/**
		 * Rotates the tree in the shortest way until the parameter is the root.
		 * @param node The node to become the new root.
		 */
		private void splay(Node node/*, Node parent, Node grandparent*/) {
			/*E x = node.getData();
			E p = parent.getData();
			E g = grandparent.getData();
			
			
			//Three cases:
			//1. 	a	 O			b	O		if x >= p && p <= g
			//			/		OR		 \
			//		   O				  O		OR
			//			\				 /
			//			 O				O		if x <= p && p >= g
			//
			//
			//
			//2. 	a	 O			b	O		if x <= p && p <= g
			//			/		OR		 \
			//		   O				  O		OR
			//		  /					   \
			//		 O						O	if x >= p && p >= g
			//
			//
			//
			//3. 
			//
			//
			//
			//
			//
			//
			
			
			//1. a
			if ((x.compareTo(p) > 0 && p.compareTo(g) < 0)) {
				//We first rotate X and P left,
				//zig (x, p)
				
				//and then rotate X and G right,
				//zag(x, g)
			}
			
			//1. b
			if (x.compareTo(p) < 0 && p.compareTo(g) > 0) {
				//We first rotate X and P right
				//zag(x, p)
				
				
				//and then X and G left.
				//zig(x, g)
				
			}
			
			//2. a
			if (x.compareTo(p) < 0 && p.compareTo(g) < 0) {
				//We first rotate G and P right
				
				//and then P and X right
			}
			
			//2. b
			if (x.compareTo(p) > 0 && p.compareTo( )
			
			
			
			
			
			
			*/
			
		}
		
		private void rotateLeft(Node node) {
			//barnet är åt höger --> rotera åt vänster
			Node child = node.getRight();
			node.setRight(child.getLeft());
			child.setLeft(node);
			
			
			
			
			
			
			
			
			/*
			if (grandparent.getLeft().equals(parent)) {
				grandparent.setLeft(child);
				parent.setLeft(child.getRight();
				child.setLeft(parent);
				
			} else if (grandparent.getRight().equals(parent)) {
				grandparent.setRight(child);
				
				
			} else {
				return; //Things are wrong if we get here. This shouldn't really be possible?
			}*/
			
			
		}
		
		private void rotateRight(Node node) {
			//barnet är år vänster --> rotera åt höger
			
		}
		
		private int getSize() {
			return size;
		}
		
		
		
		private class Node {
			
			private E data;
			private Node left;
			private Node right;
			private Node parent;
			
			/**
			 * Constructs a new Node containing the data.
			 * @param data The data the Node should contain.
			 */
			private Node(E data) {
				this(data, null);
			}
			
			private Node(E data, Node parent) {
				this.data = data;
				this.parent = parent;
				left = null;
				right = null;
			}
			
			private E getData() {
				return data;
			}
			
			private Node getLeft() {
				return left;
			}
			
			private Node getRight() {
				return right;
			}
			
			private Node getParent() {
				return parent;
			}
			
			private void setLeft(Node newNode) {
				left = newNode;
			}
			
			private void setRight(Node newNode) {
				right = newNode;
			}
			
			private void setData(E data) {
				this.data = data;
			}
			
			private void setParent(Node newNode) {
				parent = newNode;
			}
		}	
	}
}

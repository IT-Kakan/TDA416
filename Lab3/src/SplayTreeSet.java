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
		if (!contains(x)) {
			return false;
		} else {
			return tree.remove(x);
		}
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
	
	
	private class SplayTree {
		
		private Node root;
		private int size;
		
		private SplayTree() {
			root = null;
			size = 0;
		}
		
		private void add(E x) {
			if (size == 0) {
				root = new Node(x);				
			} else {
				
				Node current = root;
				int difference;
				//add as for a binary search tree
				do {
					difference = x.compareTo(current.getData());
					if (difference < 0) {
						current = current.getLeft();
					} else if (difference > 0) {
						current = current.getRight();
					}				
				} while (current != null && difference != 0);
				current = new Node(x);
				
				splay(current);
			}
			size++;
		}
		
		private boolean remove(E x) {
			
			//There are several cases to consider.
			
			//0. The tree is empty. 
			//1. The value does not exist.
			//2. The value to remove is in the root.
			//	2. a) The node to be removed has two children
			//	2. b) The node to be removed has zero or one children.
			//3. The value is to the left of its parent
			//	3. a) The node to be removed has two children
			//	3. b) The node to be removed has zero or one children
			//4. The value is to the right of its parent
			//	4. a) The node to be removed has two children
			//	4. b) The node to be removed has zero or one children
			
			//Handles the case where the tree is empty.
			if (size == 0) {
				return false;
			}		
			
			Node parent = null;
			
			if (x.compareTo(root.getData()) == 0) {
				Node dataToRemove = root;
				//Handles the case where the node to be removed has two children.
				if (dataToRemove.getLeft() != null && dataToRemove.getRight() != null) {
					
					//Find the next higher key by traversing right once, then left as far as possible.
					//Update parent with each traversing.
					Node nextHigher = dataToRemove.getRight();
					parent = dataToRemove;
					while (nextHigher.getLeft() != null) {
						parent = nextHigher;
						nextHigher = nextHigher.getLeft();
					}
					
					
							
					//Remove the parameterized data by replacing it with the value of the next higher node.
					dataToRemove.setData(nextHigher.getData());
					
					//Remove the node whose data was copied (nextHigher) to the node specified by the parameter.
					//In effect, the result will be as if the node specified by the parameter was removed.
					if (nextHigher.getRight() != null) {
						parent.setLeft(nextHigher.getRight());
					} else {					
						parent.setLeft(null);
					}	
			
				} else { //Handles the case where the node to be removed has zero or one child.
					if (dataToRemove.getLeft() != null) {
						root = dataToRemove.getLeft();
					} else if (dataToRemove.getRight() != null) {
						root = dataToRemove.getRight();
					} else {
						root = null;
					}
				}
				
				size--;
				splay(parent);
				return true;
			}
			
			parent = root;
			
			
			boolean finished = false;
			
			
			while (!finished) {
								
				//Handles the case where the node to be removed is to the left of the parent.
				if (x.compareTo(parent.getLeft().getData()) == 0) {

					Node dataToRemove = parent.getLeft();
					
					//Handles the case where the node to be removed has two children.
					if (dataToRemove.getLeft() != null && dataToRemove.getRight() != null) {
						
						//Find the next higher key by traversing right once, then left as far as possible.
						//Update parent with each traversing.
						Node nextHigher = dataToRemove.getRight();
						parent = dataToRemove;
						while (nextHigher.getLeft() != null) {
							parent = nextHigher;
							nextHigher = nextHigher.getLeft();
						}
						
						//Remove the parameterized data by replacing it with the value of the next higher node.
						dataToRemove.setData(nextHigher.getData());
						
						//Remove the node whose data was copied (nextHigher) to the node specified by the parameter.
						//In effect, the result will be as if the node specified by the parameter was removed.
						if (nextHigher.getRight() != null) {
							parent.setLeft(nextHigher.getRight());
						} else {					
							parent.setLeft(null);
						}		
						
					} else { //Handles the case where the node to be removed has zero or one child.
						if (dataToRemove.getLeft() != null) {
							parent.setLeft(dataToRemove.getLeft());
						} else if (dataToRemove.getRight() != null){
							parent.setLeft(dataToRemove.getRight());
						} else {
							parent.setLeft(null);
						}
						
					}
					size--;
					splay(parent);
					return true;
				}
				
				//Handles the case where the node to be removed is to the left of the parent.
				if (x.compareTo(parent.getRight().getData()) == 0) {	
					
					Node dataToRemove = parent.getRight();
					
					//Handles the case where the node to be removed has two children.
					if (dataToRemove.getLeft() != null && dataToRemove.getRight() != null) {
					
						//Find the next higher key by traversing right once, then left as far as possible.
						//Update parent with each traversing.
						Node nextHigher = dataToRemove.getRight();
						parent = dataToRemove;
						while (nextHigher.getLeft() != null) {
							parent = nextHigher;
							nextHigher = nextHigher.getLeft();
						}
						//Remove the parameterized data by replacing it with the value of the next higher node.
						dataToRemove.setData(nextHigher.getData());
						
						//Remove the node whose data was copied (nextHigher) to the node specified by the parameter.
						//In effect, the result will be as if the node specified by the parameter was removed.
						if (nextHigher.getRight() != null) {
							parent.setRight(nextHigher.getRight());
						} else {					
							parent.setRight(null);
						}						
						
					} else { //Handles the case where the node to be removed has zero or one child.
						if (dataToRemove.getLeft() != null) {
							parent.setRight(dataToRemove.getLeft());
						} else if (dataToRemove.getRight() != null) {
							parent.setRight(dataToRemove.getRight());
						} else {
							parent.setRight(null);
						}
					}
					size--;
					splay(parent);
					return true;
				}
				
				//The node to remove was not a child to the parent.
				//Continue traversing the tree.
				if (x.compareTo(parent.getData()) < 0) {
					if (parent.getLeft() == null) {
						return false;
					} else {
						parent = parent.getLeft();
					}
				}
				
				if (x.compareTo(parent.getData()) > 0) {
					if(parent.getRight() == null) {
						return false;
					} else {
						parent = parent.getRight();
					}
				}
			}
			return false;
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
			//1. 		 O				O		if x >= p && p <= g
			//			/		OR		 \
			//		   O				  O		OR
			//			\				 /
			//			 O				O		if x <= p && p >= g
			
			if ((x.compareTo(p) > 0 && p.compareTo(g) < 0)) {
				//We first rotate X and P left,
				//zig (x, p)
				
				//and then rotate X and G right,
				//zag(x, g)
			}
			
			if (x.compareTo(p) < 0 && p.compareTo(g) > 0) {
				//We first rotate X and P right
				//zag(x, p)
				
				
				//and then X and G left.
				//zig(x, g)
				
			}*/
			
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
		
		private void zag(Node node, Node parent) {
			
		}
		
		private int getSize() {
			return size;
		}
		
		
		
		private class Node {
			
			private E data;
			private Node left;
			private Node right;
			
			/**
			 * Constructs a new Node containing the data.
			 * @param data The data the Node should contain.
			 */
			private Node(E data) {
				this.data = data;
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
			
			private void setLeft(Node newNode) {
				left = newNode;
			}
			
			private void setRight(Node newNode) {
				right = newNode;
			}
			
			private void setData(E data) {
				this.data = data;
			}
		}	
	}
}

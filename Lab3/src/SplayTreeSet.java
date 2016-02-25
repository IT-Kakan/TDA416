
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
					} else {
						throw new InternalError("In add(): Contains was erroneous");
					}
				} while (current != null);
				current = new Node(x);
				current.setParent(parent);				
				
				if (current.getData().compareTo(parent.getData()) < 0) {
					parent.setLeft(current);
				} else if (current.getData().compareTo(parent.getData()) > 0) {
					parent.setRight(current);
				} else {
					throw new InternalError("In add(): Contains was erroneous");
				}
				
				splay(current);
			}			
			size++;
		}
		
		private boolean remove(E x) {			
			if (size == 0) {
				if (root != null) {
					throw new InternalError("In remove(): size is zero, but root is not null");
				}
				return false;
			}
			
			
			//the value does not exist in the tree
			if (find(x) == null) {
				return false;
			} else {
				//The value exists ==> the node is in root
				if (root.getLeft() == null) {
					if (root.getRight() != null) {
						root.getRight().setParent(null);
					}
					root = root.getRight();
					size--;
					return true;
				} else {
					//save right subtree
					Node temp = root.getRight();
					//make left subtree root
					root = root.getLeft();
					root.setParent(null);
					//splay the highest value of the left subtree to the root
					find(x);
					//reattach the right subtree
					root.setRight(temp);
					if (temp != null) {
						temp.setParent(root);
					}
					size--;
					return true;
				}
			}
		}
		
		/**
		 * Locates and returns the first node containing the parameterized value.
		 * Post: If the node is found, it is rotated to root. Otherwise, the node with the closest value is rotated to root.
		 * @param x The value that the node to be found should contain.
		 * @return The first node containing the parameterized value. Null if no such node exists.
		 */
		private Node find(E x) {
			if (root == null) {
				return null;
			}
			
			Node next = root;
			int difference = x.compareTo(next.getData());
			
			//Node is root, do not splay
			if (difference == 0) {
				return next;
			}
			
			while (difference != 0) {
				if (difference < 0) {
					if (next.getLeft() == null) {
						splay(next);
						return null;
					}
					next = next.getLeft();
				} else if (difference > 0) {
					if (next.getRight() == null) {
						splay(next);
						return null;
					}
					next = next.getRight();
				}
				difference = x.compareTo(next.getData());
			}
			splay(next);
			return next;
		}
		
		/**
		 * Rotates the tree in the shortest way until the parameter is the root.
		 * @param node The node to become the new root.
		 */
		private void splay(Node node) {
			if (root == null || node == root) {
				return;
			} else if (node == root.getLeft()) {
				rotateRight(node);
				return;
			} else if (node == root.getRight()) {
				rotateLeft(node);
				return;
			}
			
			Node parent = node.getParent();			
			
			if (node.getParent() == null && node != root) {
				throw new InternalError("In splay(): node is not root, but has no parent");
			}
			
			Node grandparent = parent.getParent();			
			
			if (parent.getParent() == null && parent != root) {
				throw new InternalError("In splay(): parent is not root, but has no parent");
			}
			
			if (node == parent.getLeft()) { //node is left child
				if (parent == grandparent.getLeft()) { //parent is left child
					//zigzig, case parent left and node left		
					rotateRight(parent);
					rotateRight(node);
				} else if (parent == grandparent.getRight()) { //parent is right child
					//zigzag, case parent right and node left
					rotateRight(node);
					rotateLeft(node);
				} else {
					throw new InternalError("In Splay(), grandparent is not connected to parent");
				}
			} else if (node == parent.getRight()) { //node is right child
				if (parent == grandparent.getLeft()) { //parent is left child
					//zigzag, case parent left and node right
					rotateLeft(node);
					rotateRight(node);
				} else if (parent == grandparent.getRight())  { //parent is right child
					//zigzig, case parent right and node right
					rotateLeft(parent);
					rotateLeft(node);
				} else {
					throw new InternalError("In Splay(), grandparent is not connected to parent");
				}
			} else {
				throw new InternalError("In Splay(), parent is not connected to node");
			}
			splay(node);			
		}
		
		/**
		 * Rotates the tree to the left around the given node
		 * Pre: node is a right child
		 * @param node The node to be the rotation epicentre
		 */
		private void rotateLeft(Node node) {
			if (node == root || node == null) {
				throw new InternalError("In rotateLeft(): node is root");
			}
			
			if (node == root.getRight()) {
				root.setRight(node.getLeft());
				if (node.getLeft() != null) {
					node.getLeft().setParent(root);
				}
				root.setParent(node);
				node.setLeft(root);
				root = node;
				return;
			}
			
			if (node.getParent() == null && node != root) {
				throw new InternalError("In rotateLeft(): node is not root, but has no parent");
			}
			
			Node parent = node.getParent();
			parent.setRight(node.getLeft());
			
			if (node.getLeft() != null) {
				node.getLeft().setParent(parent);
			}
			node.setLeft(parent);
			
			if (parent != root) {
				//check if node should be a right or left child of grandparent
				//(the same type as parent was, but we don't know if parent was a right or left child)
				if (parent == parent.getParent().getLeft()) {
					parent.getParent().setLeft(node);
				} else if (parent == parent.getParent().getRight()) {
					parent.getParent().setRight(node);
				} else {
					throw new InternalError("In rotateLeft(): grandparent is not connected to parent");
				}
			
			}
			node.setParent(parent.getParent());
			parent.setParent(node);
			
			if(node.getParent() == null) {
				root = node;
			}
		}
		
		/**
		 * Rotates the tree to the right around the given node
		 * Pre: node is a left child
		 * @param node The node to be the rotation epicentre
		 */
		private void rotateRight(Node node) {
			if (node == root || node == null) {
				throw new InternalError("In rotateRight(): node is root");
			}
			
			if (node == root.getLeft()) {
				root.setLeft(node.getRight());
				if (node.getRight() != null) {
					node.getRight().setParent(root);
				}
				
				root.setParent(node);
				node.setRight(root);
				
				root = node;
				return;
			}
			
			if (node.getParent() == null && node != root) {
				throw new InternalError("In rotateLeft(): node is not root, but has no parent");
			}
			
			Node parent = node.getParent();
			parent.setLeft(node.getRight());
			
			if (node.getRight() != null) {
				node.getRight().setParent(parent);
			}
			node.setRight(parent);
			
			if (parent != root) {				
				//check if node should be a right or left child of grandparent
				//(the same type as parent was, but we don't know if parent was a right or left child)
				if (parent == parent.getParent().getLeft()) {
					parent.getParent().setLeft(node);
				} else if (parent == parent.getParent().getRight()) {
					parent.getParent().setRight(node);
				} else {
					throw new InternalError("In rotateLeft(): grandparent is not connected to parent");
				}
			}
		
			node.setParent(parent.getParent());
			parent.setParent(node);	
			
			if(node.getParent() == null) {
				root = node;
			}
		}
					
		private int getSize() {
			return size;
		}
		
		
		
		private class Node {
			
			private E data = null;
			private Node left = null;
			private Node right = null;
			private Node parent = null;
			
			/**
			 * Constructs a new Node containing the data.
			 * @param data The data the Node should contain.
			 */
			private Node(E data) {
				this.data = data;
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

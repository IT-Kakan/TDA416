
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
		System.out.println("CONTAINS " + x);
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
			System.out.println("ADD " + x);
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
			System.out.println("REMOVE " + x);
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
		 * Locates and returns the first node containing the parameterized value.
		 * Post: If the node is found, it is rotated to root. Otherwise, the node with the closest value is rotated to root.
		 * @param x The value that the node to be found should contain.
		 * @return The first node containing the parameterized value. Null if no such node exists.
		 */
		private Node find(E x) {
			System.out.println("FIND " + x);
			if (root == null) {
				return null;
			}
			
			Node next = root;
			int difference = x.compareTo(next.getData());
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
			System.out.println("SPLAY");
			System.out.println("Before splay");
			print();
			if (root == null || node == root) {
				System.out.println("After splay");
				print();
				return;
			} else if (node == root.getLeft()) {
				rotateRight(node);
				System.out.println("After splay");
				print();
				return;
			} else if (node == root.getRight()) {
				rotateLeft(node);
				System.out.println("After splay");
				print();
				return;
			}
			
			Node parent = node.getParent();
			Node grandparent = parent.getParent();
			
			if (node.getData().compareTo(parent.getData()) < 0) { //node is left child
				if (parent.getData().compareTo(grandparent.getData()) < 0) { //parent is left child
					//zigzig, case parent left and node left
					System.out.println("Left-left");
					
					rotateRight(parent);
					rotateRight(node);
				} else if (parent.getData().compareTo(grandparent.getData()) > 0) { //parent is right child
					//zigzag, case parent right and node left
					System.out.println("right-left");
					
					rotateRight(node);
					rotateLeft(node);
				}
			} else if (node.getData().compareTo(parent.getData()) > 0) { //node is right child
				if (parent.getData().compareTo(grandparent.getData()) < 0) { //parent is left child
					//zigzag, case parent left and node right
					System.out.println("left-right");
					
					rotateLeft(node);
					rotateRight(node);
				} else if (parent.getData().compareTo(grandparent.getData()) > 0)  { //parent is right child
					//zigzig, case parent right and node right
					System.out.println("right-right");
					
					rotateLeft(parent);
					rotateLeft(node);
				}
			}
			System.out.println("After splay");
			print();
			splay(node);
			
					
			
			
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
			//3. a	0 <-root OR	b	0  <-- root
			//	   /				 \
			//	  0					  0
			//
			//
			
			*/
			
		}
		
		/**
		 * Rotates the tree to the left around the given node
		 * Pre: node is a right child
		 * @param node The node to be the rotation epicentre
		 */
		private void rotateLeft(Node node) {
			Node parent = node.getParent();
			Node grandparent = parent.getParent();
			
			//Move node's left subtree
			parent.setRight(node.getLeft());
			
			//Change grandparent's linking
			if (grandparent != null) {
				if (parent == grandparent.getLeft()) {
					grandparent.setLeft(node);
				} else if (parent == grandparent.getRight()) {
					grandparent.setRight(node);
				}
			} else { //grandparent == null --> parent == root
				root.setParent(node);
				root = node;
				node.setParent(null);
			}
			
			//Replace node with parent
			parent.setParent(node);
			node.setLeft(parent);
		}
		
		/**
		 * Rotates the tree to the right around the given node
		 * Pre: node is a left child
		 * @param node The node to be the rotation epicentre
		 */
		private void rotateRight(Node node) {
			Node parent = node.getParent();
			Node grandparent = parent.getParent();
			
			//Move node's right subtree
			parent.setLeft(node.getRight());
			
			//Change grandparent's linking
			if (grandparent != null) {
				if (parent == grandparent.getLeft()) {
					grandparent.setLeft(node);
				} else if (parent == grandparent.getRight()) {
					grandparent.setRight(node);
				}
			} else { //grandparent == null --> parent == root
				root.setParent(node);
				root = node;
				node.setParent(null);
			}
			
			//Replace node with parent
			parent.setParent(node);
			node.setRight(parent);
		}
		
		
		
		private void print() {
			StringBuilder sb = new StringBuilder();
			pre(root, 1, sb);
			System.out.println(sb.toString());
		}
		
		private void pre(Node node, int depth, StringBuilder sb) {
			for (int i = 1; i < depth; i++) {
				sb.append("  ");
			}
			if(node == null) {
				sb.append("null\n");
			} else {
				sb.append(node.getData());
				sb.append("\n");
				pre(node.getLeft(), depth + 1, sb);
				pre(node.getRight(), depth + 1, sb);
			}
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
				if (parent != null) {
					if (data.compareTo(parent.getData()) < 0) {
						parent.setLeft(this);
					} else if (data.compareTo(parent.getData()) > 0) {
						parent.setRight(this);
					}
				}
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


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
		System.out.print("Command Size: " + tree.getSize());
		System.out.println();
		return tree.getSize();
	}

	/**
	 * Adds the element to the set. If the element already exists, nothing is altered.
	 * @param x The element to be added.
	 * @return True if the element did not exist. False otherwise.
	 */
	@Override
	public boolean add(E x) {
		System.out.print("Command Add: " + x);
		//Only add an element if it is not already in the list.
		//if (contains(x)) {
		if (tree.find(x, false) != null) {
			System.out.println(" Size is now:" + tree.getSize());
			return false;
		} else {
			tree.add(x);
			System.out.println(" Size is now: " + tree.getSize());
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
		System.out.print("Command remove: " + x);
		boolean rm = tree.remove(x);
		System.out.println(" Size is now: " + tree.getSize());
		return rm;
	}

	/**
	 * Checks whether or not the element is part of the set.
	 * @param x The element to be checked.
	 * @return True is the element is part of the set. False otherwise.
	 */
	@Override
	public boolean contains(E x) {		
		System.out.print("Command contains: " + x);
		SplayTreeSet<E>.SplayTree.Node result = tree.find(x, false);
		
		if (result == null) {
			System.out.println(" Size is now: " + tree.getSize());
			return false;
		} else {
			tree.splay(result);
			System.out.println(" Size is now: " + tree.getSize());
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
			System.out.print("--> add " + x);
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
						print();
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
					print();
					throw new InternalError("In add(): Contains was erroneous");
				}
				
				splay(current);
			}			
			size++;
		}
		
		private boolean remove(E x) {
			System.out.print("--> remove" + x);
			
			if (size == 0) {
				if (root != null) {
					throw new InternalError("------------------------------------------------------SIZE IS ZERO; ROOT NOT NULL");
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
					root.getLeft().setRight(root.getRight());
					root.getLeft().setParent(null);
					root = root.getLeft();
					if (root.getRight() != null) {
						root.getRight().setParent(root);
					}
					size--;
					return true;
				}
			}


			
			
			
			
			/*System.out.print("--> remove" + x);
			//There are three cases to consider:
			//1. The tree is empty
			//2. The element does not exist
			//3. The element has zero children
			//4. The element has one children.
			//4. a) The child is a left child
			//4. b) The child is a right child
			//5. The element has two children.
			
			//Handles the case where the tree is empty.
			if (size == 0) {
				return false;
			}
			
			Node nodeToRemove = find(x, false);
			
			//Handles the case where the element does not exist.
			if (nodeToRemove == null) {
				return false;
			}
			
			//NOTE: parent can still be null
			Node parent = nodeToRemove.getParent();
			
			//Handles the case when the node has zero children
			if (nodeToRemove.getLeft() == null && nodeToRemove.getRight() == null) {
				if (nodeToRemove == root) {
					root = null;
					size--;
					return true;
				} else {
					if (nodeToRemove == parent.getLeft()) {
						parent.setLeft(null);
					} else if (nodeToRemove == parent.getRight()) {
						parent.setRight(null);
					} else {
						print();
						throw new InternalError("In remove(): parent is not connected to node");
					}
					splay(parent);
					size--;
					return true;
				}
			}
			
			//Handles the case when the single child is a left child
			if (nodeToRemove.getLeft() != null && nodeToRemove.getRight() == null) {
				Node child = nodeToRemove.getLeft();
				
				if (nodeToRemove == root) {
					child.setParent(null);
					root = child;
					//Unable to splay, since there was no parent
					size--;
					return true;
				} else {
					if (nodeToRemove == parent.getLeft()) {
						parent.setLeft(child);
					} else if (nodeToRemove == parent.getRight()) {
						parent.setRight(child);
					} else {
						print();
						throw new InternalError("In remove(): parent is not connected to node");
					}
					
					child.setParent(parent);
					splay(parent);
					size--;
					return true;
				}
			}
			
			//Handles the case when the single child is a right child
			if (nodeToRemove.getLeft() == null && nodeToRemove.getRight() != null) {
				Node child = nodeToRemove.getRight();
				
				if (nodeToRemove == root) {
					child.setParent(null);
					root = child;
					//No need to splay, since node is root
					size--;
					return true;
				} else {
					if (nodeToRemove == parent.getLeft()) {
						parent.setLeft(child);
					} else if (nodeToRemove == parent.getRight()) {
						parent.setRight(child);
					} else {
						print();
						throw new InternalError("In remove(): parent is not connected to node");
					}
					
					child.setParent(parent);
					splay(parent);
					size--;
					return true;
				}
			}
			
			//Handles the case where the node has two children
			Node nextHigher = nodeToRemove.getRight();
			while (nextHigher.getLeft() != null) {
				nextHigher = nextHigher.getLeft();
			}
			
			//Remove data
			nodeToRemove.setData(nextHigher.getData());
			
			parent = nextHigher.getParent();
			
			if (nextHigher.getRight() != null) {
				parent.setLeft(nextHigher.getRight());
				nextHigher.getRight().setParent(parent);
				nextHigher.setRight(null);
			} else {
				parent.setLeft(null);
			}
			
			nextHigher.setParent(null);
			
			splay(parent);
			size--;
			return true;*/
		}
		
		/**
		 * Locates and returns the first node containing the parameterized value.
		 * Post: If the node is found, it is rotated to root. Otherwise, the node with the closest value is rotated to root.
		 * @param x The value that the node to be found should contain.
		 * @param splay Splays the tree if true. Otherwise, only splays if x does not exist.
		 * @return The first node containing the parameterized value. Null if no such node exists.
		 */
		private Node find(E x, boolean splay) {		
			System.out.print("--> find " + x);
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
			if (splay) {
				splay(next);
			}
			return next;
		}
		
		/**
		 * Locates and returns the first node containing the parameterized value.
		 * Post: If the node is found, it is rotated to root. Otherwise, the node with the closest value is rotated to root.
		 * @param x The value that the node to be found should contain.
		 * @return The first node containing the parameterized value. Null if no such node exists.
		 */
		private Node find(E x) {
			return find(x, true);
		}
		
		/**
		 * Rotates the tree in the shortest way until the parameter is the root.
		 * @param node The node to become the new root.
		 */
		private void splay(Node node) {
			System.out.print("--> splay");
		//	System.out.println("Before splay");
			//print();
			if (root == null || node == root) {
		//		System.out.println("After splay");
				//print();
				return;
			} else if (node == root.getLeft()) {
				rotateRight(node);
		//		System.out.println("After splay");
				//print();
				return;
			} else if (node == root.getRight()) {
				rotateLeft(node);
		//		System.out.println("After splay");
				//print();
				return;
			}
			
			Node parent = node.getParent();
			
			
			
			
			if (node.getParent() == null && node != root) {
				print();
				throw new InternalError("In splay(): node is not root, but has no parent");
			}
			
			Node grandparent = parent.getParent();
			
			
			
			if (parent.getParent() == null && parent != root) {
				print();
				throw new InternalError("In splay(): parent is not root, but has no parent");
			}
			
			if (node == parent.getLeft()) { //node is left child
				if (parent == grandparent.getLeft()) { //parent is left child
					//zigzig, case parent left and node left
					System.out.println("Left-left");
					
					rotateRight(parent);
					rotateRight(node);
				} else if (parent == grandparent.getRight()) { //parent is right child
					//zigzag, case parent right and node left
					System.out.println("right-left");
					
					rotateRight(node);
					rotateLeft(node);
				} else {
					throw new InternalError("In Splay(), grandparent is not connected to parent");
				}
			} else if (node == parent.getRight()) { //node is right child
				if (parent == grandparent.getLeft()) { //parent is left child
					//zigzag, case parent left and node right
					System.out.println("left-right");
					
					rotateLeft(node);
					rotateRight(node);
				} else if (parent == grandparent.getRight())  { //parent is right child
					//zigzig, case parent right and node right
					System.out.println("right-right");
					
					rotateLeft(parent);
					rotateLeft(node);
				} else {
					throw new InternalError("In Splay(), grandparent is not connected to parent");
				}
			} else {
				throw new InternalError("In Splay(), parent is not connected to node");
			}
		//	System.out.println("After splay");
			//print();
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
			System.out.print("--> rotateLeft");
			if (node == root || node == null) {
				print();
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
				print();
				throw new InternalError("In rotateLeft(): node is not root, but has no parent");
			}
			
			Node parent = node.getParent();
			parent.setRight(node.getLeft());
			
			if (node.getLeft() != null) {
				System.out.println("Parent is: " + parent);
				System.out.println("LeftChild is: " + node.getLeft());
				
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
			System.out.print("--> rotateRight");
			if (node == root || node == null) {
				throw new InternalError("In rotateRight(): node is root");
			}
			
			
			//TODO tell Dice
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
				print();
				throw new InternalError("In rotateLeft(): node is not root, but has no parent");
			}
			
			Node parent = node.getParent();
			parent.setLeft(node.getRight()); //TODO tell Dice this was in IF below
			
			if (node.getRight() != null) {
				System.out.println("Parent is: " + parent);
				System.out.println("RightChild is: " + node.getRight());
				
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
			
		
		
		private void print() {
			System.out.println("Root data: " + root.getData() + ", root left: " + root.getLeft() + ", root right: " + root.getRight());
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

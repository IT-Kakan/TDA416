/** Doubly-linked list with user access to nodes
 */
public class DLList<E> {
  public class Node {
    /** The contents of the node is public */
    public E elt;

    protected Node prev, next;

    Node() {
      this(null);
    }
    Node(E elt) {
      this.elt = elt;
      prev = next = null;
    }

    /**
     * Returns the next node. Returns null if no next node exists.
     * @return the next node, null if no next node exists
     */
    public Node getNext() {
    	return next;
    }

    /**
     * Returns the previous node. Returns null if no previous node exists.
     * @return the previous node, null if current node is head
     */
    public Node getPrev() {
    	return prev;
    }
  }
  
  /** first and last nodes in list, null when list is empty */
  Node first, last;
  
  DLList() {
    first = last = null;
  }
  
  /** inserts an element at the beginning of the list
   * @param e   the new element value
   * @return    the node holding the added element
   */
  public Node addFirst(E e) {
	  if (first == null) {
		  Node newNode = new Node(e);
		  
		  first = newNode;
		  newNode.prev = null;
		  
		  if (last == null) {
			  last = first;
		  } else {
			  newNode.next = last;
		  }
		  
		  return newNode;
	  } else {
		  return insertBefore(e, first);
	  }
  }

  /** inserts an element at then end of the list
   * @param e   the new element
   * @return    the node holding the added element
   */
  public Node addLast(E e) {
	  if (last == null) {
		  Node newNode = new Node(e);
		  
		  last = newNode;
		  newNode.next = null;
		  
		  if (first == null) {
			  first = last;
		  } else {
			  newNode.prev = first;
		  }
		  
		  return newNode;
	  } else {
		  return insertAfter(e, last);
	  }
  }
  
  /**
   * @return    the node of the list's first element, null if list is empty
   */
  public Node getFirst() {
	  return first;
  }
  
  /**
   * @return    the node of the list's last element, null if list is empty
   */
  public Node getLast() {
	  return last;
  }
  
  /** inserts a new element after a specified node
    * @param e   the new element
    * @param l   the node after which to insert the element, must be non-null and a node belonging to this list
    * @return    the node holding the inserted element
    */
  public Node insertAfter(E e, Node l) {
	  Node newNode = new Node(e);
	  
	  newNode.prev = l;
	  newNode.next = l.next;
	  
	  if (l != last) {
		  l.next.prev = newNode;
	  } else {
		  last = newNode;
		  
	  }
	  
	  l.next = newNode;
	  
	  return newNode;
  }

  /** inserts a new element before a specified node
    * @param e   the new element
    * @param l   the node before which to insert the element, must be non-null and a node belonging to this list
    * @return    the node holding the inserted element
    */
  public Node insertBefore(E e, Node l) {
	  Node newNode = new Node(e);
	  
	  newNode.prev = l.prev;
	  newNode.next = l;
	  
	  if (l != first) {
		  l.prev.next = newNode;
	  } else {
		  first = newNode;
	  }
	  
	  l.prev = newNode;
	  
	  return newNode;
  }

  /** removes an element
    * @param l   then node containing the element that will be removed, must be non-null and a node belonging to this list
    */
  public void remove(Node l) {
      if (l.prev != null) {
    	  l.prev.next = l.next;
      } else {
    	  first = l.next;
      }
      
      if (l.next != null) {
    	  l.next.prev = l.prev;
      } else {
    	  last = l.prev;
      }
  }
}

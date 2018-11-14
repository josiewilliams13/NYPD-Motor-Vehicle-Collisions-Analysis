package project5;

import java.util.ArrayList;

public class CollisionsData {
	//current number of nodes in the tree
	private int numOfElements;
	//root of the tree
	private Node<Collision> root;
	//helper variable used by the remove methods.
	private boolean found;


	//Beginning of nested Class
	protected static class Node <Collision extends Comparable <Collision>> {


		protected Node <Collision> left;  //reference to the left subtree 
		protected Node <Collision> right; //reference to the right subtree
		protected Collision data;            //data item stored in the node

		protected int height; 


		/**
		 * Constructs a BSTNode initializing the data part 
		 * according to the parameter and setting both 
		 * references to subtrees to null.
		 * @param data
		 *    data to be stored in the node
		 */
		protected Node(Collision data) {
			this.data = data;
			left = null;
			right = null;
			height = 0; 
		}
	}		

	//End of nested class
	public CollisionsData() {
		this.root = null;
		numOfElements = 0;
	}

	/**
	 * Add the given data item to the tree. If item is null, the tree does not
	 * change. If item already exists, the tree does not change. 
	 * 
	 * @param item the new element to be added to the tree
	 */
	public void add(Collision item) {
		if (item == null)
			return;
		root = add (root, item);		
	}

	/**
	 * Actual recursive implementation of add.  
	 * 
	 * @param item the new element to be added to the tree
	 */

	private Node<Collision> add(Node<Collision> node, Collision item) {
		if (node == null) { 
			numOfElements++;
			return new Node<Collision>(item);
		}
		if (node.data.compareTo(item) > 0)
			node.left = add(node.left, item);
		else if (node.data.compareTo(item) <= 0)
			node.right = add(node.right, item);
		balance(node);
		return node; 
	}

	public boolean remove(Collision target) {
		root = recRemove(target, root);
		return found;
	}
	/*
	 * Actual recursive implementation of remove method: find the node to remove.  
	 * 
	 * @param target the item to be removed from this tree 
	 */

	private Node<Collision> recRemove(Collision target, Node<Collision> node)
	{
		if (node == null)
			found = false;
		else if (target.compareTo(node.data) < 0)
			node.left = recRemove(target, node.left);
		else if (target.compareTo(node.data) > 0)
			node.right = recRemove(target, node.right );
		else {
			node = removeNode(node);
			found = true;
		}
		balance(node);
		return node;
	}
	/*
	 * Actual recursive implementation of remove method: perform the removal.  
	 * 
	 * @param target the item to be removed from this tree 
	 * @return a reference to the node itself, or to the modified subtree 
	 */
	private Node<Collision> removeNode(Node<Collision> node)
	{
		Collision data;
		if (node.left == null)
			return node.right ;
		else if (node.right  == null)
			return node.left;
		else {
			data = getPredecessor(node.left);
			node.data = data;
			node.left = recRemove(data, node.left);
			return node;
		}
	}
	/*
	 * Returns the information held in the rightmost node of subtree  
	 * 
	 * @param subtree root of the subtree within which to search for the rightmost node 
	 * @return returns data stored in the rightmost node of subtree  
	 */
	private Collision getPredecessor(Node<Collision> subtree)
	{
		if (subtree==null) throw new NullPointerException("getPredecessor called with an empty subtree");
		Node<Collision> temp = subtree;
		while (temp.right  != null)
			temp = temp.right ;
		return temp.data;
	}

	/**
	 * Produces tree like string representation of this BST.
	 * @return string containing tree-like representation of this BST.
	 */
	public String toStringTreeFormat() {

		StringBuilder s = new StringBuilder();

		preOrderPrint(root, 0, s);
		return s.toString();
	}

	/**
	 * Actual recursive implementation of preorder traversal to produce tree-like string
	 * representation of this tree.
	 * 
	 * @param tree the root of the current subtree
	 * @param level level (depth) of the current recursive call in the tree to
	 *   determine the indentation of each item
	 * @param output the string that accumulated the string representation of this
	 *   BST
	 */
	private void preOrderPrint(Node<Collision> tree, int level, StringBuilder output) {
		if (tree != null) {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append(tree.data);
			preOrderPrint(tree.left, level + 1, output);
			preOrderPrint(tree.right , level + 1, output);
		}
		// uncomment the part below to show "null children" in the output
		else {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append("null");
		}
	}


	//recursive call to the tree.
	//use compareTo from Collision class.

	ArrayList<int[]> collisionStorage = new ArrayList <int[]>();

	int personsInjured;
	int pedestriansInjured;
	int cyclistsInjured;
	int motoristsInjured;

	int personsKilled;
	int pedestriansKilled;
	int cyclistsKilled;
	int motoristsKilled;	
	int totalCollisions = personsInjured + pedestriansInjured + cyclistsInjured + personsKilled + pedestriansKilled + cyclistsKilled + motoristsKilled;

	public String getReport (String zip, Date dateBegin, Date dateEnd) {
		recursive(root, zip, dateBegin, dateEnd);

		return "Motor Vechicle Collisions for zipcode " + zip + " (" + dateBegin + " - " + dateEnd + ") \n"
		+ "====================================================================\n" + "Total number of collisions: " + totalCollisions +
		"Number of fatalities: " + personsInjured + "\n\tpedestrians: " + pedestriansKilled + "\n\tcyclists: " + cyclistsKilled +
		"\n\tmotorists: " + motoristsKilled + "Number of injuries: " + personsInjured + "\n\tpedestrians: " + pedestriansInjured +
		"\n\tcyclists: " + cyclistsInjured + "\n\tmotorists: " + motoristsInjured;

		//		Node <Collision> current = root;

		//		recursion through the tree. 
	}
	public void recursive(Node<Collision> current, String zip, Date dateBegin, Date dateEnd) {
		if(current.data.getZip().equals(zip) && current.data.getDate().compareTo(dateBegin) >= 0 && current.data.getDate().compareTo(dateEnd) <= 0) {
			personsInjured += current.data.getPersonsInjured();
			pedestriansInjured += current.data.getPedestriansInjured();
			cyclistsInjured += current.data.getCyclistsInjured();
			motoristsInjured += current.data.getMotoristsInjured();

			personsKilled += current.data.getPersonsKilled();
			pedestriansKilled += current.data.getPedestriansKilled();
			cyclistsKilled += current.data.getCyclistsKilled();
			motoristsKilled += current.data.getMotoristsKilled();


		}
		if (current.data.getZip().compareTo(zip) >= 0)
			recursive(current.right, zip, dateBegin, dateEnd);
		else
			recursive(current.left, zip, dateBegin, dateEnd);

	}

	//Rotations: LL rotation, RR rotation, LR rotation, and RL rotation
	//Four separate methods for each rotation

	//LL Rotation
	private Node<Collision> LLrotation(Node<Collision> A) {	
		Node<Collision> B = A.left;
		A.left = B.right;
		B.right = A;

		updateHeight(A);
		updateHeight(B);

		return B;	
	}

	//LR Rotation
	private Node<Collision> LRrotation(Node<Collision> A){
		Node<Collision> B = A.left;
		Node<Collision> C = B.right;

		A.left = C.right;
		B.right = C.left;
		C.left = B;
		C.right = A;

		updateHeight(A);
		updateHeight(B);
		updateHeight(C);

		return C;
	}

	//RR Rotation
	private Node<Collision> RRrotation(Node<Collision> A) {
		Node<Collision> B = A.right;
		A.right = B.left;
		B.left = B;

		updateHeight(A);
		updateHeight(B);

		return B;
	}

	//RL Rotation
	private Node<Collision> RLrotation(Node<Collision> A){
		Node<Collision> B = A.right;
		Node<Collision> C = B.left;

		A.right = C.left;
		B.left = C.right;
		C.right = B;
		C.left = A;

		updateHeight(A);
		updateHeight(B);
		updateHeight(C);

		return C;
	}

	//Determine which rotation 
	//Check with each Remove or add node method
	//Update height, check to see if balance factor is off by more than 2.
	//Calls the next rotation method

	//greater than on right hand side is bigger. 
	private void balance(Node<Collision> A) {
		if (balanceFactor(A) >= 2) {
			if (balanceFactor (A.right) < 0 && A.right != null)
				RLrotation(A.right);
			else if (balanceFactor(A.right) > 0)
				RRrotation(A.right);
		}
		else if (balanceFactor(A) <= 0 && A.left != null) {
			if (balanceFactor (A.left) <= 0)
				LLrotation(A.left);
			else if(balanceFactor(A.left) > 0)
				LRrotation(A.left);
		}
	}

	private void updateHeight(Node<Collision> n) {
		if (n.left == null && n.right == null)
			n.height = 0;
		else if (n.left == null)
			n.height = n.right.height + 1;
		else if (n.right == null)
			n.height = n.left.height + 1;
		else
			n.height = max(n.right.height, n.left.height) +1;
	}

	private int max(int height, int height2) {
		if (height == height2)
			return height;
		if (height > height2)
			return height;

		return height2;
	}

	private int balanceFactor(Node<Collision> n) {
		if (n.right == null)
			return (n.height *= -1);
		if (n.left == null)
			return n.height;

		return n.left.height - n.right.height;
	}
}




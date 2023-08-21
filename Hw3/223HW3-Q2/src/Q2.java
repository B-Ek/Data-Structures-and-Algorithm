import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Captain {

	// Ýd
	int id;

	// Name of Captain
	String name;

	// Avaliable
	boolean avaliable;

	// rating of captain
	int rating;

	// Consturactor
	public Captain(int id, String name, boolean avaliable, int rating) {
		this.id = id;
		this.name = name;
		this.avaliable = avaliable;
		this.rating = rating;
	}

	@Override
	public String toString() {

		if (this.avaliable == true) {
			return "\n\t\t\tID: " + this.id + "\n\t\t\tName: " + this.name + "\n\t\t\tAvailable: " + "True"
					+ "\n\t\t\tRating star: " + this.rating;
		} else {
			return "\n\t\t\tID: " + this.id + "\n\t\t\tName: " + this.name + "\n\t\t\tAvailable: " + "False"
					+ "\n\t\t\tRating star: " + this.rating;

		}

	}

}

class Node {

	// Stored Product
	Captain captain;

	// Child nodes
	Node leftNode, rightNode;

	public Node(Captain captain) {
		this.captain = captain;
	}

}

class SearchTree {

	// Root of the tree
	Node root;

	public SearchTree() {

		// init root as null
		// Tree is empty
		this.root = null;
	}

	public Node add(Node node, Captain captain) {
		// If we reach a leaf node, add the product
		if (node == null) {
			node = new Node(captain);

			return node;
		}

		// If the product's ID is less than the current node's product, add left
		if (captain.id < node.captain.id) {

			node.leftNode = add(node.leftNode, captain);
		}
		// If the product's ID is greater than the current node's product, add right
		else if (captain.id > node.captain.id) {

			node.rightNode = add(node.rightNode, captain);
		} else {
			return node;
		}

		return node;

	}

	public void add(Captain captain) {
		// Runn recursive function
		this.root = add(this.root, captain);
	}

	public Node find(Node node, int id) {

		// Ýf not in tree and found return the node
		// Ýf not found it will be null
		if (node == null || node.captain.id == id) {
			return node;
		}

		// Search it at left
		if (id < node.captain.id) {
			return find(node.leftNode, id);
		}
		// Search it at right
		else {
			return find(node.rightNode, id);
		}

	}

	public Node delete(Node root, int id) {
		// Find the node to delete
		Node current = root;
		Node parent = null;
		while (current != null && current.captain.id != id) {
			parent = current;
			if (id < current.captain.id) {
				current = current.leftNode;
			} else {
				current = current.rightNode;
			}
		}

		// Node not found
		if (current == null) {
			return root;
		}

		// Case 1: No children
		if (current.leftNode == null && current.rightNode == null) {
			if (parent == null) {
				// Deleting the root
				root = null;
			} else {
				// Detach the node from the tree
				if (parent.leftNode == current) {
					parent.leftNode = null;
				} else {
					parent.rightNode = null;
				}
			}
		}
		// Case 2: One child
		else if (current.leftNode == null || current.rightNode == null) {
			Node child = (current.leftNode != null) ? current.leftNode : current.rightNode;
			if (parent == null) {
				// Deleting the root
				root = child;
			} else {
				// Replace the current node with its child
				if (parent.leftNode == current) {
					parent.leftNode = child;
				} else {
					parent.rightNode = child;
				}
			}
		}
		// Case 3: Two children
		else {
			// Find the inorder successor (minimum value in the right subtree)
			Node successor = current.rightNode;
			Node successorParent = current;
			while (successor.leftNode != null) {
				successorParent = successor;
				successor = successor.leftNode;
			}

			// Replace the current node with the inorder successor
			if (successorParent.leftNode == successor) {
				successorParent.leftNode = successor.rightNode;
			} else {
				successorParent.rightNode = successor.rightNode;
			}
			current.captain = successor.captain;
		}

		return root;
	}

	public void display(Node node) {
		if (node == null) {
			return;
		}

		// Traverse the left subtree
		display(node.leftNode);

		// Visit the root node
		System.out.println("--CAPTAIN:\n");
		System.out.println(node.captain.toString());

		// Traverse the right subtree
		display(node.rightNode);
	}

}

public class Q2 {

	SearchTree captainDatabase;

	public Q2() {
		this.captainDatabase = new SearchTree();
	}

	public void Add_Captain(Captain captain) {
		// Add captain to binary tree
		captainDatabase.add(captain);

		System.out.println("Add Captain: Add a new captain record in the System");
		System.out.println(captain.toString());

	}

	public void Delete_captain(int id) {

		Node captaNode = captainDatabase.find(captainDatabase.root, id);
		// Delete
		captainDatabase.delete(captainDatabase.root, id);

		if (captaNode == null) {
			System.out.println("Delete Captain: Couldn't find any captain with ID number " + id);
		} else {
			System.out.println("Delete Captain:The captain " + captaNode.captain.name + " left CCR");
		}
	}

	public void Display_captain(int id) {
		// display captain

		// Find
		Node captaNode = captainDatabase.find(captainDatabase.root, id);

		// Display
		if (captaNode != null) {
			System.out.println("Display Captain:");
			System.out.println(captaNode.captain.toString());
		} else {
			System.out.println("Display Captain: Couldn't find any captain with ID number " + id + "\n");
			System.out.println("----------------------------------------------------------------");
		}

	}

	public void Display_all_captains() {
		System.out.println("\n----------ALL CAPTAINS----------");

		// Display all
		captainDatabase.display(captainDatabase.root);
	}

	public void Is_Available(int id) {
		// Check avaliability

		// Find captain
		Node captaNode = captainDatabase.find(captainDatabase.root, id);

		if (captaNode == null) {
			System.out.println("\nIsAvailable: Couldn't find any captain with ID number " + id);
			return;
		}

		// Ýf captain avaliable reserve it
		if (captaNode.captain.avaliable) {
			captaNode.captain.avaliable = false;
			System.out.println("\nIsAvailable: Reserve a new Ride with captain " + captaNode.captain.id);

		} else {
			System.out.println("\nIsAvailable: The captain " + captaNode.captain.name
					+ " is not available. He is on another ride!");
		}

	}

	public void Finish(int id, int grade) {
		// Find the node
		Node captaNode = captainDatabase.find(captainDatabase.root, id);

		if (captaNode == null) {
			System.out.println("\nFinish: Couldn't find any captain with ID number " + id);
			return;
		}

		if (captaNode.captain.avaliable) {
			System.out.println("\nFinish: The captain " + captaNode.captain.name + " is not in a ride");
			return;
		}

		// Make captain avaliable
		captaNode.captain.avaliable = true;

		// increase raiting
		if (grade == 0 && captaNode.captain.rating > 0)
			captaNode.captain.rating--;
		else if (grade == 1 && captaNode.captain.rating < 5)
			captaNode.captain.rating++;

		System.out.println("\nFinish: Finish ride with captain " + captaNode.captain.id);
		System.out.println(captaNode.captain.toString());

	}

	public static void main(String[] args) throws FileNotFoundException {
        
        Q2 q2 = new Q2();
        Scanner scanner = new Scanner(System.in);
        String f = scanner.next();
        File file = new File(f);

         scanner = new Scanner(file);
        String line ;



        System.out.println("--------------- WELCOME TO CDRC SYSTEM ---------------");
        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            String comands[] = line.split("\\s+");


            
            if(comands.length > 0){

                if( comands[0].equals("Add_Captain")){
                    // Adding product
                    Captain c = new Captain(Integer.parseInt(comands[1]), comands[2], true ,0);


                    q2.Add_Captain(c);
                    System.out.println("----------------------------------------------------------------");



                }
                else if(comands[0].equals("Is_Available")){
                        // Search Product
                        
                        q2.Is_Available(Integer.parseInt(comands[1]));
                        System.out.println("\n"+"----------------------------------------------------------------");


                }
                else if(comands[0].equals("Display_captain")){
                    q2.Display_captain(Integer.parseInt(comands[1]));

                }
                else if(comands[0].equals("Finish")){
                    q2.Finish(Integer.parseInt(comands[1]), Integer.parseInt(comands[2]));

                }
                else if(comands[0].equals("Delete_captain")){
                    q2.Delete_captain(Integer.parseInt(comands[1]));
                }
                else if(comands[0].equals("Display_all_captains")){
                    q2.Display_all_captains();
                }
                else if(comands[0].equals("Quit")){
                    // Quit

                    System.out.println("\nThank you for using CDRC, Good Bye!");
                    break;
                }
 
          //   System.out.println("----------------------------------------------------------------");

            }
        }
        

            
           
        }

	            
}}

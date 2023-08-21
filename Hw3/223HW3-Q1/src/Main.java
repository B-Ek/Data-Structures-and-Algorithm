import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


class Product {

    // Ýd
    int id;

    // Name of product
    String name;

    // Count of product
    int piece;

    // Consturactor
    public Product(int id, String name, int piece){
        this.id = id;
        this.name = name;
        this.piece = piece;
    }

}


class Node{

    // Stored Product
    Product product;


    // Child nodes
    Node leftNode, rightNode;


    public Node(Product product){
        this.product = product;
    }

}





class SearchTree{

    // Root of the tree
    Node root;


    public SearchTree(){

        // init root as null
        // Tree is empty
        this.root = null;
    }


    public Node add(Node node,Product product){
        // If we reach a leaf node, add the product
        if (node == null) {
            node = new Node(product);
            
            return node;
        }

        // If the product's ID is less than the current node's product, add left
        if (product.id < node.product.id) {

            node.leftNode = add(node.leftNode, product);
        }
        // If the product's ID is greater than the current node's product, add right
        else if(product.id > node.product.id) {

            node.rightNode =  add(node.rightNode, product);
        }
        else{
            return node;
        }

        return node;

    }

    public void add(Product product){
        this.root = add(this.root, product);
    }





    public Node find(Node node,int id){

        if(node == null || node.product.id == id){
            return node;
        }

        if( id < node.product.id){
            return find(node.leftNode, id);
        }
        else{
            return find(node.rightNode, id);
        }



    }



    public void display(Node node) {
        if (node == null) {
            return;
        }
    
        // Traverse the left subtree
        display(node.leftNode);
    
        // Visit the root node
        System.out.println(node.product.id);
    
        // Traverse the right subtree
        display(node.rightNode);
    }
    





}




public class Main{

    SearchTree productDatabase;

    public Main()
    {
        productDatabase = new SearchTree();
    }


    public void createProduct(Product product){
        this.productDatabase.add(product);
    }

    public Node isAvaliable(int id){
        return this.productDatabase.find(this.productDatabase.root, id);

    }



    public static void main(String[] args) throws FileNotFoundException {
    	
        
        Main q1 = new Main();
        
        Scanner scanner = new Scanner(System.in);
        String f = scanner.next();
        

        File file = new File(f);

         scanner = new Scanner(file);
        String line ;



        System.out.println("--------------- WELCOME TO ITS SYSTEM ---------------");
        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            String comands[] = line.split("\\s+");


            
            if(comands.length > 0){

                if( comands[0].equals("Add_product")){
                    // Adding product
                    System.out.println("Create Product:");
                    System.out.println("\t\tID: " + comands[1]);
                    System.out.println("\t\tName: " + comands[2]);
                    System.out.println("\t\tPiece: " + comands[3]);

                    q1.createProduct(new Product(Integer.parseInt(comands[1]), comands[2], Integer.parseInt(comands[3])));


                }
                else if(comands[0].equals("Is_Available")){
                        // Search Product


                        Node searced = q1.isAvaliable(Integer.parseInt(comands[1]));

                        if(searced != null)
                        	if(searced.product.piece>0) {
                        		
                        		System.out.println("\nThere are "+ searced.product.piece + " products");
                        	}else {
                        		
                        		System.out.println("\nThe product is out of stock!!!");
                        	}
                            
                        else
                            System.out.println("\nThe product is out of stock!!!");



                }
                else if(comands[0].equals("Quit")){
                    // Quit

                    System.out.println("\nThank you for using ITS, Good Bye!");
                }
 

            }
        }

            
            
           
            
            
        }




        
    }
    
}
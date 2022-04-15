import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

//node class that symbolizes the numbers on graph
class Node{
	int id;
	int distance;
	int parent = -1;
	int maxDistance = 0;
	boolean isAvailable = false;
	boolean inQueue = false;
	ArrayList<Node> neighbours = new ArrayList<>();
	
	public Node(int id, int distance){
		this.id = id;
		this.distance = distance;
	}
	
	//a method that helps to find a number whether the number is prime or not
	public boolean isPrime(int number) {
		if(number <= 1 || number%2 == 0 ) {
			return false;
		}else if(number==2 ){
			return true;
		}else{
			for (int i = 3; i <= Math.sqrt(number); i += 2){ 
				if (number % i == 0) {
		           return false;
				}
		    }
		    return true;
		}
	}
}

public class Main {
	public static void main(String[] args) throws FileNotFoundException{
		Scanner in = new Scanner(new File(args[0]));
		ArrayList<ArrayList<Node>> inputList = new ArrayList<>();
		int id = 0;
		int num = 1;
		//take input as two dimensional array
		while(in.hasNextInt()) {
			ArrayList<Node> intList = new ArrayList<>();
			for(int i = 0; i < num; i++ ) {
				int distance = in.nextInt();
				Node node = new Node(id, distance);
				intList.add(node);
				id += 1;
			}
			inputList.add(intList);
			num += 1;
		}
		//Connect the neighbour nodes
		for(int currentRow = 0 ; currentRow < inputList.size() - 1; currentRow++) {
			for(int currentColumn = 0; currentColumn < inputList.get(currentRow).size(); currentColumn++) {
				Node currentNode = inputList.get(currentRow).get(currentColumn);
				Node leftNeighbour = inputList.get(currentRow+1).get(currentColumn);
				Node rightNeighbour = inputList.get(currentRow+1).get(currentColumn+1);
				currentNode.neighbours.add(leftNeighbour);
				currentNode.neighbours.add(rightNeighbour);
			}
		}
		// a variable to store max distance to the end
		int maxDistance = 0;
		Node firstNode = inputList.get(0).get(0);
		if(!firstNode.isPrime(firstNode.distance)) {
			firstNode.isAvailable = true;
			maxDistance = firstNode.distance;
		}
		firstNode.maxDistance = firstNode.distance;
		
		for( int i=0; i<inputList.size()-1; i++ ) {
			ArrayList<Node> list = inputList.get(i);
			for(Node currentNode : list) {
				//if current node is available it means that it is not prime
				if(currentNode.isAvailable) {
					for(Node neighbour: currentNode.neighbours) {
						//if the neighbour of the current node is not prime, node become available.
						if(!neighbour.isPrime(neighbour.distance)) {
							
							neighbour.isAvailable = true;
							//if the new distance to neighbour is greater than former distance then code change the maxDistance to new distance
							if(neighbour.maxDistance < currentNode.maxDistance + neighbour.distance) {
								neighbour.maxDistance = currentNode.maxDistance + neighbour.distance;
								neighbour.parent = currentNode.id;
							}
							
						}
					}
				}
			}
		}
		
		for(int i = 0; i < inputList.get(inputList.size()-1).size(); i++) {
			ArrayList<Node> list = inputList.get(inputList.size()-1);
			for(Node n : list) {
				if(n.maxDistance > maxDistance) {
					maxDistance = n.maxDistance;
				}
			}
		}
		
		System.out.println(maxDistance);
	}
}

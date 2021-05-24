package CancerClassifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


// Weighted Graph implementation 

//creating the attributes of the edge
class Edge {
        int source;
       	int destiny;
       	int weight;
       	public Edge(int source, int destiny, int weight) {
       		this.source = source;
       		this.destiny = destiny;
       		this.weight = weight;
		}
}

public class WGraph implements Serializable{
	private static final long serialVersionUID = 7L;

	//Class Attributes
	int dim; 
	double [][] wmtx; 
	
	//WGraph Constructor 
	public WGraph (int d) {
		this.dim = d;
		this.wmtx = new double[d][d];
	}
	
	//getter for WGraph's dimension
	public int getDim() { 
		return this.dim; 
	}
	
	//adding an edge with a corresponding weight
	public void add(int source, int destiny, double weight) { 
		int minor = Math.min(source, destiny); 
        int major = Math.max(source, destiny);
		if (minor >= 0 && minor < this.dim && major >= 0 && major < this.dim) { 
			if (this.wmtx[minor][major] < 10e-10) {
				this.wmtx[minor][major] = weight;
			}
			
		}
		else {
			throw new AssertionError("this node does not belongs in this graph");
		}
			
	}
	
	//getter for the weight of a specific edge
	public double get_weight(int source, int destiny) { 
		int minor = Math.min(source, destiny); 
        int major = Math.max(source, destiny);
		return this.wmtx[minor][major];
	}
	
	//returns whether the node exits or not
	public boolean indexisnotemptyQ (int index) {
		boolean found = false;
		for (int i = 0; i < this.dim && !found; i++ ) {
				if (this.wmtx[index].length != 0) {
					found = true;
				 
				}
			
			}
		return found;
	}
	
<<<<<<< Updated upstream
	public ArrayList<ArrayList<Integer>> Edge_Sort() { //n�o funciona :(((
		ArrayList<ArrayList<Integer>> edgelist = new ArrayList<ArrayList<Integer>>();
		ArrayList<Double> weightlist = new ArrayList<Double>();
		
		
		for (int i = 0; i < this.dim ; i++) {
			for (int j = i+1;j < this.dim; j++) {
				boolean put = false;
				if (weightlist.isEmpty()) {
					System.out.println("primeiro if ahaha");
					ArrayList<Integer> edge = new ArrayList<Integer>(); 
					edge.add(i);
					edge.add(j);
					weightlist.add(this.wmtx[i][j]);
					edgelist.add(edge);
				}
				else {
					for (int k = 0; k < weightlist.size() && !put ; k++) {
						System.out.println("pr�ximo ahaha " + i + ", " + j +  " get_weight = " + this.get_weight(i, j) + " weightlist.get = " + weightlist.get(k));
						if (this.get_weight(i, j) >= weightlist.get(k)) {
							//n�o entra aqui
							ArrayList<Integer> edge = new ArrayList<Integer>(); 
							edge.add(i);
							edge.add(j);
							weightlist.add(k+1,this.wmtx[i][j]);
							edgelist.add(k+1,edge);
							put = true;
						}
						
					}
				}
			}
		}
		return edgelist;
	}
	

	

	
=======
>>>>>>> Stashed changes
	
	public String toString() {
		return "WGraph [dim=" + dim + ", wmtx=" + Arrays.deepToString(wmtx);
	}
	
    public static void main(String[] args) {
    	//creating a Weighted Graph
        WGraph wg = new WGraph(3);
        
        //adding the edges
        int[][] edges = { {0,1,10}, {1,2,7}, {2,0,3} };
        for (int[] e : edges) {
        	wg.add(e[0], e[1], e[2]);
        }
        
        //printing the Weighted Graph
        System.out.println(wg);
        
        //getting the Weighted Graph's dimension
        System.out.println("Weighted Graph dimension: " + wg.getDim());
        
        //getting the weight of a specific edge
        System.out.println("(1,2) weight: " + wg.get_weight(1, 2));
        
        //checking if the node and are already in the Weighted Graph
        System.out.println("Index 1 is not empty: " + wg.indexisnotemptyQ(1));
    }


}
        
        

    

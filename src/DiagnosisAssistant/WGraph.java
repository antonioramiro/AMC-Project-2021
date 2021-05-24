package DiagnosisAssistant;

import java.io.Serializable;
//import java.util.ArrayList;
import java.util.Arrays;


// Weighted Graph implementation, by 

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
        
        
    }


}
        
        

    

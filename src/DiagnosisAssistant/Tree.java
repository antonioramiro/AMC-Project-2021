package DiagnosisAssistant;

import java.io.Serializable;
import java.util.ArrayList;


//implements a Tree, by doing a special implementation of sparse matrices of a adjacency matrix
public class Tree implements Serializable{
    private static final long serialVersionUID = 3L;
    private int dim;
    private ArrayList<ArrayList<Integer>> index; //list of lists of column indexes

    //constructor, receives dim
    public Tree(int dim) {
        ArrayList<ArrayList<Integer>> index = new ArrayList<ArrayList<Integer>>();
        this.index = index;
        this.dim = dim;
        for (int i = 0; i< this.dim ;i++) {
        	this.index.add(new ArrayList<Integer>());
        }
        	
    }

    //devolve numero de nos
    public int getDimension(){
        return this.dim;
    }

    //devolve o primeiro nó que encontrar
    public ArrayList<Integer> first(){
        boolean firstFound = false;
        ArrayList<Integer> firstBranch = new ArrayList<Integer>();

        for(int i = 0;!firstFound && i < dim; i++){
            int connectedNr = this.index.get(i).size();
            for(int j = 0; !firstFound && j < connectedNr; j++){
                
                firstBranch.add(i);
                firstBranch.add(this.index.get(i).get(j));

                firstFound = true;
            }
            
        }
        return firstBranch;
    }

    //adds a leaf to the tree
    public void addLeaf(int o, int d) {
        int minor = Math.min(o, d); 
        int major = Math.max(o, d);
        if (minor > this.dim || major > this.dim) {
        	throw new AssertionError("Leaves out of treetop.");
        }else {
           if(!(leafQ(major) && leafQ(minor))){
                //this.index.add(new ArrayList<Integer>());
                this.index.get(minor).add(major);
            }else{
                throw new AssertionError("Tree doesn't allow interconnected leaves or unexisting pairs of leaves");
            }
        }
    }
       
    //verifies if certain branch exists
    public boolean branchQ(int o, int d) {
        boolean isThere = false;

        //protects simetric implementation
        int minor = Math.min(o, d); 
        int major = Math.max(o, d);
        
        if (minor >= 0 && minor < this.dim && major > 0 && major < this.dim){
            for (int y = 0; !isThere && y < this.dim &&  y < this.index.get(minor).size(); y++) {
                if (this.index.get(minor).get(y) == major) {
                    isThere = true;
                }
            }
        }
        return isThere;
    }

    //checks if a tree exists
    public boolean leafQ(int i){
        boolean isThere = false;
        if (this.index.get(i).size() > 0) {
        	isThere = true;
        }else {
	        for(ArrayList<Integer> a : this.index){
	            for(int b : a){
	                isThere = b==i; 
	            }
	        }   
	    }
    return isThere;
    }

    @Override
    public String toString() {
        return "{" + " dim='" + this.dim + "'" + ", index='" + (this.index) + "'" + "}";
    }

    public static void main(String[] args) {
        Tree t = new Tree(4);
        t.addLeaf(1, 0);
        t.addLeaf(3, 2);
        System.out.println(t);
        
        System.out.println(t.leafQ(3));

        System.out.println("branch: " + t.leafQ(0));
    }
}
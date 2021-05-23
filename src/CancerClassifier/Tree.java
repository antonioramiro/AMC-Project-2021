package CancerClassifier;

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

    public int getDimension(){
        return this.dim;
    }

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

    public  ArrayList<ArrayList<Integer>> branchLister(){

        ArrayList<ArrayList<Integer>> branchList = new ArrayList<ArrayList<Integer>>();
        int dim = this.dim;

        for(int i = 0; i < dim; i++){
            int connectedNr = this.index.get(i).size();
            for(int j = 0; j < connectedNr; j++){
                ArrayList<Integer> currentBranch = new ArrayList<Integer>();
                currentBranch.add(i);
                currentBranch.add(this.index.get(i).get(j));

                branchList.add(currentBranch);
            }
            
        }
        
        return branchList;

    }

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
        	//}else {
        		//throw new AssertionError("You cannot add a fallen leaf. Please, add branches to catch it");
        	}
       
        
   // }

    public boolean branchQ(int o, int d) {
        boolean isThere = false;
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

    // verificar se ja existe o no ao qual vai ligar
    // verificar se nao faz clique
    // verificar se ele proprio ja existe

    @Override
    public String toString() {
        return "{" + " dim='" + this.dim + "'" + ", index='" + (this.index) + "'" + "}";
    }

    public static void main(String[] args) {
        Tree t = new Tree(4);
        t.addLeaf(1, 0);
        t.addLeaf(3, 2);
        System.out.println(t);

        System.out.println("branch: " + t.leafQ(0));

    

        ArrayList<ArrayList<Integer>> z = new ArrayList<ArrayList<Integer>>();
        //Tree tree = new Tree(8);
        
        ArrayList<Integer> x1 = new ArrayList<Integer>();
        ArrayList<Integer> x2 = new ArrayList<Integer>();
        ArrayList<Integer> x3 = new ArrayList<Integer>();
        ArrayList<Integer> x4 = new ArrayList<Integer>();
        ArrayList<Integer> x5 = new ArrayList<Integer>();
        ArrayList<Integer> x6 = new ArrayList<Integer>();
        ArrayList<Integer> x7 = new ArrayList<Integer>();
        x1.add(0);
        x1.add(1);
        
        x3.add(0);
        x3.add(2);
        
        x2.add(1);
        x2.add(2);
        
        x4.add(0);
        x4.add(3);
        
        x5.add(1);
        x5.add(4);
        
        x6.add(2);
        x6.add(4);
        
        x7.add(7);
        x7.add(5);
        //System.out.println("arraylist x = " + x);
        z.add(x1);
        z.add(x3);
        z.add(x2);
        z.add(x4);
        z.add(x5);
        z.add(x6);
        z.add(x7); 
        
        /*

       {{1,3},{3,2}}

       for (branch : arvore) {~
    }

        */

        
        //Tree tree = MST.MaximalTree(z);
        //System.out.println("tree: " + tree);
     
        //System.out.println("arrayList z = " + z);
        //System.out.println("path: " + MST.pathQ(2, 4, MST.MaximalTree(z)));


        //System.out.println("maximalTree z = " + ChowLiu.MaximalTree(z));
        //System.


        //System.out.println(t.leafQ(5));
        //System.out.println(t.branchQ(1,3));

        //System.out.println(t);
        //System.out.println(z.get(0).get(1));

    }}


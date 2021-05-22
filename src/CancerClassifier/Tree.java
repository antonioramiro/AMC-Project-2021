package CancerClassifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Tree implements Serializable{
    int dim;
    public ArrayList<ArrayList<Integer>> index;

    public Tree() {
        ArrayList<ArrayList<Integer>> index = new ArrayList<ArrayList<Integer>>();
        this.index = index;
        this.index.add(new ArrayList<Integer>());
        this.dim = 1;
    }

    public  Iterator<ArrayList<Integer>> branchIterator(){

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

        Iterator<ArrayList<Integer>> branchIterator = branchList.iterator();
        
        return branchIterator;

    }

    public void addLeaf(int o, int d) {
        int minor = Math.min(o, d); 
        int major = Math.max(o, d);
        if (minor >= 0 && minor < this.dim && major >= 0 && major <= this.dim) {
            if (this.branchQ(minor, major)) {
            	throw new AssertionError("This leaf already exists in the Tree.");
            }else{
                if(!this.leafQ(major)){
                    this.index.add(new ArrayList<Integer>());
                    this.index.get(minor).add(major);
                    this.dim++;
                }
                else{
                    throw new AssertionError("Tree doesn't allow three interconnected leafs.");
                }
            }
        } else {
            throw new AssertionError("You cannot add a fallen leaf. Please, add branches to catch it");
        }
    }

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

    public boolean leafQ(int l){
        boolean isThere = false;
        for(ArrayList<Integer> a : this.index){
            for(int b : a){
                isThere = b==l;
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
        Tree t = new Tree();
        System.out.println(t);

        System.out.println("branch: " + t.branchQ(1, 3));

        int[][] edges = {{0,1}, {1,2}, {1,3},{1,4}};
        for (int[] e : edges) {
            t.addLeaf(e[0], e[1]);
        }
       // System.out.println("t br: " +  Arrays.toString(t.branchIterator()));
       Iterator<ArrayList<Integer>> branchIterator = t.branchIterator();
       while(branchIterator.hasNext()){
           System.out.println(branchIterator.next());
       }

        ArrayList<ArrayList<Integer>> z = new ArrayList<ArrayList<Integer>>();
        
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
        
        x7.add(3);
        x7.add(0);
        //System.out.println("arraylist x = " + x);
        z.add(x1);
        z.add(x3);
        z.add(x2);
        z.add(x4);
        z.add(x5);
        z.add(x6);
        z.add(x7);
        
        
        //Tree tree = MST.MaximalTree(z);
        //System.out.println("tree: " + tree);
     
        //System.out.println("arrayList z = " + z);
        //System.out.println("path: " + MST.pathQ(2, 4, MST.MaximalTree(z)));


        //System.out.println("maximalTree z = " + MST.MaximalTree(z));


        //System.out.println(t.leafQ(5));
        //System.out.println(t.branchQ(1,3));

        //System.out.println(t);
        //System.out.println(z.get(0).get(1));

    }
}

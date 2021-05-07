package CancerClassifier;

import java.util.ArrayList;
import java.util.Arrays;

public class Tree {
    int dim;
    public ArrayList<ArrayList<Integer>> index;

    public Tree() {
        ArrayList<ArrayList<Integer>> index = new ArrayList<ArrayList<Integer>>();
        this.index = index;
        this.index.add(new ArrayList<Integer>());
        this.dim = 1;
    }

    public void addLeaf(int o, int d) {
        int minor = Math.min(o, d); 
        int major = Math.max(o, d);

        System.out.println("o: " + o + "; d:" + d);

        if (minor >= 0 && minor < this.dim && major >= 0 && major <= this.dim) {

            if(!this.leafQ(major) && !this.branchQ(minor, major)){
                this.index.add(new ArrayList<Integer>());
                this.index.get(minor).add(major);
                this.dim++;
            }
            
            else{
                throw new AssertionError("Tree doesn't allow three interconnected leafs");
            }   
        } else {
            throw new AssertionError("node not in graph");
        }
    }

    public boolean branchQ(int o, int d) {
        boolean isThere = false;
        if (o >= 0 && o < this.dim && d >= 0 && d < this.dim) {
            
            for (int y = 0; !isThere && y < this.dim; y++) {
                if (this.index.get(o).get(y) == d) {
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

        System.out.println(t.branchQ(1, 3));

        int[][] edges = { { 0, 1},{0,2},{1,2}};
        for (int[] e : edges) {
            t.addLeaf(e[0], e[1]);
        }

        System.out.println(t);

    }
}

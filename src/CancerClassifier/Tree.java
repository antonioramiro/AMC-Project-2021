package CancerClassifier;

import java.util.ArrayList;
import java.util.Arrays;

public class Tree {
    /* Tree implementation using the same thought process as Markov's implementation (see Markov.java),
    considering Trees are non directed graphs (its adjacency matrix would be simetric) and that each element
    can only have one parent - no clicks are created
    */

    /*Atributos são a dimensão e a matriz de adjacência optimizada para matrizes esparsas simétricas,
    ou seja é uma lista (matriz) de listas (linhas) de indíces das colunas*/
    int dim;
    private ArrayList<ArrayList<Integer>> index;

    //Método construtor, cria arvore vazia e adiciona o nó 0
    public Tree() {
        ArrayList<ArrayList<Integer>> index = new ArrayList<ArrayList<Integer>>();
        this.index = index;
        this.index.add(new ArrayList<Integer>());
        this.dim = 1;
    }

    //Adicionar uma folha ao grafo
    public void addLeaf(int o, int d) {

        //recomenda-se o estudo da implementação em Markov.java. Aqui é análogo (mas basta a matriz dos indices)
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

    //verifica a presença ou ausência de um ramo
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

    //verifica a presença ou não de uma folha
    // esta função nunca é usada para verificar se existe uma folha na árevore, mas sim se 
    //esse nó existe ligado a um outro. esta situação deve ser corrigida
    public boolean leafQ(int l){
        boolean isThere = false;
        for(ArrayList<Integer> a : this.index){
            for(int b : a){
                isThere = b==l;
            }
        }
        return isThere;
    }

 
    //tostring
    @Override
    public String toString() {
        return "{" + " dim='" + this.dim + "'" + ", index='" + (this.index) + "'" + "}";
    }

    public static void main(String[] args) {
        Tree t = new Tree();
        System.out.println(t);

        System.out.println(t.branchQ(1, 3));

        int[][] edges = {{0,1}, {1,2}, {1,3},{1,4}};
        for (int[] e : edges) {
            t.addLeaf(e[0], e[1]);
        }

        System.out.println(t.leafQ(5));
        System.out.println(t.branchQ(1,3));
        System.out.println(t);

    }
}

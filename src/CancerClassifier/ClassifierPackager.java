package CancerClassifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.Serializable;

public class ClassifierPackager implements Serializable{
    private int dim;
    private ArrayList<MRFT> mrftList;
    private double[] freqList;

    public ClassifierPackager(Dataset data){

        Tree chowliuTree = new Tree(); //ChowLiu(data);

		int[][] edges = {{0,1}, {1,2}, {1,3},{1,4},{0,5}, {1,6}, {1,7}, {1,8}, {1,9} };
		for(int[] e : edges) {
			chowliuTree.addLeaf(e[0], e[1]);
		}

        this.dim = data.classDim();
        this.mrftList = new ArrayList<MRFT>(); 
        this.freqList = new double[this.dim];
    
        for (int i=0; i < this.dim; i++){

            MRFT thisMrft = new MRFT(data.Fiber(i), chowliuTree);
            this.mrftList.add(thisMrft);
            this.freqList[i] = data.classFrequency(i);
        }
    }    
    

    public int getDim() {
        return this.dim;
    }

    public ArrayList<MRFT> getMrft() {
        return this.mrftList;
    } 

    public double[] getFreq() {
        return this.freqList;
    }

    @Override
    public String toString() {
        return "{" +
            " dim='" + this.dim + "'" +
            ", mrftList='" + this.mrftList + "'" +
            ", freqList='" + Arrays.toString(this.freqList) + "'" +
            "}";
    }

    public static void main(String[] args) {
    
        //Creating Dataset
        Dataset ds1 = new Dataset(8);
        int[] m6 = {3,3,3,3,3,3,3,1,};
        int c6 = 0;
        DataPoint dp6 = new DataPoint(m6,c6);
        int[] m7 = {3,3,3,3,3,3,3,3};
        int c7 = 1;
        DataPoint dp7 = new DataPoint(m7,c7);
        int[] m8 = {3,3,3,3,3,3,3,3};
        int c8 = 0;
        DataPoint dp8 = new DataPoint(m8,c8);
        ds1.Add(dp6);
        for(int i = 0; i < 300; i++){
            ds1.Add(dp7);
        }
        ds1.Add(dp8);
        
        ClassifierPackager cp1 = new ClassifierPackager(ds1);
        System.out.println(cp1);

    }
    
}

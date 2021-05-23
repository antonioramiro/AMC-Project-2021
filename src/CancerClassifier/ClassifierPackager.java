package CancerClassifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.Serializable;

public class ClassifierPackager implements Serializable{
	private static final long serialVersionUID = 5L;
	private int dim;
    private ArrayList<MRFT> mrftList;
    private double[] freqList;

    public ClassifierPackager(Dataset T){
    	
  
    	System.out.println("domain" + Arrays.toString(T.measurementsDomain));
    	
    	ChowLiu cl = new ChowLiu(T);
        Tree chowliuTree = cl.getTree(); //ChowLiu(data);


        this.dim = T.classDim();
        this.mrftList = new ArrayList<MRFT>(); 
        this.freqList = new double[this.dim];
    
        for (int i=0; i < this.dim; i++){

            MRFT thisMrft = new MRFT(T.Fiber(i), chowliuTree);
            this.mrftList.add(thisMrft);
            this.freqList[i] = T.classFrequency(i);
        }
    }    
    

    public int getDim() {
        return this.dim;
    }

    public ArrayList<MRFT> getMrft() {
        return this.mrftList;
    }
    public int numberOfMeasurements() {
    	return this.getMrft().get(0).getDim();
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
    
    	Dataset T = FileHandling.getDataset("Datasets/bcancer.csv");
        //Creating Dataset
       /* Dataset ds1 = new Dataset(8);
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
        */
    	
        ClassifierPackager cp1 = new ClassifierPackager(T);
        System.out.println(cp1);

    }
    
}

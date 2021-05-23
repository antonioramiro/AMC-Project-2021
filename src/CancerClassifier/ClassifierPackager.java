package CancerClassifier;

//modules
import java.util.ArrayList;
import java.util.Arrays;
import java.io.Serializable;

//Bundles a group of MRFTS (one for each class) and a list of frequencies (one for each class)
//into only one object, in order to be properly seriazable, and easier to access previous
//variables when deserealizing
public class ClassifierPackager implements Serializable{

    //declaring variables
	private static final long serialVersionUID = 5L; //seriazable ID, must be unique and prevents conflicts in different IDEs/machines
	private int dim; //how many different classes there are
    private ArrayList<MRFT> mrftList; //list of MRFTs
    private double[] freqList; //list of frequencies

    //constructor
    public ClassifierPackager(Dataset T){
    	    	
    	//creates a ChowLiu object (that receives a Dataset and outpus a Tree)
    	ChowLiu cl = new ChowLiu(T);
        Tree chowliuTree = cl.getTree(); 

        //atributions
        this.dim = T.classDim(); //looking for how many classes there are
        this.mrftList = new ArrayList<MRFT>(); 
        this.freqList = new double[this.dim];
    
        //looping through all available classes to create each MRFT and frequency 
        for (int i=0; i < this.dim; i++){
            MRFT thisMrft = new MRFT(T.Fiber(i), chowliuTree);
            this.mrftList.add(thisMrft);
            this.freqList[i] = T.classFrequency(i);
        }
    }    
    
    //Dimension Getter
    public int getDim() {
        return this.dim;
    }

    //MRFT Getter
    public ArrayList<MRFT> getMrft() {
        return this.mrftList;
    } 

    //Freq Getter
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
    
        //The input will be a dataset, T
    	Dataset T = FileHandling.getDataset("Datasets/bcancer.csv");

        //Initializing the object onto cp
        ClassifierPackager cp = new ClassifierPackager(T);

        
        System.out.println("Classifier Package:" + cp);
        System.out.println("Length of each list:" + cp.getDim());
        System.out.println("Class of 0:" + cp.getMrft().get(0));
        System.out.println("Frequency of 0:" + (cp.getFreq()[0]));

    }
    
}

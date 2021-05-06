package CancerClassifier;

import java.util.Array;
import java.util.ArrayList;


public class Classifier {

    Dataset T;
    Graph A;
    MRFT markov;
    double [] Frequency;
    

    ArrayList<Double[][]>[][] benignMRFT, malignantMRFT; //erro aqui
    double benignFrequency, malignantFrequency;
    
	public Classifier(ArrayList<ArrayList<Double[][]>>  MRFT, double[] Frequency) {
		this.benignMRFT = MRFT[0];
		this.malignantMRFT = MRFT[1];
		this.benignFrequency = Frequency[0];
	    this.malignantFrequency = Frequency[1];
	    }
	
	public int Frequency(Dataset T, int i, int xi) {
		int freq;
		freq = T.Count(i, xi)/T.Dim();
		return freq;
		
	}
    

    public int Classify(int[] Xn){
    	
        int p = 0;
        int diagnosis;

        for(int i = 0; i < A.getDim(); i++){  //
            if(p < MRFT.Prob(T.Fiber(i), A, Xn)) {
            	
                diagnosis = i;
                p = MRFT.Prob(T.Fiber(i), A, Xn) * Frequency[i];
                
            }
 
        return diagnosis;
            	
            
        }

        



        return diagnosis;
    }


    
}

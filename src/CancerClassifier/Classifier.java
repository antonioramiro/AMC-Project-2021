package CancerClassifier;

import java.util.ArrayList;

public class Classifier {

    Dataset T;
    Tree A;
    MRFT markov;

    ArrayList<Double[][]>[][] benignMRFT, malignantMRFT; //erro aqui
    double benignFrequency, malignantFrequency;
    
    public Classifier( Array(ArrayList<Double[][]>[][])  MRFT, double[] Frequency){
        this.benignMRFT = MRFT[0];
        this.malignantMRFT = MRFT[1];
        this.benignFrequency = Frequency[0];
        this.malignantFrequency = mFrequency[1];
    }

    public int Classify(int[] Xn){
        
        int classDim = T.dim(class);
        int p = 0;
        int diagnosis;

        for(int i = 0; i < classNumber, i++){
            if(p < MRFT.getProbability(T.Fiber(i), A, Xn)){
                diagnosis = i;
            }
            
        }

        



        return diagnosis;
    }


    
}

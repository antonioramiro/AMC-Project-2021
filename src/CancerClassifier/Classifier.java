package CancerClassifier;

import java.lang.reflect.Array;

public class Classifier {
    Array benignMRFT, malignantMRFT, benignFrequency, malignantFrequency;
    
    public Classifier(Array bMRFT, Array mMRFT, Array bFrequency,Array mFrequency){
        this.benignMRFT = bMRFT;
        this.malignantMRFT = mMRFT;
        this.benignFrequency = bFrequency;
        this.malignantFrequency = mFrequency;
    }

    public String Classify(){
        String classification = "ola";





        return classification;
    }


    
}

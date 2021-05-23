package CancerClassifier;
import java.util.ArrayList;
import java.util.Arrays;

public class CrossValidation {

    public static int[] convertIntegers(ArrayList<Integer> integers){
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
            {
                ret[i] = integers.get(i).intValue();
            }
    return ret;
}

    private static void CrossValidationG(String datasetsFolderPath, int partitionNumber, String diseaseName){       
        int[][] total = new int[2][2];
        for (int rejectedDataset = 0; rejectedDataset < partitionNumber; rejectedDataset++){
            

            ArrayList<ArrayList<Integer>> testingValues = FileHandling.getTestingMeasurements("Datasets/CrossValidation/"+ diseaseName+"/"+ rejectedDataset +".csv");
            ArrayList<Integer> groundTruth = FileHandling.getGroundTruth("Datasets/CrossValidation/"+ diseaseName +"/"+ rejectedDataset +".csv");
            
            int[][] confMatrix = new int[2][2];

            Dataset T = FileHandling.getDatasetsMinusOne("Datasets/CrossValidation/"+diseaseName+"/",rejectedDataset,partitionNumber);
            
            // T.setDomain not implemented publically since it harms the Dataset object, however
            // there can be a case in which the testing dataset has one of the measurements
            // out of the testing domain, and it won't be able to test. The lines bellow,
            // would set the testing dataset to be equal to the complete dataset (which includes the 
            // domain of the testing one) - which makes sense, it has never seen that data point before

            //T.setDomain(FileHandling.getDataset("Datasets/" + diseaseName + ".csv").measurementDim());
            //System.out.println("A" + Arrays.toString(FileHandling.getDataset("Datasets/" + diseaseName + ".csv").measurementDim()));
            //System.out.println("B" + Arrays.toString(T.measurementDim()));

            ClassifierPackager  cp = new ClassifierPackager(T);
            Classifier c = new Classifier(cp.getMrft(), cp.getFreq()); //creates a classifier from the ClassifierPackager
            
            for(int i = 0; i < testingValues.size(); i++){
               
                
                int predicted = c.Classify(convertIntegers(testingValues.get(i)));
                int actual = groundTruth.get(i);
                
                confMatrix[predicted][actual]++;
                total[predicted][actual]++;
            }
            //System.out.println("Without partition " + rejectedDataset +" of the Dataset, result is:" + Arrays.deepToString(confMatrix));
            
        }
        
        System.out.println(diseaseName + " Result" + Arrays.deepToString(total));
        
        
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        //running 5 classifiers for each disease, trained on 4/5th of the
        //available data, and using said classifiers to test the remaining 1/5th of 
        //the data of each classifier
        CrossValidationG("Datasets/CrossValidation/hepatitis/",5,"hepatitis");
        CrossValidationG("Datasets/CrossValidation/bcancer/",5,"bcancer");
        CrossValidationG("Datasets/CrossValidation/thyroid/",5,"thyroid");
        CrossValidationG("Datasets/CrossValidation/diabetes/",5,"diabetes");
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("Elapsed time to create 20 classifiers (trained on a combined number of ~ 4000 datapoints) and test them on more than 800 sets of measurements (combined): " + timeElapsed/1000000000d + " s");
        //                                                          800 = 1/5 of every classifier summed up

        //we suggest running FileHandling main, to re-partition the datasets in a different way so that
        //different results can be seen. previously partitioned datasets have to be deleted 1st
    }



}

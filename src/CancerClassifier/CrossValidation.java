package CancerClassifier;
import java.util.ArrayList;
import java.util.Arrays;



public class CrossValidation {
    private Dataset T;
    private int partitionNumber;

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
            
            // T.setDomain not implemented since it harms the Dataset object, however
            // there can be a case in which the testing dataset has one of the measurements
            // out of the testing domain, and it won't be able to test. The lines bellow,
            // would set the testing dataset to be equal to the complete dataset (which includes the 
            // domain of the testing one)

            T.setDomain(FileHandling.getDataset("Datasets/" + diseaseName + ".csv").measurementDim());
            System.out.println("A1" + Arrays.toString(FileHandling.getDataset("Datasets/" + diseaseName + ".csv").measurementDim()));
            System.out.println("B" + Arrays.toString(T.measurementDim()));

            ClassifierPackager  cp = new ClassifierPackager(T);
            Classifier c = new Classifier(cp.getMrft(), cp.getFreq()); //creates a classifier from the ClassifierPackager
            
            for(int i = 0; i < testingValues.size(); i++){
               
                //System.out.println("T" +testingValues.get(i));
                int predicted = c.Classify(convertIntegers(testingValues.get(i)));
                int actual = groundTruth.get(i);
                
                confMatrix[predicted][actual]++;
                total[predicted][actual]++;
            }
            System.out.println("Without partition " + rejectedDataset +" of the Dataset, result is:" + Arrays.deepToString(confMatrix));
            
        }
        System.out.println("Result" + Arrays.deepToString(total));
        
    }

    public static void main(String[] args) {
        CrossValidationG("Datasets/CrossValidation/hepatitis/",5,"hepatitis");
   
    }



}

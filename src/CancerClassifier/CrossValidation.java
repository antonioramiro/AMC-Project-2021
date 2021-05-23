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
        ArrayList<Integer[][]> confMatrixList = new ArrayList<Integer[][]>();
        
        int[][] total = new int[2][2];
        for (int rejectedDataset = 0; rejectedDataset < partitionNumber; rejectedDataset++){
            System.out.println("Rejected:" + rejectedDataset);

            ArrayList<ArrayList<Integer>> testingValues = FileHandling.getTestingMeasurements("Datasets/CrossValidation/"+ diseaseName+"/"+ rejectedDataset +".csv");
            ArrayList<Integer> groundTruth = FileHandling.getGroundTruth("Datasets/CrossValidation/"+ diseaseName +"/"+ rejectedDataset +".csv");
            
            int[][] confMatrix = new int[2][2];

            Dataset T = FileHandling.getDatasetsMinusOne("Datasets/CrossValidation/"+diseaseName+"/",rejectedDataset,partitionNumber);
            //T.setDomain(FileHandling.getDataset("Datasets/" + diseaseName + ".csv").measurementDim());
            
            System.out.println("A" + Arrays.toString(FileHandling.getDataset("Datasets/" + diseaseName + ".csv").measurementDim()));
            System.out.println("B" + Arrays.toString(T.measurementDim()));

            //System.out.println("Dataset" + T);

            ClassifierPackager  cp = new ClassifierPackager(T);
            Classifier c = new Classifier(cp.getMrft(), cp.getFreq()); //creates a classifier from the ClassifierPackager
            
            for(int i = 0; i < testingValues.size(); i++){
                System.out.println("T"+testingValues.get(i));
                int predicted = c.Classify(convertIntegers(testingValues.get(i)));
                int actual = groundTruth.get(i);

                confMatrix[predicted][actual]++;
                total[predicted][actual]++;
            }
            System.out.println("Without partition " + rejectedDataset +" of the Dataset, result is:" + Arrays.deepToString(confMatrix));
            
        }
        
    }

    private double[][] getConfSum(){
        double[][] a = {{1},{2}};
        return a;
    }


    //cortar o dataset em 5
    //inicializar arraylist de doubles
    ArrayList<Double[]> ola = new  ArrayList<Double[]>();

    //ciclo testes, rodando
        //constroi classificador com 4
        //testa com 1, e compara com ground truth
        //adiciona nova conf.matrix à array list~

    //soma todas e devolve

    public static void main(String[] args) {
        CrossValidationG("Datasets/CrossValidation/diabetes/",5,"diabetes");
    }



}

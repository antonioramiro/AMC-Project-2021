package CancerClassifier;
import java.util.ArrayList;

public class CrossValidation {
    private Dataset T;
    private int partitionNumber;


    private CrossValidation(String datasetsFolderPath, int partitionNumber){
        ArrayList<Double[][]> confMatrixList = new ArrayList<Double[][]>();
        
        for (int rejectedDataset = 0; rejectedDataset < partitionNumber; rejectedDataset++){
            double[][] confMatrix = new double[2][2];
            Dataset T = FileHandling.getDatasetsMinusOne("Datasets/CrossValidation/hepatitis/",rejectedDataset,partitionNumber);
            

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
        double[][] bcancerConf = (new CrossValidation("Datasets/bcancer")).getConfSum();
    }



}

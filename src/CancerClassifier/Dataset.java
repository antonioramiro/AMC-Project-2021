package CancerClassifier;

//To allow ArrayList use
import java.util.ArrayList;

//Dataset stores an ArrayList of DataPoints, and its length.
public class Dataset {

    //Class Attributes
    ArrayList<DataPoint> dataList;
    int[] measurementsDomain;
    int classDomain;
   
    //Constructor of empty Dataset
    public Dataset(){
        this.dataList = new ArrayList<DataPoint>();
        int[] emptyDomain = {0,0,0,0,0,0,0,0,0,0};
        this.measurementsDomain = emptyDomain;
        this.classDomain = 0;
    }

    //Constructor of filled Dataset
    public Dataset(ArrayList<DataPoint> dL){
        this.dataList = dL;
        int[] measurementsD = {0,0,0,0,0,0,0,0,0,0};
        int classD = 0;
         
        for(DataPoint dp : this.dataList){
            for(int j = 0; j < 10; j++){
                if(dp.getMeasurements()[j] > measurementsD[j]){
                    measurementsD[j] = dp.getMeasurements()[j];
                }
                if(dp.getClassification() > classD){
                    classD = dp.getClassification();
                }
            }
        }
        this.measurementsDomain = measurementsD;
        this.classDomain = classD;      
    }

    //Dataset size
    public int len(){
        return this.dataList.size();
    }

    public int Dim(int i){
        int max = 0; 
        for(DataPoint dp : this.dataList){
            if(dp.measurements[i] > max){
                max = dp.measurements[i];
            }
        }
        return (max + 1);
    }
    
    //Counting datapoints in the dataset, in which there is xi in index i and xi in index j, simultaneously
    public int Count(int i, int j, int xi, int xj){
        int counter = 0;
        for (DataPoint dp: this.dataList){
            if (dp.isThere(i, j, xi, xj)) counter++;
        }
        return counter;
    }

    public int Count(int i, int xi){
        int counter = 0;
        for (DataPoint dp: this.dataList){
            if (dp.isThere(i, xi)) counter++;
        }
        return counter;
    }


    //Adding a single DataPoint to the dataset
    public void Add(DataPoint v){
        this.dataList.add(v);
        for(int j = 0; j < 10; j++){
            if(v.getMeasurements()[j] > this.measurementsDomain[j]){
                this.measurementsDomain[j] = v.getMeasurements()[j];
            }
            if(v.getClassification() > this.classDomain){
                this.classDomain = v.getClassification();
            }
        }

    }

    //Returning a sub-Dataset, depending on which classification is requested, the domain remains unaltered, regardless of 
    //the fiber class and its distribution, given that the domain maximizers are known to exist, even if not present in
    //the current fiber
    public Dataset Fiber(int classification){
        Dataset fiber = new Dataset();
        for (DataPoint dp: this.dataList){
            if (dp.getClassification() == classification){
                fiber.Add(dp);
            }
        }
        return fiber;

    }

    //Print
    @Override
    public String toString() {
        return "{" +
            " len='" + len() + "'" +                  //alterei aqui porque estava a chmar uma fun�ao chamada getLen e o nome da que esta em cima � so len
            ", dataList='" + getDataList() + "'" +
            "}";
    }


    //Getter for the Array
    public ArrayList<DataPoint> getDataList() {
        return this.dataList;
    }


    public static void main(String[] args) {
        
        //Empty Dataset
        Dataset ds1 = new Dataset();
        System.out.println("Empty Dataset: " + ds1);

        //Filled Dataset
        int[] m7 = {1,2,3,4,5,6,7,8,9,10};
        String c7 = "benign";
        DataPoint dp7 = new DataPoint(m7,c7); //Maria: aqui o problema e que a nossa funcao datapoint recebe um inteiro e uma matriz, neste daqui esta a receber uma strig no lugar do inteiro

        int[] m8 = {1,2,3,4,5,6,7,8,9,12};
        String c8 = "malignant";
        DataPoint dp8 = new DataPoint(m8,c8);

        ArrayList<DataPoint> dl = new ArrayList<DataPoint>();
        for(int i = 0; i < 93; i++){
            dl.add(dp7);
        };
        dl.add(dp8);
        
        Dataset ds2 = new Dataset(94,dl);  //Maria: se nao estou em erro, a nossa fun�ao que cria dataset ou nao recebe nenhum argumento (como na linha 122), ou recebe so um array, ou seja, para implementar desta forma temos que definir no construtor que recebe um array e um inteiro ou adiciona-lo a nossa funcao
        System.out.println("Filled Dataset: " + ds2);

        //Adding to Dataset
        int[] m6 = {1,2,3,4,5,6,7,8,9,10};
        String c6 = "benign";
        DataPoint dp6 = new DataPoint(m6,c6);
        ds1.Add(dp6);
        System.out.println("Dataset with 1 datapoint: " + ds1);

        //Getting the fiber of malignant tumours
        System.out.println(ds2.Fiber("malignant")); //Maria: a nossa funcao fiber recebe um inteiro e nao uma string

        //Counting the number of combinations in the dataset
        System.out.println(ds2.Count(0, 9, 1, 10));

        //Dim
        System.out.println(ds2.Dim(9));
        

        
    }
    
}

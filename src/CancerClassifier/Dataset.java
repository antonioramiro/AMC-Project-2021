package CancerClassifier;

//To allow ArrayList use
import java.util.ArrayList;
import java.util.Arrays;

//Dataset stores an ArrayList of DataPoints, and its length.
public class Dataset {

    //Class Attributes
    ArrayList<DataPoint> dataList;
    int[] measurementsDomain; //domain of the dataset measurements
    int classDomain; //domain of the class
    int measurementNumber = 5;
   
    //Constructor of empty Dataset
    public Dataset(){
        this.dataList = new ArrayList<DataPoint>();
        int[] emptyDomain = new int[this.measurementNumber];
        this.measurementsDomain = emptyDomain;
        this.classDomain = 0;
    }

    //Adding a single DataPoint to the dataset
    public void Add(DataPoint v){
        this.dataList.add(v);
        for(int j = 0; j < this.measurementNumber; j++){
            if(v.getMeasurements()[j] >= this.measurementsDomain[j]){
                this.measurementsDomain[j] = v.getMeasurements()[j] + 1;
            }
            if(v.getClassification() >= this.classDomain){
                this.classDomain = v.getClassification() + 1;
            }
        }
    }

    //Returns dataset size/length
    public int len(){
        return this.dataList.size();
    }

    //returns de dimension of a measurement i
    public int measurementDim(int i){
        return this.measurementsDomain[i];
    }

    //returns de dimension of a measurement i
    public int classDim(){
        return this.classDomain;
    }    
    
    //Counting datapoints in the dataset, in which there is xi in index i and xi in index j, simultaneously
    public int Count(int i, int j, int xi, int xj){
        int counter = 0;
        for (DataPoint dp: this.dataList){
            if (dp.isThere(i, j, xi, xj)) counter++;
        }
        return counter;
    }

    //Counting datapoints in the dataset, in which there is xi in index i 
    public int Count(int i, int xi){
        int counter = 0;
        for (DataPoint dp: this.dataList){
            if (dp.isThere(i, xi)) counter++;
        }
        return counter;
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

    //To String method
    @Override
    public String toString() {
        return "Dataset: \n"
               + "Sample size =" + this.len() + "\n"
               + "Measurements Domain =" + Arrays.toString(this.measurementsDomain) + ";\n" 
               + "Class Domain =" + this.classDomain + ";\n"
               + "Data =" + this.dataList
               + "\n";
    }

    public static void main(String[] args) { 
       /*
        //Empty Dataset
        Dataset ds1 = new Dataset();
        System.out.println("Empty Dataset: " + ds1);

        //Creating Datapoints
        int[] m6 = {1,0,3,4,5,6,7,8,9,10};
        int c6 = 0;
        DataPoint dp6 = new DataPoint(m6,c6);

        int[] m7 = {1,2,3,31,5,6,7,8,9,10};
        int c7 = 0;
        DataPoint dp7 = new DataPoint(m7,c7);

        int[] m8 = {1,2,3,4,5,6,22,8,9,12};
        int c8 = 1;
        DataPoint dp8 = new DataPoint(m8,c8);

        //Adding to Dataset
        ds1.Add(dp6);
        System.out.println("Dataset with 1 datapoint: " + ds1 + "\n");

        for(int i = 0; i < 3; i++){
            ds1.Add(dp7);
        }
        System.out.println("Dataset with 3 datapoints: " + ds1 + "\n");

        ds1.Add(dp8);
        System.out.println("Dataset with 4 datapoints: " + ds1 + "\n");

        //Getting the size of the dataset
        System.out.println(ds1.len());

        //Dim of variable indexed in 9
        System.out.println("Dataset with 52 datapoints: " + ds1.measurementDim(9) + "\n");

        //Dim of the class
        System.out.println("Dataset with 52 datapoints: " + ds1.classDim() + "\n");

        //Getting the fiber of 0-class tumours
        System.out.println("0-class Fiber: \n" + ds1.Fiber(0) + "\n"); 

        //Getting the fiber of 1-class tumours
        System.out.println("1-class Fiber: \n" + ds1.Fiber(1) + "\n"); 

        //Counting the number of combinations in the dataset
        System.out.println("Number of times 1 shows in variable indexed 0, and 10 in 9, respectively and simultaneosly: "
                           + ds1.Count(0, 9, 1, 10) + "\n"); */
                           
        Dataset ds1 = new Dataset();
        int[] m6 = {1,0,3,4};
        int c6 = 0;
        DataPoint dp6 = new DataPoint(m6,c6);
        int[] m7 = {3,1,2,4};
        int c7 = 0;
        DataPoint dp7 = new DataPoint(m7,c7);
        int[] m8 = {1,0,2,4};
        int c8 = 1;
        DataPoint dp8 = new DataPoint(m8,c8);
        ds1.Add(dp6);
        for(int i = 0; i < 3; i++){
            ds1.Add(dp7);
        }
        ds1.Add(dp8);
        System.out.println("Dataset: " + ds1);
    }    
}

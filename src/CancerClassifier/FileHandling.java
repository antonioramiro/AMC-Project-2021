package CancerClassifier;

//Import Dataset Needs
import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner; 

//Serialization Needs
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

//As the name suggests, handles files
public class FileHandling {

  //getDataset receives the path and imports the .csv onto a variable of the type Dataset
  public static Dataset getDataset(String path) {

    try {
      //initializing the instance of the file to be read and the csv reader of said file
      File csvFile = new File(path);
      Scanner csvReader = new Scanner(csvFile);

      //for the first line, reading the line
      String firstData = csvReader.nextLine();
      String[] firstValues = (firstData.split(",")); //parsing the string, splitting the commas
      int measurementNumber = firstValues.length - 1; //getting how many measurements the line has (the last one, -1 is the class)

      //now that we know the measurement number, we can create an int array, and convert the previous String array to int.array
      int[] firstMeasurements = new int[measurementNumber];
      for (int i = 0; i < measurementNumber; i++) {
        int thisMeasurement = Integer.parseInt(firstValues[i]);
        firstMeasurements[i] = thisMeasurement;
      }

      //setting the class of the line (last item) to a variable
      int firstClassification = Integer.parseInt(firstValues[measurementNumber]);

      //creating a datapoint w/ the measurements and the classification
      DataPoint first = new DataPoint(firstMeasurements, firstClassification);

      //creating a dataset, w/ the now know number of measurements
      Dataset newDataset = new Dataset(measurementNumber);
      
      //adding the first datapoint to the dataset
      newDataset.Add(first);

      //having the measurement number, repeat previous procedure to remaining lines
      while (csvReader.hasNextLine()) {
        //initialize data separated by commas array
        String data = csvReader.nextLine();
        String[] values = (data.split(","));

        //Setting classification
        int classification = Integer.parseInt(values[measurementNumber]);
        
        //Setting measurements
        int[] measurements = new int[measurementNumber];
        for (int i = 0; i < firstValues.length - 1; i++) {
          int thisMeasurement = Integer.parseInt(values[i]);
          measurements[i] = thisMeasurement;
        }    

        //creating and adding datapooint do dataset
        DataPoint dp = new DataPoint(measurements, classification);
        newDataset.Add(dp);
      }

      //closing the file 
      csvReader.close();
      
      //returning the Dataset
      return newDataset;

      //catching exception (usually found when the file is not in the correct path)
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } 

    //if try fails, return null (since try failed, the previous catch instance was executed)
    return null;
  }

  //exporting classifier to a file, receives the dataset and the name to be given to it
  public static void exportClassifier(Dataset data, String datasetName){
    //declaring the bundle (running chow liu, and mrft)
    ClassifierPackager  object = new ClassifierPackager(data);

    //setting the name of the file
    String filename = datasetName + ".classifier";
      
    //Serializing
    try{   
      //Saving of object in a file
      FileOutputStream file = new FileOutputStream("Classifiers/" + filename);
      ObjectOutputStream out = new ObjectOutputStream(file);
        
      // Method for serialization of object
      out.writeObject(object);
        
      //closing methods
      out.close();
      file.close();

      //confirming success
      System.out.println("Classifier exported successfully.");
    
      //catching exception - path not found
    }catch(IOException ex){
      System.out.println("IOException is caught");
      ex.printStackTrace();
    }
  }

  //importing the classifier
  public static ClassifierPackager importClassifier(String path){

    //Deserializing
    try{   
      // Reading the object from a file
      FileInputStream file = new FileInputStream(path);
      ObjectInputStream in = new ObjectInputStream(file);
        
      // Method for deserialization of object
      ClassifierPackager cp = (ClassifierPackager)in.readObject();
      
      //closing the methods
      in.close();
      file.close();
        
      //Confirming success
      System.out.println("Classifier imported successfully.");
      
      //returning the classifier package
      return cp;

    //catching exceptions
    }catch(IOException ex){
      ex.printStackTrace();
      System.out.println("IOException is caught");
    }catch(ClassNotFoundException ex){
      ex.printStackTrace();
      System.out.println("ClassNotFoundException is caught");
    }

    //returning a null (shall never happen, since exceptions are above)
    ClassifierPackager error = new ClassifierPackager(null);
    return error;
  }

    public static void main(String[] args) {
    
    //importing a dataset from path
    Dataset T = FileHandling.getDataset("Datasets/diabetes.csv");
    //proving dataset correct importing (w/o spamming the console)
    System.out.println("Dataset domain: " + Arrays.toString(T.measurementsDomain));

    //exporting the classifier
    FileHandling.exportClassifier(T,"diabetes");
  
    //importing the classifier
    ClassifierPackager cp = FileHandling.importClassifier("Classifiers/diabetes.classifier");
    
    //measurement vector
    int[] measurements = {1,3,0,0,0,0,0,0};

    //constructing a classifier w/ both arrays
    Classifier c = new Classifier(cp.getMrft(), cp.getFreq());

    //classify the measurements
    int result = c.Classify(measurements);
    System.out.println("Diagnosis: " + result);
  }
}

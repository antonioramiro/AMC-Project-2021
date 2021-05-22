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


public class FileHandling {

  //getDataset receives the path and imports the .csv onto a variable of the type Dataset
  public static Dataset getDataset(String path) {

    try {
      File csvFile = new File(path);
      Scanner csvReader = new Scanner(csvFile);

      //procedimento para a primeira linha, que definirá o nr de medições do dataset
      String firstData = csvReader.nextLine();
      String[] firstValues = (firstData.split(","));
      int measurementNumber = firstValues.length - 1;

      int[] firstMeasurements = new int[measurementNumber];
      for (int i = 0; i < measurementNumber; i++) {
        int thisMeasurement = Integer.parseInt(firstValues[i]);
        firstMeasurements[i] = thisMeasurement;
      }

      int firstClassification = Integer.parseInt(firstValues[measurementNumber]);

      Dataset newDataset = new Dataset(measurementNumber);
      DataPoint first = new DataPoint(firstMeasurements, firstClassification);
      newDataset.Add(first);

      // procedimento para as restantes linhas
      while (csvReader.hasNextLine()) {
        String data = csvReader.nextLine();
        String[] values = (data.split(","));

      int classification = Integer.parseInt(values[measurementNumber]);
      int[] measurements = new int[measurementNumber];

      for (int i = 0; i < firstValues.length - 1; i++) {
        int thisMeasurement = Integer.parseInt(values[i]);
        measurements[i] = thisMeasurement;
      }    

        DataPoint dp = new DataPoint(measurements, classification);
        newDataset.Add(dp);
      }
      csvReader.close();
      
      return newDataset;

      
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } 
    return null;
  }


  public static void exportClassifier(Dataset data, String datasetName){
    ClassifierPackager  object = new ClassifierPackager(data);
    String filename = datasetName + ".classifier";
      
    // Serialization 
    try
    {   
        //Saving of object in a file
        FileOutputStream file = new FileOutputStream("Classifiers/" + filename);
        ObjectOutputStream out = new ObjectOutputStream(file);
          
        // Method for serialization of object
        out.writeObject(object);
          
        out.close();
        file.close();
          
        System.out.println("Object has been serialized");

    }
      
    catch(IOException ex)
    {
        System.out.println("IOException is caught");
        ex.printStackTrace();
    }
  }

  public static ClassifierPackager importClassifier(String path){
    // Deserialization
    try
    {   
        // Reading the object from a file
        FileInputStream file = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(file);
          
        // Method for deserialization of object
        ClassifierPackager cp = (ClassifierPackager)in.readObject();
          
        in.close();
        file.close();
          
        System.out.println("Object has been deserialized ");
       
        return cp;

    }
      
    catch(IOException ex)
    {
      ex.printStackTrace();
      System.out.println("IOException is caught");
    }
      
    catch(ClassNotFoundException ex)
    {
      ex.printStackTrace();
      System.out.println("ClassNotFoundException is caught");
    }

    ClassifierPackager error = new ClassifierPackager(null);
    return error;
  }

  public static void main(String[] args) {
    
    //System.out.println("\n");
    Dataset a = FileHandling.getDataset("Datasets/diabetes.csv");
    System.out.println("Dataset example: " + Arrays.toString(a.measurementsDomain));

    FileHandling.exportClassifier(a,"diabetes");
    ClassifierPackager b = FileHandling.importClassifier("Classifiers/diabetes.classifier");
    System.out.println(b);
    
    int[] d = {1,3,0,0,0,0,0,0,0,0};
    Classifier c = new Classifier(b.getMrft(), b.getFreq());
    int result = c.Classify(d);
    System.out.println(result);
    
  }
}

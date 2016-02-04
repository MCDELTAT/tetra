

public class Main {

  public static void main(String[] args) {

 
  	DataManager dataFile = new DataManager("filename");

  	dataFile.dataParser(); // data does not start parsing until this is run
  	// I can change it to start this when file path and a DataManager object is created though

  	//System.out.println(dataFile.dim1Max); // example way of accessing object
		System.out.println(dataFile.dataArray.get(0).dim1);
		// dim 1 for particle 1. ArrayList is in DataManager class
		// you can iterate through it

		System.out.println(dataFile.dataArray.get(1).dim1); // dim 1 for particle 1
		System.out.println(dataFile.dataArray.size());
		
    }
}










  













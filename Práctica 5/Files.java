package org.md.practica5_Boosting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.SerializationHelper;

class Files {

		public Files(){
			//Service class
		}
		
		/**
		 * Open the file
		 * @param path <code>String</code> of path
		 * @return Descriptor of the file: FileReader
		 */
		FileReader openFile(String path){
			FileReader fi = null;
			try {
				fi= new FileReader(path);
			} catch (FileNotFoundException e) {
				System.out.println("ERROR: Revisar path del fichero de datos: " + path);
			}
			return fi;
		}
			
		/**
		 * Load Instances from file
		 * @param path
		 * @return
		 */
		public Instances loadInstances(String path){
			Instances data = null;
			FileReader fi = this.openFile(path);
			try {
				data = new Instances(fi);
				fi.close();
				data.setClassIndex(data.numAttributes()-1);
			} catch (IOException e) {
				System.out.println("ERROR: Revisar contenido del fichero de datos");
			}
			return data;
		}
		
		/**
		 * Save instances to specified file
		 * @param data input
		 * @param path where data is going to be stored
		 */
		public void saveInstancesToFile(Instances data, String path){
			try {
				PrintWriter writer = new PrintWriter(new File(path));
				writer.println(data.toString());
				writer.close();
			} catch (FileNotFoundException e) {
				System.out.println("ERROR: Revisar path para los archivos de instancias filtrados");
			}
		}
		
		/**
		 * Save a string set into a specified file
		 * @param strings
		 * @param path where data is going to be stored
		 */
		public void saveStringsToFile(String strings, String path){
			try {
				PrintWriter writer = new PrintWriter(new File(path));
				writer.println(strings);
				writer.close();
			} catch (FileNotFoundException e) {
				System.out.println("ERROR: Revisar path para el guardado de archivos");
			}
		}

		/**
		 * Load trained model
		 * @param modelPath
		 * @return
		 */
		public Classifier loadModel(String modelPath){
			Classifier classifier = null;
			try {
				classifier = (Classifier) SerializationHelper.read(modelPath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return classifier;
		}
		
		/**
		 * Save trained model
		 * @param modelPath
		 * @param classifier
		 */
		public void saveModel(String modelPath, Classifier classifier){
			try {
				weka.core.SerializationHelper.write(modelPath, classifier);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}

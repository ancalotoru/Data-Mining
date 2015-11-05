package org.md.practica2_KNN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.neighboursearch.NearestNeighbourSearch;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Normalize;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.instance.RemoveRange;

public class Main {

	private static Instances data;
	
	public static void main(String[] args) {
		boolean dTest = false, dMink = false, dParam = false, dK = false;
		String m = "", param = "", k="";
		Files svcLoad = new Files();
		Main main = new Main();
				
		//Depending on the second parameter, we make partitions of the original data
		//to use different evaluation methods
		
		if(args.length == 0){
			usage();
			return;
		}else{
			for(int i = 0; i < args.length; i += 2){
				if (args[i].equals("-d")) {
					data = svcLoad.loadInstances(args[i+1]);
					dTest = true;
				}else if(args[i].equals("-m")){
					m = args[i+1];
					dMink = true;
				}else if(args[i].equals("-p")){
					param = args[i+1];
					dParam = true;
				}else if(args[i].equals("-k")){
					k = args[i+1];
					dK = true;
				}else{
					usage();
					return;
				}
			}
			if(!(dParam && dMink && dTest)) {
				usage();
				return;
			}else{
				System.out.println();
				System.out.println("======== KNN CLASIFIER ========");
				System.out.println("Starting modeling process with this parameters:");
				System.out.println("\t -> " + k + "NN");
				System.out.println("\t -> Evaluation Method: Hold-Out" );
				System.out.println("\t -> Minkowski distance parameter: " + m);
				System.out.println("\t -> Percentage for training dataset: " + param);
			}
		}
		//Starting clasification
		try {
			float start = System.currentTimeMillis();
			// Bit of preprocess
			Preprocess prep = new Preprocess(data);
			prep.filterRandomize(data);
			data.setClassIndex(data.numAttributes() - 1);
			KNN knn = new KNN(Integer.parseInt(param));
			//We normalize the instances to place them into the same scale.
			prep.filterNormalize(data);
			//Divide the dataset into 2 subsets (train and test)
			Instances train = prep.filterRemoveRange(data, "first-" + String.valueOf(Math.round(data.numInstances()*Double.parseDouble(param)/100)));
			Instances test = prep.filterRemoveRange(data, String.valueOf(Math.round(data.numInstances()*Double.parseDouble(param)/100)) + "-last");
			System.out.println();
			System.out.println("Number of instances of TRAIN dataset = " + train.numInstances());
			System.out.println("Number of instances of TEST dataset  = " + test.numInstances());
			train.setClassIndex(train.numAttributes()-1);
			test.setClassIndex(test.numAttributes()-1);
			// Pure Clasification
			knn.holdOut(train, test, Integer.parseInt(m), Integer.parseInt(k));
			System.out.printf("\nElapsed time: %f", System.currentTimeMillis() - start);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private static void usage(){
		System.out.println("ERROR: You entered the parameters wrongly");
		System.out.println("USAGE: ");
		System.out.println("\t-d [data path].arff");
		System.out.println("\t-m Minkowski distance parameter");
		System.out.println("\t-k K neighbors of KNN classifier");
		System.out.println("\t-p Percentage for train dataset:");
	}
 }

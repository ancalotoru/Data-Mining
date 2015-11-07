package org.md.practica5_Boosting;

import java.util.Arrays;
import java.util.Random;

import weka.core.Instances;

public class Boosting {
	
	private Random random = new Random(42);
	private ConfusionMatrix confusionMatrix = new ConfusionMatrix(0, 0, 0, 0);
	
	public Boosting(){
		
	}
	
	private void kFoldCV(Instances data, int k ) throws Exception{
		// Split the data
		Preprocess prep = new Preprocess(data);
		KNN knn = new KNN(k);
		double range = data.numInstances() / k;
		double inicio = 1;
		double fin = range;
		int l = 2;
		Instances train = new Instances(data);
		Instances test = new Instances(data);
		for(int i = 0; i<k; i++){
			train = prep.filterRemoveRange(data, String.valueOf(inicio) + "-" + String.valueOf(fin));
			test = prep.filterRemoveRange(data, "first-" + String.valueOf(inicio) + "," + String.valueOf(fin+1) + "-last");
			this.boosting(l, train, test);
		}
	}
	
	/**
	 * Sample the data with a non-uniform distribution of instances.
	 * @param data instances to take from.
	 * @param weights of the given instances.
	 * @return the new weighted instances subset.
	 */
	private Instances rouletteWheel(Instances data, double[] weights){
		double r = random.nextDouble();
		Instances newdata = new Instances(data);
		double ac = weights[0];
		int j = 0;
		for(int i=0; i<data.numInstances();i++){
			while(r<=ac){
				ac = ac + weights[j];
				j++;
			}
			newdata.add(data.instance(j));
		}		
		return newdata;
	}
	
	private void boosting(int l, Instances train, Instances test){
		// We create an array of 2-NN classifier with lenght of l
		KNN classifiers[] = new KNN[l];
		// We initialize the weights to 1/N
		double weights[] = new double[train.numInstances()];
		Arrays.fill(weights, 1/train.numInstances());
		for(KNN knn : classifiers){
			knn = new KNN(2);
			// We sample l populations from the train dataset
			Instances sample = this.rouletteWheel(train, weights);
			confusionMatrix = knn.holdOut(sample, test, 2, 2);
		}
	}
}

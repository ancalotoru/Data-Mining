package org.md.practica2_KNN;

import java.util.Arrays;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class KNN {
	private int k;	
	
	public KNN(int k){
		this.k = k;
	}
	
	/**
	 * Calculate the vector between two instances,
	 * then make the module to get the distance 
	 * @param i1
	 * @param i2
	 */
	public double distance(Instance i1, Instance i2, double m){
		double distance = 0;
		
		for(int index = 0; index< i1.numAttributes() - 1 ; index++){
			if(i1.attribute(index).isNumeric() && i2.attribute(index).isNumeric()){
				distance += Math.pow(Math.abs(i1.value(index) - i2.value(index)), m);
			}else if(i1.attribute(index).isNominal() && i2.attribute(index).isNominal()){
				//If we found a nominal attribute, if the value match, we set the distance to 0 otherwise
				//the distance is set to 1
				if(i1.value(index) != i2.value(index))
					distance += 1;
			}
		}
		return Math.pow(distance, 1/m);
	}
	
	public void holdOut(Instances train, Instances test, int m, int k){
		try {
			//We make the partitions
			ConfusionMatrix confusionMatrix = new ConfusionMatrix(0, 0, 0, 0);
			double aux = 0, min = 0;
			
			for(int te = 0; te < test.numInstances(); te++){
				double distances[] = new double[k];
				Arrays.fill(distances, Double.MAX_VALUE);
				double nearestNeighbors[] = new double[k];
				Arrays.fill(nearestNeighbors, Double.MAX_VALUE);
				for(int tr = 0; tr < train.numInstances(); tr++){
					aux = distance(train.instance(tr), test.instance(te), m);
					if(aux < distances[k-1])
						insertOrdered(aux, distances, train.instance(tr).classValue(), nearestNeighbors);
				}
				statisticalMeasures(nearestNeighbors, test, te, confusionMatrix);
			}
		System.out.println();
		confusionMatrix.print();
		System.out.println("===== END =====");
		} catch (Exception e) {
			System.out.println("ERROR MAKING PARTITIONS");
			e.printStackTrace();
		}
	}
	
	private void statisticalMeasures(double[] nearestNeighbors, Instances test, int index, ConfusionMatrix confusionMatrix){
		int matches = 0;
		int positive = 0, negative = 0;
		for(int i = 0; i<nearestNeighbors.length; i++){
			if (nearestNeighbors[i] == test.instance(index).classValue()){
				matches ++;
			}
		}
		if(matches >= nearestNeighbors.length/2.0){
			if(test.instance(index).classValue() == 0.0){
				confusionMatrix.settP(confusionMatrix.gettP() + 1);
			}
			else confusionMatrix.settN(confusionMatrix.gettN() + 1);
		}else {
			if(test.instance(index).classValue() == 0.0) confusionMatrix.setfP(confusionMatrix.getfP() + 1);
			else confusionMatrix.setfN(confusionMatrix.getfN() + 1);;
		}
	}

	private void insertOrdered(double aux, double[] distances, double classValue, double[] nearestNeighbors) {
		boolean found = false;
		int i = 0;
		while (!found && i < distances.length){
			if(distances[i] >= aux){
				found = true;
				for(int j = distances.length - 2; j>=i;j--){
					distances[j+1] = distances[j];
					nearestNeighbors[j+1] = nearestNeighbors[j];
				}
				distances[i] = aux;
				nearestNeighbors[i] = classValue;
			}
			i++;
		}
	}

	
}

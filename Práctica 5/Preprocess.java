package org.md.practica5_Boosting;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Normalize;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.instance.RemoveRange;

public class Preprocess {
	
	private Instances data;
	
	public Preprocess(Instances pData){
		data = pData;
	}
	
	public Instances filterRemoveRange(Instances data, String range) throws Exception{
		RemoveRange remRange = new RemoveRange();
		remRange.setInputFormat(data);
		remRange.setInstancesIndices(range);
		return Filter.useFilter(data, remRange);
	}
	
	public Instances filterRandomize(Instances data) throws Exception{
		Randomize randomize = new Randomize();
		randomize.setRandomSeed(42);
		randomize.setInputFormat(data);
		data = Filter.useFilter(data, randomize);
		return data;
	}
	
	public Instances filterNormalize(Instances data) throws Exception{
		Normalize normalize = new Normalize();
		normalize.setInputFormat(data);
		data = Filter.useFilter(data, normalize);
		return data;
	}

}

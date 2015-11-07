package org.md.practica5_Boosting;

public class ConfusionMatrix {
	private double tP;
	private double fP;
	private double tN;
	private double fN;
	
	public ConfusionMatrix(double tP, double fP, double tN, double fN) {
		super();
		this.tP = tP;
		this.fP = fP;
		this.tN = tN;
		this.fN = fN;
	}

	public double gettP() {
		return tP;
	}

	public void settP(double tP) {
		this.tP = tP;
	}

	public double getfP() {
		return fP;
	}

	public void setfP(double fP) {
		this.fP = fP;
	}

	public double gettN() {
		return tN;
	}

	public void settN(double tN) {
		this.tN = tN;
	}

	public double getfN() {
		return fN;
	}

	public void setfN(double fN) {
		this.fN = fN;
	}
	
	public double accuracy(){
		return (tP+tN)/(tP+tN+fP+fN);
	}
	
	public double precision(){
		return 100*(tP/(tP+fP));
	}
	
	public double sensitivity(){
		return (tP/(tP+fN));
	}
	
	public double fnr(){
		return fN/(tP+fN);
	}
	
	public double fpr(){
		return fP/(fP+tN);
	}
	
	public double specificity(){
		return tN/(fP+tN);
	}
	
	public double fMeasure(){
		return Math.pow((0.5 *(1/(precision()/100)+1/sensitivity())),-1.0);
		//return (2*precision() * sensitivity())/(precision()+sensitivity());
	}
	
	public void print() {
		System.out.println("Confusion Matrix");
		System.out.println();
		System.out.println("\t   REAL");
		System.out.println("\t + \t -");
		System.out.println("\t"+String.valueOf(tP)+"\t"+String.valueOf(fP)+" <-- + ĉ");
		System.out.println("\t"+String.valueOf(fN)+"\t"+String.valueOf(tN)+" <-- - ĉ");
		System.out.println("---------------------------");
		System.out.println("Sensitivity => "+ sensitivity());
		System.out.println("FNR => "+fnr());
		System.out.println("FPR => "+fpr());
		System.out.println("Specificity => "+specificity());
		System.out.println("Precision => "+precision());
		System.out.println("Accuracy => "+accuracy());
		System.out.println("F-Measure => "+fMeasure());
	}
}

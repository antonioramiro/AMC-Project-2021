package CancerClassifier;

import java.util.Arrays;


public class Phi {
	double[][] L;

	public Phi(double[][] l) {
		this.L = l;
	}

    public Phi(int i, int j) {
        double[][] l = new double[i][j];
		this.L = l;
	}

    public double getPhi(int i, int j) {
        System.out.println("i: " + i +"; j:" + j);
        System.out.println("phi :" + Arrays.deepToString(L));
        return L[i][j];
	}

    public void setPhi(int i, int j, double value){
        this.L[i][j] = value;
    }

	@Override
	public String toString() {
		return "[Phi=" + Arrays.deepToString(L) +"]";
	}
}
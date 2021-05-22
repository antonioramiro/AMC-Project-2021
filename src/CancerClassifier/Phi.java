package CancerClassifier;

import java.io.Serializable;
import java.util.Arrays;


public class Phi implements Serializable{
	private static final long serialVersionUID = 1L;
	double[][] L;

	public Phi(double[][] l) {
		this.L = l;
	}

    public Phi(int i, int j) {
        double[][] l = new double[i][j];
		this.L = l;
	}

    public double getPhi(int i, int j) {
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
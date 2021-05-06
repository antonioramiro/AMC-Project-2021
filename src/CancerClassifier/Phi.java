package CancerClassifier;

import java.util.ArrayList;
import java.util.Arrays;


public class Phi {
	double[][] L;

	public Phi(double[][] l) {
		this.L = l;
	}

	@Override
	public String toString() {
		return "[Phi=" + Arrays.deepToString(L) +"]";
	}
}
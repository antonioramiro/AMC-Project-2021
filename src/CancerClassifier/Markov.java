package CancerClassifier;

import java.util.ArrayList;
import java.util.Arrays;

public class Markov {

	private ArrayList<ArrayList<Phi>> Phis;
	private ArrayList<ArrayList<Integer>> index;
	private int N;

	public Markov(int N){
		ArrayList<ArrayList<Phi>> phis = new ArrayList<ArrayList<Phi>>();
		this.Phis = phis;
		ArrayList<ArrayList<Integer>> index = new ArrayList<ArrayList<Integer>>();
		this.index = index;

		for (int i = 0; i < N; i++) {
			this.Phis.add(new ArrayList<Phi>());
			this.index.add(new ArrayList<Integer>());
		}

		this.N = N;
	}

	public void setMarkov(int i, int j, Phi p) {
		if (i < N && j < N) {
			this.Phis.get(i).add(p);
			this.index.get(i).add(j);
		} else {
			throw new AssertionError("At least one index (" +i+ " or "+j+ ") out of Markov's range (" +N + ").");
		}
	}
	
	//acrescentar expressao de erro caso passe o dom.
	public Phi getMarkov(int i, int j) {
		int minor = Math.min(i,j);
		int major = Math.max(i,j);
		boolean isThere = false;
		int k = 0;
		if(minor < N && major < N){
			for (int y = 0; !isThere && y < N; y++) {
				if (this.index.get(minor).size() > 0 &&	this.index.get(minor).get(y) == major) {
					isThere = true;
					k = y;
				}
				else{
					throw new AssertionError("Requested Phi matrix not in domain.");
				}
			}
			return this.Phis.get(minor).get(k);
		}else{
			throw new AssertionError("Requested i  j are out of phi domain");
		}
		
	}


	public ArrayList<ArrayList<Phi>> getPhis() {
		return this.Phis;
	}

	public void setPhis(ArrayList<ArrayList<Phi>> Phis) {
		this.Phis = Phis;
	}

	public ArrayList<ArrayList<Integer>> getIndex() {
		return this.index;
	}

	public void setIndex(ArrayList<ArrayList<Integer>> index) {
		this.index = index;
	}

	public int getN() {
		return this.N;
	}

	public void setN(int N) {
		this.N = N;
	}

	@Override
	public String toString() {
		return "{" +
			" Phis='" + getPhis() + "'" +
			", index='" + getIndex() + "'" +
			", N='" + getN() + "'" +
			"}";
	}

	public static void main(String[] args) {
		double[][] x = { { 1, 2, 4 , 5, 1, 2 }, {1, 2, 4 , 5, 1, 2  } };
		double[][] z = { { 1, 2,3 }, {  1, 2,3  }, {  1, 2,3  } };
		Phi a = new Phi(x);
		Phi b = new Phi(z);

		Markov benign = new Markov(5);
		benign.setMarkov(0, 4, a);
		benign.setMarkov(4, 3, b);
		benign.setMarkov(0, 0, a);
		System.out.println(benign.toString());

		System.out.println("benign 0,4" + benign.getMarkov(0, 4));
		
		System.out.println(benign.getMarkov(4,0));
		/*
		ArrayList<ArrayList<Phi>> X = new ArrayList<ArrayList<Phi>>();

		X.add(new ArrayList<Phi>());
		X.add(new ArrayList<Phi>());

		X.get(0).add(a);
		X.get(0).add(b);

		System.out.println(X);*/

		
	}
}
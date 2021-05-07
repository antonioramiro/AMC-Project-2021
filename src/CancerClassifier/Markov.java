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
			}
			if (!isThere) {
				throw new AssertionError("Node not found");
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
		double[][] matrizdophi01 = {{ 2, 2 }, { 2, 2 }, {2 , 2}};
		double[][] matrizdophi12 = {{ 1.0, 1.0, 1.0, 1.0, 1.0}, {1.0, 1.0, 1.0, 1.0, 1.0}};
		double[][] matrizdophi13 = {{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}};
		double[][] matrizdophi14 = {{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0} , {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}};
		
		Phi phi01 = new Phi(matrizdophi01);
		Phi phi12 = new Phi(matrizdophi12);
		Phi phi13 = new Phi(matrizdophi13);
		Phi phi14 = new Phi(matrizdophi14);

		Markov markov = new Markov(5);
		markov.setMarkov(0, 1, phi01);
		markov.setMarkov(1, 2, phi12);
		markov.setMarkov(1, 3, phi13);
		markov.setMarkov(1, 4, phi14);
		System.out.println(markov.toString());
		
		System.out.println("markov 0,1" + markov.getMarkov(0, 1));
		System.out.println("markov 1,2" + markov.getMarkov(1, 2));
		System.out.println("markov 1,4" + markov.getMarkov(1, 4));
		System.out.println("markov 1,3" + markov.getMarkov(1, 3));

		

		/*
		ArrayList<ArrayList<Phi>> X = new ArrayList<ArrayList<Phi>>();

		X.add(new ArrayList<Phi>());
		X.add(new ArrayList<Phi>());

		X.get(0).add(a);
		X.get(0).add(b);

		System.out.println(X);*/
		//markov: { Phis='[[[Phi=[[1.0, 1.0], [1.0, 1.0], [1.0, 1.0]]]],
				//		[[Phi=[[1.0, 1.0, 1.0, 1.0, 1.0], [1.0, 1.0, 1.0, 1.0, 1.0]]], 
		//[Phi=[[1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0], [1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0]]], 
		//[Phi=[[1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0], [1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0]]]],
					//	[], [], []]', index='[[1], [2, 3, 4], [], [], []]', N='5'}		
	}
}
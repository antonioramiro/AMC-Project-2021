package CancerClassifier;

import java.util.Arrays;

/* A implementação da classe Phi é trivial e essencialmente um wrapper
para matrizes de reais. Tal é necessário para que seja possível
definir estes objetos como o elemento básico de uma ArrayList. */

public class Phi {

	//Atributo
	private double[][] matrix; 

	//Construtor que recebe uma matriz de doubles e a envolve (wraps) pela classe Phi
	public Phi(double[][] l) {
		this.matrix = l;
	}

	//Construtor baseadop na dimensão da matriz, criando uma matriz Phi vazia.
    public Phi(int i, int j) {
        double[][] l = new double[i][j];
		this.matrix = l;
	}

	//Getter para obter o elemento que ocupa a posiçãi i,j da matriz Phi
    public double getPhi(int i, int j) {
        return matrix[i][j];
	}

	//Setter que define determinado valor (value) na posição i,j da matriz
    public void setPhi(int i, int j, double value){
        this.matrix[i][j] = value;
    }

	//Método impressora - recorre ao pacote Arrays
	@Override
	public String toString() {
		return "[Phi=" + Arrays.deepToString(matrix) +"]";
	}
}
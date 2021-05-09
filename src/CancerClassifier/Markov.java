package CancerClassifier;

/* A classe Markov implementa um objeto no qual se pode pensar como uma matriz cujos elementos
são o objeto Phi (implementado em Phi.java). A implementação da mesma aproveita-se do facto
de ser uma matriz simétrica e esparsa, e é inspirada pelo trabalho de  Gundersen e Steihaug, ilustrado em:
Storage Formats for Sparse Matrices in Java, de Mikel Luján, Anila Usman, Patrick Hardie, T. L. Freeman e
 John R. Gurd.  (https://link.springer.com/content/pdf/10.1007%2F11428831_45.pdf, Figura 2) 
*/

import java.util.ArrayList;
import java.util.Arrays;

public class Markov {

	//Atributos
	//Lista de Listas com os elementos de Phi
	private ArrayList<ArrayList<Phi>> Phis; //Lista de Listas com os elementos de Phi

	//Lista de Listas com os índices das colunas onde há um phi. As posições dos indices na lista, 
	//é igual à posição do Phi correspondente no atrbuto anterior. A lista na qual o índice se localiza
	//permite conhecer qual a linha da matriz na qual ele está 
	private ArrayList<ArrayList<Integer>> index;  
	
	//número de linhas da matriz abstrata (dimensão N*N)
	private int N; 

	//Método construtor, recebendo a dimensão
	public Markov(int N){
		
		//inicialização e atribuição dos tipos de dados aos atributos
		ArrayList<ArrayList<Phi>> phis = new ArrayList<ArrayList<Phi>>();
		this.Phis = phis;
		ArrayList<ArrayList<Integer>> index = new ArrayList<ArrayList<Integer>>();
		this.index = index;

		//anteriormente é apenas criada a lista "mãe" de cada um dos objetos
		//aqui, são criadas as listas correspondentes às linhas da matriz, simultaneamente
		//na lista dos indices, como na lista dos phis
		for (int i = 0; i < N; i++) {
			this.Phis.add(new ArrayList<Phi>());
			this.index.add(new ArrayList<Integer>());
		}

		//por fim, sem motivo particular, definir o atributo da dimensão
		this.N = N;
	}

	//setter desta classe de dados, atribui na posição i,j da markov o elemento phi
	public void setMarkov(int i, int j, Phi p) {

		//Reconhecendo a simetricidade da "matriz", vamos apenas trabalhar com a sua
		//componente triangular superior, onde o índice da linha é sempre menor que 
		//o da coluna. É para isso que se usa a seguinte comparação 
		int minor = Math.min(i,j);
		int major = Math.max(i,j);

		//de seguida, se estiver no domínio, inserir a matriz phi (p) e o indice da coluna (major) onde estará
		//na lista com o indice da linha (minor) onde estará, nas implementações respetivas
		if (minor < N && major < N) {
			this.Phis.get(minor).add(p);
			this.index.get(minor).add(major);
		} else {
			throw new AssertionError("At least one index (" +i+ " or "+j+ ") out of Markov's range (" +N + ").");
		}
	}
	
	//getter desta classe, recebe as coordenadas (i,j) na matriz e devolve o phi nessa posição
	public Phi getMarkov(int i, int j) {

		//primeiro, dado que é simétrica, manipular os indíces para que se trabalhe sempre na
		//matriz triangular superior, e nunca se vá tentar buscar Phis à metade de baixo, 
		//que não existe nesta representação - mas cujos valores estão armazenados na triangular
		//superior, dada a simetricidade da mesma
		int minor = Math.min(i,j);
		int major = Math.max(i,j);
		
		//declarar variáveis
		boolean isThere = false; //phi encontrado?
		int k = 0; //onde será armazenado o indíce da coluna
		
		if(minor < N && major < N){

			//este ciclo irá correr todas as posições ocupadas da linha minor
			//da matriz e comparar o indíce da coluna com o valor major,
			//mas apenas se a linha minor tiver elementos, se o valor ainda não tiver sido encontrado
			//e enquanto o índice para a posição do número da coluna na matriz dos indices ainda não tiver ultrapassado a sua extensão máxima
			int indexSize = this.index.get(minor).size();
			if(indexSize != 0){
				for (int y = 0; !isThere && y < indexSize; y++) {

					//compara o número da coluna com o número da coluna no índice y da matriz dos indices
					if (this.index.get(minor).get(y) == major) {
						isThere = true;
						k = y;
					}
				}
				if (!isThere) {
					throw new AssertionError("Phi ("+minor+","+ major+") not found");
				}
				return this.Phis.get(minor).get(k);
			}else{
				throw new AssertionError("Matrix line " +minor+" only has "+indexSize+" elements. So "+ major + "is out of range.");
			}
		}else{
			throw new AssertionError("Requested ("+minor+","+ major+") are out of Markov's domain");
		}
		
	}

	@Override
	public String toString() {
		return "{" +
			" Phis='" + this.Phis + "'" +
			", index='" + this.index + "'" +
			", N='" + this.N + "'" +
			"}";
	}

	public static void main(String[] args) {

		//Creating the Phis content
		double[][] matrizdophi01 = {{ 2, 2 }, { 2, 2 }, {2 , 2}};
		double[][] matrizdophi12 = {{ 1.0, 1.0, 1.0, 1.0, 1.0}, {1.0, 1.0, 1.0, 1.0, 1.0}};
		double[][] matrizdophi13 = {{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}};
		double[][] matrizdophi14 = {{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0} , {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}};
		
		//Wrapping content in Phi class
		Phi phi01 = new Phi(matrizdophi01);
		Phi phi12 = new Phi(matrizdophi12);
		Phi phi13 = new Phi(matrizdophi13);
		Phi phi14 = new Phi(matrizdophi14);

		//Creating a Phi matrix and populating it
		Markov markov = new Markov(5);
		markov.setMarkov(0, 1, phi01);
		markov.setMarkov(1, 2, phi12);
		markov.setMarkov(3, 1, phi13);
		markov.setMarkov(1, 4, phi14);
		System.out.println(markov.toString());
		
		//Obtaining the phis in markov(i,j)
		System.out.println("markov 0,1" + markov.getMarkov(0, 1));
		System.out.println("markov 1,2" + markov.getMarkov(1, 2));
		System.out.println("markov 1,4" + markov.getMarkov(1, 4));
		System.out.println("markov 1,3" + markov.getMarkov(1, 3));

		//Testing for inexistant phis in domain
		//System.out.println("markov 1,3" + markov.getMarkov(0, 4));

		

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
import java.io.IOException;


public class Main {


	public static void main(String[] args) throws IOException{
		Graph graph = new Graph("test.txt");

		graph.solveTSP();
	}


}

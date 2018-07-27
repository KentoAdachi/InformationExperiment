import java.io.IOException;


public class Main {
	
	public static void main(String[] args) throws IOException{
		Graph graph = new Graph("test.txt");
//		Graph graph = new Graph(6);

		graph.solveTSPEnum();
		graph.solveTSPNearestNeihgbor();
		graph.solveTSPNearestAddition();
	}


}

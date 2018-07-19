import java.io.IOException;


public class Main {
	
	public static void main(String[] args) throws IOException{
		Graph graph = new Graph("test.txt");
//		Graph graph = new Graph(10);

		graph.solveTSPEnum();
		graph.solveTSPNearestNeihgbor();
		graph.solveTSPNearestAddition();
	}


}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


/**
 * @author BP16001
 *
 */
public class Graph {

	private static final int SEED = 100;
	private static final int INF = 9999;
	private int n_;//要素数
	private int[][] elements_;
	String filename_;
	/**
	 * @param filename
	 * @throws IOException
	 */
	public Graph(String filename) throws IOException {
		// TODO 自動生成されたコンストラクター・スタブ
		filename_ = filename;
		init_graph();
	}
	
	public Graph(int size){
		init_graph(size);
	}


	/**
	 * 完全列挙法により
	 * 巡回セールスマン問題を解き最短経路を返す
	 * @return 最短経路
	 */
	void solveTSPEnum(){
		//最短距離を定義

		ArrayList<Integer>list = new ArrayList<>();
		Route route = searchEnum(list,0, 0);
		System.out.print("最短経路 = ");
		for (Integer integer : route.elements_) {
			System.out.print(integer+" ");
		}
		System.out.println("");
		System.out.println("道のり = "+route.far_);
		//選択した場所までの距離を加算
		//その都市を選択済みにする
		return;
	}

	Route searchEnum(ArrayList<Integer>list,int far,int position) {
		ArrayList<Integer>tmp;
		Route route;
		Route min = new Route(INF, null);
		int far2;

		if (list.size() == n_) {
			far += distance(position, list.get(0));
			return new Route(far, list);
		}

		for(int i = 0;i < n_;i++) {
			if (!list.contains(i)) {
				tmp = new ArrayList<>(list);
				tmp.add(i);
				far2 = far;
				if (tmp.size() != 1) {
					far2 += distance(position, i);
				}
				route = searchEnum(tmp,far2,i);
				if (route.far_ < min.far_ ) {
					min = route;
				}
			}
		}
		return min;
	}
	
	void solveTSPNearestNeihgbor(){
		ArrayList<Integer>list = new ArrayList<>();
		Route route = searchNearestNeighbor(0,list,0);
		System.out.print("最短経路 = ");
		for (Integer integer : route.elements_) {
			System.out.print(integer+" ");
		}
		System.out.println("");
		System.out.println("道のり = "+route.far_);
		
		return;
	}
	
	Route searchNearestNeighbor(int position,ArrayList<Integer>list,int far){
		int next_city = -1;
		int min_dist = INF;
		int i;
		
		if (list.size() == n_) {
			far += distance(position, list.get(0));
			return new Route(far, list);
		}
		
		for(i = 0;i < n_; i++){
			if(list.contains(i))continue;
			if (distance(position, i) < min_dist ) {
				next_city = i;
				min_dist = distance(position, i);
			}
		}
		list.add(next_city);
		far += distance(position, next_city);
		
		
		return searchNearestNeighbor(next_city, list, far);
	}
	
	void solveTSPNearestAddition(){
		ArrayList<Integer>list = new ArrayList<>();
//		list.add(0);
		list.add(0);
		Route route = new Route(0, list);
		route = searchNearestAddition(route);
		
		route.calcFar(this);
		
		System.out.print("最短経路 = ");
		for (Integer integer : route.elements_) {
			System.out.print(integer+" ");
		}
		System.out.println("");
		System.out.println("道のり = "+route.far_);
	}
	
	Route searchNearestAddition(Route route){
		int min_dist = INF;
		int from = -1;
		int to = -1;
		
		if(route.elements_.size() == n_){
			return route;
		}
		
		
		for (int i = 0; i < n_; i++) {
			if(route.elements_.contains(i)){
				for (int j = 0; j < n_; j++) {
					if(!route.elements_.contains(j)){
						if(distance(i, j) < min_dist){
							min_dist = distance(i, j);
							from = i;
							to = j;
						}
					}
				}
			}
		}
		
		route.elements_.add(route.elements_.lastIndexOf(from), to);
		
		return searchNearestAddition(route);
		
	}

	/**
	 * 点間の距離
	 * @return
	 */
	public int[][] getElements(){
		return elements_;
	}

	/**
	 * 距離を返す
	 * @param a
	 * @param b
	 * @return
	 */
	public int distance(int a, int b){
		return elements_[a][b];
	}

	/**
	 * 要素数
	 * @return
	 */
	public int getNumElements(){
		return n_;
	}

	/**
	 * グラフの初期化
	 * @throws IOException
	 */
	public void init_graph() throws IOException{
		System.out.println("ファイルを読み込みます : "+ filename_);

		BufferedReader reader = new BufferedReader(new FileReader(filename_));
		String buf;

		ArrayList<ArrayList<Integer>>lists = new ArrayList<>();
		 while ((buf = reader.readLine()) != null) {

			 ArrayList<Integer> ints = new ArrayList<>();
			 for (String s : buf.split(" ")) {
				ints.add(Integer.parseInt(s));
			}
			 lists.add(ints);
			
		}
		 n_ = lists.size();
		 elements_ = new int[n_][n_];
		 for(int i = 0; i < n_;i++){
			 for(int j = 0;j <n_;j++){
				 elements_[i][j] = lists.get(i).get(j);
			 }
		 }
		 
		 print_elements();

		reader.close();
	}

	public void init_graph(int size){
		Random random = new Random(SEED);
		
		 n_ = size;
		 elements_ = new int[n_][n_];
		 for(int i = 0; i < n_;i++){
			 for(int j = 0;j <n_;j++){
				 if(j < i){
					elements_[i][j] = elements_[j][i];
				 }
				 if(i == j){
					 elements_[i][j] = 0;
				 }
				 if(j > i){
					 elements_[i][j] = random.nextInt(9)+1;
				 }
			 }
		 }
		 
		 print_elements();
		
		
	}
	
	public void print_elements(){
		 for(int i = 0; i < n_;i++){
			 for(int j = 0;j <n_;j++){
				 System.out.print(elements_[i][j]+" ");
			 }
			 System.out.println("");
		 }
		 
		 System.out.println("");
	}

}


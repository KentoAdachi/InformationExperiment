import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author BP16001
 *
 */
public class Graph {

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


	/**
	 * 巡回セールスマン問題を解き最短経路を返す
	 * @return 最短経路
	 */
	ArrayList<Integer> solveTSP(){
		//最短距離を定義

		ArrayList<Integer>list = new ArrayList<>();
		Route route = search(list,0, 0);
		System.out.print("最短経路 = ");
		for (Integer integer : route.elements_) {
			System.out.print(integer+" ");
		}
		System.out.println("");
		System.out.println("道のり = "+route.far_);
		//選択した場所までの距離を加算
		//その都市を選択済みにする
		return null;
	}

	Route search(ArrayList<Integer>list,int far,int position) {
		ArrayList<Integer>tmp;
		Route route;
		Route min = new Route(INF, null);
		int far2;

		if (list.size() == n_) {

//			System.out.printf("far = %2d  %2d  ", far,distance(position, 0));
			far += distance(position, list.get(0));
			//完走
			for (Integer integer : list) {
				System.out.print(integer + " ");
			}
			System.out.println("");
			return new Route(far, list);
		}
//		System.out.printf("far = %2d      ", far);
//		for (Integer integer : list) {
//			System.out.print(integer + " ");
//		}
//		System.out.println("");




		for(int i = 0;i < n_;i++) {


			if (!list.contains(i)) {
				tmp = new ArrayList<>(list);
				tmp.add(i);
				far2 = far;
				if (tmp.size() != 1) {
					far2 += distance(position, i);
				}
				route = search(tmp,far2,i);
				if (route.far_ < min.far_ ) {
					min = route;
				}
			}
		}
		return min;
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
			System.out.println(buf);
		}
		 n_ = lists.size();
		 elements_ = new int[n_][n_];
		 for(int i = 0; i < n_;i++){
			 for(int j = 0;j <n_;j++){
				 elements_[i][j] = lists.get(i).get(j);
			 }
		 }
		 System.out.println("");
		reader.close();
	}



}


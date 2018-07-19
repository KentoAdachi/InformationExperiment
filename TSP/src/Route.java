import java.util.ArrayList;

public class Route {

	int far_;
	ArrayList<Integer>elements_;

	public Route(int far, ArrayList<Integer>elements) {
		// TODO 自動生成されたコンストラクター・スタブ
		far_ = far;
		elements_ = elements;

	};
	
	
	int calcFar(Graph graph){
		int far = 0;
		int i;
		for(i = 0;i < elements_.size()-1;i++){
			far += graph.distance(elements_.get(i),elements_.get(i+1));
		}
		far += graph.distance(elements_.get(elements_.size()-1), elements_.get(0));
		
		far_ = far;
		return far;
	}
	
	public String toString(){
		return "test";
	}
}

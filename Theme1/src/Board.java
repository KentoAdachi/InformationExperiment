import java.util.ArrayList;

/**
 * ボード
 *
 * @author bp16001
 *
 */
public class Board {
	int width_, height_;
	final Color color_ = Color.Whilte;//これいる?
	ArrayList<Rectangle> rectangles_ = new ArrayList<Rectangle>();

	public Board() {
		// TODO 自動生成されたコンストラクター・スタブ
		width_ = 500;
		height_ = 400;
	}


}

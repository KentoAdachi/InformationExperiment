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

	/**
	 * 同じ長方形が登録されているか判定
	 * @param rectangle
	 * @return
	 */
	public boolean hasRectangle(Rectangle rectangle) {
		for (Rectangle r : this.rectangles_) {
			if (r.equals(rectangle))
				return true;

		}
		return false;
	}



}

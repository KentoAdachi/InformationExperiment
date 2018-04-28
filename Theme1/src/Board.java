import java.util.ArrayList;

/**
 * ボード
 *
 *	exeptionに関してはここで出力せずに例外を定義して投げる形がよい
 * @author bp16001
 *
 */
public class Board {
	int width_, height_;
	final Color color_ = Color.Whilte;//これいる?
	ArrayList<Rectangle> rectangles_ = new ArrayList<Rectangle>();
	private static final int MAX_NUMBER_OF_RECTANGLES = 10;
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

	/**
	 * 長方形の生成
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	boolean create(int x, int y, int width, int height, Color color) {

		//辺の長さに関する制約
		if (width <= 0 || height <= 0) {
			System.out.println("幅や高さは正数でなくてはいけません");
			return false;
		}
		//ボードの登録数に関する制約
		if (this.rectangles_.size() >= MAX_NUMBER_OF_RECTANGLES) {
			System.out.println("ボードがいっぱいです");
			return false;
		}

		Rectangle keyRectangle = new Rectangle(x, y, width, height, color);

		//ボードの位置に関する制約
		if (!keyRectangle.onBoard(this)) {
			System.out.println("操作後の位置はボードの上である必要があります");
			return false;
		}
		//同一の値を持つ長方形が登録されているか線形探索
		if (this.hasRectangle(keyRectangle)) {
			System.out.println("その長方形は登録済みです");
			return false;
		}
		//登録
		this.rectangles_.add(keyRectangle);
		return true;
	}

	//操作系はboardクラスに実装してもいいような気がする
	//例外処理を実装する
	void delete(int index) {
		rectangles_.remove(index);
	}

	void scale(int index,float mx,float my) {
		//可能か判断
		Rectangle rectangle = new Rectangle(this.rectangles_.get(index));
		rectangle.width_ = Math.round(rectangle.width_ * mx);
		rectangle.height_ = Math.round(rectangle.height_ * my);
		if (rectangle.onBoard(this)&&rectangle.hasArea()&&!this.hasRectangle(rectangle)) {
			System.out.println("成功");
			this.rectangles_.set(index, rectangle);
		}
	}

	void move(int index,int dx,int dy) {
		Rectangle rectangle = new Rectangle(this.rectangles_.get(index));
		rectangle.x_ += dx;
		rectangle.y_ += dy;
		if (rectangle.onBoard(this)&&rectangle.hasArea()&&!this.hasRectangle(rectangle)) {
			System.out.println("成功");
			this.rectangles_.set(index, rectangle);
		}

	}



}

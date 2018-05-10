package rectangleEditor;
import java.util.ArrayList;

/**
 * ボード
 *
 *	exeptionに関してはここで出力せずに例外を定義して投げる形がよい
 * @author bp16001
 *
 */
public class Board {

	@SuppressWarnings("javadoc")
	public int width_, height_;
	@SuppressWarnings("javadoc")
	public final Color color_ = Color.White;
	private ArrayList<Rectangle> rectangles_;

	private static final int MAX_NUMBER_OF_RECTANGLES = 10;

	/**
	 * コンストラクタ
	 * 幅と高さを指定する
	 */
	public Board() {
		width_ = 500;
		height_ = 400;
		rectangles_ = new ArrayList<Rectangle>();

	}

	/**
	 * 同じ長方形が登録されているか判定
	 * @param rectangle 長方形
	 * @return 長方形が登録済みならtrue
	 */
	public boolean hasRectangle(Rectangle rectangle) {
		for (Rectangle r : this.rectangles_) {
			if (r.equals(rectangle))
				return true;
		}
		return false;
	}

	/**
	 * 長方形の大きさ、位置、登録済みでないかを調べ、適切に例外を投げる
	 * @param rectangle 長方形
	 */
	private void verify(Rectangle rectangle)throws IllegalArgumentException, RuntimeException {
		//辺の長さに関する制約
		if (!rectangle.hasArea()) {
			throw new IllegalArgumentException("幅や高さは正数でなくてはいけません");
		}
		//ボードの位置に関する制約
		if (!rectangle.onBoard(this)) {
			throw new IllegalArgumentException("操作後の位置はボードの上である必要があります");
		}
		//同一の値を持つ長方形が登録されているか線形探索
		if (this.hasRectangle(rectangle)) {
			throw new RuntimeException("その長方形は登録済みです");
		}
	}

	/**
	 * 長方形の生成
	 * @param x 左上のx座標
	 * @param y 左上のy座標
	 * @param width 幅
	 * @param height 高さ
	 * @param color 色
	 * @throws IllegalArgumentException 正しくない引数
	 */
	public void create(int x, int y, int width, int height, Color color) throws IllegalArgumentException {
		//ボードの登録数に関する制約
		if (this.rectangles_.size() >= MAX_NUMBER_OF_RECTANGLES) {
			//			System.out.println("ボードがいっぱいです");
			throw new ArrayIndexOutOfBoundsException("ボードがいっぱいです");
		}
		if (color == null) {
			throw new IllegalArgumentException("正しい色を選択してください");
		}
		Rectangle rectangle = new Rectangle(x, y, width, height, color);
		//辺の長さに関する制約
		verify(rectangle);
		//登録
		this.rectangles_.add(rectangle);

	}

	/**
	 * 長方形の削除
	 * @param index リスト内での長方形の添え字
	 */
	public void delete(int index) {
		this.rectangles_.remove(index);
	}

	/**
	 * 長方形の大きさの変更
	 * @param index リスト内での長方形の添え字
	 * @param mx x倍率
	 * @param my y倍率
	 */
	public void scale(int index, float mx, float my) {
		//可能か判断
		Rectangle rectangle = new Rectangle(this.rectangles_.get(index));
		rectangle.width_ = Math.round(rectangle.width_ * mx);
		rectangle.height_ = Math.round(rectangle.height_ * my);
		verify(rectangle);
		this.rectangles_.set(index, rectangle);
	}

	/**
	 * 長方形の移動
	 * @param index 長方形のリスト内での添え字
	 * @param dx x変位
	 * @param dy y変位
	 */
	public void move(int index, int dx, int dy) {
		Rectangle rectangle = new Rectangle(this.rectangles_.get(index));
		rectangle.x_ += dx;
		rectangle.y_ += dy;
		verify(rectangle);
		this.rectangles_.set(index, rectangle);

	}


	/**
	 * 指定した長方形が重なっている場合新その領域で新たな長方形を作成する
	 * @param index1 指定した長方形のリスト内での添え字
	 * @param index2 指定した長方形のリスト内での添え字
	 */
	public void intersect(int index1, int index2) {
		Rectangle r1 = new Rectangle(this.rectangles_.get(index1));
		Rectangle r2 = new Rectangle(this.rectangles_.get(index2));
		int sx = Math.max(r1.x_, r2.x_);
		int sy = Math.max(r1.y_, r2.y_);
		int ex = Math.min(r1.x_ + r1.width_, r2.x_ + r2.width_);
		int ey = Math.min(r1.y_ + r1.height_, r2.y_ + r2.height_);
		int w = ex - sx;
		int h = ey - sy;
		Color color = Color.Cyan;

		if (r1.color_ == r2.color_) {
			color = Color.Gray;
		}
		if (r1.color_ == Color.Yellow && r2.color_ == Color.Blue
				|| r1.color_ == Color.Blue && r2.color_ == Color.Yellow) {
			color = Color.Green;
		}
		if (r1.color_ == Color.Yellow && r2.color_ == Color.Red
				|| r1.color_ == Color.Red && r2.color_ == Color.Yellow) {
			color = Color.Orange;
		}
		if (r1.color_ == Color.Red && r2.color_ == Color.Blue || r1.color_ == Color.Blue && r2.color_ == Color.Red) {
			color = Color.Magenta;
		}
		if (w > 0 && h > 0) {
			create(sx, sy, w, h, color);
		}
	}

	/**
	 * rectangles getter
	 * @return rectangles
	 */
	public ArrayList<Rectangle> getRectangles_() {
		return rectangles_;
	}

}

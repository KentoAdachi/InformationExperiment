package EIEV3;

import java.awt.Color;
import java.util.ArrayList;

/**
 * ボード
 *
 * @author bp16001 足立賢人
 *
 */
public class Board {

	/**
	 * 幅
	 */
	public int width_;
	/**
	 * 高さ
	 */
	public int height_;
	/**
	 * 色
	 */
	public Color color_ = Color.WHITE;
	private ArrayList<Rectangle> rectangles_;

	private static final int MAX_NUMBER_OF_RECTANGLES = 10;

	/**
	 * コンストラクタ
	 * 幅と高さを指定する
	 * @param width 幅
	 * @param height 高さ
	 */
	public Board(int width, int height) {
		width_ = width;
		height_ = height;
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
	 * @throws Exception 例外
	 */
	private void verify(Rectangle rectangle) throws Exception {
		//辺の長さに関する制約
		if (!rectangle.hasArea()) {
			throw new Exception("幅や高さは正数でなくてはいけません");
		}
		//ボードの位置に関する制約
		if (!rectangle.onBoard(this)) {
			throw new Exception("操作後の位置はボードの上である必要があります");
		}
		//同一の値を持つ長方形が登録されているか線形探索
		if (this.hasRectangle(rectangle)) {
			throw new Exception("その長方形は登録済みです");
		}
	}

	/**
	 * 長方形の生成
	 * @param x 左上のx座標
	 * @param y 左上のy座標
	 * @param width 幅
	 * @param height 高さ
	 * @param color 色
	 * @throws Exception 例外
	 */
	public void create(int x, int y, int width, int height, Color color) throws Exception {
		//ボードの登録数に関する制約
		if (this.rectangles_.size() >= MAX_NUMBER_OF_RECTANGLES) {
			//			System.out.println("ボードがいっぱいです");
			throw new Exception("ボードがいっぱいです");
		}
		if (color == null) {

			throw new Exception("正しい色を選択してください");
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
	 * 右下の点を決めてリサイズ
	 * @param index 添え字
	 * @param x x座標
	 * @param y y座標
	 * @throws Exception 例外
	 */
	public void scale(int index, int x, int y) throws Exception {
		Rectangle rectangle = new Rectangle(this.rectangles_.get(index));
		rectangle.width_ = x - rectangle.x_;
		rectangle.height_ = y - rectangle.y_;
		verify(rectangle);
		this.rectangles_.set(index, rectangle);
	}

	/**
	 * 長方形の移動
	 * @param index 長方形のリスト内での添え字
	 * @param dx x変位
	 * @param dy y変位
	 * @throws Exception 例外
	 */
	public void move(int index, int dx, int dy) throws Exception {
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
	 * @throws Exception i
	 */
	public void intersect(int index1, int index2) throws Exception {
		Rectangle r1 = new Rectangle(this.rectangles_.get(index1));
		Rectangle r2 = new Rectangle(this.rectangles_.get(index2));
		int sx = Math.max(r1.x_, r2.x_);
		int sy = Math.max(r1.y_, r2.y_);
		int ex = Math.min(r1.x_ + r1.width_, r2.x_ + r2.width_);
		int ey = Math.min(r1.y_ + r1.height_, r2.y_ + r2.height_);
		int w = ex - sx;
		int h = ey - sy;
		Color color = Color.CYAN;

		if (r1.color_ == r2.color_) {
			color = Color.GRAY;
		}
		if (r1.color_ == Color.YELLOW && r2.color_ == Color.BLUE
				|| r1.color_ == Color.BLUE && r2.color_ == Color.YELLOW) {
			color = Color.GREEN;
		}
		if (r1.color_ == Color.YELLOW && r2.color_ == Color.RED
				|| r1.color_ == Color.RED && r2.color_ == Color.YELLOW) {
			color = Color.ORANGE;
		}
		if (r1.color_ == Color.RED && r2.color_ == Color.BLUE || r1.color_ == Color.BLUE && r2.color_ == Color.RED) {
			color = Color.MAGENTA;
		}
		if (w > 0 && h > 0) {
			create(sx, sy, w, h, color);
		}
	}

	/**
	 * @param x x座標
	 * @param y y座標
	 * @return i 添え字
	 * @throws Exception 例外
	 */
	public int search(int x, int y) throws Exception {
		for (int i = rectangles_.size() - 1; i >= 0; i--) {
			Rectangle rectangle = rectangles_.get(i);
			if (rectangle.hit(x, y)) {
				return i;
			}
		}
		throw new Exception();

	}

	/**
	 * 全部消す
	 */
	public void deleteAll() {
		while (this.rectangles_.size() > 0) {
			delete(0);
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

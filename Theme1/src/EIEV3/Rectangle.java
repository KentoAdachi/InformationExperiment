package EIEV3;

import java.awt.Color;

/**
 * 長方形クラス
 *
 * @author bp16001
 */
public class Rectangle {
	/**
	 * 左上頂点x座標
	 */
	public int x_;
	/**
	 * 左上頂点y座標
	 */
	public int y_;
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
	public Color color_;

	/**
	 * コンストラクタ
	 * @param x 左上のx座標
	 * @param y 左上のy座標
	 * @param width 幅
	 * @param height 高さ
	 * @param color 色
	 */
	public Rectangle(int x, int y, int width, int height, Color color) {
		// TODO 自動生成されたコンストラクター・スタブ
		x_ = x;
		y_ = y;
		width_ = width;
		height_ = height;
		color_ = color;

	}

	/**
	 * コンストラクタ
	 * @param rectangle 複製元のコンストラクタ
	 */
	public Rectangle(Rectangle rectangle) {
		x_ = rectangle.x_;
		y_ = rectangle.y_;
		width_ = rectangle.width_;
		height_ = rectangle.height_;
		color_ = rectangle.color_;
	}

	/**
	 * 正しいサイズを持つか(辺の長さが正の正数であるか)判定する
	 * @return 正常な時true
	 */
	public boolean hasArea() {
		return width_ > 0 && height_ > 0;

	}

	/**
	 * ボード上にいるか判定する
	 * @param board ボード
	 * @return ボード上にある時true
	 */
	public boolean onBoard(Board board) {
		return this.x_ >= 0 && this.y_ >= 0 && this.x_ + this.width_ <= board.width_
				&& this.y_ + this.height_ <= board.height_;

	}

	public String toString() {
		return "x " + x_ + "\ny " + y_ + "\nwidth " + width_ + "\nheight "
				+ height_ + "\ncolor " + color_;
	}

	/**
	 * 同一の長方形を判定
	 * @param rectangle 比較対象
	 * @return 一致するときtrue
	 */
	public boolean equals(Rectangle rectangle) {
		return this.x_ == rectangle.x_ && this.y_ == rectangle.y_
				&& this.width_ == rectangle.width_
				&& this.height_ == rectangle.height_;

	}

	/**
	 * 指定した座標が長方形上に存在するかどうか判定する
	 * @param x x座標
	 * @param y y座標
	 * @return ヒット
	 */
	public boolean hit(int x, int y) {
		return x > x_ && x < x_ + width_ && y > y_ && y < y_ + height_;

	}

}

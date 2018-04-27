/**
 * 長方形クラス
 *
 * @author bp16001
 */
public class Rectangle {
	int x_, y_, width_, height_;
	Color color_;

	public Rectangle(int x, int y, int width, int height, Color color) {
		// TODO 自動生成されたコンストラクター・スタブ
		x_ = x;
		y_ = y;
		width_ = width;
		height_ = height;
		color_ = color;

	}
	public Rectangle(Rectangle rectangle) {
		x_ = rectangle.x_;
		y_ = rectangle.y_;
		width_ = rectangle.width_;
		height_ = rectangle.height_;
	}

	/**
	 * 正しいサイズを持つか(辺の長さが正の正数であるか)判定する
	 * @return
	 */
	public boolean hasArea() {
		return width_ > 0 && height_ > 0;

	}

	/**
	 * ボード上にいるか判定する
	 * @param board
	 * @return
	 */
	public boolean onBoard(Board board) {
		return this.x_ >= 0 && this.y_ >= 0 && this.x_ + this.width_ <= board.width_
				&& this.y_ + this.height_ <= board.height_;

	}

	public String toString() {
		return "x " + x_ + "\ny " + y_ + "\nwidth " + width_ + "\nheight "
				+ height_ + "\ncolor " + color_;
	}

	// 同一の長方形とは何か定義
	public boolean equals(Rectangle rectangle) {
		return this.x_ == rectangle.x_ && this.y_ == rectangle.y_
				&& this.width_ == rectangle.width_
				&& this.height_ == rectangle.height_;

	}
}

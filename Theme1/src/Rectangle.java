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

	public String toString() {
		return "x " + x_ + "\ny " + y_ + "\nwidth " + width_ + "\nheight "
				+ height_ + "\ncolor " + color_;
	}

	// 同一の長方形とは何か定義
	boolean equals(Rectangle rectangle) {
		if (this.x_ == rectangle.x_ && this.y_ == rectangle.y_
				&& this.width_ == rectangle.width_
				&& this.height_ == rectangle.height_)
			return true;
		return false;
	}
}

package EIEV3;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EventHandler implements MouseListener, ItemListener, KeyListener {

	private static final int NULL = -1;
	RectangleEditor rectangleEditor_;
	Color color_ = Color.RED;
	int keyCodePressing_ = NULL;
	Point tmpPosition;
	int tmpRectangleIndex = NULL;

	/**
	 * @param rectangleEditor
	 */
	public EventHandler(RectangleEditor rectangleEditor) {
		// TODO 自動生成されたコンストラクター・スタブ
		rectangleEditor_ = rectangleEditor;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if (e.isAltDown()) {
			int index = rectangleEditor_.board_.search(e.getPoint().x, e.getPoint().y);
			if (tmpRectangleIndex == NULL) {
				tmpRectangleIndex = index;
			}else {
				if (index != NULL) {
					rectangleEditor_.board_.intersect(tmpRectangleIndex, index);
					tmpRectangleIndex =NULL;
				}
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		tmpPosition = e.getPoint();
		System.out.println(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (e.isMetaDown()) {
			delete(tmpPosition);
		} else if (e.isControlDown()) {
			move(tmpPosition, e.getPoint());
		}else if (e.isShiftDown()) {
			scale(tmpPosition, e.getPoint());
		}else if (e.isAltDown()) {

		} else {
			create(tmpPosition, e.getPoint());
		}

		rectangleEditor_.repaint();
		System.out.println(e);
		System.out.println(e.getModifiersEx());
		System.out.println(e.isMetaDown());
	}
		private void intersect(Point pressPosition, Point releasePosition) {
		int index1 = rectangleEditor_.board_.search(pressPosition.x, pressPosition.y);
		int index2 = 0;
		rectangleEditor_.board_.intersect(index1, index2);
	}


	private void scale(Point pressPosition, Point releasePosition) {
		int index = rectangleEditor_.board_.search(pressPosition.x, pressPosition.y);
		rectangleEditor_.board_.scale(index, releasePosition.x, releasePosition.y);
		//		rectangleEditor_.board_.scale(index, mx, my);

	}

	private void delete(Point pressPosition) {
		int index = rectangleEditor_.board_.search(pressPosition.x, pressPosition.y);
		rectangleEditor_.board_.delete(index);
	}

	private void move(Point pressPosition, Point releasePosition) {
		int dx = releasePosition.x - pressPosition.x;
		int dy = releasePosition.y - pressPosition.y;
		int index = rectangleEditor_.board_.search(pressPosition.x, pressPosition.y);

		rectangleEditor_.board_.move(index, dx, dy);
	}

	private void create(Point pressPosition, Point releasePosition) {
		//クリックの始点が左上とは限らない
		Point leftUp = new Point(Math.min(pressPosition.x, releasePosition.x),
				Math.min(pressPosition.y, releasePosition.y));
		Point rightDown = new Point(Math.max(pressPosition.x, releasePosition.x),
				Math.max(pressPosition.y, releasePosition.y));
		int width = rightDown.x - leftUp.x;
		int height = rightDown.y - leftUp.y;
		rectangleEditor_.board_.create(leftUp.x, leftUp.y, width, height, color_);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if (e.getItemSelectable() == rectangleEditor_.chx1_)
			color_ = Color.red;
		if (e.getItemSelectable() == rectangleEditor_.chx2_)
			color_ = Color.blue;
		if (e.getItemSelectable() == rectangleEditor_.chx3_)
			color_ = Color.yellow;
		if (e.getItemSelectable() == rectangleEditor_.chx4_)
			color_ = Color.gray;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

		switch (e.getKeyCode()) {
		case KeyEvent.VK_SHIFT:
		case KeyEvent.VK_CONTROL:
		case KeyEvent.VK_ALT:
		case KeyEvent.VK_META:
			keyCodePressing_ = e.getKeyCode();
			break;

		default:
			break;
		}
		System.out.println(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		keyCodePressing_ = NULL;
		System.out.println(e);
	}
}

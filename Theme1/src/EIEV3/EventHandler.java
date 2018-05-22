package EIEV3;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * 無駄な文章を削除する
 * イベントハンドラ
 * @author kent
 *
 */
public class EventHandler implements MouseListener, ItemListener, KeyListener, ActionListener {

	private static final int NULL = -1;
	RectangleEditor rectangleEditor_;
	Color color_ = Color.RED;
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

			int index;
			try {
				index = rectangleEditor_.board_.search(e.getPoint().x, e.getPoint().y);
			} catch (Exception e1) {
				return;
			}
			if (tmpRectangleIndex == NULL) {
				tmpRectangleIndex = index;
			} else {
				if (index != NULL) {
					try {
						rectangleEditor_.board_.intersect(tmpRectangleIndex, index);
					} catch (Exception e1) {
						// TODO 自動生成された catc
						e1.printStackTrace();
					}
					tmpRectangleIndex = NULL;
				}
			}
		} else {
			tmpRectangleIndex = NULL;
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		tmpPosition = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		try {
			if (e.isMetaDown()) {
				delete(tmpPosition);
			} else if (e.isControlDown()) {
				move(tmpPosition, e.getPoint());
			} else if (e.isShiftDown()) {
				scale(tmpPosition, e.getPoint());
			} else if (e.isAltDown()) {

			} else {
				create(tmpPosition, e.getPoint());
			}
		} catch (Exception e2) {

		}

		rectangleEditor_.repaint();
	}

	private void scale(Point pressPosition, Point releasePosition) throws Exception {
		int index = rectangleEditor_.board_.search(pressPosition.x, pressPosition.y);
		rectangleEditor_.board_.scale(index, releasePosition.x, releasePosition.y);

	}

	private void delete(Point pressPosition) throws Exception {
		int index = rectangleEditor_.board_.search(pressPosition.x, pressPosition.y);
		rectangleEditor_.board_.delete(index);
	}

	private void move(Point pressPosition, Point releasePosition) throws Exception {
		int dx = releasePosition.x - pressPosition.x;
		int dy = releasePosition.y - pressPosition.y;
		int index = rectangleEditor_.board_.search(pressPosition.x, pressPosition.y);

		rectangleEditor_.board_.move(index, dx, dy);
	}

	private void create(Point pressPosition, Point releasePosition) throws Exception {
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

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if (e.getItemSelectable() == rectangleEditor_.redchx_)
			color_ = Color.red;
		if (e.getItemSelectable() == rectangleEditor_.bluechx_)
			color_ = Color.blue;
		if (e.getItemSelectable() == rectangleEditor_.yellowchx_)
			color_ = Color.yellow;
		if (e.getItemSelectable() == rectangleEditor_.graychx_)
			color_ = Color.gray;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			rectangleEditor_.board_.deleteAll();
			rectangleEditor_.repaint();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}

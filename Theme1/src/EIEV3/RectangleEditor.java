package EIEV3;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * 操作系2(直感型)
 *
 * @author bp16001
 *
 */
public class RectangleEditor extends Applet implements Runnable {
	/**
	 * ボード
	 */
	public Board board_ = null;//new Board(0,0);//何故かここで初期化しないとヌルポで落ちることがある

	@SuppressWarnings("javadoc")
	public Checkbox redchx_, bluechx_, yellowchx_, graychx_;
	private Button deletebtn_;
	private EventHandler handler_ = new EventHandler(this);

	@Override
	public void init() {
		Thread thread = new Thread(this);
		thread.start();
		super.init();
		board_ = new Board(getSize().width, getSize().height);

		CheckboxGroup cbg = new CheckboxGroup();
		this.redchx_ = new Checkbox("red", cbg, true);
		this.redchx_.addItemListener(handler_);
		this.add(redchx_);

		this.bluechx_ = new Checkbox("blue", cbg, false);
		this.bluechx_.addItemListener(handler_);
		this.add(bluechx_);

		this.yellowchx_ = new Checkbox("yellow", cbg, false);
		this.yellowchx_.addItemListener(handler_);
		this.add(yellowchx_);

		this.graychx_ = new Checkbox("gray", cbg, false);
		this.graychx_.addItemListener(handler_);
		this.add(graychx_);

		deletebtn_ = new Button("deleteAll");
		this.add(deletebtn_);
		addMouseListener(handler_);
		addKeyListener(handler_);
		deletebtn_.addActionListener(handler_);
	}

	@Override
	public void paint(Graphics g) {
		requestFocusInWindow();
		ArrayList<Rectangle> list = board_.getRectangles_();
		for (Rectangle rectangle : list) {
			g.setColor(rectangle.color_);
			g.fillRect(rectangle.x_, rectangle.y_, rectangle.width_, rectangle.height_);
		}

		super.paint(g);
	}

	@Override
	public void run() {
	}

}

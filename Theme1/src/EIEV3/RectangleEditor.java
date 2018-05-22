package EIEV3;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * マルチスレッドに対応
 *
 * @author bp16001
 *
 */
public class RectangleEditor extends Applet implements Runnable {
	private static String OPARATION_LIST = "1:create\n"
			+ "2:move\n"
			+ "3:expand/shrink\n"
			+ "4:delete\n"
			+ "5:deleteAll\n"
			+ "6:intersect\n"
			+ "7:displayBoard\n"
			+ "8:exit";

	Board board_ = null;//new Board(0,0);//何故かここで初期化しないとヌルポで落ちることがある
	Checkbox chx1_, chx2_, chx3_, chx4_;
	Button btn_;
	private EventHandler handler = new EventHandler(this);



	@Override
	public void init() {
		Thread thread = new Thread(this);
		thread.start();
		super.init();
		board_ = new Board(getSize().width,getSize().height);

	    CheckboxGroup cbg = new CheckboxGroup();
	    this.chx1_ = new Checkbox("red",cbg,true);
	    this.chx1_.addItemListener(handler);
	    this.add(chx1_);

	    this.chx2_ = new Checkbox("blue",cbg,false);
	    this.chx2_.addItemListener(handler);
	    this.add(chx2_);

	    this.chx3_ = new Checkbox("yellow",cbg,false);
	    this.chx3_.addItemListener(handler);
	    this.add(chx3_);

	    this.chx4_ = new Checkbox("gray",cbg,false);
	    this.chx4_.addItemListener(handler);
	    this.add(chx4_);

	    btn_ = new Button("deleteAll");
	    this.add(btn_);
	    addMouseListener(handler);
	    addKeyListener(handler);
	    btn_.addActionListener(handler);
	}

	@Override
	public void paint(Graphics g) {
		// TODO 自動生成されたメソッド
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
		// TODO 自動生成されたメソッド・スタブ

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Command command = new Command(board_);

		while (true) {
			System.out.println("操作一覧\n" + OPARATION_LIST);
			try {
				String input = reader.readLine();
				switch (input) {
				case "1":
				case "create":
					command.create();
					break;
				case "2":
				case "move":
					command.move();
					break;
				case "3":
				case "expand/shrink":
					command.scale();
					break;
				case "4":
				case "delete":
					command.delete();
					break;
				case "5":
				case "deleteAll":
					command.deleteAll();
					break;
				case "6":
				case "intersect":
					command.intersect();
					break;
				case "7":
				case "displayBoard":
					break;
				case "8":
				case "exit":
					//環境によってはSecurityExceptionを投げてくる
					try {
					System.exit(0);//正常に終了できる場合は終了する
					}catch (SecurityException e) {
						System.out.println(e);
						System.out.println("プログラムを終了します,ウインドウを閉じてください");
						return;//applet側のスレッドが生きてるから制御が帰ってこない
					}
				default:
					continue;
				}
				command.displayBoard();
				repaint();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}



}

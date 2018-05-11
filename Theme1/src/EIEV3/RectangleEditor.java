package EIEV3;

import java.applet.Applet;
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

	static Board board_;
	Thread thread = null;


	@Override
	public void init() {
		thread = new Thread(this);
		thread.start();
		super.init();
		board_ = new Board(getSize().width,getSize().height);
	}

	@Override
	public void paint(Graphics g) {
		// TODO 自動生成されたメソッド

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

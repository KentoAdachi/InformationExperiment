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

	static Board board_ = new Board();
	static Command command_ = new Command(board_);
	Thread thread = null;


	@Override
	public void init() {
		thread = new Thread(this);
		thread.start();
		super.init();
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

		while (true) {
			System.out.println("操作一覧\n" + OPARATION_LIST);
			try {
				String input = reader.readLine();
				switch (input) {
				case "1":
				case "create":
					command_.create();
					break;
				case "2":
				case "move":
					command_.move();
					break;
				case "3":
				case "expand/shrink":
					command_.scale();
					break;
				case "4":
				case "delete":
					command_.delete();
					break;
				case "5":
				case "deleteAll":
					command_.deleteAll();
					break;
				case "6":
				case "intersect":
					command_.intersect();
					break;
				case "7":
				case "displayBoard":
					break;
				case "8":
				case "exit":
					//ググったら環境によってはSecurityExceptionを投げてくるらしい
					//今のところappletviewerは大丈夫そうだから放置
					System.exit(0);



				default:
					continue;
				}
				command_.displayBoard();
				repaint();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

}

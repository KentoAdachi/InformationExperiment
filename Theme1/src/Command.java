import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 操作
 * なんだか忙しいクラス
 * @author bp16001
 *
 */
public class Command {

	private static final int MAX_NUMBER_OF_RECTANGLES = 10;
	Board board_;

	Command(Board board) {
		board_ = board;
	}

	/**
	 * 長方形の作成
	 * 手動でしなければいけない入力処理と自動でできる登録処理に分けることで
	 * 単体テストをしやすくする
	 * 登録の部分がintersectで使えるかも
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	void create() throws NumberFormatException, IOException {

		Color color;
		int x;
		int y;
		int height;
		int width;
		do {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("xを入力してください");
			x = Integer.parseInt(reader.readLine());
			System.out.println("yを入力してください");
			y = Integer.parseInt(reader.readLine());
			System.out.println("幅を入力してください");
			width = Integer.parseInt(reader.readLine());
			System.out.println("高さを入力してください");
			height = Integer.parseInt(reader.readLine());
			System.out.println("色を指定してください\n" +
					"1:red\n" +
					"2:blue\n" +
					"3:yellow\n" +
					"4:gray");
			String colorString = reader.readLine();
			color = Color.Cyan;
			switch (colorString) {
			case "red":
			case "1":
				color = Color.Red;
				break;
			case "blue":
			case "2":
				color = Color.Blue;
				break;
			case "yellow":
			case "3":
				color = Color.Yellow;
				break;
			case "gray":
			case "4":
				color = Color.Gray;
				break;

			default:
				System.out.println("正しく認識されませんでした");
			}
		} while (!create(x, y, width, height, color));

	}

	/**
	 * 長方形の生成
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	boolean create(int x, int y, int width, int height, Color color) {

		//辺の長さに関する制約
		if (width <= 0 || height <= 0) {
			System.out.println("幅や高さは正数でなくてはいけません");
			return false;
		}
		//ボードの登録数に関する制約
		if (board_.rectangles_.size() >= MAX_NUMBER_OF_RECTANGLES) {
			System.out.println("ボードがいっぱいです");
			return false;
		}

		Rectangle keyRectangle = new Rectangle(x, y, width, height, color);

		//ボードの位置に関する制約
		if (!keyRectangle.onBoard(board_)) {
			System.out.println("操作後の位置はボードの上である必要があります");
			return false;
		}
		//同一の値を持つ長方形が登録されているか線形探索
		for (Rectangle r : board_.rectangles_) {
			if (r.equals(keyRectangle)) {
				System.out.println(keyRectangle + "\nは登録済みです");
				return false;
			}

		}
		//登録
		board_.rectangles_.add(keyRectangle);
		return true;
	}



	void delete() throws NumberFormatException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("xを入力してください");
		int x = Integer.parseInt(reader.readLine());
		System.out.println("yを入力してください");
		int y = Integer.parseInt(reader.readLine());
		System.out.println("幅を入力してください");
		int width = Integer.parseInt(reader.readLine());
		System.out.println("高さを入力してください");
		int height = Integer.parseInt(reader.readLine());
		delete(x, y, width, height);
	}

	void delete(int x, int y, int width, int height) {
		//色はなんでもいい
		Rectangle keyRectangle = new Rectangle(x, y, width, height, Color.Cyan);
		// すべての長方形を線形探索
		for (Rectangle r : board_.rectangles_) {
			// 合致する長方形を見つけた場合削除して抜ける
			// 見つからなかった場合continue
			if (r.equals(keyRectangle)) {
				board_.rectangles_.remove(r);
				System.out.println("deleted");
				return;
			}
		}
		System.out.println("not found");

	}

	void displayBoard() {
		int i = 1;
		for (Rectangle r : board_.rectangles_) {
			System.out.println(i + "番目の要素");
			System.out.println(r);
			i++;
		}
		System.out.println(" ");
	}

}

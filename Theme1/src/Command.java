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
		} while (!board_.create(x, y, width, height, color));

	}


	void delete() throws NumberFormatException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("削除する要素番号を選んでください");
		displayBoard();
		int index = Integer.parseInt(reader.readLine()) - 1;
		board_.delete(index);
	}



	void scale() throws NumberFormatException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		displayBoard();
		int index = Integer.parseInt(reader.readLine()) - 1;
		System.out.println("幅の倍率を入力してください");
		float mx = Float.parseFloat(reader.readLine());
		System.out.println("高さの倍率を入力してください");
		float my = Float.parseFloat(reader.readLine());
		board_.scale(index, mx, my);

	}

	void move() throws NumberFormatException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("移動する長方形を選択してください");
		displayBoard();
		int index = Integer.parseInt(reader.readLine()) - 1;
		System.out.println("xの変位を入力してください");
		int dx = Integer.parseInt(reader.readLine());
		System.out.println("yの変位を入力してください");
		int dy = Integer.parseInt(reader.readLine());
		board_.move(index, dx, dy);

	}

	void deleteAll() {
		board_ = new Board();
	}
	/**
	 * 自分自身を選べてしまう
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	void intersect() throws NumberFormatException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("一つ目の長方形を選択してください");
		displayBoard();
		int index1 = Integer.parseInt(reader.readLine()) - 1;
		System.out.println("二つ目の長方形を選択してください");
		displayBoard();
		int index2 = Integer.parseInt(reader.readLine()) - 1;
		board_.intersect(index1, index2);
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

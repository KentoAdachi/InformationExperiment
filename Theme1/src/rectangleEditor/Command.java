package rectangleEditor;
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
	private static final int MAX_ERROR_INPUTS = 3;
	private Board board_;

	/**
	 * コンストラクタ
	 * @param board ボードを設定
	 */
	public Command(Board board) {
		board_ = board;
	}


	/**
	 * 長方形の作成
	 * @throws IOException BufferedReader.readline()
	 */
	public void create() throws IOException {

		Color color = null;
		int x = 0;
		int y = 0;
		int height = 0;
		int width = 0;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		for (int count = 0; count < MAX_ERROR_INPUTS; count++) {
			try {
				System.out.println("xを入力してください");
				x = Integer.parseInt(reader.readLine());
				System.out.println("yを入力してください");
				y = Integer.parseInt(reader.readLine());
				System.out.println("幅を入力してください");
				width = Integer.parseInt(reader.readLine());
				System.out.println("高さを入力してください");
				height = Integer.parseInt(reader.readLine());
			} catch (NumberFormatException e) {
				System.out.println("数値を入力してください");
				continue;
			}
			System.out.println("色を指定してください\n" +
					"1:red\n" +
					"2:blue\n" +
					"3:yellow\n" +
					"4:gray");
			String colorString = reader.readLine();
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
			try {
				board_.create(x, y, width, height, color);
				return;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	/**
	 * 削除
	 * @throws IOException BufferedReader.readline()
	 */
	public void delete() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		for (int count = 0; count < MAX_ERROR_INPUTS; count++) {
			System.out.println("削除する要素番号を選んでください");
			displayBoard();
			try {
				int index = Integer.parseInt(reader.readLine()) - 1;
				board_.delete(index);
				return;
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("実在する要素番号を選んでください");
			}
		}
	}

	/**
	 * 長方形の大きさの変更
	 * @throws IOException BufferedReader.readline()
	 */
	public void scale() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		for (int count = 0; count < MAX_ERROR_INPUTS; count++) {
			System.out.println("サイズを変更する長方形を選択してください");
			displayBoard();
			try {
				int index = Integer.parseInt(reader.readLine()) - 1;
				System.out.println("幅の倍率を入力してください");
				float mx = Float.parseFloat(reader.readLine());
				System.out.println("高さの倍率を入力してください");
				float my = Float.parseFloat(reader.readLine());
				board_.scale(index, mx, my);
				return;
			}catch (NumberFormatException e) {
				// TODO: handle exception
				System.out.println("数値を入力してください");
				continue;
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("実在する要素番号を選んでください");
			}
		}

	}

	/**
	 * 長方形の移動
	 * @throws IOException BufferedReader.readline()
	 */
	public void move() throws  IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		for (int count = 0; count < MAX_ERROR_INPUTS; count++) {
			System.out.println("移動する長方形を選択してください");
			displayBoard();
			try {
				int index = Integer.parseInt(reader.readLine()) - 1;
				System.out.println("xの変位を入力してください");
				int dx = Integer.parseInt(reader.readLine());
				System.out.println("yの変位を入力してください");
				int dy = Integer.parseInt(reader.readLine());
				board_.move(index, dx, dy);
				return;
			}catch (NumberFormatException e) {
				System.out.println("数値を入力してください");
				continue;
			}catch (Exception e) {
				System.out.println(e);
				System.out.println("実在する要素番号を選んでください");
			}
		}

	}

	/**
	 * 長方形の削除
	 */
	public void deleteAll() {
		board_ = new Board();
	}

	/**
	 * 重なっている長方形を選択
	 * @throws IOException BufferedReader.readline()
	 */
	public void intersect() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		for (int count = 0; count < MAX_ERROR_INPUTS; count++) {
			System.out.println("一つ目の長方形を選択してください");
			displayBoard();
			try {
				int index1 = Integer.parseInt(reader.readLine()) - 1;
				System.out.println("二つ目の長方形を選択してください");
				displayBoard();
				int index2 = Integer.parseInt(reader.readLine()) - 1;
				board_.intersect(index1, index2);
				return;
			}catch (NumberFormatException e) {
				System.out.println("数値を入力してください");
				continue;
			}catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * 長方形リストの表示
	 */
	public void displayBoard() {
		int i = 1;
		for (Rectangle r : board_.getRectangles_()) {
			System.out.println(i + "番目の要素");
			System.out.println(r);
			i++;
		}
		System.out.println(" ");
	}

}

package rectangleEditor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * main class
 *
 * @author bp16001
 *
 */
public class RectangleEditor {
	private static String OPARATION_LIST = "1:create\n"
			+ "2:move\n"
			+ "3:expand/shrink\n"
			+ "4:delete\n"
			+ "5:deleteAll\n"
			+ "6:intersect\n"
			+ "7:displayBoard\n"
			+ "8:exit\n";

	/**
	 * メインメソッド
	 * @param args コマンドライン引数
	 * @throws IOException BufferedReader.readline()
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Board board = new Board();
		Command command = new Command(board);

		while (true) {
			System.out.println("操作一覧\n" + OPARATION_LIST);
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
			case"5":
			case"deleteAll":
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
				return;
			default:
				continue;
			}
			command.displayBoard();
		}
	}

}

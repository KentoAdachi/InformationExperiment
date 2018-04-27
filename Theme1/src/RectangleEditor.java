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
			+ "2:display\n"
			+ "3:delete\n"
			+ "4:exit\n";

	public static void main(String[] args) throws IOException {
		// 起動
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
			case "display":
				command.displayBoard();

				break;
			case "3":
			case "delete":
				command.delete();
				break;
			case "4":
			case "exit":
				return;
			default:
				break;
			}

		}
	}

}

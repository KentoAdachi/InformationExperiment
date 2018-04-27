
public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("test");
		Board board = new Board();
		Command command = new Command(board);
//		command.create(0, 10, 20, 30);
//		command.create(0, 10, 20, 50);
		command.displayBoard();
//		command.delete(new Rectangle(0, 10, 20, 30));
		command.displayBoard();
	}

}

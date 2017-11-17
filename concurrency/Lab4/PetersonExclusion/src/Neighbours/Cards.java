package Neighbours;

public class Cards {
	
	private int turn = 1;
	
	public synchronized String query_turn() {
		if (turn == 1)
			return "Alice";
		else
			return "Bob";
	}

	public synchronized void set_turn(String s) {
		if (s.equals("Alice")) {
			turn = 2;
		} else {
			turn = 1;
		}
	}

}

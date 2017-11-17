package Neighbours;

public class Neighbour extends Thread {

	private Flags flags;

	private Cards turns;

	public Neighbour(Flags flags, Cards turns) {
		this.flags = flags;
		this.turns = turns;
	}

	public void run() {
		while (true) {
			try {
				String name = Thread.currentThread().getName();
				
				flags.set_true(name);				
				turns.set_turn(name);

				while (!flags.query_flag(name) && !turns.query_turn().equals(name))
					Thread.sleep((int) (200 * Math.random()));

				System.out.println(name + " enters the field");
				Thread.sleep((int) (200 * Math.random()));
				System.out.println(name + " leaves the field");
				flags.set_false(name);
				
			} catch (InterruptedException e) {
			}
		}
	}
}

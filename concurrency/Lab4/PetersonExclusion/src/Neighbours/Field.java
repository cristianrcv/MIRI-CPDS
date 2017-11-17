package Neighbours;

public class Field {

	public static void main(String args[]) {
		Flags flags = new Flags();
		Cards turns = new Cards();

		Thread a = new Neighbour(flags, turns);
		a.setName("Alice");

		Thread b = new Neighbour(flags, turns);
		b.setName("Bob");

		a.start();
		b.start();
	}
}

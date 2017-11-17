package Field;

public class Field {

	public static void main(String args[]) {
		Flags flags = new Flags();

		Thread a = new Neighbour(flags);
		a.setName("Alice");

		Thread b = new Neighbour(flags);
		b.setName("Bob");

		a.start();
		b.start();
	}
}

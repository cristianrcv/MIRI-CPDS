package BadBanketTwo;

public class Banket {

	public static void main(String args[]) {
		BadPotTwo pot = new BadPotTwo(5);

		Thread a = new Savage(pot);
		a.setName("Alice");
		Thread b = new Savage(pot);
		b.setName("Bob");

		Thread c = new Cook(pot);
		c.setName("Cook");

		a.start();
		b.start();
		c.start();
	}
}

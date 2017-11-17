package Field;

public class Neighbour extends Thread {

	private Flags flags;

	public Neighbour(Flags flags) {
		this.flags = flags;
	}

	public void run() {
		while (true) {
			try {
				String name = Thread.currentThread().getName();
				System.out.println("Try again, my name is: " + name);

				flags.set_true(name);
				Thread.sleep((int) (200 * Math.random()));

				if (flags.query_flag(name)) {
					System.out.println(name + " enter");
					//Thread.sleep(400);
					System.out.println(name + " exits");
				}
				Thread.sleep((int) (200 * Math.random()));
				flags.set_false(name);
			} catch (InterruptedException e) {
			}
			;
		}
	}
}

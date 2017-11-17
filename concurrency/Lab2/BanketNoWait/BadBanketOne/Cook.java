package BadBanketOne;

public class Cook extends Thread {
	private BadPot pot;

	public Cook(BadPot pot) {
		this.pot = pot;
	}

	public void run() {
		while (true) {
			System.out.println(Thread.currentThread().getName()
					+ " would like to fill the pot");
			try {
				Thread.sleep(200);
				pot.fillpot();
			} catch (InterruptedException e) {

			}
		}
	}

}

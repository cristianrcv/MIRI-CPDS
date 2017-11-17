package SavingAccountProblem;

import java.util.Random;

public class Person extends Thread {

	private Account account;
	private static final int PROB_MARGIN = 60;

	public Person(Account account) {
		this.account = account;
	}

	public void run() {
		while (true) {
			int prob = new Random().nextInt(100);
			int quantity = new Random().nextInt(2) + 1;
			if (prob < PROB_MARGIN) {
				System.out.println(Thread.currentThread().getName()
						+ " wants to deposit");
				try {
					Thread.sleep(200);
					account.deposit(quantity);
				} catch (InterruptedException e) {

				}
			} else {
				System.out.println(Thread.currentThread().getName()
						+ " wants to withdraw");
				try {
					Thread.sleep(200);
					account.withdraw(quantity);
				} catch (InterruptedException e) {

				}
			}
		}
	}

}

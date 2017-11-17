package SavingAccountProblem;

public class Account {
	int currentBalance = 0;
	int capacity;

	public Account(int capacity) {
		this.capacity = capacity;
	}

	public synchronized void deposit(int quantity) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " deposits "
				+ quantity);
		if (currentBalance >= this.capacity) {
			System.out.println(Thread.currentThread().getName()
					+ "cannot deposit because account is full. Goes away.");
		} else {
			currentBalance = currentBalance + quantity;
			print_balance();
			notifyAll();
		}
	}

	public synchronized void withdraw(int quantity) throws InterruptedException {
		while (currentBalance <= 0) {
			System.out.println(Thread.currentThread().getName()
					+ " waits to withdraw " + quantity);
			wait();
		}

		if (currentBalance < quantity) {
			System.out.println(Thread.currentThread().getName()
					+ " cannot withdraw " + quantity
					+ " because no money. Goes away.");
		} else {
			currentBalance = currentBalance - quantity;
			System.out.println(Thread.currentThread().getName()
					+ " has withdrawn " + quantity);
		}
		print_balance();
	}

	private void print_balance() {
		System.out.println("Current Account balance: " + this.currentBalance);
	}
}

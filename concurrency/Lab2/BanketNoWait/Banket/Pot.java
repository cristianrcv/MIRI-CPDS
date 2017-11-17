package Banket;

public class Pot {
	int servings = 0;
	int capacity;

	public Pot(int capacity) {
		this.capacity = capacity;
	}

	public synchronized void getserving() throws InterruptedException {
		// Condition synchronization: at least one serving available,
		// otherwise, go to the Waiting Set until the cook fill the pot
		while (servings == 0) {
			System.out.println(Thread.currentThread().getName()
					+ " has to wait");
			wait();
		}
		--servings;

		System.out.println(Thread.currentThread().getName() + " served");
		// when necessary, wake up threads in Waiting Set in order to ensure a
		// runnable cook
		if (servings == 0) {
			notifyAll();
		}
		print_servings();
	}

	public synchronized void fillpot() throws InterruptedException {
		// Condition synchronization: no servings available
		while (servings > 0) {
			System.out.println(Thread.currentThread().getName()
					+ " cannot refill the pot because it is not empty");
			wait();
		}

		servings = capacity;
		System.out.println(Thread.currentThread().getName() + " fills the pot");
		// wake up threads in Waiting Set in order to ensure they can get served
		print_servings();
		notifyAll();
	}

	// only for trace purposes
	public synchronized void print_servings() {
		System.out.println("Servings in the pot: " + servings);
	}

}

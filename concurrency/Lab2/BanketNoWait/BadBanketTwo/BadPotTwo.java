package BadBanketTwo;

public class BadPotTwo {
	int servings = 0;
	int capacity;

	public BadPotTwo(int capacity) {
		this.capacity = capacity;
	}

	public void getserving() throws InterruptedException {
		if (servings == 0) {
			System.out.println(Thread.currentThread().getName() + " go walk");
		} else {
			Thread.sleep(200);
			--servings;
			System.out.println(Thread.currentThread().getName() + " is served");
			print_servings();
		}
	}

	public void fillpot() throws InterruptedException {
		if (servings > 0) {
			System.out.println(Thread.currentThread().getName()
					+ " cannot refill the pot because it is not empty");
		} else {
			servings = capacity;
			System.out.println(Thread.currentThread().getName()
					+ " fills the pot");
			print_servings();
		}
	}

	// only for trace purposes
	public synchronized void print_servings() {
		System.out.println("Servings in the pot: " + servings);
	}

}

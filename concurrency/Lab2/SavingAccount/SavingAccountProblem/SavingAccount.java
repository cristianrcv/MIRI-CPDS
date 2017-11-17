package SavingAccountProblem;

public class SavingAccount {

	private static final int N = 10;

	public static void main(String args[]) {
		Account acc = new Account(N);

		Thread t1 = new Person(acc);
		t1.setName("Alice");
		Thread t2 = new Person(acc);
		t2.setName("Bob");

		t1.start();
		t2.start();
	}

}

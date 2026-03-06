
public class BankAccount {

	private String name;
	private double savings;
	private double checking;

	public BankAccount(String newName, double amt1, double amt2) {
		setName(newName);
		setChecking(amt1);
		setSavings(amt2);
		// - set the customer name to parameter newName, set the checking account
		// balance to parameter amt1 and set the savings account balance to parameter
		// amt2. (amt stands for amount)

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSavings() {
		return savings;
	}

	public void setSavings(double savings) {
		this.savings = savings;
	}

	public double getChecking() {
		return checking;
	}

	public void setChecking(double checking) {
		this.checking = checking;
	}

	public void depositChecking(double amt) {
		if (amt > 0) {
			checking += amt;
		} // - add parameter amt to the checking account balance (only if positive)
	}

	public void depositSavings(double amt) {
		if (amt > 0) {
			savings += amt;
		} // - add parameter amt to the savings account balance (only if positive)
	}

	public void withdrawChecking(double amt) {
		if (amt > 0) {
			checking -= amt;
		} // - subtract parameter amt from the checking account balance (only if positive)

	}

	public void withdrawSavings(double amt) {
		if (amt > 0) {
			savings -= amt;
		} // - subtract parameter amt from the savings account balance (only if positive)
	}

	public void transferToSavings(double amt) {
		if (amt > 0) {
			checking -= amt;
			savings += amt;
		} // - subtract parameter amt from the checking account balance and add to the
			// savings account balance (only if positive)
	}
}
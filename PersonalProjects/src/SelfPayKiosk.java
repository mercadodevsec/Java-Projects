public class SelfPayKiosk {
	private int numCustomers;
	private double sales;
	private double amtDue;
	private final double SALES_TAX = 0.07;
	private boolean isCheckedOut;

	// Constructor
	public SelfPayKiosk() {
		numCustomers = 0;
		sales = 0;
		amtDue = 0;
		isCheckedOut = false;
		/* Complete the method */
	}

	// Return total daily sales
	public double getTotalSales() {
		/* Update the return statment */
		return sales;
	}

	// Return current amount due
	public double getAmountDue() {
		/* Update the return statment */
		return amtDue;
	}

	// Return number of customers served
	public int getNumCustomers() {
		/* Update the return statment */
		return numCustomers;
	}

	// Scan one item
	public void scanItem(double price) {
		if (price > 0) {
			amtDue += price;
			isCheckedOut = false;/* Complete the method */
		}
	}

	// Apply sales tax to current purchases
	public void checkOut() {
		if (!isCheckedOut) {
			amtDue += (amtDue * SALES_TAX);
			isCheckedOut = true;/* Complete the method */
		}
	}

	// Cancel current purchases
	public void cancelTransaction() {
		if (!isCheckedOut) {
			amtDue = 0;
		}
	}

	// Reset register for the day
	public void resetKiosk() {
		numCustomers = 0;
		sales = 0;
		amtDue = 0;/* Complete the method */
	}

	// Apply payment to amount due
	public void makePayment(double payment) {
		if (isCheckedOut) {
			if (payment > 0) {
				if (payment >= amtDue) {
					sales += amtDue;
					numCustomers++;/* Complete the method */
					amtDue = 0;
				} else {
					sales += payment;
					amtDue -= payment;
				}
			}
		}
	}

	// Simulate multiple transactions
	public void simulateSales(int numSales, double initialPrice, double incrPrice) {
		for (int i = 0; i < numSales; i++) {
			isCheckedOut = false;
			scanItem(initialPrice);
			checkOut();
			makePayment(amtDue + 1);
			initialPrice += incrPrice;
		} /* Complete the method */
	}
}
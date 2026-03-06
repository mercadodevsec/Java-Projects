public class Calculator {

	private double value;

	public Calculator() {
		value = 0.0;
	}

	public void add(double val) {
		value = value + val;
	}

	public void subtract(double val) {
		value = value - val;
		// - subtract the parameter from the member field
	}

	public void multiply(double val) {
		value = value * val;
		// - multiply the member field by the parameter
	}

	public void divide(double val) {
		value = value / val;
		// - divide the member field by the parameter
	}

	public void clear() {
		value = 0.0;
		// - set the member field to 0.0
	}

	public double getValue() {
		return value;
		// - return the member field
	}
}

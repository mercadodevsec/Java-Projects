public class Product {

	private String code;
	private double price;
	private int count;

	public Product(String code, double price, int count) {
		this.code = code;
		this.price = price;
		this.count = count;// - set the member fields using the three parameters
	}

	public void setCode(String code) {
		this.code = code;// - set the product code (i.e. SKU234) to parameter code
	}

	public String getCode() {
		return this.code;// - return //the product code
	}

	public void setPrice(double p) {
		price = p;// - set the price to parameter p
	}

	public double getPrice() {
		return price; // - return the price
	}

	public void setCount(int num) {
		count = num;// - set the number of items in inventory to parameter num
	}

	public int getCount() {
		return count;// - return the count
	}

	public void addInventory(int amt) {
		count = count + amt;// - increase inventory by parameter amt
	}

	public void sellInventory(int amt) {
		count = count - amt; // - decrease inventory by parameter amt
	}

}

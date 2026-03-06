/**
 * The Class Person. Represents a basic person with name, email, and age
 * information. Implements the Printable interface for CSV output support.
 */
abstract class Person implements Printable {

	/** The name of the person. */
	private String name;

	/** The email address of the person. */
	private String email;

	/** The age of the person. */
	private int age;

	/**
	 * Instantiates a new person with the specified name, email, and age.
	 *
	 * @param name  the name of the person
	 * @param email the email address of the person
	 * @param age   the age of the person
	 */
	public Person(String name, String email, int age) {
		this.setName(name);
		this.setEmail(email);
		this.setAge(age);
	}

	/**
	 * Gets the age of the person.
	 *
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Sets the age of the person.
	 *
	 * @param age the new age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Gets the email address of the person.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address of the person.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the name of the person.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the person.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns a string representation of the person. (Currently returns an empty
	 * string. Can be customized as needed.)
	 *
	 * @return an empty string
	 */
	public String toString() {
		return "";
	}

	/**
	 * Converts the person object to a CSV-formatted string.
	 *
	 * @return the CSV string in the format "name,email,age"
	 */
	public String toCSV() {
		return name + "," + email + "," + age;
	}
}

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * The Class Client_2 establishes a connection to a server,
 * sends user-selected commands via multiple threads, and
 * reports turnaround time statistics.
 */
public class Client_2 {

	/**
	 * The main method to start the client program.
	 *
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		// Variables for timing and statistics
		long totalElapsedTime = 0;
		double avgElapsedTime = 0.0;
		int totalClientRequest = 0;
        // For testing purposes, override client IP

		// Server connection parameters
		String ipAddress = "";
		int portNumber = 0;

		Scanner in = new Scanner(System.in);
		System.out.println("Client started");

		// Retry connection until a successful socket is established
		while (true) {

			// Prompt and validate IP address input
			while (true) {
				System.out.print("Enter IP Address: ");
				ipAddress = in.nextLine();

				int dotCount = 0;
				boolean isValid = true;

				// Count dots and validate format
				for (int i = 0; i < ipAddress.length(); i++) {
					if (ipAddress.charAt(i) == '.') {
						dotCount++;
						// Check for invalid formats
						if (ipAddress.contains("..") || ipAddress.charAt(0) == '.'
								|| ipAddress.charAt(ipAddress.length() - 1) == '.') {
							isValid = false;
							break;
						}
					}
				}

				// IP address must have exactly 3 dots and no formatting issues
				if (dotCount != 3 || !isValid) {
					System.out.println("Please enter a valid IP address.");
				} else {
					break;
				}
			}

			// Prompt and validate port number input
			while (true) {
				System.out.print("Enter port number: ");
				if (in.hasNextInt()) {
					portNumber = in.nextInt();
					break;
				} else {
					System.out.println("Please enter a valid port number.");
					in.next(); // Discard invalid input
				}
			}

			// Attempt to create a socket with provided IP and port
			try (Socket socketClient = new Socket(ipAddress, portNumber)) {
				break; // Exit loop if connection succeeds
			} catch (IOException e) {
				// Retry on failure
				System.out.println("Connection refused. Either wrong IP address or port number.");
				in.nextLine(); // Clear buffer
			}
		}

		try {
			// Main execution loop to handle user commands
			while (true) {
				// Read the user command selection
				String userCommand = readUserInputCommand(in);

				// Exit the program if user enters "0"
				if (userCommand.equalsIgnoreCase("0")) {
					System.out.println("Goodbye!");
					ClientThreadHandler.clientDisconnected("DISCONNECTED", ipAddress, portNumber);
					in.close();
					System.exit(0);
				}

				// Prompt user for number of requests to send
				int clientRequestAmount = readUserInputRequestAmount(in);

				// Initialize shared data and thread array
				ClientSharedData.clientElapsedTime = new long[clientRequestAmount];
				ClientThreadHandler[] requestHandling = new ClientThreadHandler[clientRequestAmount];

				// Create and start request threads
				for (int i = 0; i < clientRequestAmount; i++) {
					requestHandling[i] = new ClientThreadHandler(i, userCommand, ipAddress, portNumber);
					requestHandling[i].start();
				}

				// Wait for all threads to complete
				for (ClientThreadHandler thread : requestHandling) {
					try {
						thread.join(); // Wait for thread to finish
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				// Output individual turnaround times
				for (int i = 0; i < clientRequestAmount; i++) {
					System.out.printf("Turn-around time for client request number %d: %dms\n\n",
						i + 1, ClientSharedData.clientElapsedTime[i]);
					totalElapsedTime += ClientSharedData.clientElapsedTime[i];
				}

				// Update and display overall statistics
				totalClientRequest += clientRequestAmount;
				if (totalClientRequest != 0) {
					avgElapsedTime = totalElapsedTime / (double) totalClientRequest;
				}

				System.out.println("Total Turn-around Time for client requests: " + totalElapsedTime + "ms");
				if (avgElapsedTime > 0) {
					System.out.printf("Average Turn-around Time for client requests: %.2fms\n", avgElapsedTime);
				} else {
					System.out.println("Average Turn-around Time: 0ms");
				}

				// Reset stats for next batch
				totalElapsedTime = 0;
				avgElapsedTime = 0;
				totalClientRequest = 0;
			}

		} catch (Exception e) {
			// Handle any runtime exceptions
			e.printStackTrace();
		}
	}

	/**
	 * Prompts user to select a command from the menu.
	 *
	 * @param in the Scanner object used for user input
	 * @return a valid command string
	 */
	private static String readUserInputCommand(Scanner in) {
		String userCommand = "";
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("Choose one of the commands below.");
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("1: Date and Time     - Shows the current date and time on the server");
		System.out.println("2: Uptime            - Displays how long the server has been running");
		System.out.println("3: Memory Use        - Shows the current memory usage on the server");
		System.out.println("4: Netstat           - Lists active network connections on the server");
		System.out.println("5: Current Users     - Lists users currently logged into the server");
		System.out.println("6: Running Processes - Displays processes currently running on the server");
		System.out.println("0: End Program       - Terminates the client program");
		System.out.println("--------------------------------------------------------------------------");

		// Validate command input
		while (true) {
			System.out.print("Enter the command: ");
			userCommand = in.next();

			// Accept only valid command numbers
			if (userCommand.equalsIgnoreCase("1") || userCommand.equalsIgnoreCase("2")
					|| userCommand.equalsIgnoreCase("3") || userCommand.equalsIgnoreCase("4")
					|| userCommand.equalsIgnoreCase("5") || userCommand.equalsIgnoreCase("6")
					|| userCommand.equalsIgnoreCase("0")) {
				break;
			}

			// Notify user of invalid input
			System.out.println("Invalid command. Please select a number from the list above.");
		}
		return userCommand;
	}

	/**
	 * Prompts user for number of client requests to send.
	 *
	 * @param in the Scanner object used for user input
	 * @return validated number of requests
	 */
	private static int readUserInputRequestAmount(Scanner in) {
		int clientRequestAmount = 0;
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("How many client requests to generate the command (1, 5, 10, 15, 20, 25, or 100)?");
		System.out.println("------------------------------------------------------------------------------");

		// Loop until user enters a valid request count
		while (true) {
			System.out.print("Enter the value: ");
			if (in.hasNextInt()) {
				clientRequestAmount = in.nextInt();

				// Accept only specific values
				if (clientRequestAmount == 1 || clientRequestAmount == 5 || clientRequestAmount == 10
						|| clientRequestAmount == 15 || clientRequestAmount == 20 || clientRequestAmount == 25
						|| clientRequestAmount == 100) {
					break;
				} else {
					System.out.println("Invalid input. Please enter one of the allowed values.");
				}
			} else {
				// Handle non-integer input
				System.out.println("Invalid input. Please enter an integer.");
				in.next(); // Discard invalid input
			}
		}
		return clientRequestAmount;
	}
}

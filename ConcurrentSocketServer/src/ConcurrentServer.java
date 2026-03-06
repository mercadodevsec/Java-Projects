import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * The ConcurrentServer class handles individual client connections in separate
 * threads. It reads client commands, executes corresponding system commands,
 * and sends the results back to the client.
 */
public class ConcurrentServer extends Thread {

    /**
     * The socket for the connected client.
     */
    private Socket socketClient;

    /**
     * Constructs a new ConcurrentServer thread with the given client socket.
     *
     * @param socketClient the client socket
     */
    public ConcurrentServer(Socket socketClient) {
        this.socketClient = socketClient;
    }

    /**
     * The main logic for this client handler thread. It reads commands from the client,
     * executes corresponding system-level commands, and returns results back.
     */
    @Override
    public void run() {
        try {
            // Retrieve client's IP address
            String clientIP = socketClient.getInetAddress().getHostAddress();
            boolean isNewClient = true;

            // If first-time connection, add to shared IP list and increment count
            if (ServerSharedData.ClientIPList.isEmpty()) {
                System.out.printf("Client with IP address %s is connected\n", clientIP);
                ServerSharedData.activeClientCount++;
                ServerSharedData.ClientIPList.add(clientIP);
                printClientCount();
            }

            // Check if IP is already in the list
            for (String ip : ServerSharedData.ClientIPList) {
                if (clientIP.equals(ip)) {
                    isNewClient = false;
                    break;
                }
            }

            // Handle new client connection
            if (isNewClient) {
                System.out.printf("Client with IP address %s is connected\n", clientIP);
                ServerSharedData.activeClientCount++;
                ServerSharedData.ClientIPList.add(clientIP);
                printClientCount();
            }

            // Setup input/output communication streams
            BufferedReader clientDataRequest = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            DataOutputStream sendServerResponse = new DataOutputStream(socketClient.getOutputStream());

            // Continuously process client requests
            while (true) {
                String clientData = clientDataRequest.readLine();

                if (clientData != null) {
                    if (clientData.equals("DISCONNECTED")) {
                        System.out.printf("Client with IP address %s is diconnected\n", clientIP);
                        ServerSharedData.activeClientCount--;
                        ServerSharedData.ClientIPList.remove(clientIP);
                        printClientCount();
                        break;
                    }

                    // Execute command and send back the response
                    String serverResponseData = readRequest(clientData);
                    sendServerResponse.writeBytes(serverResponseData + "\n" + "ENDSTREAM\n");
                } else {
                    // Client closed the connection unexpectedly
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the current count of active clients.
     */
    private void printClientCount() {
        if (ServerSharedData.activeClientCount == 0) {
            System.out.println("No clients are connected. Waiting for new clients...");
        } else if (ServerSharedData.activeClientCount == 1) {
            System.out.println("1 client is connected");
        } else {
            System.out.println(ServerSharedData.activeClientCount + " clients are connected");
        }
    }

    /**
     * Maps command number strings received from the client to actual system commands.
     *
     * @param request the command number
     * @return the command output
     */
    public static String readRequest(String request) {
        switch (request) {
            case "1":
                return executeCommand("date");
            case "2":
                return executeCommand("uptime");
            case "3":
                return executeCommand("free");
            case "4":
                return executeCommand("netstat");
            case "5":
                return executeCommand("who");
            default:
                return executeCommand("ps -ef");
        }
    }

    /**
     * Executes a shell command and returns its combined output (stdout and stderr).
     *
     * @param command the shell command to execute
     * @return the result of the command
     */
    private static String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        String line;
        try {
            command = "wsl " + command;
            String[] cmdArray = command.split(" ");
            ProcessBuilder pb = new ProcessBuilder(cmdArray);
            Process p = pb.start();

            // Read standard output
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = stdInput.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Read standard error
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = stdError.readLine()) != null) {
                output.append(line).append("\n");
            }

            p.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Trim trailing newline if present
        if (output.length() > 0) {
            output.deleteCharAt(output.length() - 1);
        }

        return output.toString();
    }
}

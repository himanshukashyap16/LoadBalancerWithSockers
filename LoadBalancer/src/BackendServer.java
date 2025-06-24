import java.io.*;
import java.net.*;
import java.util.Scanner;

public class BackendServer {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java BackendServer <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Backend Server started on port " + port);

        while (true) {
            Socket socket = serverSocket.accept();
            handleClient(socket, port);
        }
    }

    private static void handleClient(Socket clientSocket, int port) {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();


            Scanner scanner = new Scanner(inputStream);
            String request = scanner.nextLine();

            System.out.println("Received request on port " + port + ": " + request);


            PrintWriter printWriter = new PrintWriter(outputStream, true);
            printWriter.println("Response from server on port " + port + "Message received to Server ");

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
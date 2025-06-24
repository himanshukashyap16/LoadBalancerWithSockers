import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080);

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        Scanner scanner = new Scanner(inputStream);
        Scanner readFromClient = new Scanner(System.in);

        PrintWriter printWriter = new PrintWriter(outputStream, true);

        System.out.println("Enter the message you want to send to server");
        String messageSendToServer = readFromClient.nextLine();

        printWriter.println(messageSendToServer);

        String response = scanner.nextLine();
        System.out.println("Received from load balancer: " + response);

        socket.close();
    }
}

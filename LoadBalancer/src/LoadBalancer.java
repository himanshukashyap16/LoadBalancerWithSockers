import java.io.*;
import java.net.*;
import java.util.*;

public class LoadBalancer {
    static List<InetSocketAddress> backendServers = Arrays.asList(
            new InetSocketAddress("localhost", 9001),
            new InetSocketAddress("localhost", 9002),
            new InetSocketAddress("localhost", 9003)
    );

    static int current = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket loadBalancerSocket = new ServerSocket(8080);
        System.out.println("Load Balancer running on port 8080...");

        while (true) {
            Socket clientSocket = loadBalancerSocket.accept();

            LoadBalancingStrategy loadBalancingStrategy = null;
            InetSocketAddress balancingStrategyNextServer;

            String strategyType = args[0];

            switch (strategyType)
            {
                case "RoundRobin":
                    loadBalancingStrategy = new RoundRobin();
                    balancingStrategyNextServer = loadBalancingStrategy.getNextServer(backendServers);
                    break;
                case "LeastConnections":
                    loadBalancingStrategy = new LeastConnection(backendServers);
                    balancingStrategyNextServer = loadBalancingStrategy.getNextServer(backendServers);
                    break;
                case "Random":
                    loadBalancingStrategy = new RandomStrategy();
                    balancingStrategyNextServer = loadBalancingStrategy.getNextServer(backendServers);
                    break;
                default:
                    System.out.println("Invalid load balancing strategy. Defaulting to Random.");
                    balancingStrategyNextServer = new RandomStrategy().getNextServer(backendServers);
            }


            try {
                // Read Request from Client
                InputStream inputStream = clientSocket.getInputStream();
                Scanner readRequestFromClient = new Scanner(inputStream);
                String clientMessage = readRequestFromClient.nextLine();

                // Connect to backend
                Socket backendSocket = new Socket();
                backendSocket.connect(balancingStrategyNextServer);

                //Send Output to Server whichever is recevied from client
                OutputStream serverOutputStream = backendSocket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(serverOutputStream, true);
                printWriter.println(clientMessage);

                // Read response from backend
                Scanner backendScanner = new Scanner(backendSocket.getInputStream());
                String backendResponse = backendScanner.nextLine();

                // Send response to client
                PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                clientWriter.println(backendResponse);

                System.out.println("Message received from Server " + backendSocket.getPort() + backendResponse);


                // Close sockets
                backendSocket.close();
                clientSocket.close();
            }
            
            finally {
                if(loadBalancingStrategy instanceof LeastConnection){
                    ((LeastConnection) loadBalancingStrategy).decrementConnection(balancingStrategyNextServer);
                }
                System.out.println("Client disconnected.");
            }
        }
    }
}

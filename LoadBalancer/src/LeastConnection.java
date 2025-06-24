import java.net.InetSocketAddress;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeastConnection implements LoadBalancingStrategy{
    private Map<InetSocketAddress, Integer> serverConnectionCount = new HashMap<>();

    public LeastConnection(List<InetSocketAddress> servers) {
        for (InetSocketAddress server : servers) {
            serverConnectionCount.put(server, 0);
        }
    }

    public void incrementConnection(InetSocketAddress server) {
        serverConnectionCount.put(server, serverConnectionCount.get(server) + 1);
    }

    public void decrementConnection(InetSocketAddress server) {
        serverConnectionCount.put(server, serverConnectionCount.get(server) - 1);
    }

    @Override
    public InetSocketAddress getNextServer(List<InetSocketAddress> servers) {
        InetSocketAddress inetSocketAddress = serverConnectionCount.entrySet().stream()
                .min(Comparator.comparingInt(Map.Entry::getValue))
                .get()
                .getKey();
        incrementConnection(inetSocketAddress);
        return inetSocketAddress;
    }
}

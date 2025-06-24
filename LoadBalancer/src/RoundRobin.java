import java.net.InetSocketAddress;
import java.util.List;

public class RoundRobin implements LoadBalancingStrategy{

    private Integer currentIndex = 0;
    @Override
    public InetSocketAddress getNextServer(List<InetSocketAddress> servers) {
        InetSocketAddress server = servers.get(currentIndex);
        currentIndex = (currentIndex + 1) % servers.size();
        return server;
    }
}

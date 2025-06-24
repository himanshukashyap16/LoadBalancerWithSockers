import java.net.InetSocketAddress;
import java.util.List;

public interface LoadBalancingStrategy {
    InetSocketAddress getNextServer(List<InetSocketAddress> servers);
}

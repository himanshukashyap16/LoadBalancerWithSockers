import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;

public class RandomStrategy implements LoadBalancingStrategy{
    Random random = new Random();
    @Override
    public InetSocketAddress getNextServer(List<InetSocketAddress> servers) {
       return servers.get(random.nextInt(servers.size()));
    }
}

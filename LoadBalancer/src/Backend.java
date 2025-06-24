import java.net.InetSocketAddress;


public class Backend {
    InetSocketAddress inetSocketAddress;
    Integer weight;

    public Backend(InetSocketAddress inetSocketAddress, Integer weight) {
        this.inetSocketAddress = inetSocketAddress;
        this.weight = weight;
    }
}

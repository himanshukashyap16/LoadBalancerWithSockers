---

## How to Run

1. **Compile the code:**
   ```sh
   javac src/*.java
   ```

2. **Start three server instances on different ports** (e.g., 9001, 9002, 9003):
   ```sh
   java -cp src Server 9001
   java -cp src Server 9002
   java -cp src Server 9003
   ```

3. **Start the LoadBalancer and specify the strategy** (e.g., `RoundRobin` or `Random`):
   ```sh
   java -cp src LoadBalancer <strategy>
   ```

4. **Run the client:**
   ```sh
   java -cp src Client
   ```

- Make sure to start the servers and load balancer before running the client.
- Replace `<strategy>` with your desired load balancing strategy.

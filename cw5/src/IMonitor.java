public interface IMonitor {
    boolean produced(int val, Producent p);

    boolean consume(int val, Konsumer c);

    void getTime();
}

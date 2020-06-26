import java.net.*;

public class Server {
    /* -------------------------------- variables ------------------------------- */
    ServerSocket s = null;
    Socket ss = null;

    /* ---------------------------------- main ---------------------------------- */
    public static void main(String[] args) {

        Server s = new Server();
        s.connect();

    }

    /* --------------------------------- connect -------------------------------- */
    public void connect() {
        try {

            System.out.println("-------------------");
            System.out.println("[Server] Init");
            System.out.println("-------------------\n");

            s = new ServerSocket(0);

            System.out.println("-------------------");
            System.out.println(
                    "[Server] Created socket\nLocal port : " + s.getLocalPort() + "\nAddress : " + s.getInetAddress());
            System.out.println("-------------------\n");

            ss = s.accept();

            System.out.println("-------------------");
            System.out
                    .println("[Server] Client accepted \nLocal port : " + ss.getLocalPort() + "\nPort : " + ss.getPort()
                            + "\nLocal address : " + ss.getLocalAddress() + "\nAddress : " + ss.getInetAddress());
            System.out.println("-------------------\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
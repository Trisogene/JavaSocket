import java.io.*;
import java.net.*;

public class Client {

    /* -------------------------------- variables ------------------------------- */
    Socket client;
    InputStream in;
    OutputStream out;
    BufferedReader keyboard;

    /* ---------------------------------- main ---------------------------------- */
    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
        client.talk();
    }

    /* --------------------------------- connect -------------------------------- */

    public void connect() {
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Insert server port : ");
        try {
            int port = Integer.parseInt(keyboard.readLine());
            client = new Socket("localhost", port);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ---------------------------------- talk ---------------------------------- */

    public void talk() {
        while(true){
            keyboard = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Insert an operator like (+,-,*,/) : ");
            try {
                String msg = keyboard.readLine();
                out = client.getOutputStream();
                out.write(msg.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
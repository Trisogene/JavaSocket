import java.io.*;
import java.net.*;

public class Client {
    /* -------------------------------- variables ------------------------------- */
    Socket client;
    InputStream in;
    OutputStream out;
    BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));;
    int state = 0;

    /* ---------------------------------- main ---------------------------------- */
    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
        client.talk();
    }

    /* --------------------------------- connect -------------------------------- */
    public void connect() {
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
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            switch (state) {
                case 0:
                    System.out.print("Insert an operator like ( + - * / ) : ");
                    break;

                case 1:
                    System.out.print("Insert first argument : ");
                    break;

                case 2:
                    System.out.print("Insert second argument : ");
                    break;

                default:
                    break;
            }
            String msg;
            try {
                msg = keyboard.readLine();
                out = client.getOutputStream();
                out.write(msg.getBytes());

                byte[] buf = new byte[100];
                in = client.getInputStream();
                in.read(buf,0,buf.length);
                String resp = new String(buf, 0, buf.length).trim();

                if (resp.equals(".")) {
                    return;
                } else if (resp.equals("Ok")) {
                    state = (state + 1) % 3;
                } else if (resp.equals("NotOk")) {
                    continue;
                } else {                    
                    System.out.println("Result is : " + resp);
                    state = (state + 1) % 3;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("[Client] State : " + state);
        }
    }
}
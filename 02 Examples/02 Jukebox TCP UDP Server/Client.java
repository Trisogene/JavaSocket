import java.io.*;
import java.net.*;

public class Client {

    /* -------------------------------- variables ------------------------------- */
    InputStream in;
    OutputStream out;
    String msgIn,msgOut,serverPortTCP;
    Socket s;
    DatagramSocket ds;
    DatagramPacket dpOut,dpIn;
    BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

    /* ---------------------------------- main ---------------------------------- */
    public static void main(String[] args) {
        Client client = new Client();
        client.login();
        client.choose();
    }

    /* ---------------------------------- login --------------------------------- */
    public void login() {
        while (true) {
            try {
                System.out.print("Insert WebServer TCP port: ");
                serverPortTCP = keyboard.readLine();
                s = new Socket("localhost", Integer.parseInt(serverPortTCP));
                ds = new DatagramSocket(0);
                out = s.getOutputStream();
                String UDPPort = Integer.toString(ds.getLocalPort());
                out.write(UDPPort.getBytes());

                byte[] buf = new byte[100];
                in = s.getInputStream();
                in.read(buf);
                String msgIn = new String(buf, 0, buf.length).trim();
                if (msgIn.equals("ACK")) {
                    s.close();
                    return;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* --------------------------------- choose --------------------------------- */
    public void choose() {
        while (true) {
            try {
                System.out.print("(1) Listen \n(2) Play\nChoose :");
                String choice = keyboard.readLine();
                
                if(choice.equals("2")){
                    System.out.print("(1) song1 \n(2) song2\n(3) song3 \n(4) song4\n(5) song5\nChoose :");
                    choice = keyboard.readLine();
                    if(Integer.parseInt(choice) > 0 && Integer.parseInt(choice) < 6){
                        s = new Socket("localhost",Integer.parseInt(serverPortTCP));
                        out = s.getOutputStream();
                        out.write(choice.getBytes());
                    }else{
                        continue;
                    }
                }

                byte[] buf = new byte[100];
                dpIn = new DatagramPacket(buf, buf.length);
                ds.receive(dpIn);
                msgIn = new String(buf,0,buf.length);
                System.out.println("Currently playng : " + msgIn);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
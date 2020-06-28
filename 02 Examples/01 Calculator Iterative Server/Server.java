/*
    ! Need refactor
*/

import java.io.*;
import java.net.*;

public class Server {

    /* -------------------------------- variables ------------------------------- */
    ServerSocket ss;
    Socket client;
    InputStream in;
    OutputStream out;
    int state = 0;

    char op;
    double arg1;
    double arg2;

    /* ---------------------------------- main ---------------------------------- */
public static void main(String[] args) {
    Server s = new Server();
    s.init();
    while(true){
        s.waitForConnection();
        s.talk();
    }
}
    /* ---------------------------------- init ---------------------------------- */
    public void init() {
        try {
            ss = new ServerSocket(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("[Server] Open on port : " + ss.getLocalPort());
}
/* ---------------------------- waitForConnection --------------------------- */
    public void waitForConnection(){
        try {
            client = ss.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[Server] Accepted client , client port : " + client.getPort());
    }
/* ---------------------------------- talk ---------------------------------- */
    public void talk(){
        while(true){
            try {
                in = client.getInputStream();
                out = client.getOutputStream();
                
                byte[] buf = new byte[100];
                in.read(buf,0,buf.length);
                String msg = new String(buf,0,buf.length).trim();

                if(msg.equals(".")){
                    state = 0;
                    System.out.println("[Server] Closing client on port " + client.getPort());
                    System.out.println("[Server] Status : " + state);
                    out.write(".".getBytes());
                    client.close();
                    return;
                }

                if(checkMsg(msg)){
                    if(state>1){
                        String result = operate();
                        out.write(result.getBytes());
                        state = (state+1)%3;
                        System.out.println("[Server] Status : " + state);
                        continue;
                    }
                    state = (state+1)%3;
                    System.out.println("[Server] Status : " + state);
                    out.write("Ok".getBytes());
                }else{
                    System.out.println("[Server] Status : " + state);
                    out.write("NotOk".getBytes());
                };
            } catch (SocketException e) {
                state = 0;
                System.out.println("[Server] Status : " + state);
                System.out.println("[Server] client on port " + client.getPort() + "disconnected");

                return;
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
/* -------------------------------- checkMsg -------------------------------- */
    public boolean checkMsg(String msg){
        switch (state) {
            case 0:
                if(msg.equals("+") || msg.equals("-") || msg.equals("/") || msg.equals("*")){
                    op = msg.charAt(0) ;
                    return true;
                }
                return false;
        
            case 1:
                try {
                    arg1 = Double.parseDouble(msg);
                } catch (NumberFormatException e) {
                    return false;
                }
                return true;

            case 2:
                try {
                    arg2 = Double.parseDouble(msg);
                } catch (NumberFormatException e) {
                    return false;
                }
                return true;
                
            default:
                return false;
        }
    }
/* --------------------------------- operate -------------------------------- */
    public String operate(){
        double result = 0;
        switch (op) {
            case '+':
                result = arg1+arg2;
                break;
            
            case '-':
                result = arg1-arg2;
                break;
        
            case '*':
                result = arg1*arg2;
                break;
            
            case '/':
                result = arg1/arg2;
                break;
            
                default:
                break;
        }
        return Double.toString(result);
    }
}

import java.io.*;
import java.net.*;

public class client {
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    public client(){
        try{
            System.out.println("sending req to server");
            socket=new Socket("127.0.0.1",7777);
            System.out.println("connection done.");

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void startReading(){
        // thread-> continuously read the data
        Runnable r1=()->{
            System.out.println(" reader started...");
            try {
                while (true) {

                    String msg = br.readLine();

                    if (msg.equals("exit")) {
                        System.out.println("server terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("server: " + msg);
                }
            }catch(Exception e){
              //  e.printStackTrace();
                System.out.println("Connection is closed");
            }

        };
        new Thread(r1).start();
    }
    public void startWriting(){
        // thread-> uses the data and send it to the client
        Runnable r2=()->{
            System.out.println("writer started");
            try {
                while (!socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
                }


            }
            catch (Exception e){
                e.printStackTrace();
            }

        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("this is client class");
        new client();
    }
}

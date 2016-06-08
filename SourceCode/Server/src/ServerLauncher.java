import java.net.ServerSocket;
import java.net.Socket;

public class ServerLauncher {
    public static void main(String[] args) throws Exception{

        //Server port is 1234
        ServerSocket server = new ServerSocket(1234);
        Socket client;
        boolean f = true;
        while(f){
            client = server.accept();
            System.out.println("Server:Receive a new connect.");
            System.out.println("From Tcp Address:" + client.getInetAddress());
            System.out.println("From Tcp Port:" + client.getPort());
            new Thread(new Server(client)).start();
        }
        server.close();
    }
}
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void readFile(DataOutputStream dos, String response, File file) throws IOException {

        dos.writeBytes(response);
        DataInputStream filedis = new DataInputStream(new FileInputStream(file));
        byte[] b = new byte[1024];
        int i;
        while ((i = filedis.read(b)) > 0) {
            dos.write(b, 0, i);
            dos.flush();
        }

        filedis.close();
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        System.out.println("客户端连接成功: " + socket.getInetAddress().getHostAddress());

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String[] list = dis.readUTF().split(" ");
        String requesString = list[1];
        File file = new File("." + requesString);

        dos.writeUTF(file.getName());
        if(file.exists()){
            String response = "HTTP/1.1 200 OK\n";
            server.readFile(dos, response, file);
        }
        else {
            String response = "HTTP/1.1 404\n";
            dos.writeBytes(response);
        }
        dos.close();
        dis.close();
        socket.close();
        serverSocket.close();
    }
}

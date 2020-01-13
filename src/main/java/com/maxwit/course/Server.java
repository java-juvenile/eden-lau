import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        System.out.println("客户端连接成功: " + socket.getInetAddress().getHostAddress());

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        File file = new File(dis.readUTF().replaceAll("mycurl ", "."));
        dos.writeLong(file.length());
        dos.writeUTF(file.getName());
        
        DataInputStream filedis = new DataInputStream(new FileInputStream(file));
        byte[] b = new byte[1024];
        int i;
        while ((i = filedis.read(b)) > 0) {
            dos.write(b, 0, i);
            dos.flush();
        }

        filedis.close();
        dos.close();
        dis.close();
        socket.close();

    }
}
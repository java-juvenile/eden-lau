import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        String[] list = args[0].split(":");
        String path = list[0];
        int port = Integer.parseInt(list[1]);

        Socket socket = new Socket(path, port);
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

       
        String requesString = "GET " + args[1] + " HTTP/1.1";
        dos.writeUTF(requesString);

        String filename = dis.readUTF();

        File file = new File(filename);
        DataOutputStream filedos = new DataOutputStream(new FileOutputStream(file));

        byte[] b = new byte[1024];
        int i;
        double curLen = 0.0;
        while ((i = dis.read(b)) > 0) {
            filedos.write(b, 0, i);
            curLen += i;
        }
        dos.flush();

        dos.close();
        dis.close();
        filedos.close();
        socket.close();
    }
}

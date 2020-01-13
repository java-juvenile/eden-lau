import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public void writeFile(DataInputStream dis, DataOutputStream dos, String filename) throws IOException {
        File file = new File(filename);
        DataOutputStream filedos = new DataOutputStream(new FileOutputStream(file));

        byte[] b = new byte[1024];
        int i;

        while ((i = dis.read(b)) > 0) {
            filedos.write(b, 0, i);
        }
        dos.flush();
        filedos.close();
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        String[] list = args[0].split(":");
        String path = list[0];
        int port = Integer.parseInt(list[1]);

        Socket socket = new Socket(path, port);
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        String requesString = "GET " + args[1] + " HTTP/1.1";
        dos.writeUTF(requesString);

        String filename = dis.readUTF();
        client.writeFile(dis, dos, filename);

        dos.close();
        dis.close();
        socket.close();
    }
}

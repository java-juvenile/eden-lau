import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        String[] list = args[0].split(":");
        String path = list[0];
        int port = Integer.parseInt(list[1]);

        Socket socket = new Socket(path, port);
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        dos.writeUTF(line);

        double length = dis.readLong();
        String filename = dis.readUTF();
        
        File file = new File(filename);
        DataOutputStream filedos = new DataOutputStream(new FileOutputStream(file));
        byte[] b = new byte[1024];
        int i;
        double curLen = 0.0;
        while ((i = dis.read(b)) > 0) {
            filedos.write(b, 0, i);
            curLen += i;
            System.out.println("传输进度: " + (curLen / length * 100) + "%");
        }
        dos.flush();

        dos.close();
        dis.close();
        filedos.close();
        socket.close();
    }
}
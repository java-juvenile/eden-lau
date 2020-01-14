import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void readFile(DataOutputStream dos, String response, File file) throws IOException {

        dos.writeBytes(response);
        String responseString = "Server: Apache/2.2.14\n" + "Content-Type: text/html; charset=UTF-8\n"
                + "Content-Length:" + file.length() + "\n\n";
        dos.writeBytes(responseString);
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
        while (true) {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            System.out.println("客户端连接成功: " + socket.getInetAddress().getHostAddress());

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            InputStreamReader dis = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(dis);
            String readLine = br.readLine();
            String[] list = readLine.split(" ");
            String requesString = list[1];
            System.out.println(requesString);
            File file = new File("." + requesString);
            File errorFile = new File("./aa/error.html");

            if (file.exists()) {
                String response = "HTTP/1.1 200 OK\n";
                server.readFile(dos, response, file);
            } else {
                String response = "HTTP/1.1 404 Not Found\n";

                server.readFile(dos, response, errorFile);
            }
            dos.close();
            dis.close();
            socket.close();
            serverSocket.close();

        }
    }
}

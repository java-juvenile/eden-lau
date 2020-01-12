import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileInputAndOutput{
    public static void main(String[] args) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            File srcFile = new File("./Test.java");
            if(!srcFile.exists()) {
                srcFile.createNewFile();
            }
            fis = new FileInputStream(srcFile);

            File targetFile = new File("./1.txt");
            if(!targetFile.exists()) {
                targetFile.createNewFile();
            }
            fos = new FileOutputStream(targetFile);

            byte[] b = new byte[1024];
            int i;
            while ((i = fis.read(b)) != -1) {
                fos.write(b, 0, i);
            }
            System.out.println("写入结束");
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
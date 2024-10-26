package nio.ex_2_file_copier;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileCopier {
    public static void main(String[] args) {
        File srcDir = new File("src.txt");
        File destDir = new File("dest.txt");

        try {
            if(!destDir.exists()){
                Files.createFile(destDir.toPath());
            }
        } catch (IOException e) {
            System.out.println("Encountered and error creating destination file");
        }

        try(
                FileChannel srcChannel = FileChannel.open(srcDir.toPath(), StandardOpenOption.READ);
                FileChannel destChannel = FileChannel.open(destDir.toPath(), StandardOpenOption.WRITE);
                ) {

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int bytesRead;

            while((bytesRead = srcChannel.read(buffer)) > -1){
                buffer.flip();
                destChannel.write(buffer);
                buffer.clear();
            }
            System.out.println("Copying done");

        } catch (IOException e) {
            System.out.println("Error copying file");
        }
    }
}

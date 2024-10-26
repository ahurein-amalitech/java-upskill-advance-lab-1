package nio.ex_1_save_to_file;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class NIOSample {

    public static void main(String[] args) throws IOException {
        File file = new File("data.txt");
        File outputFile = new File("output.txt");

        try (
                FileChannel readChanel = FileChannel.open(file.toPath(), StandardOpenOption.READ);
                FileChannel writeChanel = FileChannel.open(outputFile.toPath(), StandardOpenOption.WRITE)
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int bytesRead;
            while((bytesRead = readChanel.read(buffer)) > 0){
                buffer.flip();
                writeChanel.write(buffer);
                buffer.clear();
            }
        }
    }

}

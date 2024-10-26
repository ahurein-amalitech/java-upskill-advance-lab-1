package reflection.ex_2_class_loader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CustomLoader extends ClassLoader {
    private final File classDirectory;

    public CustomLoader(File classDirectory) {
        this.classDirectory = classDirectory;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadClassContent(name);
        if(classData == null) {
            throw new ClassNotFoundException("Class " + name + " not found");
        }
        return defineClass(name, classData, 0, classData.length);
    }

    private byte[] loadClassContent(String name) {
        try{
        String classPath = classDirectory.getPath() + File.separator + name.replace('.', File.separatorChar) + ".class";
        Path classFilePath = Path.of(classPath);
            return Files.readAllBytes(classFilePath);
        }catch (IOException e){
            return null;
        }
    }
}

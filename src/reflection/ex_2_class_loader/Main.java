package reflection.ex_2_class_loader;

import java.io.File;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        try {
            File classesDir = new File("compiled");
            CustomLoader customLoader = new CustomLoader(classesDir);
            Class<?> simpleClass = customLoader.loadClass("reflection.ex_2_class_loader.SimpleClass");
            Object instance = simpleClass.getDeclaredConstructor().newInstance();
            Method method = instance.getClass().getDeclaredMethod("printHello");
            method.invoke(instance);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}

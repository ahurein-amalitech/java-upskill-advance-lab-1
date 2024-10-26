package reflection.ex_1_logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ExecutionTimeHandler implements InvocationHandler {
    private final Object target;

    public ExecutionTimeHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.isAnnotationPresent(LogExecutionTime.class)) {
            long start = System.currentTimeMillis();
            Object result = method.invoke(target, args);
            long executionTime = System.currentTimeMillis() - start;
            System.out.println(method.getName() + " took " + executionTime + " ms");
            return result;
        } else {
            return method.invoke(target, args);
        }
    }
}

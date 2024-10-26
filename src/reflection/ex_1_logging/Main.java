package reflection.ex_1_logging;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        IUserService userService = new UserService();

        IUserService proxyService = (IUserService) Proxy.newProxyInstance(
                IUserService.class.getClassLoader(),
                new Class[]{IUserService.class},
                new ExecutionTimeHandler(userService)
        );

        proxyService.printName();
    }
}

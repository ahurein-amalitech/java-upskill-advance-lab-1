package reflection.ex_1_logging;

public interface IUserService {
    @LogExecutionTime
    void printName();
}

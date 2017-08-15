package moiavto.mbsl.ru.myauto.network;

/**
 * Created by Fedor on 27.06.2017.
 */

public class ApiThrowable extends Throwable {
    private int statusCode;
    private String message;

    public ApiThrowable() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }
}

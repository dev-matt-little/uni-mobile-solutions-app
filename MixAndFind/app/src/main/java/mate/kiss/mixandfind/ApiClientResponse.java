package mate.kiss.mixandfind;

public class ApiClientResponse<T> {
    public int httpStatusCode;
    public T data;

    public ApiClientResponse(int httpStatusCode, T data) {
        this.httpStatusCode = httpStatusCode;
        this.data = data;
    }

    public ApiClientResponse(int httpStatusCode) {
        this(httpStatusCode, null);
    }
}

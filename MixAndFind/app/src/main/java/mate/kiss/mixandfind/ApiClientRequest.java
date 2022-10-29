package mate.kiss.mixandfind;

import android.net.Uri;

import java.util.Map;

public class ApiClientRequest {
    Uri uri;
    Map<String, String> headerParams;
    Map<String, String> queryParams;
    Object body;

    public ApiClientRequest(Uri uri, Map<String, String> headerParams, Map<String, String> queryParams, Object body) {
        this.uri = uri;
        this.headerParams = headerParams;
        this.queryParams = queryParams;
        this.body = body;
    }

    public ApiClientRequest(Uri uri, Map<String, String> headerParams, Map<String, String> queryParams) {
        this(uri, headerParams, queryParams, null);
    }
}

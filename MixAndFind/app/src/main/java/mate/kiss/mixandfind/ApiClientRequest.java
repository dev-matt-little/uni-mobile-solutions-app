package mate.kiss.mixandfind;

import android.net.Uri;

import java.util.Dictionary;
import java.util.Map;

public class ApiClientRequest {
    Uri uri;
    Map<String, String> headerParams;
    Map<String, String> queryParams;

    public ApiClientRequest(Uri uri, Map<String, String> headerParams, Map<String, String> queryParams) {
        this.uri = uri;
        this.headerParams = headerParams;
        this.queryParams = queryParams;
    }
}

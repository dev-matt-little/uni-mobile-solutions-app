package mate.kiss.mixandfind;


import static java.util.concurrent.CompletableFuture.supplyAsync;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PexelsApiClient {

    public CompletableFuture<String> GetPexelSearchResponse(ApiClientRequest request) throws IOException, ExecutionException, InterruptedException {
        Uri.Builder uriBuilder = request.uri.buildUpon();

        if (request.queryParams != null) {
            for (Map.Entry<String, String> query : request.queryParams.entrySet()) {
                uriBuilder.appendQueryParameter(query.getKey(), query.getValue());
            }
        }

        Uri uri = uriBuilder.build();
        return sendAsync(uri, "GET");
    }

    private CompletableFuture<String> sendAsync(Uri uri, String method) throws IOException, ExecutionException, InterruptedException {

        URL url = new URL(uri.toString());
        HttpURLConnection httpClient = (HttpURLConnection) url.openConnection();

        httpClient.setRequestMethod(method);
        httpClient.setRequestProperty("Authorization", Constant.PEXELS_API_KEY);

        String response = CompletableFuture.supplyAsync(() -> {
            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(httpClient.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                return content.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).get();

        httpClient.disconnect();
        return CompletableFuture.completedFuture(response);
    }
}

package mate.kiss.mixandfind;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public abstract class ApiClientBase {

    protected <T> CompletableFuture<ApiClientResponse<T>> getAsync(Class<T> t, ApiClientRequest request) throws IOException, ExecutionException, InterruptedException {
        Uri.Builder uriBuilder = request.uri.buildUpon();

        if (request.queryParams != null) {
            for (Map.Entry<String, String> query : request.queryParams.entrySet()) {
                uriBuilder.appendQueryParameter(query.getKey(), query.getValue());
            }
        }

        request.uri = uriBuilder.build();
        return sendAsync(t, request, "GET");
    }

    private <T> CompletableFuture<ApiClientResponse<T>> sendAsync(Class<T> t, ApiClientRequest request, String httpMethod) throws IOException {
        HttpURLConnection httpConnection = (HttpURLConnection) new URL(request.uri.toString()).openConnection();

        httpConnection.setRequestMethod(httpMethod);
        httpConnection.setRequestProperty("Authorization", Constant.PEXELS_API_KEY);

        if (request.headerParams != null) {
            for (Map.Entry<String, String> header : request.headerParams.entrySet()) {
                httpConnection.setRequestProperty(header.getKey(), header.getValue());
            }
        }

        if ( request.body != null) {
            httpConnection.setDoOutput(true);
            OutputStream outputStream = httpConnection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            Gson serializer = new Gson();
            writer.write(serializer.toJson(request.body));
            writer.flush();
            writer.close();
            outputStream.close();
        }

        return CompletableFuture.supplyAsync(() -> {
            int httpStatusCode = -1;
            try {
                InputStream stream = httpConnection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(stream));
                httpStatusCode = httpConnection.getResponseCode();

                if (t == Bitmap.class) {
                    // handle image download
                    BufferedInputStream bis = new BufferedInputStream(stream);
                    Bitmap bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    stream.close();
                    return new ApiClientResponse<T>(httpStatusCode, (T)bm);
                }

                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                Gson serializer = new Gson();
                T data = serializer.fromJson(content.toString(), t);

                return new ApiClientResponse<T>(httpStatusCode, data);
            } catch (IOException e) {
                return new ApiClientResponse<T>(httpStatusCode, null);
            } finally {
                httpConnection.disconnect();
            }
        });
    }
}

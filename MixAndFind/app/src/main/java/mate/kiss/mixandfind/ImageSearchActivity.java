package mate.kiss.mixandfind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ImageSearchActivity extends AppCompatActivity {

    PexelsApiClient pexelsApiClient;
    ImageView imageView;
    Intent triggerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);

        triggerIntent = getIntent();
        imageView = findViewById(R.id.imageView);
        pexelsApiClient = new PexelsApiClient();
    }

    public void handleGetNewImagePushed(View view) {
        int color = triggerIntent.getIntExtra(Constant.COLOR_ITEM_COLOR_CODE_KEY, 0);

        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put(Constant.THEME_KEY, "city");
        queryParams.put(Constant.IMAGES_COUNT_KEY, "2");
        queryParams.put(Constant.COLOR_KEY, String.format("#%06X", (0xFFFFFF & color)));

        ApiClientRequest request = new ApiClientRequest(Uri.parse(Constant.PEXELS_SEARCH_URL), null, queryParams);
        String response = "no response";
        try {
            response = pexelsApiClient.GetPexelSearchResponse(request).get();
            Gson serializer = new Gson();
            PexelsResponseDto dto = serializer.fromJson(response, PexelsResponseDto.class);
            String imageString = dto.images.get(0).imageSources.get(Constant.PEXEL_IMAGE_SIZE_TINY_KEY);
            imageView.setImageBitmap(getImageBitmap(imageString));
        } catch (ExecutionException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImageBitmap(String url) throws ExecutionException, InterruptedException {

        Bitmap bitmap = CompletableFuture.supplyAsync(() -> {
            Bitmap bm;
            try {
                URL aURL = new URL(url);
                URLConnection conn = aURL.openConnection();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
                return bm;
            } catch (IOException e) {
                Log.e("", "Error getting bitmap", e);
                return null;
            }
        }).get();

        return bitmap;
    }
}
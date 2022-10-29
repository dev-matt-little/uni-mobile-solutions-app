package mate.kiss.mixandfind;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PexelsApiClient extends ApiClientBase {

    public List<PexelsPhotoDto> searchImagesAsync(ImageSearchData imageSearchData) throws IOException, ExecutionException, InterruptedException {

        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put(Constant.THEME_KEY, imageSearchData.keyword);
        queryParams.put(Constant.IMAGES_COUNT_KEY, "1000");
        queryParams.put(Constant.COLOR_KEY, String.format("#%06X", (0xFFFFFF & imageSearchData.color)));

        ApiClientRequest request = new ApiClientRequest(Uri.parse(Constant.PEXELS_SEARCH_URL), null, queryParams);

        ApiClientResponse<PexelImageSearchResponseDto> response = getAsync(PexelImageSearchResponseDto.class, request).get();

        return response.data.images;
    }

    public Bitmap getImage(String imageUrl) throws IOException, ExecutionException, InterruptedException {
        ApiClientRequest request = new ApiClientRequest(Uri.parse(imageUrl), null, null);

        ApiClientResponse<Bitmap> response = getAsync(Bitmap.class, request).get();

        return response.data;
    }
}

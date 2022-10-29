package mate.kiss.mixandfind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ImageSearchActivity extends AppCompatActivity {

    PexelsApiClient pexelsApiClient;
    ImageView imageView;
    Intent triggerIntent;
    EditText keyWordInput;
    List<PexelsPhotoDto> images;
    int currentImageIndex = -1;
    int totalResult;
    String currentKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);

        triggerIntent = getIntent();
        imageView = findViewById(R.id.imageView);
        keyWordInput = findViewById(R.id.imageSearchKeywordInput);
        pexelsApiClient = new PexelsApiClient();
        images = new ArrayList<>();
    }

    public void handleGetNewImagePushed(View view) {
        int color = triggerIntent.getIntExtra(Constant.COLOR_ITEM_COLOR_CODE_KEY, 0);
        String keyword = keyWordInput.getText().toString();

        ImageSearchData imageSearchData = new ImageSearchData();
        imageSearchData.color = color;
        imageSearchData.keyword = keyword;

        try {
            currentImageIndex++;
            if (images.size() == 0 || !currentKeyword.equals(keyWordInput.getText().toString())) {
                images = pexelsApiClient.searchImagesAsync(imageSearchData);

                totalResult = images.size();
                currentKeyword = keyword;
            }

            if (totalResult > currentImageIndex) {
                String imageUrl = images.get(currentImageIndex).imageSources.get(Constant.PEXEL_IMAGE_SIZE_TINY_KEY);

                Bitmap image = pexelsApiClient.getImage(imageUrl);

                imageView.setImageBitmap(image);
            }

        } catch (ExecutionException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
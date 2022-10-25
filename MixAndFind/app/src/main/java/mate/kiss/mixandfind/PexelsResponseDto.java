package mate.kiss.mixandfind;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PexelsResponseDto {
    @SerializedName("total_results")
    public int totalResults;
    @SerializedName("page")
    public int pageNumber;
    @SerializedName("per_page")
    public int imagesReturned;
    @SerializedName("photos")
    public ArrayList<PexelsPhotoDto> images;
    @SerializedName("next_page")
    public String nextPage;
}

package mate.kiss.mixandfind;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class PexelsPhotoDto {
    @SerializedName("id")
    public int id;
    @SerializedName("width")
    public int width;
    @SerializedName("height")
    public int height;
    @SerializedName("url")
    public String url;
    @SerializedName("photographer")
    public String photographerName;
    @SerializedName("photographer_url")
    public String photographerPexelSite;
    @SerializedName("photographer_id")
    public int photographerid;
    @SerializedName("avg_color")
    public String avgColor;
    @SerializedName("src")
    public HashMap<String, String> imageSources;
    @SerializedName("liked")
    public boolean isLiked;
    @SerializedName("alt")
    public String alternativeString;
}

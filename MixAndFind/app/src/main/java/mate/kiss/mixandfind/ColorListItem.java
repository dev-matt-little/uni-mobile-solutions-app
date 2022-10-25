package mate.kiss.mixandfind;

import java.util.UUID;

public class ColorListItem {
    public String name;
    public int colorCode;
    public String itemId;

    public ColorListItem(String name, int colorCode) {
        this.name = name;
        this.colorCode = colorCode;
        itemId = UUID.randomUUID().toString();
    }
}

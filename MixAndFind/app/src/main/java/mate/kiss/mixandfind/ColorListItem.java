package mate.kiss.mixandfind;

import java.util.UUID;

public class ColorListItem {
    public String name;
    public String colorCode;
    public String itemId;

    public ColorListItem(String name, String colorCode) {
        this.name = name;
        this.colorCode = colorCode;
        itemId = UUID.randomUUID().toString();
    }
}

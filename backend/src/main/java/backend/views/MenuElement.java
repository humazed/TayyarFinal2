package backend.views;

/**
 * Created by Muhammad Saeed on 4/5/2017.
 */

public class MenuElement {
    public String categoryName;
    public Long categoryID;
    public String imageURL;

    public MenuElement(Long categoryID, String categoryName, String imageURL) {
        this.categoryName = categoryName;
        this.categoryID = categoryID;
        this.imageURL =imageURL;
    }
}

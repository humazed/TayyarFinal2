package backend.helpers.enums;

/**
 * Created by Muhammad on 19/08/2017.
 */

public enum MerchantsOrderBy {
    PRICING("pricing"),RATING("rating"),ACTIVE("active"),LOCATION("location");

    private final String text;
    private MerchantsOrderBy(final String text) {
        this.text = text;
    }

    public String getMarked() {
        return text;
    }
}

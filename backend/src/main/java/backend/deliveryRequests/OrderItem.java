package backend.deliveryRequests;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad on 19/08/2017.
 */

public class OrderItem {
    Long itemId;
    List<Long> choicesIds = new ArrayList<>();
    short number ;
    String itemInstructions;

    public OrderItem(Long itemId, List<Long> choicesIds, short number, String itemInstructions) {
        this.itemId = itemId;
        this.choicesIds = choicesIds;
        this.number = number;
        this.itemInstructions = itemInstructions;
    }

    //for merchants that don't have options nor choices
    public OrderItem(Long itemId, short number, String itemInstructions) {
        this.itemId = itemId;
        this.number = number;
        this.itemInstructions = itemInstructions;
    }

    public OrderItem(Long itemId, Long choiceId, short number,String itemInstructions) {
        this.itemId = itemId;
        this.number = number;
        this.choicesIds.add(choiceId);
        this.itemInstructions = itemInstructions;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemId=" + itemId +
                ", number=" + number +
                ", itemInstructions='" + itemInstructions + '\'' +
                '}';
    }

}

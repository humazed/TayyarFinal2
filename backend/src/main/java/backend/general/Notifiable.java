package backend.general;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad on 19/08/2017.
 */

public interface Notifiable {
    List<String> regTokenList = new ArrayList<>();

    void addRegToken(String regToken);

    void removeRegToken(String regToken);

    List<String> getRegTokenList();
}

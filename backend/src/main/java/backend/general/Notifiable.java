package backend.general;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad on 19/08/2017.
 */

public interface Notifiable {
    public List<String> regTokenList = new ArrayList<>();
    public void addRegToken(String regToken);
    public void removeRegToken(String regToken);
}

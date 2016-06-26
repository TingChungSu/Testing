package myvideoview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鼎鈞 on 2016/6/23.
 */
public class PlayList {

    static private List<SourceData> myList = null;

    public PlayList() {
        myList = new ArrayList<>();
    }
    public List<SourceData> getList(){
        return myList;
    }

    public void addToBot(SourceData data) {
        myList.add(data);
    }

    public void addToTop(SourceData data) {
        myList.add(0, data);
    }

}

package myvideoview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鼎鈞 on 2016/6/23.
 */
public class PlayList {

    static public List<SourceData> myList = null;

    public PlayList() {
        myList = new ArrayList<>();
    }

    public void addToBot(SourceData data) {
        myList.add(data);
    }

    public void addToTop(SourceData data) {
        myList.add(0, data);
    }

}

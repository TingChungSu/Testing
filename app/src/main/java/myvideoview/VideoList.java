package myvideoview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鼎鈞 on 2016/6/23.
 */
public final class VideoList {

    static public List<SourceData> testList= new ArrayList<>();

    public void addToBot(SourceData data){
        testList.add(data);
    }
    public void addToTop(SourceData data){
        testList.add(0,data);
    }

}

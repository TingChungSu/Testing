package myvideoview;


/**
 * Created by 鼎鈞 on 2016/6/23.
 * Download/RivaGreen/to-01.jpg ~ to-14.jpg
 * Download\RivaGreen\done.mpg
 */

public class SourceData {
    private String strImei;
    private String strPlayCode;
    private int intFileSeq;
    private String strFileName;
    private String strFileType;
    private int screenNum;
    private String StrPath;

    //dataType: video && image
    //num: screennum 0/1
    public SourceData(String dataType, String path) {
        if (dataType.trim().toLowerCase().equals("video"))
            this.strFileType = "video";
        else
            this.strFileType = "image";

        this.StrPath = path;
    }

    public boolean isVedio() {
        return this.strFileType.equals("video");
    }

    public boolean isImage() {
        return this.strFileType.equals("image");
    }

    public int getScreenNum() {
        return this.screenNum;
    }

    public String getPath() {
        return this.StrPath;
    }

}

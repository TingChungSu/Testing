package autorun;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by 鼎鈞 on 2016/6/26.
 */
public class BootService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

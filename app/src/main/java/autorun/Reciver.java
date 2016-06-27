package autorun;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.test.MainActivity;

/**
 * Created by 鼎鈞 on 2016/6/26.
 */
public class Reciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(context.KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
            keyguardLock.disableKeyguard();
        /*
            Intent serviceIntent = new Intent(context,BootService.class);
            context.startService(serviceIntent);
        */

            Intent activityIntent = new Intent(context, MainActivity.class);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(activityIntent);

/*
            DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
            Display[] logicDisplays = dm.getDisplays();
            for (Display dp : logicDisplays) {
                ContextWrapper ctw = new ContextWrapper(context);
                Context myC = ctw.createDisplayContext(dp);
                //Toast.makeText(context, "dp:" + dp.getDisplayId(), Toast.LENGTH_LONG).show();
                if(dp.getDisplayId() == 1)
                    activityIntent = new Intent(myC, MainActivity.class);
                else
                    activityIntent = new Intent(myC, Main2Activity.class);
                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myC.startActivity(activityIntent);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
*/
        }
    }
}

package com.example.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;

import myvideoview.MyVideo;

public class MainActivity extends AppCompatActivity {
    private int[] resourceID;
    private String[] picPath;
    public int picNum;
    private ImageView myImage;
    private MyVideo MyVideo;
    private boolean isVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        DisplayManager dm = (DisplayManager) getSystemService(DISPLAY_SERVICE);
        Display[] displays = dm.getDisplays();
        for(Display display : displays){
            if(display.equals(dm.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION))){

            }
        }
*/
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        isVideo = false;

        myImage = (ImageView) findViewById(R.id.myImageView1);
        MyVideo = (MyVideo) findViewById(R.id.myVideoView1);
        picNum = 0;
        resourceID = new int[]{R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5,
                R.drawable.no2, R.drawable.no3, R.drawable.no4, R.drawable.no5};
        picPath = new String[]{"/sdcard/Download/RivaGreen/to-01.jpg", "/sdcard/Download/RivaGreen/to-02.jpg",
                "/sdcard/Download/RivaGreen/to-03.jpg", "/sdcard/Download/RivaGreen/to-04.jpg",
                "/sdcard/Download/RivaGreen/to-05.jpg", "/sdcard/Download/RivaGreen/to-06.jpg",
                "/sdcard/Download/RivaGreen/to-07.jpg", "/sdcard/Download/RivaGreen/to-08.jpg",
                "/sdcard/Download/RivaGreen/to-09.jpg", "/sdcard/Download/RivaGreen/to-10.jpg",
                "/sdcard/Download/RivaGreen/to-11.jpg", "/sdcard/Download/RivaGreen/to-12.jpg",
                "/sdcard/Download/RivaGreen/to-13.jpg", "/sdcard/Download/RivaGreen/to-14.jpg",  };

    }

    @Override
    protected void onResume() {
        /**
         * 強制設為橫屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        switchToImage();
    }

    private void showScreen() {
        DisplayManager dm = (DisplayManager) getSystemService(DISPLAY_SERVICE);
        Display[] displays = dm.getDisplays();
        int i = 0;
        for (Display display : displays) {
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            Toast.makeText(this, "screen no : " + i + "  " + width + "  " + height, Toast.LENGTH_LONG).show();
            i++;
        }
    }

    private void showData() {
        TelephonyManager tM = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tM.getDeviceId();

        String android_id = Settings.Secure.getString(this.getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        String strtoast = "";
        if (imei != null) strtoast = strtoast + "imei: " + imei;
        if (android_id != null) strtoast = strtoast + "\nandriod id: " + android_id;
        if (wifiInfo != null) strtoast = strtoast + "\nwifi mac: " + wifiInfo.getMacAddress();
        Toast.makeText(this, strtoast, Toast.LENGTH_LONG).show();
    }

    private void switchToVideo() {
        isVideo = true;
        MyVideo.setVisibility(View.VISIBLE);
        myImage.setVisibility(View.GONE);
    }

    private void switchToImage() {
        isVideo = false;
        myImage.setVisibility(View.VISIBLE);
        MyVideo.setVisibility(View.GONE);
    }

    private void setSDCardPicture(ImageView iv, String picPath) {
        Bitmap bitmap = BitmapFactory.decodeFile(picPath);
        iv.setImageBitmap(bitmap);
    }

    private void imageFadeIn() {
        Animation am = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_fadein);
        am.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                if (picNum >= picPath.length) picNum = 0;
                String path = picPath[picNum++];
                File file = new File(path);
                if (file.exists()) {
                    setSDCardPicture(myImage, path);
                }
                //myImage.setImageResource(resourceID[number++]);
                Animation am2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_fadeout);
                myImage.startAnimation(am2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
        myImage.startAnimation(am);
    }

    private static Bitmap getBitmapFromSDCard(String file) {
        try {
            String sd = Environment.getExternalStorageDirectory().toString();
            Bitmap bitmap = BitmapFactory.decodeFile(sd + "/" + file);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void imageMove() {
        Animation am = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_midtoright);
        am.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                if (picNum >= resourceID.length) picNum = 0;
                myImage.setImageResource(resourceID[picNum++]);
                Animation am2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_lefttomid);
                myImage.startAnimation(am2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
        myImage.startAnimation(am);
    }

    private void setVideo() {
        String path = "/sdcard/Download/RivaGreen/done.mpg";
        File file = new File(path);
        if (file.exists()) {
            MyVideo.setVideoPath(file.getAbsolutePath());
            MyVideo.start();
        } else Toast.makeText(this, path + "\nfile not exists! ", Toast.LENGTH_SHORT).show();

        path = "android.resource://" + getPackageName() + "/" + R.raw.thelittleprince;
        switchToVideo();

        Uri uri = Uri.parse(path);
        /*
        File file = new File(path);
        if (!file.exists()) {
        Toast.makeText(this, "file not exists! " + path, Toast.LENGTH_SHORT).show();
        }*/

        //MyVideo.setVideoURI(uri);
        //MyVideo.start();
    }

    private void fuckOff() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void onClick(View view) {
        try {
            int id = view.getId();
            if (id == R.id.Button_show) {
                showData();
            } else if (id == R.id.Button_video) {
                if (!isVideo) setVideo();
                else switchToImage();
            } else if (id == R.id.Button_fadein) {
                imageFadeIn();
            } else if (id == R.id.Button_moveright) {
                imageMove();
            } else if (id == R.id.Button_test) {
                showScreen();
            } else if (id == R.id.Button_exit) {
                fuckOff();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.example.test;

import android.app.Presentation;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
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

import java.io.File;
import java.lang.ref.WeakReference;

import myvideoview.*;
import pager.*;

public class MainActivity extends AppCompatActivity {
    private static int pageInterval = 500;
    private int[] resourceID;
    private String[] picPath;
    private String[] videoPath;
    public int picNum;
    private ImageView myImage;
    private MyVideo myVideo;
    private boolean isVideo;
    private static AutoScrollViewPager myPager;
    private static Handler handler;

    public static PlayList myPlayList;
    public static int sourceNum;
    private DisplayManager mDisplayManager;
    private Display[] displays;
    private SecondScreenPresentation secondScreenPresentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set full screen with no title
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //set resource info
        sourceNum = 0;
        picNum = 0;
        resourceID = new int[]{R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5,
                R.drawable.no2, R.drawable.no3, R.drawable.no4, R.drawable.no5};
        picPath = new String[]{"日本展用1280x320 to-01.jpg", "日本展用1280x320 to-02.jpg",
                "日本展用1280x320 to-03.jpg", "日本展用1280x320 to-04.jpg",
                "日本展用1280x320 to-05.jpg", "日本展用1280x320 to-06.jpg",
                "日本展用1280x320 to-07.jpg", "日本展用1280x320 to-08.jpg",
                "日本展用1280x320to-09.jpg", "日本展用1280x320to-10.jpg",
                "日本展用1280x320to-11.jpg", "日本展用1280x320to-12.jpg",
                "日本展用1280x320to-13.jpg", "日本展用1280x320to-14.jpg"};/**/
        videoPath = new String[]{"/sdcard/Download/Demo video_ACME/MUJI/muji01_1280x320.avi", "/sdcard/Download/Demo video_ACME/MUJI/muji02_1280x320.avi"};

        myPlayList = new PlayList();
        for (String path : picPath) {
            SourceData data = new SourceData("image", "/sdcard/Download/RivaGreen/" + path);
            myPlayList.addToBot(data);
        }
        for (String path : videoPath) {
            SourceData data = new SourceData("video", path);
            myPlayList.addToBot(data);
        }
//
        setupBlankPresentation();
        if(isDualScreenAvilable())
            secondScreenPresentation.show();

        isVideo = false;
        myImage = (ImageView) findViewById(R.id.myImageView);
        myVideo = (MyVideo) findViewById(R.id.myVideoView);

        // setAutoScroll
        myPager = (AutoScrollViewPager) findViewById(R.id.myViewPager);
        handler = new MyHandler(myPager);
        myPager.setAdapter(new MyPagerAdapter(this, myPlayList));
        myPager.setCurrentItem(0);
        myPager.setInterval(pageInterval);
        MainActivity.sendMessage(0, 0);
    }

    public boolean isDualScreenAvilable() {
        DisplayManager dm = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        Display[] logicDisplays = dm.getDisplays();
        return logicDisplays.length > 0;
    }

    private void setupBlankPresentation() {
        mDisplayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        displays = mDisplayManager
                .getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
        if (displays.length != 0)
            secondScreenPresentation = new SecondScreenPresentation(this, displays[0]);
    }
    public class SecondScreenPresentation extends Presentation {

        public SecondScreenPresentation(Context outerContext, Display display) {
            super(outerContext, display);
            // TODO Auto-generated constructor stub

/*
        Intent activityIntent = new Intent(outerContext, Main2Activity.class);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        outerContext.startActivity(activityIntent);
*/
        }
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);
        }

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
        myVideo.setVisibility(View.VISIBLE);
        //myPager.setVisibility(View.GONE);
        //myImage.setVisibility(View.GONE);
    }

    private void switchToPager() {
        isVideo = false;
        myVideo.setVisibility(View.GONE);
        //myPager.setVisibility(View.VISIBLE);
        //myImage.setVisibility(View.GONE);
    }

    private void switchToImage() {
        isVideo = false;
        myImage.setVisibility(View.VISIBLE);
        myVideo.setVisibility(View.GONE);
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

    private void setVideo(String path) {
        //String path = "/sdcard/Download/Demo_video_ACME/Asics/Asics_left.mp4";
        File file = new File(path);
        if (file.exists()) {
            myVideo.setVideoPath(file.getAbsolutePath());
            myVideo.start();
            myVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    switchToPager();
                    MainActivity.sendMessage(1, 0);
                }
            });
            switchToVideo();
        } else Toast.makeText(this, path + "\nfile not exists! ", Toast.LENGTH_SHORT).show();
    }


    private class MyHandler extends Handler {

        private final WeakReference<AutoScrollViewPager> autoScrollViewPager;

        public MyHandler(AutoScrollViewPager autoScrollViewPager) {
            this.autoScrollViewPager = new WeakReference<AutoScrollViewPager>(autoScrollViewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:// initial case
                    if (myPlayList.getList().get(sourceNum).isVedio()) {
                        setVideo(myPlayList.getList().get(sourceNum).getPath());
                        //MainActivity.sendMessage(1, pageInterval);
                    } else if (myPlayList.getList().get(sourceNum).isImage()) {
                        AutoScrollViewPager pager = this.autoScrollViewPager.get();
                        if (pager != null) {
                            MainActivity.sendMessage(1, pageInterval);
                        }
                    }
                    break;
                case 1:// change view
                    sourceNum++;
                    if (sourceNum >= myPlayList.getList().size()) sourceNum = 0;
                    AutoScrollViewPager pager = this.autoScrollViewPager.get();
                    if (pager != null) {
                        myPager.getScroller().setScrollDurationFactor(1);
                        myPager.scrollOnce();
                        myPager.getScroller().setScrollDurationFactor(1);
                        MainActivity.sendMessage(0, 0);
                    }

                case 2:// play image

                default:
                    break;
            }
        }
    }

    private static void sendMessage(int msg, long delayTimeInMills) {
        /** remove messages before, keeps one message is running at most **/
        handler.removeMessages(msg);
        handler.sendEmptyMessageDelayed(msg, delayTimeInMills);
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
                //if (!isVideo) setVideo();
                //else switchToImage();
            } else if (id == R.id.Button_fadein) {
                imageFadeIn();
            } else if (id == R.id.Button_moveright) {
                myImage.setVisibility(View.GONE);
                myVideo.setVisibility(View.GONE);
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

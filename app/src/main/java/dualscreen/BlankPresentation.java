package dualscreen;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;

import com.example.test.R;

/**
 * Created by 鼎鈞 on 2016/6/27.
 */
public class BlankPresentation  extends Presentation {

    public BlankPresentation(Context outerContext, Display display) {
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

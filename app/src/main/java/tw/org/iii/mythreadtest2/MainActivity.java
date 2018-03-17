package tw.org.iii.mythreadtest2;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView tv, tv2;
    private UIHandler handler;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();
        handler = new UIHandler(this);
        tv = findViewById(R.id.tv);
        tv2 = findViewById(R.id.tv2);
    }

    public void test1(View view){
        timer.schedule(new MyTask(), 0, 1*1000);
    }

    public void test2(View view) {
        Log.v("brad", "test2:before");
        tv.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.v("brad", "test2:run()");
                tv.setText("Hello, Brad");
                tv2.setText("Hello, Brad2");
            }
        }, 3*1000);
        Log.v("brad", "test2:after");
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            Log.v("brad", "MyTask:run()");
            handler.sendEmptyMessage(0);
        }
    }

    private class UIHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public UIHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.v("brad", "UIHandler:handleMessage()");
            MainActivity activity = mActivity.get();
            if (activity != null) {
                Log.v("brad", "UIHandler:handleMessage():tv");
                activity.tv.setText("" + (int) (Math.random() * 49 + 1));
            }else{
                Log.v("brad", "activity is null");
            }
        }
    }

//    @Override
//    public void finish() {
//        if (timer != null){
//            timer.cancel();
//            timer = null;
//        }
//        super.finish();
//    }
}

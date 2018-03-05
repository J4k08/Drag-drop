package com.example.jakobhaglof.draganddroptest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private int _xDelta;
    private int _yDelta;
    private ViewGroup rootLayout;
    private ImageView img, img2, img3;
    private LinearLayout ll1, ll2, ll3;
    private TextView t1, t2, t3;
    ImageView[] imgs;
    LinearLayout[] layouts;
    private int clicks = 0;

    /** Called when the activity is first created. */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout = findViewById(R.id.view_root);

        t1 = findViewById(R.id.text1);
        t2 = findViewById(R.id.text2);
        t3 = findViewById(R.id.text3);


        setup();

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,100);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(100,100);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(100,100);

        ll1.setLayoutParams(layoutParams);
        ll2.setLayoutParams(layoutParams2);
        ll3.setLayoutParams(layoutParams3);
    }

    private void setup() {

        setupImgs();
        setupViews();
        setupText();
    }

    private void setupText() {

    }

    private void setupViews() {

        ll1 = findViewById(R.id.myView1);
        ll2 = findViewById(R.id.myView2);
        ll3 = findViewById(R.id.myView3);

        layouts = new LinearLayout[3];
        layouts[0] = ll1;
        layouts[1] = ll2;
        layouts[2] = ll3;

        for (LinearLayout layout : layouts) {

            layout.setOnTouchListener(new ChoiceTouchListener());
        }
    }

    private void setupImgs() {
        img = rootLayout.findViewById(R.id.myimage1);
        img2 = rootLayout.findViewById(R.id.myimage2);
        img3 = rootLayout.findViewById(R.id.myimage3);

        imgs = new ImageView[3];
        imgs[0] = img;
        imgs[1] = img2;
        imgs[2] = img3;

        for (ImageView img1 : imgs) {

            img1.setAlpha(0.7f);
        }


    }
    private final class ChoiceTouchListener implements View.OnTouchListener {


        public boolean onTouch(View view, MotionEvent motionEvent) {
            final int X = (int) motionEvent.getRawX();
            final int Y = (int) motionEvent.getRawY();

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParaams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParaams.leftMargin;
                _yDelta = Y - lParaams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    clicks++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(clicks == 2) {
                                Toast.makeText(MainActivity.this,"Double clickz", Toast.LENGTH_LONG).show();
                            }
                            clicks = 0;
                        }
                    },500);
                    Log.d("motion", "up up up");
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    Log.d("motion", "down down down");
                    break;

                case MotionEvent.ACTION_POINTER_UP:
                    Log.d("motion", "pointer whaat?");
                    break;

                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();

                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            rootLayout.invalidate();
            return true;
        }
    }

}
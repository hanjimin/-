package com.example.sec.woodongsa.evaluation;

import android.content.Context;
import android.gesture.Gesture;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by sec on 2015-07-21.
 */
public class OnSwipeTouchListener implements View.OnTouchListener {

    public GestureDetector gestureDetector;
    public Context context;

    public OnSwipeTouchListener(Context context){
        gestureDetector = new GestureDetector(context, new GestureListener());
        this.context = context;
    }

    public void onSwipeLeft(){

    }
    public void onSwipeRight(){

    }
    public boolean onTouch(View v, MotionEvent event){
        return gestureDetector.onTouchEvent(event);
    }
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener{
        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e){

            return true;
        }
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //Your method to zoom the imageview
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();

            if(Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD
                    && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD){
                if(distanceX > 0){
                    onSwipeRight();
                } else {
                    onSwipeLeft();
                }
                return true;
            }
            return false;
        }
    }
}

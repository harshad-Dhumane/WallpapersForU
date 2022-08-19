package com.example.wallpapersforu.AndroidContants;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

public class PunchZoomInOut {
        private ScaleGestureDetector scaleGestureDetector;
        private float mScaleFactor = 1.0f;
        private ImageView imageView;
        private Context context;
        Activity activity;

    public PunchZoomInOut(ImageView imageView, Context context) {
        this.imageView = imageView;
        this.context = context;
        activity = (Activity)context;
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public boolean myOnTouchEvent(MotionEvent motionEvent) {
            scaleGestureDetector.onTouchEvent(motionEvent);
            return true;
        }
        private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
            @Override
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                mScaleFactor *= scaleGestureDetector.getScaleFactor();
                mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
                imageView.setScaleX(mScaleFactor);
                imageView.setScaleY(mScaleFactor);
                return true;
            }
        }
    }


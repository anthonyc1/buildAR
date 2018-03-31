/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.endercrest.arbuild.common.helpers;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Helper to detect taps using Android GestureDetector, and pass the taps between UI thread and
 * render thread.
 */
public final class TapHelper implements OnTouchListener {
  private final GestureDetector gestureDetector;
  private final BlockingQueue<MotionEvent> queuedSingleTaps = new ArrayBlockingQueue<>(16);



  enum Direction {
      LEFT,
      RIGHT,
      NONE,
  }

  /**
   * Creates the tap helper.
   *
   * @param context the application's context.
   */
  public TapHelper(Context context) {
      gestureDetector =
        new GestureDetector(
            context,
            new GestureDetector.SimpleOnGestureListener() {
              @Override
              public boolean onSingleTapUp(MotionEvent e) {
                // Queue tap if there is space. Tap is lost if queue is full.
                queuedSingleTaps.offer(e);
                return true;
              }

              @Override
              public boolean onDown(MotionEvent e) {
                return true;
              }

              @Override
              public boolean onFling(MotionEvent start, MotionEvent end, float velocityX, float velocityY) {
                //System.out.println(start.toString() + "     " + end.toString());
                //System.out.println(velocityX + "     " + velocityY);

                float yDirection = start.getY() - end.getY();
                float xDirection = start.getX() - end.getX();
                // Threshold for vertical.
                  if(Math.abs(yDirection) > 50) {
                      return true;
                  }

                if (xDirection > 0) {
                    System.out.println("Left");
                } else {
                    System.out.println("Right");
                }
                return true;
              }
            });
  }

  /**
   * Polls for a tap.
   *
   * @return if a tap was queued, a MotionEvent for the tap. Otherwise null if no taps are queued.
   */
  public MotionEvent poll() {
    return queuedSingleTaps.poll();
  }

    /**
     *
     * @return Returns a number below zero for left, right if above zero.
     */
  //public int swipe() {
  //}

  @Override
  public boolean onTouch(View view, MotionEvent motionEvent) {
    return gestureDetector.onTouchEvent(motionEvent);
  }
}

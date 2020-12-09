package com.reactnativetxlive;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.reactnativetxlive.R;


class TxlivePusherView extends FrameLayout {
  public LivePusherView mLivePusherView;

  public TxlivePusherView(Context context) {
    super(context);
    init(context);
  }

  public TxlivePusherView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public TxlivePusherView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

}

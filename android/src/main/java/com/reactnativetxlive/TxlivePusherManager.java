package com.reactnativetxlive;

import androidx.annotation.NonNull;

import android.view.SurfaceView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.views.image.ImageResizeMode;

import com.reactnativetxlive.R;

import com.tencent.liteav.demo.livepusher.camerapush.ui.CameraPushMainView;

import java.util.Map;

public class TxlivePusherManager extends SimpleViewManager<TxlivePusherView> {

  ReactApplicationContext mCallerContext;
  CameraPushMainView mCameraPushMainView;

  public TxlivePusherManager(ReactApplicationContext reactContext) {
    mCallerContext = reactContext;
  }

  @Override
  public String getName() {
    return "RCTTxlivePusherView";
  }

  @NonNull
  @Override
  protected TxlivePusherView createViewInstance(@NonNull ThemedReactContext reactContext) {
    return new TxlivePusherView(reactContext);
  }

  @ReactProp(name = "showVideoView", defaultBoolean = false)
  public void setShowVideoView(final TxlivePusherView txlivePusherView, boolean showVideoView) {
    if (showVideoView) {
      txlivePusherView.onReceiveNativeEvent("showVideoView", 1);
    }
  }

  @ReactProp(name = "url")
  public void setUrl(final TxlivePusherView txlivePusherView, String url) {
    mCameraPushMainView = txlivePusherView.getCameraPushMainView();
    if (!url.isEmpty()) {
      mCameraPushMainView.mPusherURL = url;
      mCameraPushMainView.stopPush();
      mCameraPushMainView.startPush();
      txlivePusherView.onReceiveNativeEvent("setUrl", 1);
    }
  }


  @ReactProp(name = "startPlay", defaultBoolean = false)
  public void setStartPlay(final TxlivePusherView txlivePusherView, boolean startPlay) {
    mCameraPushMainView = txlivePusherView.getCameraPushMainView();
    if (startPlay) {
      txlivePusherView.onReceiveNativeEvent("startPlay", 1);
    }
  }

  @ReactProp(name = "pausePlay", defaultBoolean = false)
  public void setPausePlay(final TxlivePusherView txlivePusherView, boolean pausePlay) {
    mCameraPushMainView = txlivePusherView.getCameraPushMainView();
    if (pausePlay) {
      txlivePusherView.onReceiveNativeEvent("pausePlay", 1);
    }
  }

  @ReactProp(name = "stopPlay", defaultBoolean = false)
  public void setStopPlay(final TxlivePusherView txlivePusherView, boolean stopPlay) {
    mCameraPushMainView = txlivePusherView.getCameraPushMainView();
    if (stopPlay) {
      txlivePusherView.onReceiveNativeEvent("stopPlay", 1);
    }
  }

  @ReactProp(name = "destroyPlay", defaultBoolean = false)
  public void setDestroyPlay(final TxlivePusherView txlivePusherView, boolean destroyPlay) {
    mCameraPushMainView = txlivePusherView.getCameraPushMainView();
    if (destroyPlay) {
      txlivePusherView.onReceiveNativeEvent("destroyPlay", 1);
    }
  }

}

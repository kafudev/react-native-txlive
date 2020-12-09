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

import com.tencent.liteav.demo.liveplayer.ui.LivePlayerMainView;

import com.reactnativetxlive.R;

import java.util.Map;

public class TxlivePlayerManager extends SimpleViewManager<TxlivePlayerView> {

  ReactApplicationContext mCallerContext;
  LivePlayerMainView mLivePlayerMainView;

  public TxlivePlayerManager(ReactApplicationContext reactContext) {
    mCallerContext = reactContext;
  }

  @Override
  public String getName() {
    return "RCTTxlivePlayerView";
  }

  @NonNull
  @Override
  protected TxlivePlayerView createViewInstance(@NonNull ThemedReactContext reactContext) {
    return new TxlivePlayerView(reactContext);
  }
  @ReactProp(name = "showVideoView", defaultBoolean = false)
  public void setShowVideoView(final TxlivePlayerView txlivePlayerView, boolean showVideoView) {
    mLivePlayerMainView = txlivePlayerView.getLivePlayerMainView();
    if (showVideoView) {
      txlivePlayerView.onReceiveNativeEvent("showVideoView", 1);
    }
  }

  @ReactProp(name = "url")
  public void setUrl(final TxlivePlayerView txlivePlayerView, String url) {
    mLivePlayerMainView = txlivePlayerView.getLivePlayerMainView();
    if (!url.isEmpty()) {
      mLivePlayerMainView.initialize(url);
      txlivePlayerView.onReceiveNativeEvent("setUrl", 1);
    }
  }

  @ReactProp(name = "startPlay", defaultBoolean = false)
  public void setStartPlay(final TxlivePlayerView txlivePlayerView, boolean startPlay) {
    mLivePlayerMainView = txlivePlayerView.getLivePlayerMainView();
    if (startPlay) {
      txlivePlayerView.onReceiveNativeEvent("startPlay", 1);
    }
  }

  @ReactProp(name = "pausePlay", defaultBoolean = false)
  public void setPausePlay(final TxlivePlayerView txlivePlayerView, boolean pausePlay) {
    mLivePlayerMainView = txlivePlayerView.getLivePlayerMainView();
    if (pausePlay) {
      txlivePlayerView.onReceiveNativeEvent("pausePlay", 1);
    }
  }

  @ReactProp(name = "stopPlay", defaultBoolean = false)
  public void setStopPlay(final TxlivePlayerView txlivePlayerView, boolean stopPlay) {
    mLivePlayerMainView = txlivePlayerView.getLivePlayerMainView();
    if (stopPlay) {
      txlivePlayerView.onReceiveNativeEvent("stopPlay", 1);
    }
  }

  @ReactProp(name = "destroyPlay", defaultBoolean = false)
  public void setDestroyPlay(final TxlivePlayerView txlivePlayerView, boolean destroyPlay) {
    mLivePlayerMainView = txlivePlayerView.getLivePlayerMainView();
    if (destroyPlay) {
      txlivePlayerView.onReceiveNativeEvent("destroyPlay", 1);
    }
  }

}

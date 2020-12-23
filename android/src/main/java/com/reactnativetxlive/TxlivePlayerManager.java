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

import com.tencent.liteav.sdk.liveplayer.LivePlayer;

import com.reactnativetxlive.R;

import java.util.Map;

public class TxlivePlayerManager extends SimpleViewManager<TxlivePlayerView> {

  ReactApplicationContext mCallerContext;
  LivePlayer mLivePlayer;

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

  @ReactProp(name = "url")
  public void setUrl(final TxlivePlayerView txlivePlayerView, String url) {
    mLivePlayer = txlivePlayerView.getLivePlayer();
    if (!url.isEmpty()) {
      mLivePlayer.startPlay(url);
      txlivePlayerView.onReceiveNativeEvent("setUrl", 1);
    }
  }

  @ReactProp(name = "startPlay", defaultBoolean = false)
  public void setStartPlay(final TxlivePlayerView txlivePlayerView, boolean startPlay) {
    mLivePlayer = txlivePlayerView.getLivePlayer();
    if (startPlay) {
      mLivePlayer.startPlay("");
      txlivePlayerView.onReceiveNativeEvent("startPlay", 1);
    }
  }

  @ReactProp(name = "pausePlay", defaultBoolean = false)
  public void setPausePlay(final TxlivePlayerView txlivePlayerView, boolean pausePlay) {
    mLivePlayer = txlivePlayerView.getLivePlayer();
    if (pausePlay) {
      mLivePlayer.pausePlay();
      txlivePlayerView.onReceiveNativeEvent("pausePlay", 1);
    }
  }

  @ReactProp(name = "resumePlay", defaultBoolean = false)
  public void setResumePlay(final TxlivePlayerView txlivePlayerView, boolean resumePlay) {
    mLivePlayer = txlivePlayerView.getLivePlayer();
    if (resumePlay) {
      mLivePlayer.resumePlay();
      txlivePlayerView.onReceiveNativeEvent("resumePlay", 1);
    }
  }

  @ReactProp(name = "stopPlay", defaultBoolean = false)
  public void setStopPlay(final TxlivePlayerView txlivePlayerView, boolean stopPlay) {
    mLivePlayer = txlivePlayerView.getLivePlayer();
    if (stopPlay) {
      mLivePlayer.stopPlay();
      txlivePlayerView.onReceiveNativeEvent("stopPlay", 1);
    }
  }

  @ReactProp(name = "destroyPlay", defaultBoolean = false)
  public void setDestroyPlay(final TxlivePlayerView txlivePlayerView, boolean destroyPlay) {
    mLivePlayer = txlivePlayerView.getLivePlayer();
    if (destroyPlay) {
      mLivePlayer.destroyPlay();
      txlivePlayerView.onReceiveNativeEvent("destroyPlay", 1);
    }
  }

  @ReactProp(name = "HWDecode", defaultBoolean = false)
  public void setHWDecode(final TxlivePlayerView txlivePlayerView, boolean enable) {
    mLivePlayer = txlivePlayerView.getLivePlayer();
    mLivePlayer.setHWDecode(enable);
  }

  @ReactProp(name = "renderRotation", defaultInt = 0)
  public void setRenderRotation(final TxlivePlayerView txlivePlayerView, int renderRotation) {
    mLivePlayer = txlivePlayerView.getLivePlayer();
    mLivePlayer.setRenderRotation(renderRotation);
  }

  @ReactProp(name = "renderMode", defaultInt = 1)
  public void setRenderMode(final TxlivePlayerView txlivePlayerView, int renderMode) {
    mLivePlayer = txlivePlayerView.getLivePlayer();
    mLivePlayer.setRenderMode(renderMode);
  }

  @ReactProp(name = "log", defaultBoolean = false)
  public void setLog(final TxlivePlayerView txlivePlayerView, boolean enable) {
    mLivePlayer = txlivePlayerView.getLivePlayer();
    mLivePlayer.showLog(enable);
  }
}

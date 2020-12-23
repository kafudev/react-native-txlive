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

import com.tencent.liteav.sdk.livepusher.LivePusher;

import java.util.Map;

public class TxlivePusherManager extends SimpleViewManager<TxlivePusherView> {

  ReactApplicationContext mCallerContext;
  LivePusher mLivePusher;

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

  @ReactProp(name = "url")
  public void setUrl(final TxlivePusherView txlivePusherView, String url) {
    mLivePusher = txlivePusherView.getLivePusher();
    if (!url.isEmpty()) {
      mLivePusher.startPush(url);
      txlivePusherView.onReceiveNativeEvent("setUrl", 1);
    }
  }


  @ReactProp(name = "startPush", defaultBoolean = false)
  public void setStartPush(final TxlivePusherView txlivePusherView, boolean startPush) {
    mLivePusher = txlivePusherView.getLivePusher();
    if (startPush) {
      mLivePusher.startPush("");
      txlivePusherView.onReceiveNativeEvent("startPush", 1);
    }
  }

  @ReactProp(name = "pausePush", defaultBoolean = false)
  public void setPausePush(final TxlivePusherView txlivePusherView, boolean pausePush) {
    mLivePusher = txlivePusherView.getLivePusher();
    if (pausePush) {
      mLivePusher.pausePush();
      txlivePusherView.onReceiveNativeEvent("pausePush", 1);
    }
  }

  @ReactProp(name = "resumePush", defaultBoolean = false)
  public void setResumePush(final TxlivePusherView txlivePusherView, boolean resumePush) {
    mLivePusher = txlivePusherView.getLivePusher();
    if (resumePush) {
      mLivePusher.resumePush();
      txlivePusherView.onReceiveNativeEvent("resumePush", 1);
    }
  }

  @ReactProp(name = "stopPush", defaultBoolean = false)
  public void setStopPush(final TxlivePusherView txlivePusherView, boolean stopPush) {
    mLivePusher = txlivePusherView.getLivePusher();
    if (stopPush) {
      mLivePusher.stopPush();
      txlivePusherView.onReceiveNativeEvent("stopPush", 1);
    }
  }

  @ReactProp(name = "destroyPush", defaultBoolean = false)
  public void setDestroyPush(final TxlivePusherView txlivePusherView, boolean destroyPush) {
    mLivePusher = txlivePusherView.getLivePusher();
    if (destroyPush) {
      mLivePusher.destroyPush();
      txlivePusherView.onReceiveNativeEvent("destroyPush", 1);
    }
  }


  @ReactProp(name = "frontCamera", defaultBoolean = true)
  public void setFrontCamera(final TxlivePusherView txlivePusherView, boolean enable) {
    mLivePusher = txlivePusherView.getLivePusher();
    if(enable){
      mLivePusher.switchCamera();
      txlivePusherView.onReceiveNativeEvent("switchCamera", 1);
    }
  }

  @ReactProp(name = "mirror", defaultBoolean = true)
  public void setMirror(final TxlivePusherView txlivePusherView, boolean enable) {
    mLivePusher = txlivePusherView.getLivePusher();
    mLivePusher.setMirror(enable);
    txlivePusherView.onReceiveNativeEvent("mirror", 1);
  }

  @ReactProp(name = "log", defaultBoolean = false)
  public void setLog(final TxlivePusherView txlivePusherView, boolean enable) {
    mLivePusher = txlivePusherView.getLivePusher();
    mLivePusher.showLog(enable);
  }
}

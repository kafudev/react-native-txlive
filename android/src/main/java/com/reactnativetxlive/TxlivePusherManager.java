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

import java.util.Map;

public class TxlivePusherManager extends SimpleViewManager<TxlivePusherView> {

  ReactApplicationContext mCallerContext;
  SuperPlayerView mSuperPlayerView;

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
  public void setShowVideoView(final TxlivePlayerView TxlivePlayerView, boolean showVideoView) {
    if (showVideoView) {
    }
  }


  @ReactProp(name = "startPlay", defaultBoolean = false)
  public void setStartPlay(final TxlivePlayerView TxlivePlayerView, boolean startPlay) {
    mSuperPlayerView = TxlivePlayerView.getSuperPlayerView();
    if (startPlay) {
      if (mSuperPlayerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PAUSE) {
        mSuperPlayerView.onResume();
        mSuperPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_WINDOW);
        TxlivePlayerView.onReceiveNativeEvent("startPlay onResume", mSuperPlayerView.getPlayMode());
      } else if (mSuperPlayerView.getPlayMode() == SuperPlayerConst.PLAYSTATE_END) {
        mSuperPlayerView.onResume();
        mSuperPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_WINDOW);
        TxlivePlayerView.onReceiveNativeEvent("startPlay onResume", mSuperPlayerView.getPlayMode());
      } else {
        // 重新开始播放
        // 通过URL方式的视频信息配置
        SuperPlayerModel model2 = new SuperPlayerModel();

        // model2.multiURLs = new ArrayList<>();
        // model2.multiURLs.add(new SuperPlayerModel.SuperPlayerURL("http://1252463788.vod2.myqcloud.com/95576ef5vodtransgzp1252463788/e1ab85305285890781763144364/v.f10.mp4", "流畅"));
        // model2.multiURLs.add(new SuperPlayerModel.SuperPlayerURL("http://1252463788.vod2.myqcloud.com/95576ef5vodtransgzp1252463788/e1ab85305285890781763144364/v.f20.mp4", "标清"));
        // model2.multiURLs.add(new SuperPlayerModel.SuperPlayerURL("http://1252463788.vod2.myqcloud.com/95576ef5vodtransgzp1252463788/e1ab85305285890781763144364/v.f30.mp4", "高清"));
        // model2.playDefaultIndex = 1;// 默认播放标清

        model2.appId = 1252463788;
        model2.title = "测试视频-720P";
        model2.url = "http://1252463788.vod2.myqcloud.com/95576ef5vodtransgzp1252463788/68e3febf4564972819220421305/v.f30.mp4";
        mSuperPlayerView.playWithModel(model2);
        mSuperPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_WINDOW);
        TxlivePlayerView.onReceiveNativeEvent("startPlay replay", mSuperPlayerView.getPlayMode());
      }
    }
  }

  @ReactProp(name = "pausePlay", defaultBoolean = false)
  public void setPausePlay(final TxlivePlayerView TxlivePlayerView, boolean pausePlay) {
    mSuperPlayerView = TxlivePlayerView.getSuperPlayerView();
    if (pausePlay) {
      if (mSuperPlayerView.getPlayMode() == SuperPlayerConst.PLAYSTATE_PLAYING) {
        // 暂停播放
        mSuperPlayerView.onPause();
        TxlivePlayerView.onReceiveNativeEvent("pausePlay", mSuperPlayerView.getPlayMode());
      }
    }
  }

  @ReactProp(name = "stopPlay", defaultBoolean = false)
  public void setStopPlay(final TxlivePlayerView TxlivePlayerView, boolean stopPlay) {
    mSuperPlayerView = TxlivePlayerView.getSuperPlayerView();
    if (stopPlay) {
      // 停止播放
      mSuperPlayerView.resetPlayer();
      TxlivePlayerView.onReceiveNativeEvent("stopPlay", mSuperPlayerView.getPlayMode());
    }
  }

  @ReactProp(name = "destroyPlay", defaultBoolean = false)
  public void setDestroyPlay(final TxlivePlayerView TxlivePlayerView, boolean destroyPlay) {
    mSuperPlayerView = TxlivePlayerView.getSuperPlayerView();
    if (destroyPlay) {
      // 销毁
      mSuperPlayerView.resetPlayer();
      mSuperPlayerView.onDestroy();
      TxlivePlayerView.onReceiveNativeEvent("destroyPlay", mSuperPlayerView.getPlayMode());
    }
  }

  public Map getExportedCustomBubblingEventTypeConstants() {
    return MapBuilder.builder()
      .put(
        "topChange",
        MapBuilder.of(
          "phasedRegistrationNames",
          MapBuilder.of("bubbled", "onChange")))
      .build();
  }

//  @ReactProp(name = "src")
//  public void setSrc(TxlivePlayerView view, @Nullable ReadableArray sources) {
//    view.setSource(sources);
//  }
//
//  @ReactProp(name = "borderRadius", defaultFloat = 0f)
//  public void setBorderRadius(TxlivePlayerView view, float borderRadius) {
//    view.setBorderRadius(borderRadius);
//  }
//
//  @ReactProp(name = ViewProps.RESIZE_MODE)
//  public void setResizeMode(TxlivePlayerView view, @Nullable String resizeMode) {
//    view.setScaleType(ImageResizeMode.toScaleType(resizeMode));
//  }
}

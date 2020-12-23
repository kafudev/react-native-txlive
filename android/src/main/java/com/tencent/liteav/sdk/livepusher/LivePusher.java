package com.tencent.liteav.sdk.livepusher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.FileProvider;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.tencent.liteav.audiosettingkit.AudioEffectPanel;
import com.tencent.liteav.demo.beauty.view.BeautyPanel;
import com.reactnativetxlive.R;
import com.tencent.liteav.demo.livepusher.camerapush.ui.view.LogInfoWindow;
import com.tencent.liteav.demo.livepusher.camerapush.ui.view.PusherPlayQRCodeFragment;
import com.tencent.liteav.demo.livepusher.camerapush.ui.view.PusherSettingFragment;
import com.tencent.liteav.demo.livepusher.camerapush.ui.view.PusherVideoQualityFragment;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.facebook.react.uimanager.ThemedReactContext;
import com.reactnativetxlive.R;

/**
 * 腾讯云 {@link TXLivePusher} 推流器使用参考 Demo
 * <p>
 * 有以下功能参考 ：
 * <p>
 * - 基本功能参考： 启动推流 {@link #startPush()} 与 结束推流 {@link #stopPush()} ()}
 * <p>
 * - 性能数据查看参考： {@link #onNetStatus(Bundle)}
 * <p>
 * - 处理 SDK 回调事件参考： {@link #onPushEvent(int, Bundle)}
 * <p>
 * - 美颜面板：{@link BeautyPanel}
 * <p>
 * - BGM 面板：{@link AudioEffectPanel}
 * <p>
 * - 画质选择：{@link PusherVideoQualityFragment}
 * <p>
 * - 混响、变声、码率自适应、硬件加速等使用参考： {@link PusherSettingFragment} 与 {@link PusherSettingFragment.OnSettingChangeListener}
 */
public class LivePusher extends RelativeLayout implements ITXLivePushListener,
        PusherVideoQualityFragment.OnVideoQualityChangeListener, PusherSettingFragment.OnSettingChangeListener {

    private static final String TAG = "CameraPushMainView";

    private static final String PUSHER_SETTING_FRAGMENT = "push_setting_fragment";
    private static final String PUSHER_PLAY_QR_CODE_FRAGMENT = "push_play_qr_code_fragment";
    private static final String PUSHER_VIDEO_QUALITY_FRAGMENT = "push_video_quality_fragment";

    private Context mContext;

    private ViewGroup mRootView;
    private TextView         mTextNetBusyTips;              // 网络繁忙Tips
    private BeautyPanel      mBeautyPanelView;              // 美颜模块pannel
    private Button           mBtnStartPush;                 // 开启推流的按钮
    private LinearLayout     mLinearBottomBar;              // 底部工具栏布局
    private AudioEffectPanel mAudioEffectPanel;             // 音效面板

    private PusherPlayQRCodeFragment   mPusherPlayQRCodeFragment;   // 拉流地址面板
    private PusherSettingFragment      mPusherSettingFragment;      // 设置面板
    private PusherVideoQualityFragment mPusherVideoQualityFragment; // 画质面板
    private LogInfoWindow              mLogInfoWindow;              // Log 信息面板

    public String mPusherURL       = "";   // 推流地址
    private String mRTMPPlayURL     = "";   // RTMP 拉流地址
    private String mFlvPlayURL      = "";   // flv 拉流地址
    private String mHlsPlayURL      = "";   // hls 拉流地址
    private String mRealtimePlayURL = "";   // 低延时拉流地址

    private int mLogClickCount = 0;

    private TXLivePusher     mLivePusher;
    private TXLivePushConfig mLivePushConfig;
    private TXCloudVideoView mPusherView;

    private Bitmap mWaterMarkBitmap;

    private boolean mIsPushing             = false;
    private boolean mIsResume              = false;
    private boolean mIsWaterMarkEnable     = true;
    private boolean mIsDebugInfo           = false;
    private boolean mIsMuteAudio           = false;
    private boolean mIsPrivateMode         = false;
    private boolean mIsLandscape           = true;
    private boolean mIsMirrorEnable        = false;
    private boolean mIsFocusEnable         = false;
    private boolean mIsZoomEnable          = false;
    private boolean mIsPureAudio           = false;
    private boolean mIsEarMonitoringEnable = false;
    private boolean mIsHWAcc               = false;
    private boolean mFrontCamera           = true;
    private boolean mIsEnableAdjustBitrate = false;
    private boolean mIsFlashLight          = false;

    private int mVideoResolution = TXLiveConstants.VIDEO_RESOLUTION_TYPE_540_960;
    private int mAudioChannels;
    private int mAudioSample;
    private int mQualityType;

    public LivePusher(Context context) {
      super(context);
      initView(context);
    }

    public LivePusher(Context context, AttributeSet attrs) {
      super(context, attrs);
      initView(context);
    }

    public LivePusher(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      initView(context);
    }

    /**
     * 初始化view
     *
     * @param context
     */
    private void initView(Context context) {
      mContext = context;
      initData();                // 初始化数据
      initPusher();              // 初始化 SDK 推流器
    }

    /**
     * 初始化数据
     */
    private void initData(){

    }

    /**
     * 初始化SDK 推流器
     */
    private void initPusher(){
      mLivePusher = new TXLivePusher(mContext);
      mLivePushConfig  = new TXLivePushConfig();
      mLivePushConfig.enableNearestIP(true);
      mLivePushConfig.setVideoEncodeGop(5);
      // 一般情况下不需要修改 config 的默认配置
      mLivePusher.setConfig(mLivePushConfig);

      //启动本地摄像头预览
      TXCloudVideoView mPusherView = new TXCloudVideoView(mContext);
      RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
      params.addRule(RelativeLayout.CENTER_IN_PARENT);
      mPusherView.setLayoutParams(params);
      // 是否打开调试信息
      mPusherView.showLog(mIsDebugInfo);
      // 显示本地预览的View
      mPusherView.setVisibility(View.VISIBLE);
      // 添加播放回调
      mLivePusher.setPushListener(this);

      // 设置本地预览View
      mLivePusher.startCameraPreview(mPusherView);
      if (!mFrontCamera) mLivePusher.switchCamera();
      // 是否开启观众端镜像观看
      mLivePusher.setMirror(mIsMirrorEnable);
      // 添加推流控件
      addView(mPusherView);
    }

    /**
     * 开始推流
     * @param URL 推流地址
     */
    public void startPush(@Nullable String URL){
      TXLog.i(TAG, "startPush: mIsPushing -> " + mIsPushing);
      if(mIsPushing){
        return;
      }
      if(URL.isEmpty()){
        URL = mPusherURL;
      }
      if(URL.isEmpty()){
        return;
      }
      int ret = mLivePusher.startPusher(URL.trim());
      Log.i(TAG, "startPusher: ret" + ret);
      mIsPushing = true;
      if(!URL.isEmpty()){
        mPusherURL = URL;
      }
      if (ret == -5) {
        Log.i(TAG, "startRTMPPush: license 校验失败");
      }
    }

    /**
     * 暂停推流
     */
    public void pausePush(){
      TXLog.i(TAG, "pause: mIsResume -> " + mIsResume);
      //表示同时暂停视频和音频采集
      mLivePushConfig.setPauseFlag(TXLiveConstants.PAUSE_FLAG_PAUSE_VIDEO|TXLiveConstants.PAUSE_FLAG_PAUSE_AUDIO);
      mLivePusher.setConfig(mLivePushConfig);
      if (mIsPushing && mLivePusher != null) {
        // 如果当前已经是隐私模式，那么则不pause
        if (!mIsPrivateMode) {
            mLivePusher.pausePusher();
        }
      }
      mIsResume = false;
    }

    /**
     * 恢复推流
     */
    public void resumePush(){
      TXLog.i(TAG, "resume: mIsResume -> " + mIsResume);
      if (mIsResume) {
          return;
      }
      if (mPusherView != null) {
          mPusherView.onResume();
      }
      if (mIsPushing && mLivePusher != null) {
          // 如果当前是隐私模式，那么不resume
          if (!mIsPrivateMode) {
              mLivePusher.resumePusher();
          }
      }
      mIsResume = true;
    }

    /**
     * 停止推流
     */
    public void stopPush(){
      TXLog.i(TAG, "stopPush: mIsPushing -> " + mIsPushing);
      if (!mIsPushing) {
        return;
      }
      mLivePusher.stopPusher();
      mLivePusher.stopCameraPreview(true); //如果已经启动了摄像头预览，请在结束推流时将其关闭。
      mIsPrivateMode = false;
      mIsPushing = false;
    }

    /**
     * 结束推流
     */
    public void destroyPush(){
      TXLog.i(TAG, "destroyPush: mIsPushing -> " + mIsPushing);
      if (!mIsPushing) {
        return;
      }
      mLivePusher.stopPusher();
      mLivePusher.stopCameraPreview(true); //如果已经启动了摄像头预览，请在结束推流时将其关闭。
      mIsPrivateMode = false;
      mIsPushing = false;
    }

    /**
     * 切换摄像头
     */
    public void switchCamera() {
      mFrontCamera = !mFrontCamera;
      mLivePusher.switchCamera();
    }

    /**
     * 设置观众端镜像
     */
    public void setMirror(boolean enable) {
      mIsMirrorEnable = enable;
      mLivePusher.setMirror(mIsMirrorEnable);
    }

    /**
     * 设置隐私模式
     * @param enable
     */
    public void setPrivateMode(boolean enable) {
      mIsPrivateMode = enable;
      // 隐私模式下，会进入垫片推流
      if (mIsPushing) {
          if (enable) {
              mLivePusher.pausePusher();
          } else {
              mLivePusher.resumePusher();
          }
      }
    }

    /**
     * 设置log信息
     * @param enable
     */
    public void showLog(boolean enable) {
      mIsDebugInfo = enable;
      mPusherView.showLog(enable);
    }

  @Override
  public void onAudioQualityChange(int channel, int sampleRate) {

  }

  @Override
  public void onHardwareAcceleration(boolean enable) {

  }

  @Override
  public void onAdjustBitrateChange(boolean enable) {

  }

  @Override
  public void onEnableAudioEarMonitoringChange(boolean enable) {

  }

  @Override
  public void onHomeOrientationChange(boolean isPortrait) {

  }

  @Override
  public void onPrivateModeChange(boolean enable) {

  }

  @Override
  public void onMuteChange(boolean enable) {

  }

  @Override
  public void onMirrorChange(boolean enable) {

  }

  @Override
  public void onFlashLightChange(boolean enable) {

  }

  @Override
  public void onWatermarkChange(boolean enable) {

  }

  @Override
  public void onPureAudioPushChange(boolean enable) {

  }

  @Override
  public void onTouchFocusChange(boolean enable) {

  }

  @Override
  public void onEnableZoomChange(boolean enable) {

  }

  @Override
  public void onClickSnapshot() {

  }

  @Override
  public void onSendMessage(String string) {

  }

  @Override
  public void onQualityChange(int type) {

  }

  @Override
  public void onPushEvent(int i, Bundle bundle) {

  }

  @Override
  public void onNetStatus(Bundle bundle) {

  }
}

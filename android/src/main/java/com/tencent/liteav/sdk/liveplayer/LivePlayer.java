package com.tencent.liteav.sdk.liveplayer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.reactnativetxlive.R;
import com.tencent.liteav.demo.liveplayer.ui.view.LogInfoWindow;
import com.tencent.liteav.demo.liveplayer.ui.view.RadioSelectView.RadioButton;
import com.tencent.liteav.demo.liveplayer.ui.view.RadioSelectView;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.facebook.react.uimanager.ThemedReactContext;
import com.reactnativetxlive.R;

/**
 * 腾讯云 {@link TXLivePlayer} 直播播放器使用参考 Demo
 * 有以下功能参考 ：
 * - 基本功能参考： 启动推流 {@link #startPlay()}与 结束推流 {@link #stopPlay()}
 * - 硬件加速： 使用硬解码
 * - 性能数据查看参考： {@link #onNetStatus(Bundle)}
 * - 处理 SDK 回调事件参考： {@link #onPlayEvent(int, Bundle)}
 * - 渲染角度、渲染模式切换： 横竖屏渲染、铺满与自适应渲染
 * - 缓存策略选择：{@link #setCacheStrategy} 缓存策略：自动、极速、流畅。 极速模式：时延会尽可能低、但抗网络抖动效果不佳；流畅模式：时延较高、抗抖动能力较强
 */
public class LivePlayer extends RelativeLayout implements ITXLivePlayListener {

    private static final String TAG = "LivePlayerActivity";

    private static final int PERMISSION_REQUEST_CODE = 100;      //申请权限的请求码

    private Context          mContext;

    private ViewGroup mRootView;
    private ImageView        mImageLoading;          //显示视频缓冲动画
    private RelativeLayout   mLayoutRoot;            //视频暂停时更新背景
    private ImageView        mImageRoot;             //背景icon
    private ImageButton      mButtonPlay;            //视频的播放控制按钮
    private ImageButton      mButtonRenderRotation;  //调整视频播放方向：横屏、竖屏
    private ImageButton      mButtonRenderMode;      //调整视频渲染模式：全屏、自适应
    private ImageButton      mButtonCacheStrategy;   //设置视频的缓存策略
    private ImageView        mImageCacheStrategyShadow;
    private ImageButton      mButtonAcc;             //切换超低时延视频源，测试专用；
    private ImageButton      mImageLogInfo;
    private RadioSelectView  mLayoutCacheStrategy;   //显示所有缓存模式的View
    private RadioSelectView  mLayoutHWDecode;        //显示所有缓存模式的View

    private LogInfoWindow    mLogInfoWindow;

    private int              mLogClickCount = 0;

    private TXLivePlayer     mLivePlayer;               //直播拉流的视频播放器
    private TXLivePlayConfig mPlayerConfig;             //TXLivePlayer 播放配置项
    private TXCloudVideoView mVideoView;

    private String mPlayURL = "";
    private String mAccPlayURL = "";

    private boolean mIsPlaying = false;
    private boolean mFetching  = false;          //是否正在获取视频源，测试专用
    private boolean mIsAcc     = false;          //是否播放超低时延视频，测试专用
    private boolean mHWDecode  = false;          //是否启用了硬解码

    private int mCurrentPlayURLType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;                 //Player 当前播放链接类型
    private int mRenderMode         = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;    //Player 当前渲染模式
    private int mRenderRotation     = TXLiveConstants.RENDER_ROTATION_PORTRAIT;         //Player 当前渲染角度

    private long mStartPlayTS       = 0;         //保存开始播放的时间戳，测试专用

    private OkHttpClient mOkHttpClient = null;   //获取超低时延视频源直播地址

    public LivePlayer(Context context) {
      super(context);
      initView(context);
    }

    public LivePlayer(Context context, AttributeSet attrs) {
      super(context, attrs);
      initView(context);
    }

    public LivePlayer(Context context, AttributeSet attrs, int defStyleAttr) {
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
      initPlayer();              // 初始化 SDK 播放器
    }

    /**
     * 初始化数据
     */
    private void initData(){

    }

    /**
     * 初始化SDK 播放器
     */
    private void initPlayer(){
      mPlayerConfig = new TXLivePlayConfig();
      //mPlayerView 即 step1 中添加的界面 view
      mVideoView = new TXCloudVideoView(mContext);
      RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
      params.addRule(RelativeLayout.CENTER_IN_PARENT);
      params.addRule(RelativeLayout.VISIBLE);
      mVideoView.setLayoutParams(params);
      //创建 player 对象
      mLivePlayer = new TXLivePlayer(mContext);
      //关键 player 对象与界面 view
      mLivePlayer.setPlayerView(mVideoView);
      mLivePlayer.setPlayListener(this);
      mLivePlayer.enableHardwareDecode(mHWDecode);
      mLivePlayer.setRenderRotation(mRenderRotation);
      mLivePlayer.setRenderMode(mRenderMode);
      mLivePlayer.setConfig(mPlayerConfig);
      // 添加播放控件
      addView(mVideoView);
    }

    /**
     * 开始播放
     * @param URL 播放地址
     */
    public void startPlay(@Nullable String URL){
      if(URL.isEmpty()){
        URL = mPlayURL;
      }
      if(URL.isEmpty()){
        return;
      }
      /**
       * result返回值：
       * 0 success; -1 empty url; -2 invalid url; -3 invalid playType;
       */
      int code = mLivePlayer.startPlay(URL, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐 FLV
      Log.i(TAG, "startPlay: code" + code);
      mIsPlaying = code == 0;
      if(!URL.isEmpty()){
        mPlayURL = URL;
      }
    }

    /**
     * 暂停播放
     */
    public void pausePlay(){
      if (!mIsPlaying) {
        return;
      }
      // 暂停
      mLivePlayer.pause();
    }

    /**
     * 恢复播放
     */
    public void resumePlay(){
      if (!mIsPlaying) {
        return;
      }
      // 继续
      mLivePlayer.resume();
    }

    /**
     * 停止播放
     */
    public void stopPlay(){
      if (!mIsPlaying) {
        return;
      }
      if (mLivePlayer != null) {
        mLivePlayer.stopRecord();
        mLivePlayer.setPlayListener(null);
        mLivePlayer.stopPlay(true);
      }
      mIsPlaying = false;
    }

    /**
     * 结束播放
     */
    public void destroyPlay(){
      if (!mIsPlaying) {
        return;
      }
      if (mOkHttpClient != null) {
          mOkHttpClient.dispatcher().cancelAll();
      }
      if (mLivePlayer != null) {
          mLivePlayer.stopPlay(true);
          mLivePlayer = null;
      }
      if (mVideoView != null) {
          mVideoView.onDestroy();
          mVideoView = null;
      }
      mPlayerConfig = null;
      mIsPlaying = false;
    }

    /**
     * 设置渲染模式
     * @param renderMode
     */
    public void setRenderMode(int renderMode) {
        mRenderMode = renderMode;
        mLivePlayer.setRenderMode(renderMode);
    }

    /**
     * 设置旋转模式
     * @param renderRotation
     */
    public void setRenderRotation(int renderRotation) {
        mRenderRotation = renderRotation;
        mLivePlayer.setRenderRotation(renderRotation);
    }

    /**
     * 设置硬解
     * @param mode
     */
    public void setHWDecode(int mode) {
        mHWDecode = mode == 0;
        if (mIsPlaying) {
            stopPlay();
            startPlay("");
        }
    }

    /**
     * 设置log信息
     * @param enable
     */
    public void showLog(boolean enable) {
      mVideoView.showLog(enable);
    }

  @Override
  public void onPlayEvent(int i, Bundle bundle) {

  }

  @Override
  public void onNetStatus(Bundle bundle) {

  }
}

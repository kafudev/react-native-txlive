#import "TxlivePlayerView.h"
#import <UIKit/UIKit.h>
@import TXLiteAVSDK_Professional;

@implementation TxlivePlayerView

TXLivePlayer *_txLivePlayer;

- (instancetype)init {
  if (self == [super init]) {
    _txLivePlayer = [[TXLivePlayer alloc] init];
    //用 setupVideoWidget 给播放器绑定决定渲染区域的view，其首个参数 frame 在 1.5.2 版本后已经被废弃
    [_txLivePlayer setupVideoWidget:CGRectMake(0, 0, 0, 0) containView:self insertIndex:0];
  }
  return self;
}

- (void)setUrl:(NSString *)url {
  NSLog(@"setUrl = %s", url);
  _url = url;
}

- (void)showVideoView:(BOOL)value {
  //;
}

- (void)startPlay:(BOOL)value {
  NSString* flvUrl = _url;
  [_txLivePlayer startPlay:flvUrl type:PLAY_TYPE_LIVE_FLV];
}

- (void)pausePlay:(BOOL)value {
  // 暂停
[_txLivePlayer pause];
}

- (void)stopPlay:(BOOL)value {
  // 停止播放
  [_txLivePlayer stopPlay];
  [_txLivePlayer removeVideoWidget]; // 记得销毁view控件
}

- (void)destroyPlay:(BOOL)value {
  // 停止播放
  [_txLivePlayer stopPlay];
  [_txLivePlayer removeVideoWidget]; // 记得销毁view控件
}


@end

#import "TxlivePusherView.h"
#import <UIKit/UIKit.h>
@import TXLiteAVSDK_Professional;

@implementation TxlivePusherView

TXLivePushConfig *_config;
TXLivePush *_pusher;

- (instancetype)init {
  if (self == [super init]) {
    _config = [[TXLivePushConfig alloc] init];  // 一般情况下不需要修改默认 config
    _pusher = [[TXLivePush alloc] initWithConfig: _config]; // config 参数不能为空

    //创建一个 view 对象，并将其嵌入到当前界面中
    UIView *_localView = [[UIView alloc] initWithFrame:self.bounds];
    [self insertSubview:_localView atIndex:0];
    _localView.center = self.center;
     //启动本地摄像头预览
    [_pusher startPreview:_localView];
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
  //启动推流
  NSString* rtmpUrl = _url;    //此处填写您的 rtmp 推流地址
  [_pusher startPush:rtmpUrl];
}

- (void)pausePlay:(BOOL)value {
  //;
}

- (void)stopPlay:(BOOL)value {
  //结束推流
  [_pusher stopPreview]; //如果已经启动了摄像头预览，请在结束推流时将其关闭。
  [_pusher stopPush];
}

- (void)destroyPlay:(BOOL)value {
  //;
}



@end

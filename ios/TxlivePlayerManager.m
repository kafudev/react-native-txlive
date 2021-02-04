#import <React/RCTViewManager.h>
#import <React/RCTUIManager.h>
#import <React/RCTLog.h>
#import "TxlivePlayerManager.h"
#import "TxlivePlayerView.h"

@implementation TxlivePlayerManager

RCT_EXPORT_MODULE(TxlivePlayerView)

RCT_EXPORT_VIEW_PROPERTY(url, NSString);
RCT_EXPORT_VIEW_PROPERTY(showVideoView, BOOL);
RCT_EXPORT_VIEW_PROPERTY(startPlay, BOOL);
RCT_EXPORT_VIEW_PROPERTY(pausePlay, BOOL);
RCT_EXPORT_VIEW_PROPERTY(stopPlay, BOOL);
RCT_EXPORT_VIEW_PROPERTY(destroyPlay, BOOL);

- (UIView *)view
{
  return [TxlivePlayerView new];
}

- (dispatch_queue_t)methodQueue
{
    return self.bridge.uiManager.methodQueue;
}

@end

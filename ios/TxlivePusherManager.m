#import <React/RCTViewManager.h>
#import <React/RCTUIManager.h>
#import <React/RCTBridge.h>
#import <React/RCTLog.h>
#import "TxlivePusherManager.h"
#import "TxlivePusherView.h"

@implementation TxlivePusherManager

RCT_EXPORT_MODULE(TxlivePusherView)

- (UIView *)view
{
  return [TxlivePusherView new];
}

- (dispatch_queue_t)methodQueue
{
    return self.bridge.uiManager.methodQueue;
}


RCT_EXPORT_VIEW_PROPERTY(url, NSString);
RCT_EXPORT_VIEW_PROPERTY(showVideoView, BOOL);
RCT_EXPORT_VIEW_PROPERTY(startPlay, BOOL);
RCT_EXPORT_VIEW_PROPERTY(pausePlay, BOOL);
RCT_EXPORT_VIEW_PROPERTY(stopPlay, BOOL);
RCT_EXPORT_VIEW_PROPERTY(destroyPlay, BOOL);


@end

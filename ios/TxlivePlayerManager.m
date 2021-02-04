#import <React/RCTViewManager.h>
#import <React/RCTUIManager.h>
#import <React/RCTLog.h>
#import "TxlivePlayerManager.h"
#import "TxlivePlayerView.h"

@implementation TxlivePlayerManager

RCT_EXPORT_MODULE()

RCT_EXPORT_VIEW_PROPERTY(url, NSString);
RCT_EXPORT_VIEW_PROPERTY(showVideoView, BOOL);
RCT_EXPORT_VIEW_PROPERTY(startPlay, BOOL);
RCT_EXPORT_VIEW_PROPERTY(pausePlay, NSString);
RCT_EXPORT_VIEW_PROPERTY(stopPlay, NSString);
RCT_EXPORT_VIEW_PROPERTY(destroyPlay, NSString);

- (UIView *)view
{
  return [TxlivePlayerView new];
}

@end

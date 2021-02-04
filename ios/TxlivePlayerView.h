#import <UIKit/UIKit.h>
#import <React/RCTComponent.h>

@interface TxlivePlayerView : UIView

@property (assign, nonatomic) NSString *url;
@property (assign, nonatomic) BOOL showVideoView;
@property (assign, nonatomic) BOOL startPlay;
@property (assign, nonatomic) BOOL pausePlay;
@property (assign, nonatomic) BOOL stopPlay;
@property (assign, nonatomic) BOOL destroyPlay;


@end

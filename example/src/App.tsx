import * as React from 'react';
import { StyleSheet, View, Dimensions, Button,Text } from 'react-native';
import {
  TxlivePlayerView,
  TxlivePusherView,
  multiply,
} from '@kafudev/react-native-txlive';
import Video from 'react-native-video'
const { width } = Dimensions.get('window');
export default class App extends React.Component<any, any> {
  constructor(props: Object) {
    super(props);
    this.state = {
      uuid: 'live',
      // pushUrl: 'rtmp://121026.livepush.myqcloud.com/live/live110',
      // playUrl: 'http://liteavapp.qcloud.com/live/liteavdemoplayerstreamid.flv',
      // playUrlm3u8: 'http://livedev.idocore.com/live/live.m3u8',
    };
  }

  componentDidMounted() {
    multiply(3, 5).then((result: Number) => {
      this.setState({
        result,
      });
    });
  }

  render() {
    return (
      <View style={styles.container}>
        {this.state.pushUrl?<TxlivePusherView url={this.state.pushUrl||''}  style={styles.player} />:null}
        {this.state.playUrl?<TxlivePlayerView url={this.state.playUrl||''} log={true} renderRotation={1} style={styles.player} />:null}
        {this.state.playUrlm3u8?<Video source={{uri:this.state.playUrlm3u8||''}} style={styles.player} />:null}
        <View style={styles.box}>
          <Text>{'这是覆盖层文字'}</Text>
        </View>
        <Button
          onPress={() => {
            console.log('xxx');
            this.setState({
              pushUrl: 'rtmp://121026.livepush.myqcloud.com/live/live110',
              playUrl: 'http://livedev.idocore.com/live/live110.flv',
              playUrlm3u8: 'http://livedev.idocore.com/live/live110.m3u8',
              // url: 'http://liteavapp.qcloud.com/live/liteavdemoplayerstreamid.flv',
            });
          }}
          title="打开视频"
        />
        <Button
          onPress={() => {
            this.setState({
              play: true,
              pause: false,
              stop: false,
            });
          }}
          title="开始播放"
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    // alignItems: 'center',
    // justifyContent: 'center',
  },
  box: {
    position: 'absolute',
    top: 50,
    left: 50,
    backgroundColor: 'rgba(255,255,255,0.8)',
    borderRadius: 10,
    width: 50,
    height: 50,
    zIndex: 999,
  },
  player: {
    backgroundColor: '#f60',
    padding: 0,
    borderTopWidth: 2,
    borderTopColor: '#f60',
    borderRadius: 5,
    marginTop: 10,
    marginLeft: 10,
    marginBottom: 10,
    width: width - 20,
    height: 160,
  },
  btn: {
    margin: 10,
  },
});

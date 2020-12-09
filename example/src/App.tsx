import * as React from 'react';
import { StyleSheet, View, Dimensions, Button, Text } from 'react-native';
import {
  TxlivePlayerView,
  TxlivePusherView,
  multiply,
} from 'react-native-txlive';
const { width, height } = Dimensions.get('window');
export default class App extends React.Component {
  constructor(props: Object) {
    super(props);
    this.state = {
      uuid: 'live',
      result: 0,
      pushUrl: '',
      playUrl: '',
      show: false,
      play: true,
      pause: false,
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
    const { result, pushUrl, playUrl } = this.state;
    return (
      <View style={styles.container}>
        <Text>Result: {result}</Text>
        <TxlivePusherView url={pushUrl} style={styles.player} />
        <TxlivePlayerView url={playUrl} style={styles.player} />
        {/* <View style={styles.box}>
          <Text>{'这是覆盖层文字'}</Text>
        </View> */}
        <Button
          onPress={() => {
            console.log('xxx');
            this.setState({
              pushUrl:
                'rtmp://121026.livepush.myqcloud.com/live/' + this.state.uuid,
              playUrl:
                'http://livedev.idocore.com/live/' + this.state.uuid + '.flv',
              // url: 'http://liteavapp.qcloud.com/live/liteavdemoplayerstreamid.flv',
            });
          }}
          title="打开视频"
        />
        <Button
          style={styles.btn}
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
    marginTop: 50,
    marginLeft: 10,
    marginBottom: 50,
    width: width - 20,
    height: 200,
  },
  btn: {
    margin: 10,
  },
});

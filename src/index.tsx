import React, { Component } from 'react';
import { requireNativeComponent, NativeModules, View } from 'react-native';
import PropTypes from 'prop-types';
const { Txlive } = NativeModules;
const RCTTxlivePlayerView = requireNativeComponent('RCTTxlivePlayerView');
const RCTTxlivePusherView = requireNativeComponent('RCTTxlivePusherView');

export async function multiply(a: number, b: number) {
  return await Txlive.multiply(a, b);
}

export class TxlivePlayerView extends Component<any, any> {
  static defaultProps = {
    style: {},
    url: '',
  };

  propTypes = {
    ...View,
    style: PropTypes.object,
    url: PropTypes.string,
  };

  constructor(props: Object) {
    super(props);
    this.state = {
      startPlay: false,
    };
  }

  render() {
    return (
      <RCTTxlivePlayerView
        {...this.props}
        // onChange={this._onChange.bind(this)}
      />
    );
  }
}

export class TxlivePusherView extends Component<any, any> {
  static defaultProps = {
    style: {},
    url: '',
  };

  propTypes = {
    ...View,
    style: PropTypes.object,
    url: PropTypes.string,
  };

  // _onChange = (event: Event) => {
  //   if (!this.props.onChangeMessage) {
  //     return;
  //   }
  //   this.props.onChangeMessage(event.nativeEvent.message);
  // };

  render() {
    return (
      <RCTTxlivePusherView
        {...this.props}
        // onChange={this._onChange.bind(this)}
      />
    );
  }
}

// TxliveView.propTypes = {
//   showVideoView: PropTypes.bool,
//   ...View.propTypes,
// };

import React, { Component } from 'react';
import { requireNativeComponent, NativeModules, View } from 'react-native';
import PropTypes from 'prop-types';
const { Txlive } = NativeModules;
const RCTTxlivePlayerView = requireNativeComponent('RCTTxlivePlayerView');
const RCTTxlivePusherView = requireNativeComponent('RCTTxlivePusherView');

export async function multiply(a: number, b: number) {
  return await Txlive.multiply(a, b);
}

export class TxlivePlayerView extends Component {
  static defaultProps = {
    style: {},
    showVideoView: false,
  };

  propTypes = {
    ...View,
    style: PropTypes.object,
    showVideoView: PropTypes.bool,
  };

  // _onChange = (event: Event) => {
  //   if (!this.props.onChangeMessage) {
  //     return;
  //   }
  //   this.props.onChangeMessage(event.nativeEvent.message);
  // };

  render() {
    return (
      <RCTTxlivePlayerView
        {...this.props}
        // onChange={this._onChange.bind(this)}
      />
    );
  }
}

export class TxlivePusherView extends Component {
  static defaultProps = {
    style: {},
    showVideoView: false,
  };

  propTypes = {
    ...View,
    style: PropTypes.object,
    showVideoView: PropTypes.bool,
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

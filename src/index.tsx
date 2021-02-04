import React, { Component } from 'react';
import { requireNativeComponent, NativeModules, View, Platform } from 'react-native';
import PropTypes from 'prop-types';
const { Txlive } = NativeModules;
const RCTTxlivePlayerView = requireNativeComponent(Platform.OS==='ios'?'TxlivePlayerView':'RCTTxlivePlayerView');
const RCTTxlivePusherView = requireNativeComponent(Platform.OS==='ios'?'TxlivePusherView':'RCTTxlivePusherView');

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
      stopPlay: false,
      destroyPlay: false,
    };
  }

  componentWillUnmount() {
    this.setState({
      stopPlay: true,
      destroyPlay: true,
    });
  }

  render() {
    return (
      <RCTTxlivePlayerView
        {...this.props}
        stopPlay={this.state.stopPlay || false}
        destroyPlay={this.state.destroyPlay || false}
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

  constructor(props: Object) {
    super(props);
    this.state = {
      stopPush: false,
      destroyPush: false,
    };
  }

  componentWillUnmount() {
    this.setState({
      stopPush: true,
      destroyPush: false,
    });
  }

  render() {
    return (
      <RCTTxlivePusherView
        {...this.props}
        stopPush={this.state.stopPush || false}
        destroyPush={this.state.destroyPush || false}
        // onChange={this._onChange.bind(this)}
      />
    );
  }
}

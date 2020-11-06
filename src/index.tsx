import { NativeModules } from 'react-native';

type TxliveType = {
  multiply(a: number, b: number): Promise<number>;
};

const { Txlive } = NativeModules;

export default Txlive as TxliveType;

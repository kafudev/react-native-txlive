package com.reactnativetxlive;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class TxliveModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext sContext;

    public TxliveModule(ReactApplicationContext reactApplicationContext) {
      super(reactApplicationContext);
      sContext = reactApplicationContext;
      // init(appKey, appName);
      //Diagnosis.setDevServer(1);
    }

    @Override
    public String getName() {
        return "Txlive";
    }

    @ReactMethod
    public void multiply(int a, int b, Promise promise) {
      promise.resolve(a * b);
    }


}

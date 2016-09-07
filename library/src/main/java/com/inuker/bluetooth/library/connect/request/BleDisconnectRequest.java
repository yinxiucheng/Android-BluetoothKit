package com.inuker.bluetooth.library.connect.request;


import com.inuker.bluetooth.library.connect.gatt.DisconnectListener;

public class BleDisconnectRequest extends BleRequest implements DisconnectListener {

    public BleDisconnectRequest(String mac) {
        super(mac, null);
    }

    @Override
    int getGattResponseListenerId() {
        return GATT_RESP_DISCONNECT;
    }

    @Override
    int getTimeoutLimit() {
        return 1000;
    }

    @Override
    void processRequest() {
        switch (getConnectStatus()) {
            case STATUS_DEVICE_SERVICE_READY:
                registerGattResponseListener(this);
                disconnect();
                break;
            default:
                onRequestFinished(REQUEST_SUCCESS);
                break;
        }

    }

    @Override
    public void onDisconnected() {
        closeBluetoothGatt();
        onRequestFinished(REQUEST_SUCCESS, 500);
    }
}

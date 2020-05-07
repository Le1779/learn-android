package kevin.le.learnandroid.presenter;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kevin.le.learnandroid.model.DeviceInfo;
import kevin.le.learnandroid.model.ble.BLEDevice;
import kevin.le.learnandroid.model.ble.BLEScanner;
import kevin.le.learnandroid.presenter.contract.LearnBLEContract;

import static android.bluetooth.le.ScanSettings.SCAN_MODE_LOW_LATENCY;
import static kevin.le.learnandroid.model.ble.BLERule.SCAN_TIMEOUT;

public class LearnBLEPresenter extends BasePresenter<LearnBLEContract.View> implements LearnBLEContract.Presenter  {

    private ScanSettings scanSettings;
    private ScanCallback scanCallback;
    private Map<String, BluetoothDevice> bluetoothDeviceMap;
    private Map<String, DeviceInfo> deviceInfoMap;

    public LearnBLEPresenter(Context context, LearnBLEContract.View view) {
        super(context, view);
    }

    @Override
    public void startScan() {
        initScanSettings();
        initScanCallback();
        if(!BLEScanner.getInstance().isScanning()){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Log.d(this.getClass().getName(), "stopScan");
                    BLEScanner.getInstance().stopScan();
                }
            };

            BLEScanner.getInstance().startScan(null, scanSettings, scanCallback);
            new Handler().postDelayed(runnable, SCAN_TIMEOUT);
        }
    }

    @Override
    public void connectDevice(String address) {
        BLEDevice.initial().findAndConnect(context, address);
    }

    @Override
    public void sandCommand(String command) {
        if (BLEDevice.initial().isConnected()) {
            BLEDevice.initial().setData(command + "\r\n");
        }
    }

    private void initScanSettings(){
        scanSettings = new ScanSettings.Builder().setScanMode(SCAN_MODE_LOW_LATENCY).build();
    }

    private void initScanCallback(){
        scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                if(result.getDevice().getName() != null){
                    if(result.getDevice().getName().contains("Tv500u-") || result.getDevice().getName().contains("SAMPO-BT")){
                        foundDevice(result);
                    }
                }
            }
        };
    }

    private void foundDevice(ScanResult scanResult){
        BluetoothDevice bluetoothDevice = scanResult.getDevice();
        String address = bluetoothDevice.getAddress();
        //bluetoothDeviceMap.put(address, bluetoothDevice);

        if(deviceInfoMap == null) {
            deviceInfoMap = new HashMap<>();
        }

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setName(bluetoothDevice.getName());
        deviceInfo.setAddress(bluetoothDevice.getAddress());
        deviceInfo.setRssi(scanResult.getRssi());
        deviceInfoMap.put(address, deviceInfo);

        Log.d(this.getClass().getName(), bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress() + "\n" + scanResult.getRssi());
        view.modifyDeviceList(new ArrayList<>(deviceInfoMap.values()));
    }
}

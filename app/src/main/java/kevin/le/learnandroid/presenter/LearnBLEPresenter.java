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

    private BLEDevice bleDevice;
    private BLEDevice.DeviceCallback deviceCallback;
    private BluetoothDevice connectedDevice;

    public LearnBLEPresenter(Context context, LearnBLEContract.View view) {
        super(context, view);
        initBLEDevice();
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
        connectedDevice = bluetoothDeviceMap.get(address);
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

        if(bluetoothDeviceMap == null) {
            bluetoothDeviceMap = new HashMap<>();
        }
        bluetoothDeviceMap.put(address, bluetoothDevice);

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

    private void initBLEDevice(){
        initDeviceCallback();
        bleDevice = BLEDevice.initial();
        bleDevice.addObserver(deviceCallback);
    }

    private void initDeviceCallback(){
        deviceCallback = new BLEDevice.DeviceCallback() {
            @Override
            public void found(int distance) {
                //not work
            }

            @Override
            public void notFound() {
                //not work
            }

            @Override
            public void connected() {
                DeviceInfo connectDeviceInfo = new DeviceInfo();
                connectDeviceInfo.setName(connectedDevice.getName());
                connectDeviceInfo.setAddress(connectedDevice.getAddress());
                connectDeviceInfo.setConnected(true);
                view.connectSuccess();
            }

            @Override
            public void disconnected() {
                view.connectFail("disconnected");
            }

            @Override
            public void onDataChange(String data) {
                view.newResponse(data);
            }

            @Override
            public void onDataWrite(String data) {

            }
        };
    }
}

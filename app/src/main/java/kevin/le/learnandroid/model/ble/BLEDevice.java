package kevin.le.learnandroid.model.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.bluetooth.le.ScanSettings.SCAN_MODE_LOW_LATENCY;
import static kevin.le.learnandroid.model.ble.BLERule.SCAN_TIMEOUT;

public class BLEDevice {

    private static volatile BLEDevice device;
    private BluetoothDevice bluetoothDevice;
    private GATTHelper gattHelper;
    private boolean isConnected = false;

    private List<ScanFilter> scanFilters;
    private ScanSettings scanSettings;
    private ScanCallback scanCallback;

    private List<DeviceCallback> observers;
    public interface DeviceCallback{
        void found(int distance);
        void notFound();
        void connected();
        void disconnected();
        void onDataChange(String data);
        void onDataWrite(String data);
    }

    private BLEDevice(){
        observers = new ArrayList<>();
    }

    public static BLEDevice initial(){
        if(device == null){
            synchronized(BLEDevice.class){
                if(device == null){
                    device = new BLEDevice();
                }
            }
        }
        return device;
    }

    public void setData(String data){
        Log.d(this.getClass().getName(), data);
        gattHelper.setData(data);
    }

    public void addObserver(DeviceCallback deviceCallback){
        observers.add(deviceCallback);
    }

    public boolean removeObserver(DeviceCallback deviceCallback){
        return observers.remove(deviceCallback);
    }

    public void connectDevice(Context context, BluetoothDevice bluetoothDevice){
        setDevice(bluetoothDevice);
        connect(context);
    }

    public boolean isConnected(){
        return isConnected;
    }

    public void disconnect(){
        if(gattHelper != null){
            gattHelper.disconnect();
        }
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void findAndConnect(Context context, String address){
        BLEScanner.getInstance().stopScan();
        disconnect();
        initScanFilter(address);
        initScanSettings();
        initScanCallback(context);
        startScan();
    }

    private void notifyFound(int distance){
        for(DeviceCallback callback : observers){
            callback.found(distance);
        }
    }

    private void notifyNotFound(){
        for(DeviceCallback callback : observers){
            callback.notFound();
        }
    }

    private void notifyConnected(){
        for(DeviceCallback callback : observers){
            callback.connected();
        }
    }

    private void notifyDisconnected(){
        for(DeviceCallback callback : observers){
            callback.disconnected();
        }
    }

    private void notifyOnDataChange(String data){
        for(DeviceCallback callback : observers){
            callback.onDataChange(data);
        }
    }

    private void notifyOnDataWrite(String data){
        for(DeviceCallback callback : observers){
            callback.onDataWrite(data);
        }
    }

    private void setDevice(BluetoothDevice bluetoothDevice){
        this.bluetoothDevice = bluetoothDevice;
        Log.d(this.getClass().getName(), "FOUND!!" + bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress());
    }

    private void connect(final Context context){
        if(this.bluetoothDevice == null){
            return;
        }
        Log.d(this.getClass().getName(), "connect");
        gattHelper = new GATTHelper(context);
        gattHelper.setCallback(new GATTHelper.Callback() {
            @Override
            public void connected() {
                Log.d(this.getClass().getName(), "connected");
                isConnected = true;
                notifyConnected();
            }

            @Override
            public void disconnected() {
                Log.d(this.getClass().getName(), "disconnected");
                isConnected = false;
                notifyDisconnected();
            }

            @Override
            public void onDataChange(String data) {
                notifyOnDataChange(data);
            }

            @Override
            public void onDataWrite(String data) {
                notifyOnDataWrite(data);
            }
        });
        gattHelper.connectDevice(bluetoothDevice);
    }

    private void startScan(){
        Log.d(this.getClass().getName(), "startScan");
        if(!BLEScanner.getInstance().isScanning()){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if(!BLEDevice.initial().isConnected) {
                        notifyNotFound();
                    }
                }
            };

            BLEScanner.getInstance().startScan(scanFilters, scanSettings, scanCallback);
            new Handler().postDelayed(runnable, SCAN_TIMEOUT);
        }
    }

    private void initScanFilter(String address){
        scanFilters = new ArrayList<>();
        ScanFilter filter = new ScanFilter.Builder().setDeviceAddress(address).build();
        scanFilters.add(filter);
    }

    private void initScanSettings(){
        scanSettings = new ScanSettings.Builder().setScanMode(SCAN_MODE_LOW_LATENCY).build();
    }

    private void initScanCallback(final Context context){
        scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                BLEScanner.getInstance().stopScan();
                BluetoothDevice bluetoothDevice = result.getDevice();
                connectDevice(context, bluetoothDevice);
            }
        };
    }
}

package kevin.le.learnandroid.model.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.os.Handler;

import java.util.List;

import static kevin.le.learnandroid.model.ble.BLERule.SCAN_TIMEOUT;


public class BLEScanner {

    private static volatile BLEScanner bleScanner;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private ScanCallback currentScanCallback;

    private boolean isScanning = false;

    private BLEScanner() {
        try {
            getAdapter();
            checkBluetooth();
            getScanner();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static BLEScanner getInstance(){
        if (bleScanner == null) {
            synchronized (BLEScanner.class) {
                if (bleScanner == null) {
                    bleScanner = new BLEScanner();
                }
            }
        }
        return bleScanner;
    }

    public boolean isScanning(){
        return isScanning;
    }

    public void startScan(List<ScanFilter> filters, ScanSettings settings, ScanCallback scanCallback) {
        if(isScanning){
            stopScan();
        }
        isScanning = true;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(isScanning){
                    stopScan();
                }
            }
        };
        bluetoothLeScanner.startScan(filters, settings, scanCallback);
        new Handler().postDelayed(runnable, SCAN_TIMEOUT);
        currentScanCallback = scanCallback;
    }

    public void stopScan() {
        isScanning = false;
        bluetoothLeScanner.stopScan(currentScanCallback);
    }

    private void checkBluetooth() throws Exception{
        if(bluetoothAdapter == null){
            throw new Exception("Device does not support Bluetooth.");
        }

        if (!bluetoothAdapter.isEnabled()) {
            throw new Exception("Bluetooth is not enable.");
        }
    }

    private void getAdapter(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void getScanner(){
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
    }
}

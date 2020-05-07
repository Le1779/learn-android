package kevin.le.learnandroid.model.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

import java.util.UUID;

class GATTHelper {

    private Context context;
    private BluetoothGatt bluetoothGatt;

    private UUID uuid_write_service = null;
    private UUID uuid_read_service = null;
    private UUID uuid_write_characteristic = null;
    private UUID uuid_read_characteristic = null;

    private BluetoothGattCharacteristic characteristic_write;

    private Callback callback;

    private byte[] previousBytes = new byte[0];

    public interface Callback{
        void connected();
        void disconnected();
        void onDataChange(String data);
        void onDataWrite(String data);
    }

    GATTHelper(Context context){
        this.context = context;
        setUUID();
    }

    void connectDevice(BluetoothDevice bluetoothDevice){
        if(callback == null){
            return;
        }
        bluetoothGatt = bluetoothDevice.connectGatt(context, false, getBluetoothGattCallback(), BluetoothDevice.DEVICE_TYPE_LE);
    }

    void setCallback(Callback callback) {
        this.callback = callback;
    }

    void setData(String data){
        Log.d(this.getClass().getName(), "setData");
        characteristic_write.setValue(data);
        bluetoothGatt.writeCharacteristic(characteristic_write);
    }

    void disconnect(){
        bluetoothGatt.disconnect();
    }

    private void setUUID(){
        uuid_write_service = get16BitTo128BitUUID("FFE5");
        uuid_read_service = get16BitTo128BitUUID("FFE0");

        uuid_write_characteristic = get16BitTo128BitUUID("FFE9");
        uuid_read_characteristic = get16BitTo128BitUUID("FFE4");
    }

    private void connectService(){
        bluetoothGatt.getService(uuid_write_service);
        bluetoothGatt.getService(uuid_read_service);
    }

    private void connectCharacteristic(){
        connectWriteCharacteristic(bluetoothGatt.getService(uuid_write_service));
        connectReadCharacteristic(bluetoothGatt.getService(uuid_read_service));
    }

    private void connectWriteCharacteristic(BluetoothGattService service){
        characteristic_write = service.getCharacteristic(uuid_write_characteristic);
    }

    private void connectReadCharacteristic(BluetoothGattService service){
        BluetoothGattCharacteristic characteristic_read = service.getCharacteristic(uuid_read_characteristic);
        bluetoothGatt.setCharacteristicNotification(characteristic_read, true);
    }

    private BluetoothGattCallback getBluetoothGattCallback(){
        return new BluetoothGattCallback() {
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    gatt.discoverServices();
                    connectService();
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    bluetoothGatt.close();
                    callback.disconnected();
                    Log.i(this.getClass().getName(), "Disconnected from GATT server.");
                } else {
                    bluetoothGatt.close();
                    callback.disconnected();
                    Log.i(this.getClass().getName(), "BluetoothProfile status: " + newState);
                }
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    connectCharacteristic();
                    callback.connected();
                } else {
                    Log.w(this.getClass().getName(), "onServicesDiscovered received: " + status);
                }
            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                byte[] receiveBytes = characteristic.getValue();

                if(previousBytes.length > 0) {
                    byte[] tempBytes = new byte[receiveBytes.length + previousBytes.length];
                    System.arraycopy(previousBytes, 0, tempBytes, 0, previousBytes.length);
                    System.arraycopy(receiveBytes, previousBytes.length, tempBytes, 0, receiveBytes.length);
                    receiveBytes = new byte[tempBytes.length];
                    System.arraycopy(tempBytes, 0, receiveBytes, 0, receiveBytes.length);
                    previousBytes = new byte[0];
                }

                int startIndex = 0;
                for (int i = 1; i < receiveBytes.length; i++) {
                    if(receiveBytes[i - 1] == 13 && receiveBytes[i] == 10){
                        previousBytes = new byte[i - startIndex];
                        System.arraycopy(receiveBytes, startIndex, previousBytes, 0, i - startIndex);
                        Log.d(this.getClass().getName(), ">>> " + new String(previousBytes));
                        callback.onDataChange(new String(previousBytes));
                        previousBytes = new byte[0];
                        startIndex = i + 1;
                    }
                }
            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    byte[] bytesreceive = characteristic.getValue();
                    callback.onDataWrite(new String(bytesreceive));
                } else {
                    Log.w(this.getClass().getName(), "onServicesDiscovered received: " + status);
                }
            }
        };
    }

    private UUID get16BitTo128BitUUID(String uuid_16){
        return UUID.fromString("0000" + uuid_16 + "-0000-1000-8000-00805f9b34fb");
    }
}

package kevin.le.learnandroid.presenter.contract;

import java.util.List;

import kevin.le.learnandroid.model.DeviceInfo;

public interface LearnBLEContract {

    interface View extends BaseContract.View{
        void modifyDeviceList(List<DeviceInfo> deviceInfo);
        void newResponse(String response);
        void connectSuccess();
        void connectFail(String message);
    }

    interface Presenter extends BaseContract.Presenter{
        void startScan();
        void connectDevice(String address);
        void sandCommand(String command);
    }
}

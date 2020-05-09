package kevin.le.learnandroid.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.model.DeviceInfo;
import kevin.le.learnandroid.model.PermissionManager;
import kevin.le.learnandroid.presenter.LearnBLEPresenter;
import kevin.le.learnandroid.presenter.contract.LearnBLEContract;

public class LearnBLEActivity extends BaseActivity<LearnBLEPresenter> implements LearnBLEContract.View  {

    private final int PERMISSIONS_REQUEST_LOCATION = 1779;
    private View viewBottomSheetArea;
    private View bottomSheet;
    private View bottomSheetHead;
    private View bottomSheetBody;
    private BottomSheetBehavior behavior;
    private TextView textView_bottomSheet_head_device_name;
    private EditText editText_scan_keyword;
    private Button button_start_scarn;
    private RecyclerView recyclerView_scan_result;
    private ScanResultAdapter scanResultAdapter;

    private EditText editText_command;
    private Button button_send_command;
    private ScrollView scrollview;
    private TextView textView_response;

    @Override
    public int getLayoutId() {
        return R.layout.activity_learn_ble;
    }

    @Override
    public LearnBLEPresenter getPresenter() {
        return new LearnBLEPresenter(this, this);
    }

    @Override
    public void init() {
        initSendCommandComponent();
        initButtonSheet();
        initScanComponent();
        initScanRecyclerView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int screenHeight = this.getWindow().getDecorView().getBottom();
        resizeBottomSheet(screenHeight);
    }

    @Override
    public void modifyDeviceList(final List<DeviceInfo> deviceInfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scanResultAdapter.setDevices(deviceInfo);
            }
        });
    }

    @Override
    public void newResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView_response.append(String.format("GET: %s\n", response));
                scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });
    }

    @Override
    public void connectSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                scanResultAdapter.stopConnect();
                textView_bottomSheet_head_device_name.setText(presenter.getConnectedDeviceName());
                enableAllView();
            }
        });
    }

    @Override
    public void connectFail(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scanResultAdapter.stopConnect();
            }
        });
    }

    private void resizeBottomSheet(int screenHeight){
        int peekHeight = (int)(screenHeight * 0.1f);
        behavior.setPeekHeight(peekHeight);
        ViewGroup.LayoutParams params = bottomSheetHead.getLayoutParams();
        params.height = peekHeight;
        bottomSheetHead.setLayoutParams(params);

        params = viewBottomSheetArea.getLayoutParams();
        params.height = peekHeight;
        viewBottomSheetArea.setLayoutParams(params);

        int bodyHeight = (int)(screenHeight * 0.52f);
        params = bottomSheetBody.getLayoutParams();
        params.height = bodyHeight;
        bottomSheetBody.setLayoutParams(params);

    }

    private void initSendCommandComponent(){
        editText_command = findViewById(R.id.editText_command);
        button_send_command = findViewById(R.id.button_send_command);
        textView_response = findViewById(R.id.textView_response);
        scrollview = findViewById(R.id.scrollview);

        button_send_command.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String command = editText_command.getText().toString();
                presenter.sandCommand(command);
                textView_response.append(String.format("SEND: %s\n", command));
            }
        });

        disableAllView();
    }

    private void initButtonSheet(){
        textView_bottomSheet_head_device_name = findViewById(R.id.textView_bottomSheet_head_device_name);
        textView_bottomSheet_head_device_name.setText("尚未連接");

        viewBottomSheetArea = findViewById(R.id.view_bottom_sheet_area);

        bottomSheet = findViewById(R.id.bottomSheet);
        bottomSheetHead = findViewById(R.id.bottomSheet_head);
        bottomSheetHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior.setState(behavior.getState() == BottomSheetBehavior.STATE_EXPANDED ? BottomSheetBehavior.STATE_COLLAPSED : BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        bottomSheetBody = findViewById(R.id.bottomSheet_body);

        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if(behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
                    Log.d(this.getClass().getName(), "STATE_COLLAPSED");
                }else {
                    Log.d(this.getClass().getName(), "STATE_EXPANDED");
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    private void initScanComponent(){
        editText_scan_keyword = findViewById(R.id.editText_scan_keyword);
        editText_scan_keyword.setMovementMethod(new ScrollingMovementMethod());

        button_start_scarn = findViewById(R.id.button_start_scarn);
        button_start_scarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isGetPermission = PermissionManager.with(LearnBLEActivity.this, LearnBLEActivity.this)
                        .setPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .setRequestCode(PERMISSIONS_REQUEST_LOCATION)
                        .request();
                if(isGetPermission){
                    presenter.startScan();
                }
            }
        });
    }

    private void initScanRecyclerView(){
        initScanResultAdapter();
        recyclerView_scan_result = findViewById(R.id.recyclerView_scan_result);
        recyclerView_scan_result.setAdapter(scanResultAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_scan_result.setLayoutManager(layoutManager);
    }

    private void initScanResultAdapter(){
        scanResultAdapter = new ScanResultAdapter(this, null);
        scanResultAdapter.setOnItemClickListener(new ScanResultAdapter.OnItemClickListener() {
            @Override
            public void onDeviceClick(String address) {
                presenter.connectDevice(address);
            }
        });
    }

    private void enableAllView() {
        button_send_command.setEnabled(true);
        editText_command.setEnabled(true);
    }

    private void disableAllView() {
        button_send_command.setEnabled(false);
        editText_command.setEnabled(false);
    }
}

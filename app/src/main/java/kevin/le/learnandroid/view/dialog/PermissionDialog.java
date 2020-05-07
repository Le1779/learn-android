package kevin.le.learnandroid.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import kevin.le.learnandroid.R;

public class PermissionDialog extends Dialog implements View.OnClickListener {

    private OnButtonClickListener onButtonClickListener;

    public interface OnButtonClickListener{
        void confirm();
        void cancel();
    }

    public PermissionDialog(@NonNull Context context) {
        super(context);
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_permission);
        setCanceledOnTouchOutside(true);

        initButton();
    }

    @Override
    public void show(){
        super.show();
    }

    @Override
    public void dismiss(){
        super.dismiss();
    }

    private void initButton(){
        findViewById(R.id.button_dialog_permission_confirm).setOnClickListener(this);
        findViewById(R.id.button_dialog_permission_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(onButtonClickListener == null){
            return;
        }
        switch (view.getId()){
            case R.id.button_dialog_permission_cancel:
                onButtonClickListener.cancel();
                break;
            case R.id.button_dialog_permission_confirm:
                onButtonClickListener.confirm();
                break;
        }
    }
}

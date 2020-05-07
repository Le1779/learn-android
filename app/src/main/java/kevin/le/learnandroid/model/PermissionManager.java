package kevin.le.learnandroid.model;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import kevin.le.learnandroid.view.dialog.PermissionDialog;


public class PermissionManager {

    private Activity activity;
    private Context context;
    private String permission;
    private int requestCode;
    private PermissionDialog permissionDialog;

    private PermissionManager(Activity activity, Context context){
        this.activity = activity;
        this.context = context;
        permissionDialog = new PermissionDialog(context);
    }

    public static PermissionManager with(Activity activity, Context context){
        return new PermissionManager(activity, context);
    }

    public PermissionManager setPermission(String permission){
        this.permission = permission;
        return this;
    }

    public PermissionManager setRequestCode(int requestCode){
        this.requestCode = requestCode;
        return this;
    }

    public boolean request(){
        if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
            permissionDialog.setOnButtonClickListener(new PermissionDialog.OnButtonClickListener() {
                @Override
                public void confirm() {
                    permissionDialog.dismiss();
                    ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
                }

                @Override
                public void cancel() {
                    permissionDialog.dismiss();
                }
            });
            permissionDialog.show();
            return false;
        }
        return true;
    }
}

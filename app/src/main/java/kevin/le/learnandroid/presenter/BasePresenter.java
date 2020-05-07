package kevin.le.learnandroid.presenter;

import android.content.Context;

import kevin.le.learnandroid.presenter.contract.BaseContract;

public abstract class BasePresenter<CV extends BaseContract.View> {

    public Context context;
    public CV view;

    public BasePresenter(Context context, CV view){
        this.context = context;
        this.view = view;
    }
}

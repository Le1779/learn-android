package kevin.le.learnandroid.view;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import kevin.le.learnandroid.presenter.BasePresenter;


public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

    public P presenter;

    public abstract @LayoutRes
    int getLayoutId();

    public abstract P getPresenter();

    public abstract void init();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        init();
        this.presenter = presenter == null ? getPresenter() : presenter;
    }
}

package kevin.le.learnandroid.view.components.device_info_area;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.view.components.shadow.ShadowAttribute;
import kevin.le.learnandroid.view.components.shadow.ShadowConstraintLayout;

public class InfoArea extends ConstraintLayout {

    public InfoArea(@NonNull Context context) {
        super(context);
        init(null);
    }

    public InfoArea(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public InfoArea(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public InfoArea(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        Log.d(this.getClass().getName(), "init");
        constraintSet.clone(this);

        generateCenterContainer();
        generateIconBackground();
        generateIconImageView();

        constraintSet.applyTo(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            updateConstraint();
        }
    }

    private ShadowConstraintLayout centerContainer;
    private InfoAreaIconBackground iconBackground;
    private AppCompatImageView iconImageView;

    private ConstraintSet constraintSet = new ConstraintSet();

    /**
     * 建立中央的容器
     */
    private void generateCenterContainer() {
        centerContainer = new ShadowConstraintLayout(getContext(), null);
        centerContainer.setId(View.generateViewId());
        centerContainer.setLayoutParams(new ConstraintLayout.LayoutParams(
                LayoutParams.MATCH_CONSTRAINT,
                LayoutParams.MATCH_CONSTRAINT
        ));
        this.addView(centerContainer);

        centerContainer.shadowAttribute = new ShadowAttribute(Color.parseColor("#38000000"), 36, new Point(0, 4));
        centerContainer.shadowDrawable.setShadowAttribute(centerContainer.shadowAttribute);

        constraintSet.constrainPercentWidth(centerContainer.getId(), 0.7f);
        constraintSet.setDimensionRatio(centerContainer.getId(), "1:1");
        constraintSet.connect(centerContainer.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(centerContainer.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(centerContainer.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(centerContainer.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
    }

    /**
     * 建立圖示的容器
     */
    private void generateIconBackground() {
        iconBackground = new InfoAreaIconBackground(getContext());
        iconBackground.setId(View.generateViewId());
        iconBackground.setLayoutParams(new ConstraintLayout.LayoutParams(
                LayoutParams.MATCH_CONSTRAINT,
                LayoutParams.MATCH_CONSTRAINT
        ));
        iconBackground.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
        centerContainer.addView(iconBackground);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(centerContainer);
        constraintSet.constrainPercentWidth(iconBackground.getId(), 0.25f);
        constraintSet.setDimensionRatio(iconBackground.getId(), "1:1");
        constraintSet.centerHorizontally(iconBackground.getId(), ConstraintSet.PARENT_ID);
        constraintSet.applyTo(centerContainer);
    }

    /**
     * 建立Icon ImageView
     */
    private void generateIconImageView() {
        iconImageView = new AppCompatImageView(getContext());
        iconImageView.setId(View.generateViewId());
        iconImageView.setLayoutParams(new ConstraintLayout.LayoutParams(
                LayoutParams.MATCH_CONSTRAINT,
                LayoutParams.MATCH_CONSTRAINT
        ));
        iconImageView.setImageResource(R.drawable.ic_light);
        iconImageView.setColorFilter(R.color.dark_gray_2, PorterDuff.Mode.SRC_IN);
        centerContainer.addView(iconImageView);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(centerContainer);
        constraintSet.constrainPercentWidth(iconImageView.getId(), 0.15f);
        constraintSet.setDimensionRatio(iconImageView.getId(), "1:1");
        constraintSet.centerHorizontally(iconImageView.getId(), iconBackground.getId());
        constraintSet.centerVertically(iconImageView.getId(), iconBackground.getId());
        constraintSet.applyTo(centerContainer);
    }

    //region Update View
    /**
     * 更新約束
     */
    private void updateConstraint() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(centerContainer);
        constraintSet.connect(iconBackground.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, (int) (getHeight()*0.1));
        constraintSet.applyTo(centerContainer);
    }

    private void updateViews() {

    }
    //endregion


}

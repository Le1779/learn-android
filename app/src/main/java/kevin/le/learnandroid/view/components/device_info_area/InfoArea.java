package kevin.le.learnandroid.view.components.device_info_area;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import kevin.le.learnandroid.R;
import kevin.le.learnandroid.view.components.shadow.ShadowAttribute;
import kevin.le.learnandroid.view.components.shadow.ShadowConstraintLayout;

public abstract class InfoArea extends ConstraintLayout {

    public InfoArea(@NonNull Context context) {
        super(context);
        init();
    }

    public InfoArea(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InfoArea(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public InfoArea(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @DrawableRes
    public abstract int getIconId();
    @ColorInt
    public abstract int getIconBackgroundColor();

    public abstract void initSubview();
    public abstract void updateSubview();

    private void init() {
        generateCenterContainer();
        generateIconBackground();
        generateIconImageView();
        initSubview();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            updateConstraint();
            updateSubview();
        }
    }

    public ShadowConstraintLayout centerContainer;
    public InfoAreaIconBackground iconBackground;
    public AppCompatImageView iconImageView;

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

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        constraintSet.constrainPercentWidth(centerContainer.getId(), 0.7f);
        constraintSet.setDimensionRatio(centerContainer.getId(), "1:1");
        constraintSet.connect(centerContainer.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(centerContainer.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(centerContainer.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(centerContainer.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.applyTo(this);
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
        iconBackground.setBackgroundColor(getIconBackgroundColor());
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
        iconImageView.setImageResource(getIconId());
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

    /**
     * 更新約束
     */
    private void updateConstraint() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(centerContainer);
        constraintSet.connect(iconBackground.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, (int) (getHeight()*0.1));
        constraintSet.applyTo(centerContainer);
    }
}

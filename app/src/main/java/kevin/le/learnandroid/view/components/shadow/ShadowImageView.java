package kevin.le.learnandroid.view.components.shadow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import kevin.le.learnandroid.R;

public class ShadowImageView extends AppCompatImageView {
    public ShadowImageView(@NonNull Context context) {
        super(context);
        init(null);
    }

    public ShadowImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ShadowImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowImageView);
        int shadowColor = a.getColor(R.styleable.ShadowImageView_shadowColor, Color.BLACK);
        int shadowRadius = a.getInt(R.styleable.ShadowImageView_shadowRadius, 25);
        shadowAttribute = new ShadowAttribute(shadowColor, shadowRadius, new Point(0, 0));
        a.recycle();
    }

    private ShadowAttribute shadowAttribute;
    private Bitmap shadowBitmap;

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        if (shadowBitmap == null) {
            generateShadowBitmap();
        }

        Rect bounds = getDrawable().copyBounds();
        canvas.drawBitmap(shadowBitmap, bounds.left - shadowAttribute.getRadius(), bounds.top - shadowAttribute.getRadius() / 2f, null);
        canvas.restore();
        super.onDraw(canvas);
    }

    private void generateShadowBitmap() {
        Bitmap bitmap = getBitmapFromDrawable();

        Bitmap blurBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blurBitmap);

        Paint paint = new Paint();
        ColorFilter filter = new PorterDuffColorFilter(shadowAttribute.getColor(), PorterDuff.Mode.SRC_IN);
        paint.setColorFilter(filter);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        blurBitmap = Bitmap.createScaledBitmap(blurBitmap, (int) (bitmap.getWidth()*shadowAttribute.getBitmapScale()), (int) (bitmap.getHeight()*shadowAttribute.getBitmapScale()), true);
        blurBitmap = BlurProvider.blur(getContext(), blurBitmap, shadowAttribute.getRadius());

        shadowBitmap = Bitmap.createScaledBitmap(blurBitmap, bitmap.getWidth(), bitmap.getHeight(), true);
    }

    private Bitmap getBitmapFromDrawable() {
        Drawable drawable = getDrawable();
        int width = getWidth() + 2 * shadowAttribute.getRadius();
        int height = getHeight() + 2 * shadowAttribute.getRadius();

        Bitmap bitmap;
        if (width <= 0 || height <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        canvas.translate(getPaddingLeft() + shadowAttribute.getRadius(), getPaddingTop() + shadowAttribute.getRadius());

        Matrix imageMatrix = getImageMatrix();
        if (imageMatrix != null) {
            canvas.concat(imageMatrix);
        }

        drawable.draw(canvas);
        return bitmap;
    }
}

package kevin.le.learnandroid.view.components.shadow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Size;
import android.view.ViewGroup;

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
        a.recycle();

        shadowAttribute = new ShadowAttribute(shadowColor, shadowRadius, new Point(0, 0));
    }

    private ShadowAttribute shadowAttribute;
    private Bitmap shadowBitmap;

    @Override
    protected void onDraw(Canvas canvas) {
        generateShadowBitmap();
        if (shadowBitmap != null) {
            canvas.save();
            Rect bounds = getDrawable().copyBounds();
            canvas.drawBitmap(shadowBitmap, bounds.left - shadowAttribute.getRadius(), bounds.top - shadowAttribute.getRadius() / 2f, null);
            canvas.restore();
        }

        super.onDraw(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        ViewGroup parent = (ViewGroup)getParent();
        parent.setClipChildren(false);
        super.onAttachedToWindow();
    }

    /**
     * 產生陰影圖層。
     */
    private void generateShadowBitmap() {
        shadowBitmap = null;
        Bitmap bitmap = getBitmapFromDrawable();
        if (bitmap == null) {
            return;
        }

        Bitmap tintBitmap = getTintBitmap(bitmap);
        Bitmap blurBitmap = getBlurBitmap(tintBitmap);
        shadowBitmap = Bitmap.createScaledBitmap(blurBitmap, bitmap.getWidth(), bitmap.getHeight(), true);
    }

    /**
     * 從ImageView的Drawable取得Bitmap，且增加邊緣讓產生的陰影不會超出邊界。
     * @return Bitmap
     */
    private Bitmap getBitmapFromDrawable() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return null;
        }

        int radius = shadowAttribute.getRadius();
        int width = getWidth() + 2 * radius;
        int height = getHeight() + 2 * radius;

        Bitmap bitmap;
        if (width <= 0 || height <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        canvas.translate(getPaddingLeft() + radius, getPaddingTop() + radius);

        Matrix imageMatrix = getImageMatrix();
        if (imageMatrix != null) {
            canvas.concat(imageMatrix);
        }

        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 取得被填色的Bitmap，當做成圖片陰影的原圖層。
     * @param bitmap 來源
     * @return 填色過後的Bitmap
     */
    private Bitmap getTintBitmap(Bitmap bitmap) {
        Paint paint = new Paint();
        ColorFilter filter = new PorterDuffColorFilter(shadowAttribute.getColor(), PorterDuff.Mode.SRC_IN);
        paint.setColorFilter(filter);

        Bitmap tintBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(tintBitmap);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return tintBitmap;
    }

    /**
     * 取得模糊後的Bitmap，如果模糊的半徑大於25，則先按造比例縮小Bitmap。
     * @param bitmap 來源
     * @return 模糊過後的Bitmap
     */
    private Bitmap getBlurBitmap(Bitmap bitmap) {
        float scale = shadowAttribute.getBitmapScale();
        Size bounds = new Size((int) (bitmap.getWidth() * scale), (int) (bitmap.getHeight() * scale));
        Bitmap blurBitmap = Bitmap.createScaledBitmap(bitmap, bounds.getWidth(), bounds.getHeight(), true);
        return BlurProvider.blur(getContext(), blurBitmap, shadowAttribute.getRadius());
    }
}

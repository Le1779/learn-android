package kevin.le.learnandroid.view.components.circular_slider;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class TrackDrawable extends Drawable {

    private TrackPath trackPath;
    private final Paint paint;

    public TrackDrawable(int strokeWidth, int strokeColor) {
        paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(strokeColor);
    }

    public void updateTrackPath(TrackPath trackPath) {
        this.trackPath = trackPath;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (trackPath != null) {
            canvas.drawPath(trackPath.path, paint);
        }
    }

    @Override
    public void setAlpha(int i) {}

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {}

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}

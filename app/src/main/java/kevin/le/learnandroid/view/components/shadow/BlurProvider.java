package kevin.le.learnandroid.view.components.shadow;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public class BlurProvider {

    public static Bitmap blur(Context context, Bitmap source) {
        return blur(context, source, 25);
    }

    public static Bitmap blur(Context context, Bitmap source, int radius) {
        int width = Math.round(source.getWidth());
        int height = Math.round(source.getHeight());

        Bitmap destination = Bitmap.createScaledBitmap(source, width, height, false);

        RenderScript renderScript =  RenderScript.create(context);

        Allocation input = Allocation.createFromBitmap(renderScript, destination);
        Allocation output = Allocation.createTyped(renderScript, input.getType());

        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(destination);

        input.destroy();
        output.destroy();
        renderScript.destroy();
        return destination;
    }
}

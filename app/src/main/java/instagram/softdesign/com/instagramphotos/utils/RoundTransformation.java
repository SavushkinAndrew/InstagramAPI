package instagram.softdesign.com.instagramphotos.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

public class RoundTransformation implements Transformation {
    private int radius;

    public RoundTransformation(int radius) {
        this.radius = radius;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        if (source == null)
        {
            return null;
        }

        int diam = radius << 1;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        final Shader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);

        Bitmap targetBitmap = Bitmap.createBitmap(diam, diam, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);
        canvas.drawCircle(radius, radius, radius, paint);
        source.recycle();
        return targetBitmap;
    }

    @Override
    public String key() {
        return "аватар готов";
    }
}

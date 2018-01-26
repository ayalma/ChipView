package com.ayalma.chipview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.TypedValue;

/**
 * this class is for creating text drawable
 * Created by ali on 1/25/2018.
 */

public class TextDrawable extends Drawable {
    private static final float SHADE_FACTOR = 0.9f;

    private TextPaint textPaint;
    private Paint bgPaint, borderPaint;

    private String text;
    private RectF rect;

    private float borderThickness;
    private int height, textHeight, width;
    private float radius;

    public TextDrawable(Context context, String text) {
        this.text = text;

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(Color.GRAY);
        bgPaint.setStyle(Paint.Style.FILL);

        rect = new RectF(0, 0, 0, 0);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics()));


        Rect bounds = new Rect();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.getTextBounds(text, 0, 1, bounds);
        textHeight = bounds.height();

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(getDarkerShade(Color.GRAY));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeCap(Paint.Cap.ROUND);


    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getFontSize() {
        return textPaint.getTextSize();
    }

    public void setFontSize(float fontSize) {
        textPaint.setTextSize(fontSize);
    }

    public int getBackgroundColor() {
        return this.bgPaint.getColor();
    }

    public void setBackgroundColor(int backgroundColor) {
        this.bgPaint.setColor(backgroundColor);
        this.borderPaint.setColor(getDarkerShade(backgroundColor));
        this.textPaint.setColor(invertColor(backgroundColor,false));
    }

    public int getTextColor() {
        return this.textPaint.getColor();
    }

    public void setTextColor(int textColor) {
        this.textPaint.setColor(textColor);
    }

    public float getBorderThickness() {
        return borderThickness;
    }

    public void setBorderThickness(float borderThickness) {
        this.borderThickness = borderThickness;
        this.borderPaint.setStrokeWidth(borderThickness);
    }

    public void setTextAlign(Paint.Align align) {
        this.textPaint.setTextAlign(align);
    }


    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        this.rect.set(0, 0, width, rect.bottom);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        this.rect.set(0, 0, rect.right, height);
    }


    private int getDarkerShade(int color) {
        return Color.rgb((int) (SHADE_FACTOR * Color.red(color)),
                (int) (SHADE_FACTOR * Color.green(color)),
                (int) (SHADE_FACTOR * Color.blue(color)));
    }
    private int invertColor(int color,boolean bw) {


        if (bw) {
            // http://stackoverflow.com/a/3943023/112731
            return (Color.red(color) * 0.299 + Color.green(color) * 0.587 + Color.blue(color) * 0.114) > 186
                    ? Color.WHITE
                    : Color.BLACK;
        }

        return Color.rgb((int) (255 - Color.red(color)),
                (int) (255 - Color.green(color)),
                (int) (255 - Color.blue(color)));
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        rect.inset(0, 0);
        canvas.drawRoundRect(rect, radius, radius, bgPaint);

        rect.inset(borderThickness / 2, borderThickness / 2); // this is for setteing inside padding to drawable
        canvas.drawRoundRect(rect, radius, radius, borderPaint);

        canvas.drawText(text, width >> 1, (height + textHeight) >> 1, textPaint);
    }

    @Override
    public void setAlpha(int i) {
        this.textPaint.setAlpha(i);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.textPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }


}

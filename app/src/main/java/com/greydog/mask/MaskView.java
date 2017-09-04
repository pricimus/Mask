package com.greydog.mask;

/**
 * Created by edprice on 27/07/2017.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MaskView extends RelativeLayout
{
    Paint _paint;
    Button _spotlightButton;
    PorterDuffXfermode _blender;
    LayoutParams _spotlightButtonLayout;
    Matrix _matrix;
    Context _context;

    int _spotlightX;
    int _spotlightY;
    float _scale;

    public MaskView(Context context)
    {
        super(context);

        _context = context;
    }

    public MaskView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        _context = context;
    }

    public MaskView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        _context = context;
    }

    public Button getMaskButton()
    {
        return _spotlightButton;
    }

    public void init(Point point, String buttonText, float scale)
    {
        SetHardwareAccelerated(true);

        _spotlightX = point.x;
        _spotlightY = point.y;
        _scale = scale;

        _blender = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        _paint = new Paint();
        _paint.setColor(getResources().getColor(android.R.color.white));
        _paint.setAlpha(0);
        _paint.setXfermode(_blender);
        _paint.setAntiAlias(true);

        addButton(buttonText);
    }

    void addButton(String buttonText)
    {
        _spotlightButton = (Button) LayoutInflater.from(_context).inflate(R.layout.include_mask_button, null);

        _spotlightButtonLayout = (LayoutParams)generateDefaultLayoutParams();
        _spotlightButtonLayout.addRule(ALIGN_PARENT_BOTTOM);
        _spotlightButtonLayout.addRule(CENTER_IN_PARENT);
        _spotlightButtonLayout.bottomMargin = 10;

        _spotlightButton.setLayoutParams(_spotlightButtonLayout);
        _spotlightButton.setText(buttonText);

        addView(_spotlightButton);
    }

    public void SetHardwareAccelerated(boolean accelerated)
    {
        if (accelerated)
        {
            if (isHardwareAccelerated())
            {
            Paint hardwarePaint = new Paint();
            hardwarePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));

            setLayerType(LAYER_TYPE_HARDWARE, hardwarePaint);
            }
            else
            {
                setLayerType(LAYER_TYPE_SOFTWARE, null);
            }
        }
        else
        {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas)
    {
        canvas.drawColor(Color.parseColor("#8C000000"));

        _matrix = new Matrix();
        _matrix.postScale(_scale, _scale, _spotlightX, _spotlightY);
        canvas.setMatrix(_matrix);

        canvas.drawCircle(_spotlightX, _spotlightY, 200, _paint);

        canvas.setMatrix(new Matrix());

        super.dispatchDraw(canvas);
    }
}


package com.xinxin.facelinker.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by xinxin on 2015/7/19.
 */
public class IconfontTextView extends TextView {
    public IconfontTextView(Context paramContext)
    {
        super(paramContext);
    }

    public IconfontTextView(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
    }

    public IconfontTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
    }

    public void setTypeface(Typeface paramTypeface, int paramInt)
    {
        Typeface iconfont = Typeface.createFromAsset((getContext().getAssets()), "iconfont.ttf");
        super.setTypeface(iconfont);
//        if (!isInEditMode())
//            super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "iconfont.ttf"));
    }
}

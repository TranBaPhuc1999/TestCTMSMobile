package com.dmt.tranbaphuc1999.ctms;

import android.content.Context;
import android.widget.ExpandableListView;

public class CustomExpListView extends ExpandableListView
{
    public CustomExpListView(Context context)
    {
        super(context);
    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(1200, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}

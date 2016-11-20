package com.raindrops.util.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.raindrops.util.DensityUtils;
import com.raindrops.util.R;


public class LeftRightItem extends RelativeLayout {
    /**
     * 左边标题控件
     */
    private TextView leftTv;

    /**
     * 右边标题控件
     */
    private EditText rightTv;


    public LeftRightItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray style = context.obtainStyledAttributes(attrs, R.styleable.CommonItem, 0, 0);
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        Log.e("LeftRightItem width", width + "");
        int leftWidth = (int) style.getDimension(R.styleable.CommonItem_item_left_width, 0);
        int rightWidth = (int) style.getDimension(R.styleable.CommonItem_item_right_width, 0);

        if (rightWidth == 0 && leftWidth != 0) {
            rightWidth = width - leftWidth;
        } else if (rightWidth != 0 && leftWidth == 0) {
            leftWidth = width - rightWidth;
        }


        leftTv = new TextView(context);
        leftTv.setIncludeFontPadding(false);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                leftWidth == 0 ? LayoutParams
                        .WRAP_CONTENT : leftWidth);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        addView(leftTv, layoutParams);

        leftTv.setId(style.getResourceId(R.styleable.CommonItem_item_left_id,
                R.id.item_left));
        rightTv = new EditText(context);
        rightTv.setBackgroundColor(0);
        rightTv.setIncludeFontPadding(false);
        LayoutParams valueParams = new LayoutParams(LayoutParams.WRAP_CONTENT, rightWidth == 0 ?
                LayoutParams
                        .WRAP_CONTENT : rightWidth);
        valueParams.addRule(RelativeLayout.CENTER_VERTICAL);
        valueParams.addRule(RelativeLayout.RIGHT_OF, leftTv.getId());
        addView(rightTv, valueParams);
        rightTv.setPadding(0, 0, 0, 0);
        rightTv.setId(style.getResourceId(R.styleable.CommonItem_item_right_id,
                R.id.item_right));

        rightTv.setSingleLine(true);
        rightTv.setEllipsize(TextUtils.TruncateAt.END);
        rightTv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

        leftTv.setText(style.getText(R.styleable.CommonItem_item_left_text));
        leftTv.setTextColor(style.getColor(R.styleable.CommonItem_item_left_text_color, 0xff3B3B3B));

        leftTv.setTextSize(DensityUtils.px2sp(
                style.getDimension(R.styleable.CommonItem_item_left_text_size, 15)));

        rightTv.setText(style.getText(R.styleable.CommonItem_item_right_text));
        rightTv.setTextColor(style.getColor(R.styleable.CommonItem_item_right_text_color, 0xff848484));
        rightTv.setTextSize(DensityUtils.px2sp(style.getDimension(
                R.styleable.CommonItem_item_right_text_size, DensityUtils.sp2px(15f))));
        rightTv.setHintTextColor(style.getColor(R.styleable.CommonItem_item_right_hint_color,
                0xff848484));
        rightTv.setHint(style.getString(R.styleable.CommonItem_item_right_hint));

        boolean rightCanEdit = style.getBoolean(R.styleable.CommonItem_item_right_can_edit, false);
        setRightFocus(rightCanEdit);

        style.recycle();
    }


    public LeftRightItem setLeftText(String text) {
        leftTv.setText(text);
        return this;
    }

    public LeftRightItem setRightText(String msg) {
        rightTv.setText(msg);
        return this;
    }

    public LeftRightItem setRightHint(String msg) {
        rightTv.setHint(msg);
        return this;
    }

    public LeftRightItem setRightHintColor(int color) {
        rightTv.setHintTextColor(color);
        return this;
    }

    public LeftRightItem setRightGravity(int g) {
        rightTv.setGravity(g);
        return this;
    }

    public LeftRightItem setRightFocus(boolean b) {
        rightTv.setFocusable(b);
        rightTv.setFocusableInTouchMode(b);
        rightTv.setCursorVisible(b);
        return this;
    }

    public String getRightText() {
        return rightTv.getText().toString().trim();
    }


    public LeftRightItem setRightInputType(int type) {
        rightTv.setInputType(type);
        return this;
    }

    public EditText getRightTv() {
        return rightTv;
    }

    public LeftRightItem setRightFilters(int length) {
        rightTv.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
        return this;
    }

    public LeftRightItem setRightOnClickListener(OnClickListener listener) {
        rightTv.setOnClickListener(listener);
        return this;
    }

    public LeftRightItem addRightTextChangedListener(TextWatcher watcher) {
        rightTv.addTextChangedListener(watcher);
        return this;
    }

    public TextView getLeftTv() {
        return leftTv;
    }
}
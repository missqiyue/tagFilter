package com.qiyue.tagfilter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.qiyue.tagfilter.model.ITagFilterViewData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * CHENGC
 */
public class TagFilterView<T> extends ViewGroup {

    /**
     * 子View之间的行间距
     */
    private int verticalSpacing;
    /**
     * 子View之间的列间距
     */
    private int horizontalSpacing;

    /**
     * 是否显示标题
     */
    private boolean isShowTitle = false;
    /**
     * 是否支持多选
     */
    private boolean isMoreSelect = true;
    /**
     * 是否支持返选 - 只有单选模式有效
     */
    private boolean isBackSelect = false;
    /**
     * 是否显示不限/全部
     */
    private boolean isShowUnlimited = false;
    /**
     * 是否默认选择不限
     */
    private boolean isSelectUnlimited = false;
    /**
     * 是否自适应
     */
    private boolean isAdaptive = false;
    /**
     * 不限/全部
     */
    private String unlimitedText = "不限";
    /**
     * 标题字体大小
     */
    private int titleTextSize = 20;
    /**
     * 标题
     */
    private String titleText = "标题";
    /**
     * 标题默认字体验证
     */
    private int titleTextColor = Color.parseColor("#333333");

    /**
     * title与标签之间的间距
     */
    private int titleVerticalSpacing;
    /**
     * 标签字体
     */
    private int tagTextSize = 12;
    /**
     * 标签默认颜色
     */
    private int tagTextColor = Color.parseColor("#333333");
    /**
     * 标签选择颜色
     */
    private int tagTextColorSelect = Color.parseColor("#E03236");
    /**
     * 标签的宽度
     */
    private int tagWidth = 0;
    /**
     * 标签的高度
     */
    private int tagHeight = 0;
    /**
     * 标签的上间距
     */
    private int tagPaddingTop = 10;
    /**
     * 标签左间距
     */
    private int tagPaddingLeft = 15;
    /**
     * 标签右间距
     */
    private int tagPaddingRight = 15;
    /**
     * 标签底部间距
     */
    private int tagPaddingBottom = 10;
    /**
     * 标签背景
     */
    private int tagBackground;
    /**
     * 选中背景
     */
    private int tagBackgroundSelect;
    /**
     * 最大行数 默认不限制
     */
    private int maxRowNum = -1;
    /**
     * 最的列表数
     */
    private int maxColumn = 4;


    private Context mContext;
    /**
     * 标签
     */
    private List<TextView> mTags = new ArrayList<>();
    /**
     * 选中的标签
     */
    private List<TextView> mSelectTag = new ArrayList<>();
    /**
     * 选择的标签
     */
    private int mSelectTagIndex = -1;
    private String mSelectTagName;
    private List<String> mSelectTagsIndex = new ArrayList<>();
    private List<String> mSelectTagsName = new ArrayList<>();

    /***
     * 数据源
     */
    private List<T> mList = new ArrayList<>();
    private String[] tags;

    public TagFilterView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public TagFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public TagFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;

        verticalSpacing = dip2px(10);
        horizontalSpacing = dip2px(10);
        titleVerticalSpacing = dip2px(15);

        isShowTitle = false;
        isBackSelect = false;
        isMoreSelect = true;
        isShowUnlimited = false;
        isSelectUnlimited = false;
        isAdaptive = false;

        maxRowNum = Integer.MAX_VALUE;

        unlimitedText = "不限";
        titleText = "标签选择";

        titleTextSize = pxToSp(20);
        titleTextColor = Color.parseColor("#333333");

        tagTextSize = pxToSp(12);
        tagTextColor = Color.parseColor("#333333");
        tagTextColorSelect = Color.parseColor("#E03236");
        tagWidth = 0;
        tagHeight = 0;
        maxColumn = 4;
        tagBackground = R.drawable.bg_tag_defualt;
        tagBackgroundSelect = R.drawable.bg_tag_select;
        tagPaddingTop = dip2px(5);
        tagPaddingBottom = dip2px(5);
        tagPaddingLeft = dip2px(15);
        tagPaddingRight = dip2px(15);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TagFilterView);

        try {
            //通过属性获取配置信息

            //行列间距
            verticalSpacing = (int) array.getDimension(R.styleable.TagFilterView_verticalSpacing, verticalSpacing);
            horizontalSpacing = (int) array.getDimension(R.styleable.TagFilterView_horizontalSpacing, horizontalSpacing);
            titleVerticalSpacing = (int) array.getDimension(R.styleable.TagFilterView_titleVerticalSpacing, titleVerticalSpacing);

            //配置显示项
            isShowTitle = array.getBoolean(R.styleable.TagFilterView_isShowTitle, isShowTitle);
            isAdaptive = array.getBoolean(R.styleable.TagFilterView_isAdaptive, isAdaptive);
            isShowUnlimited = array.getBoolean(R.styleable.TagFilterView_isShowUnlimited, isShowUnlimited);
            isSelectUnlimited = array.getBoolean(R.styleable.TagFilterView_isSelectUnlimited, isSelectUnlimited);
            isMoreSelect = array.getBoolean(R.styleable.TagFilterView_isMoreSelect, isMoreSelect);
            isBackSelect = array.getBoolean(R.styleable.TagFilterView_isBackSelect, isBackSelect);

            //配置不限和标题
            unlimitedText = array.getString(R.styleable.TagFilterView_unlimitedText);
            titleText = array.getString(R.styleable.TagFilterView_tagTitleText);

            //标签的宽高
            if (!isAdaptive) {
                tagWidth = (int) array.getDimension(R.styleable.TagFilterView_tagWidth, tagWidth);
                tagHeight = (int) array.getDimension(R.styleable.TagFilterView_tagHeight, tagHeight);
                maxColumn = array.getInteger(R.styleable.TagFilterView_maxColumn, maxColumn);
            }

            maxRowNum = array.getInteger(R.styleable.TagFilterView_maxRowNum, maxRowNum);

            if (maxRowNum <= 0) {
                throw new NumberFormatException("maxRowNum 必须大于0，默认为不限制行数");
            }
            if (maxColumn <= 0) {
                throw new NumberFormatException("maxColumn 必须大于0，默认为以手机屏幕宽分成四等分，即每行显示4列");
            }

            //标题字体大小和颜色
            titleTextSize = array.getInteger(R.styleable.TagFilterView_titleTextSize, titleTextSize);
            titleTextColor = array.getColor(R.styleable.TagFilterView_titleTextColor, titleTextColor);

            //标签相关属性
            tagTextSize = array.getInteger(R.styleable.TagFilterView_tagTextSize, tagTextSize);
            tagTextColor = array.getColor(R.styleable.TagFilterView_tagTextColor, tagTextColor);
            tagTextColorSelect = array.getColor(R.styleable.TagFilterView_tagTextColorSelect, tagTextColorSelect);
            tagBackground = array.getResourceId(R.styleable.TagFilterView_tagBackground, R.drawable.bg_tag_defualt);
            tagBackgroundSelect = array.getResourceId(R.styleable.TagFilterView_tagBackgroundSelect, R.drawable.bg_tag_select);
            tagPaddingTop = (int) array.getDimension(R.styleable.TagFilterView_tagPaddingTop, tagPaddingTop);
            tagPaddingRight = (int) array.getDimension(R.styleable.TagFilterView_tagPaddingRight, tagPaddingRight);
            tagPaddingLeft = (int) array.getDimension(R.styleable.TagFilterView_tagPaddingLeft, tagPaddingLeft);
            tagPaddingBottom = (int) array.getDimension(R.styleable.TagFilterView_tagPaddingBottom, tagPaddingBottom);
        } finally {
            array.recycle();
        }
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {

        final int parentLeft = getPaddingLeft();
        final int parentRight = right - left - getPaddingRight();
        final int parentTop = getPaddingTop();

        //子View左右间距
        int childViewLeft = parentLeft;
        int childViewTop = parentTop;

        int row = 1;//行数
        //行高最大值
        int rowMaxHeight = 0;
        //获取子view
        final int childCount = getChildCount();

        //遍历子view
        for (int i = 0; i < childCount; i++) {

            final View child = getChildAt(i);

            final int childWidth = child.getMeasuredWidth();
            final int childHeight = child.getMeasuredHeight();

            if (child.getVisibility() != View.GONE) {

                if (childViewLeft + childWidth > parentRight) {

                    //设置显示行数 由于行数是从
                    if (maxRowNum > 0 && row == maxRowNum) {
                        break;
                    }

                    //换行
                    childViewLeft = parentLeft;
                    if (isShowTitle && row == 1) {//如果显示title的时候，也要计算title与tag之间的高度
                        childViewTop += rowMaxHeight + titleVerticalSpacing;
                    } else {
                        childViewTop += rowMaxHeight + verticalSpacing;
                    }
                    rowMaxHeight = childHeight;

                    //记录行数
                    row++;
                } else {
                    rowMaxHeight = Math.max(rowMaxHeight, childHeight);
                }
                //重新绘制子view的位置
                child.layout(childViewLeft, childViewTop, childViewLeft + childWidth, childViewTop + childHeight);
                childViewLeft += childWidth;
                if (i < childCount - 1) {
                    childViewLeft += +horizontalSpacing;
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //测量子View视图
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = 0;

        int row = 1;//行数
        int rowWidth = 0;//行宽
        int rowMaxHeiht = 0;//行高

        //获取子view的总数
        int count = getChildCount();
        //遍历子view
        for (int i = 0; i < count; i++) {

            final View child = getChildAt(i);

            final int childWidth = child.getMeasuredWidth();
            final int childHeight = child.getMeasuredHeight();

            //判断子view是否是不占位隐藏，如果是占位隐藏，还有计算
            if (child.getVisibility() != View.GONE) {

                rowWidth += childWidth;

                //判断行的宽度是否已经大于组件的宽度，如果大于则就换行
                if (rowWidth > widthSize) {
                    //下一行
                    rowWidth = childWidth;
                    //高度
                    if (isShowTitle && row == 1) {
                        height += rowMaxHeiht + titleVerticalSpacing;
                    } else {
                        height += rowMaxHeiht + verticalSpacing;
                    }
                    //下一行的高度
                    rowMaxHeiht = childHeight;
                    //记录行数
                    row++;
                } else {
                    //单行的高度
                    rowMaxHeiht = Math.max(rowMaxHeiht, childHeight);
                }
                if (i < count - 1) {
                    rowWidth += horizontalSpacing;
                }
            }

            //设置显示行数
            if (maxRowNum > 0 && row == maxRowNum) {
                break;
            }
        }

        //最后一行的高度
        height += rowMaxHeiht;
        //最后一行的内间距
        height += getPaddingBottom() + getPaddingTop();

        //如果标签只有一行，则就设置宽度包裹标签
        if (row == 1) {
            width += rowWidth;
            width += getPaddingLeft() + getPaddingRight();
        } else {
            //如果标签超一行，则设置宽度填充父布局
            width = widthSize;
        }
        //设置组件大小
        setMeasuredDimension(widthModel == MeasureSpec.EXACTLY ? widthSize : width, heightModel == MeasureSpec.EXACTLY ? heightSize : height);
    }

    /**
     * @param tags 标签数据 List<T>
     */
    public void onCreateTagByObject(List<T> tags) {
        onCreateTagByObject(tags, false);
    }

    public void onCreateTagByObject(List<T> tags, boolean isRefresh) {
        if (isRefresh) {
            removeAllViews();
        }
        onCreateTagByObjList(tags);
    }

    /**
     * @param tags 标签数据 String[]
     */
    public void onCreateTag(String[] tags) {
        onCreateTag(tags, false);
    }

    public void onCreateTag(String[] tags, boolean isRefresh) {
        if (isRefresh) {
            removeAllViews();
        }
        onCreateTagByStringArray(tags);
    }

    /**
     * @param tags 标签数据 List<String>
     */
    public void onCreateTag(List<String> tags) {
        onCreateTag(tags, false);
    }

    public void onCreateTag(List<String> tags, boolean isRefresh) {
        if (isRefresh) {
            removeAllViews();
        }
        onCreateTagByStringList(tags);
    }

    private void setTagWhSize() {
        //计算tag的宽高
        if (!isAdaptive) {
            if (tagWidth == 0) {
                int parentPadding = 0;
                if (null != getParent()) {
                    ViewGroup viewGroup = (ViewGroup) getParent();
                    parentPadding = viewGroup.getPaddingLeft() + viewGroup.getPaddingRight();
                }
                tagWidth = ((getScreenWidth() - parentPadding - getPaddingRight() - getPaddingLeft() - (horizontalSpacing * 3)) / maxColumn);
            }
            if (tagHeight == 0) {
                tagHeight = dip2px(32);
            }
        } else {
            if (tagWidth == 0) {
                tagWidth = 0;
            }
            if (tagHeight == 0) {
                tagHeight = 0;
            }
        }
    }

    /**
     * @param tags
     */
    public void onCreateTagByStringArray(String[] tags) {
        this.tags = tags;
        //计算Tag的宽高度
        setTagWhSize();

        //创建标题
        if (isShowTitle) {
            TextView titleTv = new TextView(mContext);
            titleTv.setText(TextUtils.isEmpty(titleText) ? "标题" : titleText);
            titleTv.setTextColor(titleTextColor);
            titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize);
            LinearLayout.LayoutParams paramsTitle = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            paramsTitle.setMargins(0, 0, 0, titleVerticalSpacing);
            titleTv.setLayoutParams(paramsTitle);
            addView(titleTv);
        }

        //创建标签
        for (int i = 0; i < tags.length; i++) {
            TextView textView = onCreateTagView(tags[i], i);
            int index = i;
            final int finalIndex = index;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTagClick(view, finalIndex);
                }
            });
        }
    }

    public void onCreateTagByStringList(List<String> tags) {
        this.mList.clear();
        if (tags != null) {
            this.mList.addAll((Collection<? extends T>) tags);
        }
        //计算Tag的宽高度
        setTagWhSize();

        //创建标题
        if (isShowTitle) {
            TextView titleTv = new TextView(mContext);
            titleTv.setText(TextUtils.isEmpty(titleText) ? "标题" : titleText);
            titleTv.setTextColor(titleTextColor);
            titleTv.setTextSize(titleTextSize);

            LayoutParams paramsTitle = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            titleTv.setLayoutParams(paramsTitle);

            addView(titleTv);
        }

        //创建标签
        for (int i = 0; i < tags.size(); i++) {
            TextView textView = onCreateTagView(tags.get(i), i);
            int index = i;
            final int finalIndex = index;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTagClick(view, finalIndex);
                }
            });
        }
    }


    public void onCreateTagByObjList(List<T> tags) {
        this.mList.clear();
        if (tags != null) {
            this.mList.addAll(tags);
        }
        //计算Tag的宽高度
        setTagWhSize();

        //创建标题
        if (isShowTitle) {
            TextView titleTv = new TextView(mContext);
            titleTv.setText(TextUtils.isEmpty(titleText) ? "标题" : titleText);
            titleTv.setTextColor(titleTextColor);
            titleTv.setTextSize(titleTextSize);

            LayoutParams paramsTitle = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            titleTv.setLayoutParams(paramsTitle);

            addView(titleTv);
        }
        //创建标签
        for (int i = 0; i < tags.size(); i++) {
            T data = tags.get(i);
            TextView textView;
            if (data instanceof ITagFilterViewData) {
                textView = onCreateTagView(((ITagFilterViewData) data).getTagFilterLabelViewText(), i);
            } else {
                textView = onCreateTagView(data.toString(), i);
            }
            final int finalIndex = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTagClick(view, finalIndex);
                }
            });
        }
    }

    /**
     * 创建标签
     *
     * @param text 文案
     * @return
     */
    private TextView onCreateTagView(String text, int index) {
        TextView tag = new TextView(mContext);
        tag.setText(text);
        tag.setGravity(Gravity.CENTER);
        tag.setTextColor(tagTextColor);
        tag.setTextSize(TypedValue.COMPLEX_UNIT_SP, tagTextSize);
        tag.setBackgroundResource(tagBackground);
        LayoutParams paramsTag;
        if (tagHeight > 0 && tagWidth <= 0) {
            paramsTag = new LayoutParams(LayoutParams.WRAP_CONTENT, tagHeight);
        } else if (tagHeight <= 0 && tagWidth > 0) {
            paramsTag = new LayoutParams(tagWidth, LayoutParams.WRAP_CONTENT);
        } else if (tagHeight > 0 && tagWidth > 0) {
            paramsTag = new LayoutParams(tagWidth, tagHeight);
        } else {
            tag.setPadding(tagPaddingLeft, tagPaddingTop, tagPaddingRight, tagPaddingBottom);
            paramsTag = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        tag.setLayoutParams(paramsTag);

        if (isShowUnlimited && isSelectUnlimited && text.equals(unlimitedText)) {
            tag.setTextColor(tagTextColorSelect);
            tag.setBackgroundResource(tagBackgroundSelect);
            mSelectTag.add(tag);
            mSelectTagsIndex.add(index + "");
            mSelectTagIndex = index;
            mSelectTagName = unlimitedText;
            mSelectTagsName.add(unlimitedText);
        }

        addView(tag);
        mTags.add(tag);
        return tag;
    }


    private int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, mContext
                .getResources().getDisplayMetrics());
    }

    private int pxToSp(int px) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return (int) (displayMetrics.density * 160 / displayMetrics.densityDpi) * px;
    }

    /**
     * 获取屏幕的宽
     *
     * @return
     */
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        return size.x - params.leftMargin - params.rightMargin;
    }

    /**
     * tag点击事件处理
     *
     * @param view
     */
    private void onTagClick(View view, int position) {
        TextView tagTextView = ((TextView) view);
        if (isShowUnlimited && unlimitedText.equals(tagTextView.getText())) {//不限
            for (TextView textView : mSelectTag) {
                textView.setTextColor(tagTextColor);
                textView.setBackgroundResource(tagBackground);
            }
            mSelectTag.clear();
            mSelectTagsIndex.clear();
            mSelectTagsName.clear();
            mSelectTagIndex = position;
            mSelectTagName = unlimitedText;
            tagTextView.setBackgroundResource(tagBackgroundSelect);
            tagTextView.setTextColor(tagTextColorSelect);
            mSelectTagsIndex.add(position + "");
            mSelectTagsName.add(tagTextView.getText().toString());
            mSelectTag.add(tagTextView);
            isSelectUnlimited = true;
            if (isMoreSelect) {//多选
                if (null != moreSelectClickListener) {
                    moreSelectClickListener.onClicked(position, tagTextView.getText().toString(), onListToList(mSelectTagsIndex), mSelectTagsName);
                }
            } else {//单选
                if (null != singleSelectClickListener) {
                    singleSelectClickListener.onClicked(position, tagTextView.getText().toString());
                }
            }
        } else {
            if (isMoreSelect) {//支持多选
                if (isShowUnlimited && isSelectUnlimited) {
                    for (TextView textView : mSelectTag) {
                        textView.setTextColor(tagTextColor);
                        textView.setBackgroundResource(tagBackground);
                    }
                    mSelectTag.clear();
                    mSelectTagsName.clear();
                    mSelectTagsIndex.clear();
                    isSelectUnlimited = false;
                }
                if (!mSelectTag.contains(tagTextView)) {
                    tagTextView.setBackgroundResource(tagBackgroundSelect);
                    tagTextView.setTextColor(tagTextColorSelect);
                    mSelectTag.add(tagTextView);
                    mSelectTagsIndex.add(position + "");
                    mSelectTagsName.add(tagTextView.getText().toString());
                } else {
                    mSelectTag.remove(tagTextView);
                    mSelectTagsName.remove(tagTextView.getText().toString());
                    mSelectTagsIndex.remove(position + "");
                    tagTextView.setTextColor(tagTextColor);
                    tagTextView.setBackgroundResource(tagBackground);
                }

                if (null != moreSelectClickListener) {
                    if (null != mSelectTagsIndex && mSelectTagsIndex.size() > 0) {
                        moreSelectClickListener.onClicked(position, tagTextView.getText().toString(), onListToList(mSelectTagsIndex), mSelectTagsName);
                    } else {
                        moreSelectClickListener.onClicked(-1, "", onListToList(mSelectTagsIndex), mSelectTagsName);
                    }
                }
            } else if (isBackSelect) {//单选可反选
                if (mSelectTagsIndex.contains(position + "")) {
                    for (TextView textView : mSelectTag) {
                        textView.setBackgroundResource(tagBackground);
                        textView.setTextColor(tagTextColor);
                    }

                    tagTextView.setTextColor(tagTextColor);
                    tagTextView.setBackgroundResource(tagBackground);
                    mSelectTag.clear();
                    mSelectTagsName.clear();
                    mSelectTagsIndex.clear();

                    if (null != singleSelectClickListener) {
                        singleSelectClickListener.onClicked(-1, "");
                    }
                } else {
                    for (TextView textView : mSelectTag) {
                        textView.setTextColor(tagTextColor);
                        textView.setBackgroundResource(tagBackground);
                    }
                    mSelectTag.clear();
                    mSelectTagsName.clear();
                    mSelectTagsIndex.clear();

                    tagTextView.setTextColor(tagTextColorSelect);
                    tagTextView.setBackgroundResource(tagBackgroundSelect);
                    mSelectTag.add(tagTextView);
                    mSelectTagsIndex.add(position + "");
                    mSelectTagsName.add(tagTextView.getText().toString());

                    if (null != singleSelectClickListener) {
                        singleSelectClickListener.onClicked(position, tagTextView.getText().toString());
                    }
                }

            } else {//单选

                if (!mSelectTag.contains(tagTextView)) {
                    for (TextView textView : mSelectTag) {
                        textView.setTextColor(tagTextColor);
                        textView.setBackgroundResource(tagBackground);
                    }
                    mSelectTag.clear();
                    mSelectTagsName.clear();
                    mSelectTagsIndex.clear();

                    tagTextView.setBackgroundResource(tagBackgroundSelect);
                    tagTextView.setTextColor(tagTextColorSelect);
                    mSelectTag.add(tagTextView);
                    mSelectTagsIndex.add(position + "");
                    mSelectTagsName.add(tagTextView.getText().toString());
                }
                if (null != singleSelectClickListener) {
                    singleSelectClickListener.onClicked(position, tagTextView.getText().toString());
                }
            }
        }
    }

    /**
     * 设置选中项（单项）
     *
     * @param index 下标
     */
    public void setSelectTagByIndex(int index) {
        this.mSelectTagIndex = index;
        mSelectTag.clear();
        mSelectTagsName.clear();
        mSelectTagsIndex.clear();
        if (null != mTags && mTags.size() > 0) {
            for (int i = 0; i < mTags.size(); i++) {
                TextView textView = mTags.get(i);
                if (index == i) {
                    if (isSelectUnlimited && textView.getText().toString().equals(unlimitedText)) {
                        isSelectUnlimited = true;
                    }
                    mSelectTag.add(textView);
                    mSelectTagsName.add(textView.getText().toString());
                    mSelectTagsIndex.add(i + "");
                    mSelectTagName = textView.getText().toString();
                    textView.setBackgroundResource(tagBackgroundSelect);
                    textView.setTextColor(tagTextColorSelect);
                } else {
                    textView.setBackgroundResource(tagBackground);
                    textView.setTextColor(tagTextColor);
                }
            }
        }
    }


    /**
     * 设置选中项（多项）
     *
     * @param selectTagsIndex 下标
     */
    public void setSelectTagsByIndex(List<Integer> selectTagsIndex) {
        mSelectTagIndex = -1;
        mSelectTag.clear();
        mSelectTagsName.clear();
        mSelectTagsIndex.clear();
        if (selectTagsIndex == null || selectTagsIndex.size() == 0) {
            return;
        }

        if (null != mTags && mTags.size() > 0) {
            for (int i = 0; i < mTags.size(); i++) {
                TextView textView = mTags.get(i);
                if (selectTagsIndex.contains(i)) {
                    if (isSelectUnlimited && textView.getText().toString().equals(unlimitedText)) {
                        isSelectUnlimited = true;
                    }
                    mSelectTag.add(textView);
                    mSelectTagsName.add(textView.getText().toString());
                    mSelectTagsIndex.add(i + "");
                    mSelectTagName = textView.getText().toString();
                    textView.setBackgroundResource(tagBackgroundSelect);
                    textView.setTextColor(tagTextColorSelect);
                } else {
                    textView.setBackgroundResource(tagBackground);
                    textView.setTextColor(tagTextColor);
                }
            }
        }
    }


    /**
     * 设置选中项（单项）
     *
     * @param tagName 标签名称
     */
    public void setSelectTagByName(String tagName) {
        this.mSelectTagName = tagName;
        mSelectTagIndex = -1;
        mSelectTag.clear();
        mSelectTagsName.clear();
        mSelectTagsIndex.clear();
        if (null != mTags && mTags.size() > 0) {
            for (int i = 0; i < mTags.size(); i++) {
                TextView textView = mTags.get(i);
                if (tagName.equals(textView.getText().toString())) {
                    if (isSelectUnlimited && textView.getText().toString().equals(unlimitedText)) {
                        isSelectUnlimited = true;
                    }
                    mSelectTag.add(textView);
                    mSelectTagsName.add(textView.getText().toString());
                    mSelectTagsIndex.add(i + "");
                    mSelectTagIndex = i;
                    mSelectTagName = textView.getText().toString();
                    textView.setBackgroundResource(tagBackgroundSelect);
                    textView.setTextColor(tagTextColorSelect);
                } else {
                    textView.setBackgroundResource(tagBackground);
                    textView.setTextColor(tagTextColor);
                }
            }
        }
    }


    /**
     * 设置选中项（多项）
     *
     * @param tagsName 标签名称
     */
    public void setSelectTagsByName(List<String> tagsName) {
        mSelectTagName = "";
        mSelectTagIndex = -1;
        mSelectTag.clear();
        mSelectTagsName.clear();
        mSelectTagsIndex.clear();

        if (tagsName == null || tagsName.size() == 0) {
            return;
        }

        if (null != mTags && mTags.size() > 0) {
            for (int i = 0; i < mTags.size(); i++) {
                TextView textView = mTags.get(i);
                if (tagsName.contains((textView.getText().toString()))) {
                    if (isSelectUnlimited && textView.getText().toString().equals(unlimitedText)) {
                        isSelectUnlimited = true;
                    }
                    mSelectTag.add(textView);
                    mSelectTagsName.add(textView.getText().toString());
                    mSelectTagsIndex.add(i + "");
                    mSelectTagIndex = i;
                    mSelectTagName = textView.getText().toString();
                    textView.setBackgroundResource(tagBackgroundSelect);
                    textView.setTextColor(tagTextColorSelect);
                } else {
                    textView.setBackgroundResource(tagBackground);
                    textView.setTextColor(tagTextColor);
                }
            }
        }
    }

    /**
     * 设置选中项（单项）
     *
     * @param key 标签名称
     */
    public void setSelectTagByKey(String key) {
        mSelectTagName = "";
        mSelectTagIndex = -1;
        mSelectTag.clear();
        mSelectTagsName.clear();
        mSelectTagsIndex.clear();
        if (null != mTags && mTags.size() > 0 && null != mList && mList.size() > 0) {
            for (int i = 0; i < mTags.size(); i++) {
                T data = mList.get(i);
                TextView textView = mTags.get(i);
                if (data != null && data instanceof ITagFilterViewData) {
                    if (key.equals(((ITagFilterViewData) data).getTagFilterLabelViewValue())) {
                        if (isSelectUnlimited && textView.getText().toString().equals(unlimitedText)) {
                            isSelectUnlimited = true;
                        }
                        mSelectTag.add(textView);
                        mSelectTagsName.add(textView.getText().toString());
                        mSelectTagsIndex.add(i + "");
                        mSelectTagIndex = i;
                        mSelectTagName = textView.getText().toString();
                        textView.setBackgroundResource(tagBackgroundSelect);
                        textView.setTextColor(tagTextColorSelect);
                    } else {
                        textView.setBackgroundResource(tagBackground);
                        textView.setTextColor(tagTextColor);
                    }
                } else {
                    textView.setBackgroundResource(tagBackground);
                    textView.setTextColor(tagTextColor);
                }
            }
        }
    }

    /**
     * 设置选中项（多项）
     *
     * @param keys 标签名称
     */
    public void setSelectTagsByKey(List<String> keys) {
        mSelectTagName = "";
        mSelectTagIndex = -1;
        mSelectTag.clear();
        mSelectTagsName.clear();
        mSelectTagsIndex.clear();

        if (keys == null || keys.size() == 0) {
            return;
        }

        if (null != mTags && mTags.size() > 0 && null != mList && mList.size() > 0) {
            for (int i = 0; i < mTags.size(); i++) {
                T data = mList.get(i);
                TextView textView = mTags.get(i);
                if (data != null && data instanceof ITagFilterViewData) {
                    if (keys.contains((((ITagFilterViewData) data).getTagFilterLabelViewValue()))) {
                        if (isSelectUnlimited && textView.getText().toString().equals(unlimitedText)) {
                            isSelectUnlimited = true;
                        }
                        mSelectTag.add(textView);
                        mSelectTagsName.add(textView.getText().toString());
                        mSelectTagsIndex.add(i + "");
                        mSelectTagIndex = i;
                        mSelectTagName = textView.getText().toString();
                        textView.setBackgroundResource(tagBackgroundSelect);
                        textView.setTextColor(tagTextColorSelect);
                    } else {
                        textView.setBackgroundResource(tagBackground);
                        textView.setTextColor(tagTextColor);
                    }
                } else {
                    textView.setBackgroundResource(tagBackground);
                    textView.setTextColor(tagTextColor);
                }
            }
        }
    }

    public List<Integer> getSelectTagsIndex() {
        return onListToList(mSelectTagsIndex);
    }

    public List<String> getSelectTagsName() {
        return mSelectTagsName;
    }

    public List<T> getListData() {
        return mList;
    }

    public String[] getStringData() {
        return tags;
    }

    private OnSingleSelectClickListener singleSelectClickListener;
    private OnMoreSelectClickListener moreSelectClickListener;

    public interface OnSingleSelectClickListener {
        void onClicked(int position, String tag);
    }

    public interface OnMoreSelectClickListener {
        void onClicked(int position, String tag, List<Integer> mSelectIndex, List<String> mSelectTag);
    }

    public void setSingleSelectClickListener(OnSingleSelectClickListener singleSelectClickListener) {
        this.singleSelectClickListener = singleSelectClickListener;
    }

    public void setMoreSelectClickListener(OnMoreSelectClickListener moreSelectClickListener) {
        this.moreSelectClickListener = moreSelectClickListener;
    }

    /**
     * List<String> -->List<Integer>
     *
     * @param list list集合
     * @return
     */
    public ArrayList<Integer> onListToList(List<String> list) {
        ArrayList<Integer> mList = new ArrayList<>();
        for (String str : list) {
            mList.add(Integer.parseInt(str));
        }
        return mList;
    }
}

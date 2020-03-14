package leo.work.support.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import leo.work.support.R;
import leo.work.support.Support.ToolSupport.A2BSupport;
import leo.work.support.Support.ToolSupport.LeoSupport;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/5/30
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class TopBar extends RelativeLayout {
    private ImageView iv_Back;
    private TextView tv_Title;
    private ImageView iv_Menu;
    private TextView tv_Menu;

    //数据
    private int background;//背景色

    private boolean hasShowBreak;//是否显示返回键
    private int backImage;

    private String title;
    private int titleColor;
    private float titleSize;

    private boolean hasShowMenu;
    private int menuIma;
    private String menuText;
    private int menuTextColor;
    private float menuTextSize;

    private OnBreakClickListener onBreakClickListener;

    public TopBar(final Context context, AttributeSet attrs) {
        super(context, attrs);
        /**
         * 初始化数据
         */
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TopBar, 0, 0);

        background = ta.getResourceId(R.styleable.TopBar_background, LeoSupport.bgColor);

        hasShowBreak = ta.getBoolean(R.styleable.TopBar_hasShowBreak, true);
        backImage = ta.getResourceId(R.styleable.TopBar_BreakIma, LeoSupport.backIcon);

        title = ta.getString(R.styleable.TopBar_Title);
        titleColor = ta.getColor(R.styleable.TopBar_TitleColor, getResources().getColor(LeoSupport.titleColor));
        titleSize = ta.getDimension(R.styleable.TopBar_TitleSize, 18);

        hasShowMenu = ta.getBoolean(R.styleable.TopBar_hasShowMenu, false);
        menuIma = ta.getResourceId(R.styleable.TopBar_MenuIma, R.color.transp);
        menuText = ta.getString(R.styleable.TopBar_MenuText);
        menuTextColor = ta.getColor(R.styleable.TopBar_MenuTextColor, getResources().getColor(LeoSupport.menuTextColor));
        menuTextSize = ta.getDimension(R.styleable.TopBar_MenuTextSize, 16);

        /**
         * 初始化控件
         */
        iv_Back = new ImageView(context);
        tv_Title = new TextView(context);
        iv_Menu = new ImageView(context);
        tv_Menu = new TextView(context);
        /**
         * 设置背景
         */
        setBackgroundResource(background);
        /**
         * 返回键
         */
        iv_Back.setPadding(A2BSupport.dp2px( 14), A2BSupport.dp2px( 14), A2BSupport.dp2px( 14), A2BSupport.dp2px( 14));
        iv_Back.setImageDrawable(getResources().getDrawable(backImage));
        if (hasShowBreak)
            iv_Back.setVisibility(VISIBLE);
        else
            iv_Back.setVisibility(INVISIBLE);
        LayoutParams layte1 = new LayoutParams(A2BSupport.dp2px( 44), A2BSupport.dp2px( 44));
        layte1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layte1.addRule(RelativeLayout.CENTER_VERTICAL);
        iv_Back.setLayoutParams(layte1);

        /**
         * 标题
         */
        tv_Title.setMaxLines(1);
        tv_Title.setEllipsize(TextUtils.TruncateAt.END);
        tv_Title.setText(title);
        tv_Title.setTextColor(titleColor);
        tv_Title.setTextSize(titleSize);
        tv_Title.setGravity(Gravity.CENTER);
        tv_Title.setPadding(A2BSupport.dp2px( 44), 0, A2BSupport.dp2px( 44), 0);
        LayoutParams layte2 = new LayoutParams(LayoutParams.MATCH_PARENT, A2BSupport.dp2px( 44));
        layte2.addRule(RelativeLayout.CENTER_VERTICAL);
        tv_Title.setLayoutParams(layte2);


        //优先显示文字
        if (menuText != null && !menuText.equals("")) {
            /**
             * 文字Menu
             */
            tv_Menu.setText(menuText);
            tv_Menu.setTextColor(menuTextColor);
            tv_Menu.setTextSize(menuTextSize);
            tv_Menu.setGravity(Gravity.CENTER);
            tv_Menu.setPadding(A2BSupport.dp2px( 20), 0, A2BSupport.dp2px( 20), 0);
            LayoutParams layte3 = new LayoutParams(LayoutParams.WRAP_CONTENT, A2BSupport.dp2px( 44));
            layte3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layte3.addRule(RelativeLayout.CENTER_VERTICAL);
            tv_Menu.setLayoutParams(layte3);

            tv_Menu.setVisibility(VISIBLE);
            iv_Menu.setVisibility(GONE);
        } else {
            /**
             * 图形Menu
             */
            iv_Menu.setPadding(A2BSupport.dp2px(14), A2BSupport.dp2px(14), A2BSupport.dp2px(14), A2BSupport.dp2px(14));
            iv_Menu.setImageDrawable(getResources().getDrawable(menuIma));
            LayoutParams layte4 = new LayoutParams(A2BSupport.dp2px(44), A2BSupport.dp2px(44));
            layte4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layte4.addRule(RelativeLayout.CENTER_VERTICAL);
            iv_Menu.setLayoutParams(layte4);

            tv_Menu.setVisibility(GONE);
            iv_Menu.setVisibility(VISIBLE);
        }

        addView(tv_Title);//
        addView(iv_Back);//
        if (hasShowMenu) {
            addView(tv_Menu);//
            addView(iv_Menu);//
        }

        //设置监听事件
        iv_Back.setOnClickListener(
            new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onBreakClickListener != null) {
                        onBreakClickListener.OnBreakClick();
                    }
                }
            }
        );
        iv_Menu.setOnClickListener(
            new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onBreakClickListener != null)
                        onBreakClickListener.OnMenuClick();
                }
            }
        );
        tv_Menu.setOnClickListener(
            new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBreakClickListener != null)
                        onBreakClickListener.OnMenuClick();
                }
            }
        );
        ta.recycle();
    }

    public interface OnBreakClickListener {
        public void OnBreakClick();

        public void OnMenuClick();
    }

    public void setOnClickListener(OnBreakClickListener onBreakClickListener) {
        this.onBreakClickListener = onBreakClickListener;
    }

    public void setTitle(String txt) {
        tv_Title.setText(txt);
    }

    public void setMenuText(String txt) {
        tv_Menu.setText(txt);
    }

    public void setMenuTextColor(int color) {
        tv_Menu.setTextColor(getResources().getColor(color));
    }

    public void isShowMenu(boolean b) {
        if (b) {
            iv_Menu.setVisibility(VISIBLE);
            tv_Menu.setVisibility(VISIBLE);
        } else {
            iv_Menu.setVisibility(INVISIBLE);
            tv_Menu.setVisibility(INVISIBLE);
        }
    }

    public void setTopBarBg(int bg) {
        background = bg;
        setBackgroundResource(background);
    }

    public void setTopBarAlpha(float alpha) {
        setAlpha(alpha);
    }

    public void setTopBarBgAlpha(int alpha) {
        getBackground().setAlpha(alpha);
    }
}

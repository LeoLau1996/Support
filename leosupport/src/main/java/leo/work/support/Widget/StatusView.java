package leo.work.support.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import leo.work.support.Info.StatusInfo;
import leo.work.support.R;


/**
 * Created by Best100_Android on 17/11/16.
 */

public class StatusView extends RelativeLayout {
    private RelativeLayout RL_Status;
    private LinearLayout LL_Loading;
    private LinearLayout LL_Refresh;
    private ImageView iv_Loading;
    private ImageView iv_Refresh;
    private TextView tv_Refresh;
    private Button btn_Refresh;

    public String State = StatusInfo.NullContent;
    private int image_NullNetWork = R.drawable.image_nullnetword;
    private int image_NullContent = R.drawable.image_nullcontent;
    private boolean isShowButton = true;
    private AnimationDrawable animaition;
    private OnBtnClickListener onBtnClickListener;

    public StatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.statusview, this);
        RL_Status = findViewById(R.id.RL_Status);
        LL_Loading = findViewById(R.id.LL_Loading);
        LL_Refresh = findViewById(R.id.LL_Refresh);
        iv_Refresh = findViewById(R.id.iv_Refresh);
        tv_Refresh = findViewById(R.id.tv_Refresh);
        btn_Refresh = findViewById(R.id.btn_Refresh);
        iv_Loading = findViewById(R.id.iv_Loading);

        animaition = (AnimationDrawable) iv_Loading.getBackground();
        animaition.setOneShot(false);
        btn_Refresh.setOnClickListener(
            new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onBtnClickListener != null)
                        onBtnClickListener.OnBtnClick();
                }
            }
        );

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StatusView, 0, 0);

        btn_Refresh.setBackgroundResource(ta.getResourceId(R.styleable.StatusView_btn_bg, R.drawable.shape_bule_24));

        image_NullContent = ta.getResourceId(R.styleable.StatusView_status_nullcontent, image_NullContent);
        image_NullNetWork = ta.getResourceId(R.styleable.StatusView_status_nullnetwork, image_NullNetWork);
        isShowButton = ta.getBoolean(R.styleable.StatusView_isShowBtn, true);
        showStatusView(StatusInfo.NullContent, context);
    }


    //显示状态
    public void showStatusView(String State, Context context) {
        this.State = State;
        switch (State) {
            case StatusInfo.Loading:
                animaition.start();
                RL_Status.setVisibility(View.VISIBLE);
                LL_Loading.setVisibility(View.VISIBLE);
                LL_Refresh.setVisibility(View.GONE);
                break;
            case StatusInfo.NullContent:
                RL_Status.setVisibility(View.VISIBLE);
                btn_Refresh.setVisibility(GONE);
                LL_Loading.setVisibility(View.GONE);
                LL_Refresh.setVisibility(View.VISIBLE);
                Glide.with(context).load(image_NullContent).into(iv_Refresh);
                tv_Refresh.setText("没有找到内容");
                isShowButton();
                break;
            case StatusInfo.NullNetwork:
                RL_Status.setVisibility(View.VISIBLE);
                LL_Loading.setVisibility(View.GONE);
                LL_Refresh.setVisibility(View.VISIBLE);
                btn_Refresh.setVisibility(VISIBLE);
                Glide.with(context).load(image_NullNetWork).into(iv_Refresh);
                tv_Refresh.setText("网络连接失败");
                isShowButton();
                break;
            case StatusInfo.ShowView:
                RL_Status.setVisibility(View.GONE);
                LL_Loading.setVisibility(View.GONE);
                LL_Refresh.setVisibility(View.GONE);
                break;
        }
    }

    //显示、隐藏按钮
    public void isShowButton() {
        if (isShowButton)
            btn_Refresh.setVisibility(VISIBLE);
        else
            btn_Refresh.setVisibility(GONE);
    }

    //设置显示、隐藏按钮
    public void setIsShowButton(boolean isShowButton) {
        this.isShowButton = isShowButton;
        isShowButton();
    }

    //设置按钮的字
    public void setBtnTxt(String txt) {
        btn_Refresh.setText(txt);
    }

    //设置提示的字
    public void setTiShiText(String txt) {
        tv_Refresh.setText(txt);
    }

    //按钮监听
    public interface OnBtnClickListener {
        public void OnBtnClick();
    }

    //设置按钮监听
    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
        this.onBtnClickListener = onBtnClickListener;
    }

    //获取状态值
    public String getState() {
        return State;
    }

    //设置网络为空的图片
    public void setImage_NullNetWork(int image_NullNetWork) {
        this.image_NullNetWork = image_NullNetWork;
    }

    //设置内容为空的图片
    public void setImage_NullContent(int image_NullContent) {
        this.image_NullContent = image_NullContent;
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == GONE)
            animaition.stop();
    }
}

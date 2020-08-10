package com.example.chenq.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chenq.library.utils.SoftInputUtils;
import com.chenq.library.utils.WifiUtils;
import com.example.chenq.R;

public class WifiPwdDialog extends Dialog implements View.OnClickListener {

    public TextView ok_btn, no_btn = null;
    private TextView title_txt = null;
    private Activity mActivity = null;
    private EditText edtPwd = null;
    private ImageView imgPwdContrl = null;
    private boolean cannotBack = false;

    public boolean isCannotBack() {
        return cannotBack;
    }

    private boolean isShow = false;
    private String ssid = "";
    WifiUtils mWifiUtils = null;

    public void setCannotBack(boolean cannotBack) {
        this.cannotBack = cannotBack;
    }

    public WifiPwdDialog(Activity activity, String ssid, float wRatio) {
        super(activity);

        mActivity = activity;
        this.ssid = ssid;
        View contentView = LayoutInflater.from(mActivity).inflate(R.layout.wifi_pwd_dialog, null);
        isShow = false;
        mWifiUtils = new WifiUtils(mActivity);

        String title = activity.getString(R.string.wifi_pwd_title_tips);
        title = String.format(title, ssid);
        title_txt = (TextView) contentView.findViewById(R.id.title_txt);
        setTitle(title);

        ok_btn = contentView.findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(this);
        no_btn = contentView.findViewById(R.id.no_btn);
        no_btn.setOnClickListener(this);
        setContentView(contentView);

        edtPwd = contentView.findViewById(R.id.edtPwd);
        edtPwd.addTextChangedListener(mTextWatcher);

        imgPwdContrl = contentView.findViewById(R.id.imgPwdContrl);
        imgPwdContrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShow = !isShow;
                if (isShow) {
                    //如果选中，显示密码
                    edtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    edtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                imgPwdContrl.setImageResource(isShow ? R.mipmap.pwd_open_icon : R.mipmap.pwd_close_icon);
            }
        });

        android.view.WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        layoutParams.width = (int) (display.getWidth() * wRatio);
        getWindow().setAttributes(layoutParams);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        this.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });
    }


    TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s != null && s.length() >= 5) {
                ok_btn.setTextColor(ContextCompat.getColor(mActivity, android.R.color.holo_red_dark));
                ok_btn.setEnabled(true);
            } else {
                ok_btn.setTextColor(ContextCompat.getColor(mActivity, android.R.color.holo_red_light));
                ok_btn.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public void setTitle(int title) {
        title_txt.setText(title);
    }

    public void setTitle(String title) {
        title_txt.setText(title);
    }

    public void setCancelable(boolean flag) {
        super.setCancelable(flag);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_btn:


                if (!mWifiUtils.wifiIsOpened()) {
                    mWifiUtils.openWifi();
                }

                String wifiPed = edtPwd.getText().toString();
                if (TextUtils.isEmpty(wifiPed) || TextUtils.isEmpty(wifiPed.trim())) {
                    return;
                }
                SoftInputUtils.hideSoftKeyboard(mActivity, edtPwd);

                boolean flag = mWifiUtils.setWifi(ssid, wifiPed, 3);

                if (onListener != null)
                    onListener.onClick(flag, ssid);
                this.dismiss();
                break;
            case R.id.no_btn:
                SoftInputUtils.hideSoftKeyboard(mActivity, edtPwd);
                if (onListener != null)
                    onListener.onClick(false, ssid);
                this.dismiss();
                break;
        }
    }


    OnClickButtonListener onListener = null;

    public void setOnClickButtonListener(OnClickButtonListener onListener) {
        this.onListener = onListener;
    }


    public interface OnClickButtonListener {
        void onClick(boolean isWifiOK, String ssid);
    }


    /**
     * 点击软键盘外面的区域关闭软键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (SoftInputUtils.isShouldHideInput(v, ev)) {
                //这里调用关闭方法
                SoftInputUtils.isShouldHideInput(v, ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (cannotBack && keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyUp(keyCode, event);
    }


}

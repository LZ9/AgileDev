package com.lodz.android.agiledev.utils.keyboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.lodz.android.agiledev.R;
import com.lodz.android.core.utils.AnimUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.List;

import androidx.annotation.IntDef;

/**
 * 自定义键盘管理类
 * Created by zhouL on 2018/6/21.
 */
public class KeyboardManager {

    /** 车牌键盘 */
    public static final int TYPE_CAR_NUM = 1;
    /** 身份证键盘 */
    public static final int TYPE_ID_CARD = 2;
    /** 通用证件键盘 */
    public static final int TYPE_COMMON_CERT = 3;

    /** 键盘类型 */
    @IntDef({TYPE_CAR_NUM, TYPE_ID_CARD, TYPE_COMMON_CERT })
    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyboardType {}

    /** 切换到车牌地区代码 */
    public static final int KEYCODE_CAR_AREA = -9;
    /** 切换到车牌数字字母 */
    public static final int KEYCODE_CAR_NUM_LETTER = -8;

    /** 键盘代码 */
    @IntDef({KEYCODE_CAR_AREA, KEYCODE_CAR_NUM_LETTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyboardCode {}


    /** 键盘控件 */
    private KeyboardView mKeyboardView;
    /** 输入框 */
    private EditText mEditText;

    /** 车牌地区键盘 */
    private Keyboard mCarAreaKeyboard;
    /** 车牌数字字母键盘 */
    private Keyboard mCarNumLetterKeyboard;
    /** 身份证号键盘 */
    private Keyboard mIdcardKeyboard;
    /** 通用全键盘 */
    private Keyboard mCommonFullKeyboard;

    /** 点击完成监听器 */
    private OnClickFinishListener mOnClickFinishListener;
    /** 上下文 */
    private Activity mActivity;
    /** 是否大写 */
    private boolean isUperCase = true;

    public KeyboardManager(Activity activity, KeyboardView keyboardView, EditText editText, @KeyboardType int keyboardType) {
        if (activity == null || keyboardView == null || editText == null){
            throw new NullPointerException("parameter is null");
        }

        this.mActivity = activity;
        this.mKeyboardView = keyboardView;
        this.mEditText = editText;

        mCarAreaKeyboard = new Keyboard(activity, R.xml.keyboard_car_area);
        mCarNumLetterKeyboard = new Keyboard(activity, R.xml.keyboard_car_number_letter);
        mIdcardKeyboard = new Keyboard(activity, R.xml.keyboard_idcard);
        mCommonFullKeyboard = new Keyboard(activity, R.xml.keyboard_common_number_letter);

        changeKeyboard(keyboardType);


        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(true);
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        mKeyboardView.setPreviewEnabled(false);
        replaceOriginalKeyboard(activity, mEditText);

        setKeyboardChange();
    }

    /** 替换原始键盘 */
    private void replaceOriginalKeyboard(Activity activity, EditText editText) {
        try {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            Class<EditText> cls = EditText.class;
            Method[] methods = cls.getMethods();
            Method setShowSoftInputOnFocus = null;
            // 版本不同获取不同焦点
            for (Method method : methods) {
                if (method.getName().equals("setShowSoftInputOnFocus")) {
                    setShowSoftInputOnFocus = method;
                } else if (method.getName().equals("setSoftInputShownOnFocus")) {
                    setShowSoftInputOnFocus = method;
                }
            }
            if (setShowSoftInputOnFocus != null) {
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据键盘类型更改键盘
     * @param keyboardType 键盘类型
     */
    public void changeKeyboard(@KeyboardType int keyboardType) {
        if (mKeyboardView == null){
            throw new NullPointerException("KeyboardView is null");
        }

        switch (keyboardType){
            case TYPE_CAR_NUM:// 车牌键盘
                mKeyboardView.setKeyboard(mCarNumLetterKeyboard);
                break;
            case TYPE_ID_CARD:// 身份证键盘
                mKeyboardView.setKeyboard(mIdcardKeyboard);
                break;
            case TYPE_COMMON_CERT:// 通用证件键盘
                mKeyboardView.setKeyboard(mCommonFullKeyboard);
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setKeyboardChange(){
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showKeyboard();
                    KeyboardManager.hideInputMethod(mEditText);
                }else{
                    goneKeyboard();
                }
            }
        });

        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(mEditText.hasFocus()){
                    showKeyboard();
                    KeyboardManager.hideInputMethod(mEditText);
                }
                return false;
            }
        });
    }

    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {

            int selectionStart = mEditText.getSelectionStart();
            int selectionEnd = mEditText.getSelectionEnd();

            if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
                goneKeyboard();
                if (mOnClickFinishListener != null) {
                    mOnClickFinishListener.onClick(mEditText.getText().toString());
                }
                return;
            }

            if (primaryCode == Keyboard.KEYCODE_DELETE) {// 删除
                if (mEditText.getText().length() <= 0) {
                    return;
                }
                if (selectionStart < selectionEnd) {//选中部分文字
                    mEditText.getText().delete(selectionStart, selectionEnd);
                    return;
                }
                if (selectionStart > 0) {//光标不在第一位
                    mEditText.getText().delete(selectionStart - 1, selectionStart);
                }
                return;
            }

            if (primaryCode == Keyboard.KEYCODE_SHIFT){// 切换到大小写
                uperCase();
                mKeyboardView.setKeyboard(mCommonFullKeyboard);
                return;
            }

            if (primaryCode == KEYCODE_CAR_AREA) {// 车牌数字字母切到车牌地区
                mKeyboardView.setKeyboard(mCarAreaKeyboard);
                return;
            }

            if (primaryCode == KEYCODE_CAR_NUM_LETTER) {// 车牌数字字母切到车牌地区
                mKeyboardView.setKeyboard(mCarNumLetterKeyboard);
                return;
            }

            if (selectionStart < selectionEnd) {//先删除选中的文字，再插入
                mEditText.getText().delete(selectionStart, selectionEnd);
            }
            String text = "";
            try {
                text = Character.toString((char) primaryCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(text)) {
                mEditText.getText().insert(selectionStart, text);
            }
            if (mKeyboardView.getKeyboard() == mCarAreaKeyboard){//点击了车牌地区文字后转换到数字字母
                onKey(KEYCODE_CAR_NUM_LETTER, null);
            }

        }
    };

    /** 键盘大小写切换 */
    private void uperCase() {
        final List<Keyboard.Key> keylist = mCommonFullKeyboard.getKeys();
        if (isUperCase) {
            //大写转小写
            for (Keyboard.Key key : keylist) {
                if (key.label != null && isWord(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
            isUperCase = false;
        } else {
            // 小写转大写
            for (Keyboard.Key key : keylist) {
                if (key.label != null && isWord(key.label.toString())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
            }
            isUperCase = true;
        }
    }

    /** 是否是文字 */
    private boolean isWord(String str) {
        return "abcdefghijklmnopqrstuvwxyz".contains(str.toLowerCase());
    }

    /** 显示键盘 */
    public void showKeyboard() {
        if (mKeyboardView.getVisibility() != View.VISIBLE) {
            AnimUtils.startAnim(mActivity, mKeyboardView, R.anim.anim_bottom_in, View.VISIBLE);
        }
    }

    /** 隐藏键盘 */
    public void goneKeyboard() {
        if (mKeyboardView.getVisibility() == View.VISIBLE) {
            AnimUtils.startAnim(mActivity, mKeyboardView, R.anim.anim_bottom_out, View.GONE);
        }
    }

    /** 占位隐藏键盘 */
    public void invisibleKeyboard() {
        if (mKeyboardView.getVisibility() == View.VISIBLE) {
            AnimUtils.startAnim(mActivity, mKeyboardView, R.anim.anim_bottom_out, View.INVISIBLE);
        }
    }

    /**
     * 软键盘是否打开
     * @param context 上下文
     */
    public static boolean isKeybordShow(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm != null && imm.isActive();
    }

    /**
     * 显示软键盘
     * @param view 显示的view
     */
    public static void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null){
            return;
        }
        imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 隐藏软键盘
     * @param view 隐藏的view
     */
    public static void hideInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null){
            return;
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /** 设置点击完成监听器 */
    public void setOnClickFinishListener(OnClickFinishListener listener) {
        mOnClickFinishListener = listener;
    }

    public interface OnClickFinishListener {
        void onClick(String content);
    }
}
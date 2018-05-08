package com.example.peter.touchlistener;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class DecorViewUtil {

    public static void eventListener(Activity activity, final MotionEvent ev) {
        if (activity == null || ev == null) return;

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            boolean needHideKeyBoard = false;

            // 根据坐标获取当前点的view
            View view = getViewAtActivity(activity, (int) ev.getX(), (int) ev.getY());
            if (view != null) {
                if (!(view instanceof EditText)) {
                    if (view instanceof Button || view instanceof TextView) {
                        needHideKeyBoard = true;

                        Object object = view.getTag();
                        if (object instanceof Boolean) {
                            needHideKeyBoard = (boolean) object;
                        }
                    }
                }
            } else {
                needHideKeyBoard = true;
            }

            if (needHideKeyBoard) {
                View focusView = activity.getCurrentFocus();
                if (focusView != null) {
                    focusView.clearFocus();//此行代码作用：让当前获取焦点的EditText 失去焦点
                    hideKeyboard(activity, focusView.getWindowToken());
                }
            }
        }
    }

    /**
     * 根据坐标获取相对应的子控件
     * 在Activity使用
     *
     * @param x 坐标
     * @param y 坐标
     * @return 目标View
     */
    private static View getViewAtActivity(Activity activity, int x, int y) {
        if (activity == null) return null;

        // 从Activity里获取容器
        View root = activity.getWindow().getDecorView();

        return findViewByXY(root, x, y);
    }

    private static View findViewByXY(View view, int x, int y) {
        if (view == null) return null;

        View targetView = null;
        if (view instanceof ViewGroup) {
            // 父容器 遍历子控件
            ViewGroup v = (ViewGroup) view;
            for (int i = 0; i < v.getChildCount(); i++) {
                targetView = findViewByXY(v.getChildAt(i), x, y);
                if (targetView != null) {
                    break;
                }
            }
        } else {
            targetView = getTouchTarget(view, x, y);
        }
        return targetView;
    }

    private static View getTouchTarget(View view, int x, int y) {
        View targetView = null;

        // 判断view是否可以聚焦
        ArrayList<View> TouchableViews = view.getTouchables();
        for (View child : TouchableViews) {
            if (isTouchPointInView(child, x, y)) {
                targetView = child;
                break;
            }
        }
        return targetView;
    }

    private static boolean isTouchPointInView(View view, int x, int y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (view.isClickable() &&
                y >= top && y <= bottom &&
                x >= left && x <= right) {
            return true;
        }
        return false;
    }

    /* 获取InputMethodManager，隐藏软键盘 */
    private static void hideKeyboard(Activity activity, IBinder token) {
        if (token != null && activity != null) {
            InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
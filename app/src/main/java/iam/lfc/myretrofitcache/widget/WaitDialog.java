/*
 * Copyright © YOLANDA. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package iam.lfc.myretrofitcache.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import iam.lfc.myretrofitcache.R;


public class WaitDialog extends Dialog {
    public WaitDialog(Context context) {
        super(context);
    }
    public WaitDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        // 获取ImageView上的动画背景
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        // 开始动画
        spinner.start();
    }

    /**
     * 给Dialog设置提示信息
     *
     * @param message
     */
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    /**
     * 弹出自定义ProgressDialog
     * <p>
     * 上下文
     *
     * @param message        提示
     *                       是否按返回键取消
     * @param cancelListener 按下返回键监听
     * @return
     */
    public static WaitDialog setDialog(Context context, CharSequence message, OnCancelListener cancelListener) {
        WaitDialog dialog = new WaitDialog(context, R.style.Custom_Progress);
        dialog.setTitle("");
        dialog.setContentView(R.layout.progress_custom);
        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.message);
            txt.setText(message);
        }
        // 按返回键是否取消
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        // 监听返回键处理
        dialog.setOnCancelListener(cancelListener);
        // 设置居中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        return dialog;
        // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    }


    public static WaitDialog setNoCancelDialog(Context context, CharSequence message, OnCancelListener cancelListener) {
        WaitDialog dialog = new WaitDialog(context, R.style.Custom_Progress);
        dialog.setTitle("");
        dialog.setContentView(R.layout.progress_custom);
        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.message);
            txt.setText(message);
        }
        // 按返回键是否取消
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        // 监听返回键处理
        dialog.setOnCancelListener(cancelListener);
        dialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return false;
            }
        });
        // 设置居中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        return dialog;
        // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    }
}




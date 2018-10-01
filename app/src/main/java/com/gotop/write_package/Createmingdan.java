package com.gotop.write_package;

import android.app.*;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

/**
 * Created by Administrator on 2018-03-22.
 */


    public class Createmingdan extends Dialog {

        /**
         * &#x4e0a;&#x4e0b;&#x6587;&#x5bf9;&#x8c61; *
         */
        Activity context;

        private Button btn_save;


        public EditText text_info;
        public EditText mimainfo;

        private View.OnClickListener mClickListener;

        public Createmingdan(Activity context) {
            super(context);
            this.context = context;
        }

        public Createmingdan(Activity context, int theme, View.OnClickListener clickListener) {
            super(context, theme);
            this.context = context;
            this.mClickListener = clickListener;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // 指定布局
            this.setContentView(R.layout.manuallayout);


            text_info = (EditText) findViewById(R.id.baominginfo);
            mimainfo = (EditText) findViewById(R.id.mimainfo);

  /*
   * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
   * 对象,这样这可以以同样的方式改变这个Activity的属性.
   */
            Window dialogWindow = this.getWindow();

            WindowManager m = context.getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
            p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
            dialogWindow.setAttributes(p);

            // 根据id在布局中找到控件对象
            btn_save = (Button) findViewById(R.id.btn_save);

            // 为按钮绑定点击事件监听器
            btn_save.setOnClickListener(mClickListener);

            this.setCancelable(true);
        }
    }


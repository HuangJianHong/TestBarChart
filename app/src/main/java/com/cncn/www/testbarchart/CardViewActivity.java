package com.cncn.www.testbarchart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class CardViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);


        /**
         * 使用性能一般般，不是很推荐使用; 毕竟要进行版本适配.5.0之前的有差异
         *  http://yifeng.studio/2016/10/18/android-cardview/
         */

//        cardBackgroundColor              卡片的背景颜色
//
//        cardCornerRadius                 卡片的圆角大小
//
//        cardElevation                    阴影的大小
//
//        cardMaxElevation                 阴影最大高度
//
//        cardUseCompatPadding             设置内边距，V21+的版本和之前的版本仍旧具有一样的计算方式
//
//        cardPreventCornerOverlap         在V20和之前的版本中添加内边距，这个属性为了防止内容和边角的重叠
//
//        contentPadding                   卡片内容于边距的间隔
//
//        contentPaddingTop                卡片内容与顶部的边距
//
//        contentPaddingBottom             卡片内容与底部的边距
//
//        contentPaddingLeft               卡片内容与左边的边距
//
//        contentPaddingRight              卡片内容与右边的边距
    }
}

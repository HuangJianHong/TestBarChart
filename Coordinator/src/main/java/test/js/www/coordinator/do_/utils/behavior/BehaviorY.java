package test.js.www.coordinator.do_.utils.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import test.js.www.coordinator.do_.utils.DodoMoveView;

/**
 * Created by  Hjh on 2018/1/3.
 * desc： 在Y轴方向上，跟谁移动的Behavior
 */

public class BehaviorY extends CoordinatorLayout.Behavior<Button> {

    public BehaviorY() {
    }

    public BehaviorY(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Button child, View dependency) {
        //如果dependency 是 DodoMoveView类型， 就依赖
        return dependency instanceof DodoMoveView;
    }

    @Override
    //每次dependency位置发生变化，都会执行onDependentViewChanged方法
    public boolean onDependentViewChanged(CoordinatorLayout parent, Button child, View dependency) {
        int x = 0;
        //根据dependency的位置，设置Button的位置
        int y = (int) dependency.getY();
        setPosition(child, x, y);
        return true;
    }

    private void setPosition(Button child, int x, int y) {
        CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) child.getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        child.setLayoutParams(layoutParams);
    }
}

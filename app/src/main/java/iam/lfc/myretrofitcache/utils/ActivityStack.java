package iam.lfc.myretrofitcache.utils;

import android.app.Activity;

import java.util.Stack;

public class ActivityStack {

    private static Stack<Activity> mActivityStack;
    private static ActivityStack instance;

    private ActivityStack() {
    }

    public static ActivityStack getScreenManager() {
        if (instance == null) {
            instance = new ActivityStack();
        }
        return instance;
    }

    /**
     * 移除栈顶的activity
     */
    public void popActivity() {
        Activity activity = mActivityStack.lastElement();
        if (activity != null) {
            activity.finish();
        }
    }

    /**
     * 移除一个activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            mActivityStack.remove(activity);
        }
    }

    /**
     * 获取栈顶的activity，先进后出原则
     */
    public Activity currentActivity() {
        // lastElement()获取最后个子元素，这里是栈顶的Activity
        if (mActivityStack == null || mActivityStack.size() == 0) {
            return null;
        }
        return mActivityStack.lastElement();
    }

    /**
     * 将当前Activity推入栈中
     */
    public void pushActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 是否包含指定的Activity
     */
    public static boolean isContainsActivity(Class<?> cls) {
        if (mActivityStack == null || mActivityStack.size() == 0) {
            return false;
        }
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 弹出栈中指定Activity
     */
    public boolean popOneActivity(Class<?> cls) {
        if (mActivityStack == null || mActivityStack.size() == 0)
            return false;
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                popActivity(activity);
                return true;
            }
        }
        return false;
    }

    /**
     * 弹出栈中所有Activity，保留指定的一个Activity
     */
    public void popAllActivityExceptOne(Class<?> cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null)
                break;
            if (activity.getClass().equals(cls))
                break;
//                continue;
            popActivity(activity);
        }
    }

    /**
     * 弹出栈中所有Activity，保留指定的一个Activity
     */
    public void popAllButExceptOne(Class<?> cls) {
        for (int i = 0; i < mActivityStack.size(); i++) {
            Activity activity = mActivityStack.get(i);
            if (!activity.getClass().equals(cls)) {
//                finishActivity(activity);
                popActivity(activity);
            }
        }

    }


    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 弹出栈中所有Activity，保留指定的Activity
     */
    public void popAllActivityExcept(Class<?>... clss) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null)
                break;
            for (Class<?> cls : clss) {
                if (activity.getClass().equals(cls))
                    break;
            }
            popActivity(activity);
        }
    }

    /**
     * 弹出栈中所有Activity
     */
    public void popAllActivitys() {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null)
                break;
            popActivity(activity);
        }
    }



}

package com.chenq.base;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * create by chenqi on 2020/6/11
 * Email: chenqwork@gmail.com
 * Desc: Activity栈
 */
public class ActivityStack {

    private static ActivityStack activityStack;
    private List<Activity> activityList = new LinkedList<>();

    public ActivityStack() {

    }

    public static ActivityStack getInstance(){
        if(activityStack==null){
            synchronized (ActivityStack.class){
                if(activityStack==null){
                    activityStack = new ActivityStack();
                }
            }
        }
        return activityStack;
    }

    public void addActivity(Activity act){
        activityList.add(act);
    }

    public void removeActivity(Activity act){
        activityList.remove(act);
    }

    public boolean hasActivity(Class<Activity> targetAct){
        for(Activity act:activityList){
            if(act.getClass() == targetAct){
                return true;
            }
        }
        return false;
    }

    public void finish(Class<?> cls){
        for(Activity act : activityList){
            if(act.getClass() == cls){
                act.finish();
                activityList.remove(act);
                return;
            }
        }
    }

    public void finishAllActivity(){
        int size = activityList.size();
        for(int i=0;i<size;i++){
            activityList.get(i).finish();
        }
        activityList.clear();
    }

}

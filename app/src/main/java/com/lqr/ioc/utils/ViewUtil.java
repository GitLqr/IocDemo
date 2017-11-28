package com.lqr.ioc.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.lqr.ioc.anno.BindView;
import com.lqr.ioc.anno.CheckNet;
import com.lqr.ioc.anno.ClickView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ViewUtil {

    private static Context context = null;

    // Activity
//    public static void inject(Activity activity) {
//        injectReal(activity, activity);
//    }
    public static void inject(final Activity activity) {
        Class clazz = activity.getClass();
        // 遍历属性
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null) {
                try {
                    field.setAccessible(true);
                    field.set(activity, activity.findViewById(bindView.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        // 遍历方法
        Method[] methods = clazz.getDeclaredMethods();
        for (final Method method : methods) {
            ClickView clickView = method.getAnnotation(ClickView.class);
            if (clickView != null) {
                activity.findViewById(clickView.value()).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            method.setAccessible(true);
                            method.invoke(activity);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    // v4 Fragment
    public static void inject(Fragment container, View rootView) {
        injectReal(container, rootView);
    }

    // app Fragment
    public static void inject(android.app.Fragment container, View rootView) {
        injectReal(container, rootView);
    }

    private static void injectReal(final Object container, Object rootView) {
        if (container instanceof Activity) {
            context = (Activity) container;
        } else if (container instanceof Fragment) {
            context = ((Fragment) container).getActivity();
        } else if (container instanceof android.app.Fragment) {
            context = ((android.app.Fragment) container).getActivity();
        }

        Class clazz = container.getClass();
        // 遍历属性
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null) {
                try {
                    field.setAccessible(true);
                    field.set(container, findViewById(rootView, bindView.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        // 遍历方法
        Method[] methods = clazz.getDeclaredMethods();
        for (final Method method : methods) {
            ClickView clickView = method.getAnnotation(ClickView.class);
            if (clickView != null) {
                findViewById(rootView, clickView.value()).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            CheckNet checkNet = method.getAnnotation(CheckNet.class);
                            if (checkNet != null) {
                                if (!NetManagerUtil.isOpenNetwork(context)) {
                                    Toast.makeText(context, checkNet.tip(), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            method.setAccessible(true);
                            method.invoke(container);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }


    private static View findViewById(Object layout, int resId) {
        if (layout instanceof Activity) {
            return ((Activity) layout).findViewById(resId);
        } else if (layout instanceof View) {
            return ((View) layout).findViewById(resId);
        }
        return null;
    }
}

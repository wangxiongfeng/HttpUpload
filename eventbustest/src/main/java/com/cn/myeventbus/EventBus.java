package com.cn.myeventbus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by wang on 2018/6/6.
 */

public class EventBus {
    static volatile EventBus defaultInstance;
    private final Map<Class<?>, CopyOnWriteArrayList<Subscription>> subscriptionsByEventType;
    private final Map<Object, List<Class<?>>> typesBySubscriber;

    public EventBus() {
        subscriptionsByEventType = new HashMap<>();
        typesBySubscriber = new HashMap<>();
    }

    public static EventBus getDefault() {
        if (defaultInstance == null) {
            synchronized (EventBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new EventBus();
                }
            }
        }
        return defaultInstance;
    }


    public void register(Object object) {  // object  MainActivity.class
        // 1 解析所有方法封装成SubscriberMethod集合
        Class<?> objClass = object.getClass();
        List<SubscriberMethod> subscriberMethods = new ArrayList<>();
        Method[] methods = objClass.getDeclaredMethods();
        for (Method method : methods) {
            Subscribe subscribe = method.getAnnotation(Subscribe.class);
            if (subscribe != null) {
                //所有的属性 解析出来
                Class<?>[] parameterTypes = method.getParameterTypes();  // 方法第一个参数类型  String.class
                //含有注解的方法 封装成SubscriberMethod对象
                SubscriberMethod subscriberMethod = new SubscriberMethod(method, parameterTypes[0], subscribe.threadMode(), subscribe.priority(),
                        subscribe.sticky());
                subscriberMethods.add(subscriberMethod);
            }
        }
        //2 按规则存放到subscriptionsByEventType 中去
        for (SubscriberMethod subscriberMethod : subscriberMethods) {
            subscriber(object, subscriberMethod);
        }
    }

    private void subscriber(Object object, SubscriberMethod subscriberMethod) {  //object --> MainActivity.class

        Class<?> eventType = subscriberMethod.eventType;   // 方法第一个参数类型  String.class
        CopyOnWriteArrayList<Subscription> subscriptions = subscriptionsByEventType.get(eventType);
        if (subscriptions == null) {
            subscriptions = new CopyOnWriteArrayList<>();
            subscriptionsByEventType.put(eventType, subscriptions);
        }
        //判断优先级 （忽略）
        Subscription subscription = new Subscription(object, subscriberMethod);
        subscriptions.add(subscription);

        //typesBySubscriber 方便移除
        List<Class<?>> eventTypes = typesBySubscriber.get(object);
        if (eventTypes == null) {
            eventTypes = new ArrayList<>();
            typesBySubscriber.put(object, eventTypes);
        }
        if (!eventTypes.contains(eventType)) {
            eventTypes.add(eventType);
        }

    }


    public void unregister(Object object) {   //object 对应 typesBySubscriber.put(object, eventTypes);
        List<Class<?>> eventTypes = typesBySubscriber.get(object);
        if (eventTypes != null) {
            for (Class<?> eventType : eventTypes) {
                removeObject(eventType, object);
            }
        }
    }

    private void removeObject(Class<?> eventType, Object object) {
        //一遍移除 一遍循环不行
        List<Subscription> subscriptions = subscriptionsByEventType.get(eventType);
        if (subscriptions != null) {
            int size = subscriptions.size();
            for (int i = 0; i < size; i++) {
                Subscription subscription = subscriptions.get(i);
                if (subscription.subscriber == object) { //注销此Activity中注解方法
                    subscriptions.remove(i);
                    i--;
                    size--;
                }
            }
        }


    }

    public void post(Object event) {   // event    "文本"
        //遍历subscriptionsByEventType 找到符合的方法调用方法的method.invoke() 执行  要注意线程的切换
        Class<?> eventType = event.getClass();  //String.class
//        找到符合的方法调用方法的method.invoke() 执行
        CopyOnWriteArrayList<Subscription> subscriptions = subscriptionsByEventType.get(eventType);
        if (subscriptions != null) {
            for (Subscription subscription : subscriptions) {
                executeMethod(subscription, event);
            }
        }
    }

    private void executeMethod(final Subscription subscription, final Object event) {
        ThreadMode threadMode = subscription.subscriberMethod.threadMode;
        boolean isMainThread= Looper.getMainLooper()==Looper.myLooper();
        switch (threadMode) {
            case POSTING:
                invokeMethod(subscription,event);
                break;
            case MAIN:
                if(isMainThread){
                    invokeMethod(subscription,event);
                }else{
                    Handler handler=new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            invokeMethod(subscription,event);
                        }
                    });
                }
                break;
            case BACKGROUND:
                if(!isMainThread){
                    invokeMethod(subscription,event);
                }else{
                    AsyncPoster.enqueue(subscription,event); //新线程执行
                }
                break;
            case ASYNC:
                AsyncPoster.enqueue(subscription,event);  //新线程执行
                break;
        }


    }

    private void invokeMethod(Subscription subscription, Object event) {
        // ubscription.subscriber -->  MainActivity.class      event --> String 执行的参数
        try {
            subscription.subscriberMethod.method.invoke(subscription.subscriber,event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}

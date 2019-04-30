package com.cn.retrofitsimple2;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wang on 2018/5/24.
 */

public abstract class HttpCallBack<T> implements Callback<Result<T>> {

    @Override
    public void onResponse(Call<Result<T>> call, Response<Result<T>> response) {

        Result<T> result = response.body();
        if (!result.isOk()) {  // retcode !=0  没得到正常数据
            onError(result.retCode, result.msg);
        }
        //解析 获取类上面的泛型
        /**
         * 通过Class类上的 getGenericSuperclass() 或者 getGenericInterfaces() 获取父类或者接口的类型,
         * 然后通过ParameterizedType.getActualTypeArguments()可以得到定义在类或者接口上的泛型类型
         */
        Class<T> dataClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        Gson gson = new Gson();
        T data = gson.fromJson(result.data.toString(), dataClass);    // result.data  是 string or userinfo
        onSuccess(data);

    }

    @Override
    public void onFailure(Call<Result<T>> call, Throwable t) {
        //处理失败 联网  解析出错
    }

    public abstract void onSuccess(T userInfo);

    public abstract void onError(int code, String msg);

    public static <T> T test(){
        return  null;
    }


}

package com.sk.uudc.base;

import android.app.Activity;
import android.content.Context;

import com.github.androidtools.ToastUtils;
import com.github.baseclass.utils.ActUtils;
import com.github.baseclass.view.Loading;
import com.github.retrofitutil.NoNetworkException;
import com.sk.uudc.module.my.activity.LoginActivity;

import java.net.ConnectException;

import in.srain.cube.views.ptr.PtrFrameLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/5/18.
 */

public abstract class MyCallBack<T> implements Callback<ResponseObj<T>> {

    private Context context;
    private boolean noHiddenLoad;
    private PtrFrameLayout pfl;
    private ProgressLayout progressLayout;

    public Context getContext() {
        return context;
    }

    public MyCallBack(Context ctx) {
        this.context = ctx;
    }

    public MyCallBack(Context ctx, ProgressLayout pl) {
        this.context = ctx;
        this.progressLayout = pl;
    }

    public MyCallBack(Context ctx, PtrFrameLayout pfl) {
        this.context = ctx;
        this.pfl = pfl;
    }

    public MyCallBack(Context ctx, PtrFrameLayout pfl, ProgressLayout pl) {
        this.context = ctx;
        this.pfl = pfl;
        this.progressLayout = pl;
    }

    public MyCallBack(Context ctx, ProgressLayout pl, PtrFrameLayout pfl) {
        this.context = ctx;
        this.pfl = pfl;
        this.progressLayout = pl;
    }

    public MyCallBack(Context ctx, boolean noHiddenLoad) {
        this.context = ctx;
        this.noHiddenLoad = noHiddenLoad;
    }

    public abstract void onSuccess(T obj);

    public void onError(Throwable e, boolean showContentView) {
        if (pfl != null) {
            pfl.refreshComplete();
            pfl = null;
        }
        if (progressLayout != null) {
            if (showContentView) {
                progressLayout.showErrorText();
            }
            progressLayout = null;
        }
        if (e instanceof ServerException || e instanceof NoNetworkException) {
            ToastUtils.showToast(context, e.getMessage());
        } else {
            ToastUtils.showToast(context, "连接失败");
            e.printStackTrace();
        }
        Loading.dismissLoading();
    }

    public void onError(Throwable e) {
        onError(e, true);
    }
    public void onCompelte() {
        if (!noHiddenLoad) {
            Loading.dismissLoading();
        }
        if (pfl != null) {
            pfl.refreshComplete();
        }
        if (progressLayout != null) {
            progressLayout.showContent();
            progressLayout = null;
        }
        this.context = null;
    }

    @Override
    public void onFailure(Call<ResponseObj<T>> call, Throwable t) {
        if (t instanceof ConnectException) {
            onError(new ServerException("服务器开小差去了,请稍后再试"));
        } else {
            onError(t);
        }

    }

    @Override
    public void onResponse(Call<ResponseObj<T>> call, Response<ResponseObj<T>> response) {
        if (response.body() == null) {
            if (response.code() == 500) {
                onError(new ServerException("服务器开小差去了,请稍后再试"));
            } else {
                onError(new ServerException("连接异常"));
            }
            return;
        }
        int errCode = response.body().getErrCode();
        if (errCode == 1) {
            onError(new ServerException(response.body().getErrMsg()));
            return;
        } else if (errCode == 2) {//2需要登录
            if (this.progressLayout != null) {//需要finish
                onError(new ServerException(response.body().getErrMsg()),false);
                ActUtils.STActivity((Activity) context, LoginActivity.class);
                ((Activity) context).finish();
            } else {
                onError(new ServerException(response.body().getErrMsg()));
                ActUtils.STActivity((Activity) context, LoginActivity.class);
            }
            return;
        }
        T res = response.body().getResponse();
        if (res != null) {
            Class<?> aClass = res.getClass();
            Class baseClass = BaseObj.class;
            boolean assignableFrom = baseClass.isAssignableFrom(aClass);
            if (assignableFrom) {
                ((BaseObj) res).setMsg(response.body().getErrMsg());
                onSuccess(res);
            } else {
                onSuccess(res);
            }
        } else {
            T result = (T) new BaseObj();
            ((BaseObj) result).setMsg(response.body().getErrMsg());
            onSuccess(result);
//            onError(new ServerException("暂无数据"));
        }
        onCompelte();
        res = null;
    }
}

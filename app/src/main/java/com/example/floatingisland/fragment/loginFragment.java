package com.example.floatingisland.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.MainActivity;
import com.example.floatingisland.activity.WelcomeActivity;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.security.MessageDigest;
import java.util.HashMap;

import es.dmoral.toasty.MyToast;

public class loginFragment extends Fragment {

    private View LoginFragment;
    private RequestManager glideRequests;

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化 Glide 请求管理器
        glideRequests = Glide.with(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LoginFragment = inflater.inflate(R.layout.fragment_login,container,false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        MaterialEditText account = LoginFragment.findViewById(R.id.account);
        MaterialEditText password = LoginFragment.findViewById(R.id.password);
        Button login = LoginFragment.findViewById(R.id.login);
        CheckBox agree = LoginFragment.findViewById(R.id.agree);
        TextView protocol = LoginFragment.findViewById(R.id.protocol);
        TextView register = LoginFragment.findViewById(R.id.register);
        ImageView avatar = LoginFragment.findViewById(R.id.avatar);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("lastuserInfo", Context.MODE_PRIVATE);
        String lastaccount = sharedPreferences.getString("lastaccount", "");
        String lastavatarurl = sharedPreferences.getString("lastavatarurl", "");
        if(!TextUtils.isEmpty(lastaccount) && !TextUtils.isEmpty(lastavatarurl)){
            glideRequests.load(lastavatarurl)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(200))
                            .override(400, 400))
                    .transform(new CircleTransformation())
                    .into(avatar);

            account.setText(lastaccount);

            TextWatcher mTextWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // 文本变化前的回调，可以不做处理
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // 文本变化时的回调
                    String input = s.toString();  // 获取输入内容
                    // 在此处添加您需要执行的操作
                    avatar.setImageResource(R.mipmap.avatar);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // 文本变化后的回调，可以不做处理
                }
            };
            account.addTextChangedListener(mTextWatcher);

        }else{
            avatar.setImageResource(R.mipmap.avatar);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account1 = account.getText().toString();
                String password1 = password.getText().toString();
                if(account1.equals("") || password1.equals("")){
                    MyToast.errorBig("你有为空的选项，请填写完毕！");
               }else{
                    if(agree.isChecked()==true){
                        HashMap<String, String> params = new HashMap<>();
                        params.put("uaccount", account1);
                        params.put("upassword", password1);
                        OkHttp.post(getContext(), Constant.login, params, new OkCallback<Result>() {
                            @Override
                            public void onResponse(Result response) {
                                if(response.getMessage().equals("登录成功")){
                                    MyToast.successBig(response.getMessage());
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("account", account1);
                                    editor.apply();

                                    SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("lastuserInfo", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                                    editor1.clear().apply();

                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    activity.onBackPressed();//销毁自己
                                }else if(response.getMessage().equals("登录失败")){
                                    MyToast.errorBig("账号或密码错误，请重新输入！");
                                }

                            }

                            @Override
                            public void onFailure(String state, String msg) {
                                MyToast.errorBig("连接服务器超时！");
                            }
                        });
                    }else{
                        MyToast.errorBig("请阅读并同意《用户协议》！");
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_left, R.anim.slide_in_from_left, R.anim.slide_out_from_right)
                        .replace(R.id.welcomeLayout, new registerFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        protocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.welcomeLayout, new protocolFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        return LoginFragment;
    }

    // 定义自定义的 CircleTransformation
    public class CircleTransformation extends BitmapTransformation {
        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            Bitmap bitmap = TransformationUtils.circleCrop(pool, toTransform, outWidth, outHeight);
            return bitmap;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            // do nothing
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // 暂停 Glide 加载任务
        glideRequests.pauseRequests();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 恢复 Glide 加载任务
        glideRequests.resumeRequests();
    }

}
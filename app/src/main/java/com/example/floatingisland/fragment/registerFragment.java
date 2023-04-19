package com.example.floatingisland.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.floatingisland.R;
import com.example.floatingisland.activity.MainActivity;
import com.example.floatingisland.activity.WelcomeActivity;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

import es.dmoral.toasty.MyToast;


public class registerFragment extends Fragment {

    private View registerFragment;

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        registerFragment = inflater.inflate(R.layout.fragment_register, container, false);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        Toolbar toolbar = registerFragment.findViewById(R.id.Toolbar_register);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

        MaterialEditText account = registerFragment.findViewById(R.id.account);
        MaterialEditText password = registerFragment.findViewById(R.id.password);
        MaterialEditText nickname = registerFragment.findViewById(R.id.nickname);
        Button register = registerFragment.findViewById(R.id.register);
        CheckBox agree = registerFragment.findViewById(R.id.agree);
        TextView protocol = registerFragment.findViewById(R.id.protocol);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account1 = account.getText().toString();
                String password1 = password.getText().toString();
                String nickname1 = nickname.getText().toString();
                if(account1.equals("") || password1.equals("") || nickname1.equals("")){
                    MyToast.errorBig("你有为空的选项，请填写完毕！");
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("账号作为唯一登录凭据，注册后将不可更改！确定使用此账号注册吗？")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击确定按钮后的操作
                                    if(agree.isChecked()==true){
                                        HashMap<String, String> params = new HashMap<>();
                                        params.put("uaccount", account1);
                                        params.put("upassword", password1);
                                        params.put("unickname", nickname1);
                                        OkHttp.post(getContext(), Constant.register, params, new OkCallback<Result>() {
                                            @Override
                                            public void onResponse(Result response) {
                                                if(response.getMessage().equals("注册成功")){
                                                    MyToast.successBig(response.getMessage());
                                                    getFragmentManager().beginTransaction()
                                                            .setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_from_right, R.anim.slide_in_from_right, R.anim.slide_out_from_left)
                                                            .replace(R.id.welcomeLayout, new loginFragment())
                                                            .commit();
                                                }else if(response.getMessage().equals("账号已存在")){
                                                    MyToast.errorBig("该账号已存在，请修改账号重新注册！");
                                                }else if(response.getMessage().equals("注册失败")){
                                                    MyToast.errorBig(response.getMessage());
                                                }

                                            }

                                            @Override
                                            public void onFailure(String state, String msg) {
                                                MyToast.errorBig("服务器连接超时！");
                                            }
                                        });
                                    }else{
                                        MyToast.errorBig("请阅读并同意《用户协议》！");
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击取消按钮后的操作
                                    dialog.cancel(); // 关闭对话框
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
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

        return registerFragment;
    }
}
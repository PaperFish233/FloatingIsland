package com.example.floatingisland.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

public class loginFragment extends Fragment {

    private View LoginFragment;

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LoginFragment = inflater.inflate(R.layout.fragment_login,container,false);

        MaterialEditText account = LoginFragment.findViewById(R.id.account);
        MaterialEditText password = LoginFragment.findViewById(R.id.password);
        Button login = LoginFragment.findViewById(R.id.login);
        CheckBox agree = LoginFragment.findViewById(R.id.agree);
        TextView protocol = LoginFragment.findViewById(R.id.protocol);
        TextView register = LoginFragment.findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account1 = account.getText().toString();
                String password1 = password.getText().toString();
                if(account1.equals("") || password1.equals("")){
                    Toast.makeText(getContext(), "你有为空的选项，请填写完毕！", Toast.LENGTH_SHORT).show();
               }else{
                    if(agree.isChecked()==true){
                        HashMap<String, String> params = new HashMap<>();
                        params.put("uaccount", account1);
                        params.put("upassword", password1);
                        OkHttp.post(getContext(), Constant.login, params, new OkCallback<Result>() {
                            @Override
                            public void onResponse(Result response) {
                                if(response.getMessage().equals("登录成功")){
                                    Toast.makeText(getContext(), "登录成功！", Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("account", account1);
                                    editor.apply();

                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                    activity.onBackPressed();//销毁自己
                                }else if(response.getMessage().equals("登录失败")){
                                    Toast.makeText(getContext(), "账号或密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(String state, String msg) {
                                Toast.makeText(getContext(), "连接服务器失败！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Toast.makeText(getContext(), "请阅读并同意《用户协议》！", Toast.LENGTH_SHORT).show();
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

}
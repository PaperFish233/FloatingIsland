package com.example.floatingisland.fragment;

import static android.app.Activity.RESULT_OK;

import static com.example.floatingisland.utils.PermissionHelper.requestReanAndWritePermission;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.WelcomeActivity;
import com.example.floatingisland.entity.Users;
import com.example.floatingisland.utils.Constant;
import com.example.floatingisland.utils.FileUtil;
import com.example.floatingisland.utils.net.OkCallback;
import com.example.floatingisland.utils.net.OkHttp;
import com.example.floatingisland.utils.net.Result;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.MyToast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class editInfoFragment extends Fragment {

    private static final int REQUEST_CODE_CHOOSE = 1224; // 自定义返回码
    private static Boolean avatar_update = true;

    private View editInfoFragment;
    private ImageView background;
    private SuperTextView avatar;
    private SuperTextView nickname;
    private SuperTextView signature;

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    public void refreshpage(){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        //刷新用户信息
        HashMap<String, String> params = new HashMap<>();
        params.put("uaccount", loginInfo);
        OkHttp.post(getContext(), Constant.getUsers, params, new OkCallback<Result<List<Users>>>() {
            @Override
            public void onResponse(Result<List<Users>> response) {
                String Uavatarurl="";
                String Unickname="";
                String Usignature="";
                String Ubackgroundurl="";
                for (Users users : response.getData()) {
                    Uavatarurl = users.getUavatarurl();
                    Unickname = users.getUnickname();
                    Usignature = users.getUsignature();
                    Ubackgroundurl = users.getUbackgroundurl();
                }

                Glide.with(getContext())
                        .load(Ubackgroundurl)
                        .apply(RequestOptions.overrideOf(1280, 720))
                        .into(background);

                Glide.with(getContext())
                        .load(Uavatarurl)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(100))
                                .override(100, 100))
                        .transform(new CircleTransformation())
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                avatar.setRightTvDrawableRight(resource);
                            }
                        });

                nickname.setRightString(Unickname);
                signature.setRightString(Usignature);

            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        editInfoFragment = inflater.inflate(R.layout.fragment_editinfo, container, false);

        //动态获取权限
        requestReanAndWritePermission(activity);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        Toolbar toolbar = editInfoFragment.findViewById(R.id.Toolbar_editinfo);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()

            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        background = editInfoFragment.findViewById(R.id.background);
        avatar = editInfoFragment.findViewById(R.id.avatar);
        nickname = editInfoFragment.findViewById(R.id.nickname);
        signature = editInfoFragment.findViewById(R.id.signature);
        SuperTextView password = editInfoFragment.findViewById(R.id.password);
        ImageView update = editInfoFragment.findViewById(R.id.update);

        //获取登录用户信息
        HashMap<String, String> params = new HashMap<>();
        params.put("uaccount", loginInfo);
        OkHttp.post(getContext(), Constant.getUsers, params, new OkCallback<Result<List<Users>>>() {
            @Override
            public void onResponse(Result<List<Users>> response) {
                String Uavatarurl="";
                String Unickname="";
                String Usignature="";
                String Ubackgroundurl="";
                for (Users users : response.getData()) {
                    Uavatarurl = users.getUavatarurl();
                    Unickname = users.getUnickname();
                    Usignature = users.getUsignature();
                    Ubackgroundurl = users.getUbackgroundurl();
                }

                Glide.with(getContext())
                        .load(Ubackgroundurl)
                        .apply(RequestOptions.overrideOf(1280, 720))
                        .into(background);

                Glide.with(getContext())
                        .load(Uavatarurl)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(100))
                                .override(100, 100))
                        .transform(new CircleTransformation())
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                avatar.setRightTvDrawableRight(resource);
                            }
                        });

                nickname.setRightString(Unickname);
                signature.setRightString(Usignature);

            }

            @Override
            public void onFailure(String state, String msg) {
                MyToast.errorBig("连接服务器超时！");
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                avatar_update = false;
                selectImages();

            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                avatar_update = true;
                selectImages();

            }
        });

        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                // 创建一个EditText视图
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View edittext_dialog = inflater.inflate(R.layout.edittext_dialog_layout, null);
                final MaterialEditText editText = edittext_dialog.findViewById(R.id.edit_text);

                builder.setMessage("更新昵称：")
                        .setView(edittext_dialog)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击确定按钮后的操作
                                String inputText = editText.getText().toString();

                                if(inputText.isEmpty()){
                                    MyToast.errorBig("不能更新空的内容哦！");
                                }else {
                                    HashMap<String, String> params = new HashMap<>();
                                    params.put("inputText", inputText);
                                    params.put("uaccount", loginInfo);
                                    OkHttp.post(getContext(), Constant.updateUserNickname, params, new OkCallback<Result>() {
                                        @Override
                                        public void onResponse(Result response) {
                                            if (response.getMessage().equals("更新成功")) {
                                                MyToast.successBig(response.getMessage());
                                                refreshpage();
                                            } else if (response.getMessage().equals("更新失败")) {
                                                MyToast.errorBig(response.getMessage());
                                            }
                                        }

                                        @Override
                                        public void onFailure(String state, String msg) {
                                            MyToast.errorBig("连接服务器超时！");
                                        }
                                    });
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
        });

        signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                // 创建一个EditText视图
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View edittext_dialog = inflater.inflate(R.layout.edittext_dialog_layout, null);
                final MaterialEditText editText = edittext_dialog.findViewById(R.id.edit_text);

                builder.setMessage("更新简介：")
                        .setView(edittext_dialog)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击确定按钮后的操作
                                String inputText = editText.getText().toString();

                                if(inputText.isEmpty()){
                                    MyToast.errorBig("不能更新空的内容哦！");
                                }else {
                                    HashMap<String, String> params = new HashMap<>();
                                    params.put("inputText", inputText);
                                    params.put("uaccount", loginInfo);
                                    OkHttp.post(getContext(), Constant.updateUserSignature, params, new OkCallback<Result>() {
                                        @Override
                                        public void onResponse(Result response) {
                                            if (response.getMessage().equals("更新成功")) {
                                                MyToast.successBig(response.getMessage());
                                                refreshpage();
                                            } else if (response.getMessage().equals("更新失败")) {
                                                MyToast.errorBig(response.getMessage());
                                            }
                                        }

                                        @Override
                                        public void onFailure(String state, String msg) {
                                            MyToast.errorBig("连接服务器超时！");
                                        }
                                    });
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
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                // 创建一个EditText视图
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View edittext_dialog = inflater.inflate(R.layout.edittext_dialog_layout, null);
                final MaterialEditText editText = edittext_dialog.findViewById(R.id.edit_text);

                builder.setMessage("更新密码：")
                        .setView(edittext_dialog)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击确定按钮后的操作
                                String inputText = editText.getText().toString();

                                if(inputText.isEmpty()){
                                    MyToast.errorBig("不能更新空的内容哦！");
                                }else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("确定要修改密码吗？需要重新登陆哦！")
                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // 点击确定按钮后的操作
                                                    HashMap<String, String> params = new HashMap<>();
                                                    params.put("inputText", inputText);
                                                    params.put("uaccount", loginInfo);
                                                    OkHttp.post(getContext(), Constant.updateUserPassword, params, new OkCallback<Result>() {
                                                        @Override
                                                        public void onResponse(Result response) {
                                                            if (response.getMessage().equals("更新成功")) {
                                                                MyToast.successBig(response.getMessage());
                                                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                editor.clear().apply();

                                                                Intent intent = new Intent(getContext(), WelcomeActivity.class);
                                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                startActivity(intent);
                                                                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                                                getActivity().finish();
                                                            } else if (response.getMessage().equals("更新失败")) {
                                                                MyToast.errorBig(response.getMessage());
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(String state, String msg) {
                                                            MyToast.errorBig("连接服务器超时！");
                                                        }
                                                    });
                                                }
                                            })
                                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // 点击取消按钮后的操作
                                                    dialog.cancel(); // 关闭对话框
                                                }
                                            });
                                    AlertDialog dialog1 = builder.create();
                                    dialog1.show();
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
        });

        return editInfoFragment;
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

    private void selectImages() {
        Matisse.from(this)
                .choose(MimeType.ofImage()) // 选择图片类型的文件
                .countable(true) // 显示已选项数量（默认为 true）
                .maxSelectable(1) // 最多选择项数目，默认为 9
                .theme(R.style.Matisse_My)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) // 屏幕方向控制
                .thumbnailScale(0.85f) // 缩略图比例
                .imageEngine(new GlideEngine()) // 设置图片加载引擎
                .forResult(REQUEST_CODE_CHOOSE); // 启动图片选择器，并设置返回码
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> mSelected = Matisse.obtainResult(data);
            Uri fileUri = mSelected.get(0);
            String filePath = FileUtil.getFilePathFromUri(getContext(), fileUri); // 获取文件路径
            File file = new File(filePath);
            String fileName = file.getName();
            if(avatar_update){
                updateAvatar(file, fileName);
            }else{
                updateBackground(file, fileName);
            }

        }
    }

    private void updateBackground(File file, String fileName) {
        MediaType mediaType;
        if (FileUtil.isImage(file)) {
            mediaType = MediaType.parse("image/*"); // 图片的 MIME 类型
        } else {
            return; // 非图片文件不上传
        }

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName, RequestBody.create(mediaType, file)); // 添加文件扩展名表单字段

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(Constant.upload)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    // 处理上传成功后服务器返回的数据
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 在主线程中执行UI更新操作
                            String resurl = Constant.ServiceIP + responseBody;

                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                            String loginInfo = sharedPreferences.getString("account", "");

                            HashMap<String, String> params = new HashMap<>();
                            params.put("inputText", resurl);
                            params.put("uaccount", loginInfo);
                            OkHttp.post(getContext(), Constant.updateUserBackgroundurl, params, new OkCallback<Result>() {
                                @Override
                                public void onResponse(Result response) {
                                    if(response.getMessage().equals("更新成功")){
                                        MyToast.successBig(response.getMessage());
                                        refreshpage();
                                    }else if(response.getMessage().equals("更新失败")){
                                        MyToast.errorBig(response.getMessage());
                                    }
                                }

                                @Override
                                public void onFailure(String state, String msg) {
                                    MyToast.errorBig("连接服务器超时！");
                                }
                            });

                        }
                    });

                } else {
                    // 上传失败的处理
                    MyToast.errorBig("上传失败");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                // 上传失败的处理
                MyToast.errorBig("连接服务器超时！");
            }
        });
    }

    private void updateAvatar(File file, String fileName) {
        MediaType mediaType;
        if (FileUtil.isImage(file)) {
            mediaType = MediaType.parse("image/*"); // 图片的 MIME 类型
        } else {
            return; // 非图片文件不上传
        }

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName, RequestBody.create(mediaType, file)); // 添加文件扩展名表单字段

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(Constant.upload)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    // 处理上传成功后服务器返回的数据
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 在主线程中执行UI更新操作
                            String resurl = Constant.ServiceIP + responseBody;

                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                            String loginInfo = sharedPreferences.getString("account", "");

                            HashMap<String, String> params = new HashMap<>();
                            params.put("inputText", resurl);
                            params.put("uaccount", loginInfo);
                            OkHttp.post(getContext(), Constant.updateUserAvatarurl, params, new OkCallback<Result>() {
                                @Override
                                public void onResponse(Result response) {
                                    if (response.getMessage().equals("更新成功")) {
                                        MyToast.successBig(response.getMessage());
                                        refreshpage();
                                    } else if (response.getMessage().equals("更新失败")) {
                                        MyToast.errorBig(response.getMessage());
                                    }
                                }

                                @Override
                                public void onFailure(String state, String msg) {
                                    MyToast.errorBig("连接服务器超时！");
                                }
                            });

                        }
                    });

                } else {
                    // 上传失败的处理
                    MyToast.errorBig("上传失败");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                // 上传失败的处理
                MyToast.errorBig("连接服务器超时！");
            }
        });
    }

}
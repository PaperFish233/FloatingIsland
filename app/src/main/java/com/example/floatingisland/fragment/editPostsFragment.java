package com.example.floatingisland.fragment;

import static android.app.Activity.RESULT_OK;

import static com.example.floatingisland.utils.PermissionHelper.requestReanAndWritePermission;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.floatingisland.R;
import com.example.floatingisland.activity.MainActivity;
import com.example.floatingisland.activity.PhotoActivity;
import com.example.floatingisland.activity.TwoActivity;
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
import java.util.HashMap;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import es.dmoral.toasty.MyToast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class editPostsFragment extends Fragment {

    private static final int REQUEST_CODE_CHOOSE = 1224; // 自定义返回码

    private View editPostsFragment;
    private MaterialEditText imageurl;
    private ImageView image;
    private JzvdStd jz_video;
    private SuperTextView topic;

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TopicChooseInfo", Context.MODE_PRIVATE);
        String tid = sharedPreferences.getString("tid", "");
        String name = sharedPreferences.getString("name", "");
        String imageurl = sharedPreferences.getString("imageurl", "");
        if(tid != "" && name != "" && imageurl != ""){
            topic.setLeftString("#" + name);

            int cornerRadius = 20;
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.transforms(new CenterCrop(), new RoundedCorners(cornerRadius));
            requestOptions.override(80, 80);
            Glide.with(getContext())
                    .load(imageurl)
                    .apply(requestOptions)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            topic.setLeftIcon(resource);
                        }
                    });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        editPostsFragment = inflater.inflate(R.layout.fragment_editposts, container, false);

        //动态获取权限
        requestReanAndWritePermission(activity);

        MyToast.init((Application) requireContext().getApplicationContext(),false,true);

        Toolbar toolbar = editPostsFragment.findViewById(R.id.Toolbar_editposts);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理左上角按钮点击事件
                activity.onBackPressed();//销毁自己，用全局变量activity代替getActivity()
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString("account", "");

        //接收传递值
        Bundle bundle = getArguments();
        String lastpid = "";
        String lastcontent = "";
        String lastimageurl = "";
        String lasttopicname = "";
        String topicimageurl = "";
        if (bundle != null) {
            lastpid = bundle.getString("lastpid");
            lastcontent = bundle.getString("lastcontent");
            lastimageurl = bundle.getString("lastimageurl");
            lasttopicname = bundle.getString("lasttopicname");
            topicimageurl = bundle.getString("topicimageurl");
        }

        MaterialEditText connect = editPostsFragment.findViewById(R.id.connect);
        imageurl = editPostsFragment.findViewById(R.id.imageurl);
        TextView git = editPostsFragment.findViewById(R.id.git);
        topic = editPostsFragment.findViewById(R.id.topic);
        ImageView upload = editPostsFragment.findViewById(R.id.upload);
        image = editPostsFragment.findViewById(R.id.image);
        jz_video = editPostsFragment.findViewById(R.id.jz_video);

        connect.setText(lastcontent);
        imageurl.setText(lastimageurl);
        topic.setLeftString("#" + lasttopicname);

        if (lastimageurl.toLowerCase().endsWith(".gif") || lastimageurl.toLowerCase().endsWith(".jpg") || lastimageurl.toLowerCase().endsWith(".jpeg") || lastimageurl.toLowerCase().endsWith(".png")) {
            image.setVisibility(View.VISIBLE);
            jz_video.setVisibility(View.GONE);
            Glide.with(getContext()).load(lastimageurl).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))
                    .override(1280, 720)).into(image);
        } else if (lastimageurl.toLowerCase().endsWith(".mp4") || lastimageurl.toLowerCase().endsWith(".avi") || lastimageurl.toLowerCase().endsWith(".flv") || lastimageurl.toLowerCase().endsWith(".mov")) {
            jz_video.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
            Glide.with(getContext()).load(lastimageurl).centerCrop().into(jz_video.posterImageView);
            jz_video.setUp(lastimageurl, "");
        }

        int cornerRadius = 20;
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transforms(new CenterCrop(), new RoundedCorners(cornerRadius));
        requestOptions.override(80, 80);
        Glide.with(getContext())
                .load(topicimageurl)
                .apply(requestOptions)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        topic.setLeftIcon(resource);
                    }
                });

        String finalLastpid = lastpid;
        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TopicChooseInfo", Context.MODE_PRIVATE);
                String tid = sharedPreferences.getString("tid", "");

                HashMap<String, String> params = new HashMap<>();
                params.put("pid", finalLastpid);
                params.put("uaccount", loginInfo);
                params.put("pconnect", connect.getText().toString());
                params.put("pimageurl", imageurl.getText().toString());
                params.put("tid", tid);
                if(connect.getText().toString().isEmpty()) {
                    MyToast.errorBig("不能更新空的内容哦！");
                }else{
                    OkHttp.post(getContext(), Constant.updatePosts, params, new OkCallback<Result>() {
                        @Override
                        public void onResponse(Result response) {
                            if(response.getMessage().equals("更新成功")){
                                MyToast.successBig(response.getMessage());
                                activity.onBackPressed();

                            }else{
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
        });

        topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TwoActivity.class);
                intent.putExtra("jumpcode", 2);
                getContext().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImages();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PhotoActivity.class);
                intent.setData(Uri.parse(imageurl.getText().toString()));
                getContext().startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        return editPostsFragment;
    }

    private void selectImages() {
        Matisse.from(this)
                .choose(MimeType.ofAll()) // 选择所有类型的文件
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
            uploadFile(file, fileName);
        }
    }

    private void uploadFile(File file, String fileName) {
        MediaType mediaType;
        if (FileUtil.isImage(file)) {
            mediaType = MediaType.parse("image/*"); // 图片的 MIME 类型
        } else if (FileUtil.isVideo(file)) {
            mediaType = MediaType.parse("video/*"); // 视频的 MIME 类型
        } else {
            return; // 非图片和视频文件不上传
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
                            MyToast.successBig("上传成功");
                            imageurl.setText(resurl);
                            if (resurl.toLowerCase().endsWith(".gif") || resurl.toLowerCase().endsWith(".jpg") || resurl.toLowerCase().endsWith(".jpeg") || resurl.toLowerCase().endsWith(".png")) {
                                image.setVisibility(View.VISIBLE);
                                jz_video.setVisibility(View.GONE);
                                Glide.with(getContext()).load(resurl).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))
                                        .override(1280, 720)).into(image);
                            } else if (resurl.toLowerCase().endsWith(".mp4") || resurl.toLowerCase().endsWith(".avi") || resurl.toLowerCase().endsWith(".flv") || resurl.toLowerCase().endsWith(".mov")) {
                                jz_video.setVisibility(View.VISIBLE);
                                image.setVisibility(View.GONE);
                                Glide.with(getContext()).load(resurl).centerCrop().into(jz_video.posterImageView);
                                jz_video.setUp(resurl, "");
                            } else{
                                image.setVisibility(View.GONE);
                                jz_video.setVisibility(View.GONE);
                            }
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
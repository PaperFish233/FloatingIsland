package com.example.floatingisland.activity;

import static com.example.floatingisland.utils.PermissionHelper.requestReanAndWritePermission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.example.floatingisland.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.MyToast;

public class PhotoActivity extends AppCompatActivity {

    // 定义一个ImageView对象，用于后面更新图片的位置
    private PhotoView fullscreenPhotoview;
    private ImageView close;

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        //设置状态栏为黑色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }

        MyToast.init((Application) getApplicationContext(),false,true);

        fullscreenPhotoview = findViewById(R.id.fullscreen_photoview);
        close = this.findViewById(R.id.close);

        Uri imageUri = getIntent().getData();
        if (imageUri != null) {
            Glide.with(this)
                    .load(imageUri)
                    .into(fullscreenPhotoview);
        }

        fullscreenPhotoview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //动态获取权限
                requestReanAndWritePermission(PhotoActivity.this);

                Uri imageUri = getIntent().getData();
                if (imageUri != null) {
                    String fileName = imageUri.getLastPathSegment();
                    String extension = fileName.substring(fileName.lastIndexOf("."));
                    if (extension != null && (extension.equalsIgnoreCase(".gif"))) {
                        // 执行保存图片的操作
                    } else {
                        // 文件类型不支持
                        BitmapDrawable drawable = (BitmapDrawable) fullscreenPhotoview.getDrawable();
                        Bitmap bitmap = drawable.getBitmap();

                        try {
                            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/image.png");
                            FileOutputStream fos = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                            fos.flush();
                            fos.close();

                            // 将图片添加到图库
                            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), null);

                            // 通知图库更新
                            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri contentUri = Uri.fromFile(file);
                            mediaScanIntent.setData(contentUri);
                            sendBroadcast(mediaScanIntent);

                            MyToast.successBig("保存成功");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                return true;
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // 关闭当前 Activity
            }
        });

    }

}


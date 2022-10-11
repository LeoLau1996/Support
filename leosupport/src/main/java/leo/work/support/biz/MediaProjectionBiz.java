package leo.work.support.biz;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.util.Log;
import android.view.Surface;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import leo.work.support.base.LifeControlInterface;
import leo.work.support.base.biz.CommonLifeBiz;
import leo.work.support.support.file.FileSupport;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 录频业务
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/10/9
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * 需要权限：
 * <uses-permission android:name="android.permission.CAMERA" />
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
 * <p>
 * 避坑：
 * https://blog.csdn.net/w690333243/article/details/120476935
 * ---------------------------------------------------------------------------------------------
 **/
public class MediaProjectionBiz extends CommonLifeBiz {

    private static final String TAG = MediaProjectionBiz.class.getSimpleName();
    private PermissionBiz permissionBiz;
    // 保存路径
    private String path;
    // 视频宽高
    private int width, height;

    public MediaProjectionBiz(LifeControlInterface lifeControlInterface) {
        super(lifeControlInterface);
    }

    public void start(String path, int width, int height) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        this.path = path;
        this.width = width;
        this.height = height;
        if (permissionBiz == null) {
            permissionBiz = new PermissionBiz(getLifeControlInterface());
        }
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        permissionBiz.checkPermission(permissions, 10002, new PermissionBiz.OnPermissionBizCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionSuccess(int requestCode, String[] permissions) {
                MediaProjectionManager manager = (MediaProjectionManager) getContext().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
                Intent intent = manager.createScreenCaptureIntent();
                getActivity().startActivityForResult(intent, 10001);
            }

            @Override
            public void onPermissionFail(int requestCode, String[] successPermissions, String[] failPermissions) {

            }
        });
    }

    public void stop() {
        MediaProjectionService.stop(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 10001: {
                MediaProjectionService.startService(getActivity(), path, width, height, resultCode, data);
                break;
            }
        }
    }
}

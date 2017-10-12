package com.example.shekhar.videocapturepictchingsession;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

public class Camera2VideoImageActivity extends AppCompatActivity {
    private TextureView mTexturView;
    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            //Toast removed call setup camera
            setupCamera(width,height);
            
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    private CameraDevice mCameraDevice;
    private CameraDevice.StateCallback mCameraDeviceStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            mCameraDevice = camera;

        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            //freeing camera resources
            camera.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            camera.close();
            mCameraDevice = null;

        }
    };

    @Override
    protected void onResume(){
        super.onResume();

        if(mTexturView.isAvailable()){
            setupCamera(mTexturView.getWidth(), mTexturView.getHeight());
        }
        else {
            mTexturView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }

    @Override
    protected void onPause() {

        closeCamera();
        super.onPause();
    }

    private String mCameraId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2_video_image);


        mTexturView = (TextureView) findViewById(R.id.textureView);

    }
//Converting App to fullscreen  Sticky immersive mode
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        View decorView = getWindow().getDecorView();
        if(hasFocus){
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            |View.SYSTEM_UI_FLAG_FULLSCREEN
            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


    }

    //CameraManager is needed to access the camera resources and find out the number of cameras on a device
    private void setupCamera(int width, int height){
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            for(String cameraId : cameraManager.getCameraIdList()){
                CameraCharacteristics mCameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                //skipping the front camera because we will not be using front camera
                if(mCameraCharacteristics.get(CameraCharacteristics.LENS_FACING)== CameraCharacteristics.LENS_FACING_FRONT){
                    continue;
                }
                mCameraId = cameraId;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


//closeCamera also frees up camera resources
    private void closeCamera(){
        if(mCameraDevice != null){
            mCameraDevice.close();
            mCameraDevice = null;

        }
    }
}

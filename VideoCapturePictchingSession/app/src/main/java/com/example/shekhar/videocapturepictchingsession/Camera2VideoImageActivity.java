package com.example.shekhar.videocapturepictchingsession;

import android.graphics.SurfaceTexture;
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
            //to check if textureView is available
            Toast.makeText(Camera2VideoImageActivity.this, "TextureView Available", Toast.LENGTH_SHORT).show();
            
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

    @Override
    protected void onResume(){
        super.onResume();

        if(mTexturView.isAvailable()){

        }
        else {
            mTexturView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }

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
}

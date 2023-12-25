package com.nibiru.testmediaplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int mTextureId;
    private SurfaceTexture mSurfaceTexture;
    private Surface mSurface;
    private MediaPlayer mMediaPlayer;
    private SurfaceView mSurfaceView;
    private ExoPlayer mExoPlayer;
    private String url = "rtsp://127.0.0.1:8554/jax";
//    private String url = "http://223.111.252.80/live-bvc/641571/live_3546378252847849_56347916/index.m3u8?expires=1701936452&len=0&oi=3748173316&pt=android&qn=250&trid=10078abb86e2290144e58e6208b38d31ff99&sigparams=cdn,expires,len,oi,pt,qn,trid&cdn=cn-gotcha01&sign=9c73b68cb89c8089407b424c493e296c&sk=527f7e42174f5b2fa05de6b9f7482d9e&flvsk=61a4f3899d53a462a3da3bd134780288&p2p_type=8192&sl=1&free_type=0&mid=9595564&tmshift=9&sid=cn-jssz-cm-02-29&chash=1&bmt=1&sche=ban&bvchls=1&score=1&pp=rtmp&source=onetier&trace=10c9&site=9e658ee59bcc6bf423c5020b4ef3e4c4&zoneid_l=151355395&sid_l=stream_name_cold&order=1&bili_room_id=30739632&proj_source=bilibili&_nva_ext_=---<DIDL-Lite xmlns=\"urn:schemas-upnp-org:metadata-1-0/DIDL-Lite/\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:upnp=\"urn:schemas-upnp-org:metadata-1-0/upnp/\"><item id=\"0\" parentID=\"-1\" restricted=\"1\"><dc:title>档末余欢。来对枪吧</dc:title><upnp:storageMedium>UNKNOWN</upnp:storageMedium><upnp:writeStatus>UNKNOWN</upnp:writeStatus><upnp:longDescription>jl7NqP1Ep2m-Bm3QFkS_e9YDtaHtRJjXCGeo1qwrtol1pk9N1-oCPgjiY-ilsBXcUj5tjpJ8vUj8792sh2Y4Sc9iiSbWvcypvpkUqo9f5psPdePQscovtmVgpUbZBqb882HNuFwQ0Kr-iR7I5mHaIjPmD-oj33Wu3WOHHGdnz8B7rncz6xZTy19x45-gGV25BKGGUnNBnxtaar7ecd9wHUJT3RjbSzCYP0Gv-KpIHuii12hhd84p30NicmJsgfzdj8z5dSh_Asbm8Jl2VYm48xKqRzFyHaXawHYiAC22JBsKOScWWIElTsKWwInYZOq78B7FnPHkrzRf1ZGusQiLxq3sHNnJB7GDr6jaObFO5XBD7V2cj7yOXZtlxDAQhAjOqE_teZAWmcznxS1Z1uvJyfJa0B7PdkBjWNgHJOmNQfLdbJ9XhRipxTPC5GhKYyMtSWc84AJl5Gj2RDog_20S7n_xng5O6hCxcxyUWme5CMfiT8B1t23wO83wjolrNV9YH0zPyGzINOgTzxguqyAk93YfDiQdoG3Ttxq36no9OYtzeRJtGPsOVL8w10ElxKPYVheaKdf6bz_kt6ROmLxNvCHNnDgF2QCWSp8Fo8jO31IUIdOjSAViMx4c_FWM-UOye12VfOmCDAa_BUy-4d5FLw</upnp:longDescription><res protocolInfo=\"http-get:*:application/x-mpegURL:*\">http://223.111.252.80/live-bvc/641571/live_3546378252847849_56347916/index.m3u8?expires=1701936452&amp;len=0&amp;oi=3748173316&amp;pt=android&amp;qn=250&amp;trid=10078abb86e2290144e58e6208b38d31ff99&amp;sigparams=cdn,expires,len,oi,pt,qn,trid&amp;cdn=cn-gotcha01&amp;sign=9c73b68cb89c8089407b424c493e296c&amp;sk=527f7e42174f5b2fa05de6b9f7482d9e&amp;flvsk=61a4f3899d53a462a3da3bd134780288&amp;p2p_type=8192&amp;sl=1&amp;free_type=0&amp;mid=9595564&amp;tmshift=9&amp;sid=cn-jssz-cm-02-29&amp;chash=1&amp;bmt=1&amp;sche=ban&amp;bvchls=1&amp;score=1&amp;pp=rtmp&amp;source=onetier&amp;trace=10c9&amp;site=9e658ee59bcc6bf423c5020b4ef3e4c4&amp;zoneid_l=151355395&amp;sid_l=stream_name_cold&amp;order=1&amp;bili_room_id=30739632&amp;proj_source=bilibili&amp;_nva_ext_=</res><upnp:class>object.item.videoItem</upnp:class></item></DIDL-Lite>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSurfaceView = (SurfaceView) findViewById(R.id.video_view);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            //动态检查权限
//            checkAuthority();
//        }else{
//            createPlayer();
//        }
//        createPlayer();
        createExoPlayer();
    }

    private void createPlayer() {
//        mTextureId = createTextureID();
//        mSurfaceTexture = new SurfaceTexture(mTextureId);
//        mSurface = new Surface(mSurfaceTexture);
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d("Jax", "onPrepared: ");
                mMediaPlayer.start();
            }
        });
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                try {
                    mMediaPlayer.setDisplay(mSurfaceView.getHolder());
                    mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(url));
//                    mMediaPlayer.setDataSource("https://jhkn3-vod-cdn-hicloudvr.hismarttv.com/upload/1/1895619251/deptNumber_JG20230601030303265/2ec3ce3713f3b3353740d6350c31edcc_1692234596230.mp4");
                    mMediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });
    }

    private void createExoPlayer() {
        mExoPlayer = new ExoPlayer.Builder(this).setSeekBackIncrementMs(15000L).build();
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                mExoPlayer.setVideoSurfaceHolder(mSurfaceView.getHolder());
                MediaItem mediaItem = MediaItem.fromUri(url);
                mExoPlayer.setMediaItem(mediaItem);
                mExoPlayer.prepare();
                mExoPlayer.play();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });
    }


    private int createTextureID() {
        int[] arrayOfInt = new int[1];
        GLES20.glGenTextures(1, arrayOfInt, 0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, arrayOfInt[0]);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        return arrayOfInt[0];
    }

    //检查权限
    private void checkAuthority(){

        int hasWritePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasReadPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> permissions = new ArrayList<String>();
        if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), 1);
        }else {
            createPlayer();
        }
    }

    //授权回掉
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    createPlayer();
                }else {
//                    finish();
                }
                break;
            default:
                break;
        }
    }
}
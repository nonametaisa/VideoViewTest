package com.example.yusaku.videoviewtest;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;
import java.net.URLDecoder;


public class MainActivity extends ActionBarActivity {
    private final static int CHOSE_FILE_CODE=12345;
    int RESULT_PICK_FILENAME = 1;
    private VideoView mVideoView;
    private Button button;
    private Context mContext;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVideoView =(VideoView)findViewById(R.id.videoView00);

        mContext = this;




        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pickFilenameFromGallery();

            }
        });

    }

    private void pickFilenameFromGallery() {
        Intent i = new Intent( Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_PICK_FILENAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_PICK_FILENAME && resultCode == RESULT_OK && null != data)
        {   Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Video.Media.DATA };
            Cursor cursor = getContentResolver().query( selectedImage, filePathColumn, null, null, null); cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex); cursor.close();
     //       Toast.makeText(this, picturePath, Toast.LENGTH_LONG).show();
       //     Toast.makeText(this, picturePath, Toast.LENGTH_LONG).show();

            mVideoView.setVideoURI(Uri.parse( picturePath));

       //     mVideoView.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.drawable.sample1));
            mVideoView.start();

            Toast.makeText(this,String.valueOf(getTime(selectedImage ,picturePath) ),Toast.LENGTH_LONG).show();
             }
    }

    private int getTime(Uri ur ,String path){
        int checktime = 0;
        MediaPlayer mediaPlayer = MediaPlayer.create(mContext,ur.parse(path));

            checktime = mediaPlayer.getDuration();
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }



        return checktime;


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    }


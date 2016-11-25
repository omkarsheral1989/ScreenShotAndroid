package com.example.omkars.screencapture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

  private ImageView imageView;
  private ScreenshotManager screenshotManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    imageView = (ImageView) findViewById(R.id.imageView);
    screenshotManager = new ScreenshotManager(this);
  }

  public void start(View v) {
    if(!screenshotManager.isCapturing()) {
      Intent startCapturingIntent = screenshotManager.getStartCapturingIntent();
      startActivityForResult(startCapturingIntent, 10);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 10) {
      if (resultCode == RESULT_OK) {
        screenshotManager.startCapturing(resultCode, data);
      }
    }
  }

  public void capture(View view) {
    Image image = screenshotManager.captureScreenShot();

    String fileName = "screenshot.jpg";
    FileOutputStream fileOutputStream = null;
    try {
      fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
      ImageUtils.saveImageAsJpeg(image, fileOutputStream);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    if (fileOutputStream != null) {
      try {
        fileOutputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    image.close();


    FileInputStream inputStream = null;
    try {
      File screenshotFile = this.getFileStreamPath(fileName);
      inputStream = new FileInputStream(screenshotFile);
//      inputStream = openFileInput(fileName);
      Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
      imageView.setImageBitmap(bitmap);



      UploadManager uploadManager = new UploadManager(this);
      uploadManager.uploadFile(screenshotFile);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}

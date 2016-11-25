package com.example.omkars.screencapture;

import android.content.Context;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

public class UploadManager {

  private static final String MY_ACCESS_KEY_ID = "AKIAJN3IBS52T6UHRAXQ";
  private static final String MY_SECRET_KEY = "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";
  private static final CreateBucketRequest MY_PICTURE_BUCKET = new CreateBucketRequest("kajskdjasijdhkajshd");


  public UploadManager(Context context) {
  }

  public void uploadFile(final File file) {
    new Thread() {
      @Override
      public void run() {
        AmazonS3Client s3Client = new AmazonS3Client(new BasicAWSCredentials(MY_ACCESS_KEY_ID, MY_SECRET_KEY));
        s3Client.createBucket(MY_PICTURE_BUCKET);

        PutObjectRequest por = new PutObjectRequest("kajskdjasijdhkajshd",
            "screenshot.jpg", file);
        s3Client.putObject(por);
      }
    }.start();
  }

  public void foo(File file) {
    AmazonS3Client s3Client = new AmazonS3Client(new BasicAWSCredentials(MY_ACCESS_KEY_ID, MY_SECRET_KEY));
    s3Client.createBucket(MY_PICTURE_BUCKET);

    PutObjectRequest por = new PutObjectRequest("kajskdjasijdhkajshd",
        "OmkarFile2.txt", file);
    s3Client.putObject(por);
  }

}

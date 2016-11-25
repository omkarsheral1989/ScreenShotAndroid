package com.example.omkars.screencapture;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by omkars on 25/11/16.
 */
public class UploadManagerTest {

  private Context context;

  @Before
  public void setUp() throws Exception {
    context = InstrumentationRegistry.getTargetContext();
  }

  @Test
  public void foo() throws Exception {
    UploadManager uploadManager = new UploadManager(context);
    uploadManager.foo(getFile());
  }

  File getFile() {
    FileOutputStream fileOutputStream = null;
    try {
      String fileName = "testFile.txt";
      fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
      fileOutputStream.write(65);
      fileOutputStream.flush();
      fileOutputStream.close();

      return context.getFileStreamPath(fileName);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
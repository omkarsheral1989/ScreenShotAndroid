package com.example.omkars.screencapture;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.util.DisplayMetrics;

import static android.content.Context.MEDIA_PROJECTION_SERVICE;

/**
 * Used to capture screen shot.
 */
public class ScreenshotManager {

  private Context context;
  private ImageReader imageReader;
  private MediaProjection mediaProjection;

  public ScreenshotManager(Context context) {
    this.context = context.getApplicationContext();
  }

  /**
   * Use {@link android.app.Activity#startActivityForResult(Intent, int)}.
   * Then call {@link #startCapturing(int, Intent)}.
   */
  public Intent getStartCapturingIntent() {
    MediaProjectionManager mediaProjectionManager =
        getMediaProjectionManager();
    Intent screenCaptureIntent = mediaProjectionManager.createScreenCaptureIntent();
    return screenCaptureIntent;
  }

  /**
   * You call call {@link #captureScreenShot()} after this.
   *
   * @see #getStartCapturingIntent()
   */
  public void startCapturing(int resultCode, Intent resultIntent) {
    DisplayMetrics displayMetrics = getDisplayMetrics();
    int screenHeight = getScreenHeight(displayMetrics);
    int screenWidth = getScreenWidth(displayMetrics);
    int screenDensity = getScreenDensity(displayMetrics);
    int flags = DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR;

    imageReader = ImageReader.newInstance(screenWidth, screenHeight, PixelFormat.RGBA_8888, 1);

    mediaProjection = getMediaProjectionManager().getMediaProjection(resultCode, resultIntent);
    mediaProjection.createVirtualDisplay("screenshot", screenWidth, screenHeight, screenDensity,
        flags,
        imageReader.getSurface(),
        null, null);
  }

  public void stopCapturing(){
    if (!isCapturing()) {
      throw new IllegalStateException("Call startCapturing() first");
    }

    imageReader.close();
    imageReader = null;

    mediaProjection.stop();
    mediaProjection = null;
  }


  public boolean isCapturing() {
    return null != mediaProjection;
  }

  /**
   * Call {@link Image#close()} on previously captured image before calling this.
   */
  public Image captureScreenShot() {
    if (!isCapturing()) {
      throw new IllegalStateException("Call startCapturing() first");
    }
    return imageReader.acquireLatestImage();
  }

  private MediaProjectionManager getMediaProjectionManager() {
    return (MediaProjectionManager) context.getSystemService(MEDIA_PROJECTION_SERVICE);
  }


  private DisplayMetrics getDisplayMetrics() {
    return context.getResources().getDisplayMetrics();
  }

  private int getScreenDensity(DisplayMetrics displayMetrics) {
    return displayMetrics.densityDpi;
  }

  private int getScreenWidth(DisplayMetrics displayMetrics) {
    return displayMetrics.widthPixels;
  }

  private int getScreenHeight(DisplayMetrics displayMetrics) {
    return displayMetrics.heightPixels;
  }
}

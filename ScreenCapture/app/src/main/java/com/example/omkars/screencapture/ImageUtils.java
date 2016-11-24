package com.example.omkars.screencapture;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Created by omkars on 24/11/16.
 */
public class ImageUtils {

  public static void saveImageAsJpeg(Image image, OutputStream outputStream) {
    Image.Plane plane = getPlane(image);
    int bitmapWidth = getBitmapWidth(image);
    int bitmapHeight = getBitmapHeight(image);

    ByteBuffer byteBuffer = plane.getBuffer();
    Bitmap bitmap = Bitmap.createBitmap(
        bitmapWidth,
        bitmapHeight,
        Bitmap.Config.ARGB_8888);
    bitmap.copyPixelsFromBuffer(byteBuffer);
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
  }

  private static Image.Plane getPlane(Image image) {
    Image.Plane[] planes = image.getPlanes();
    return planes[0];
  }

  private static int getBitmapWidth(Image image) {
    Image.Plane plane = getPlane(image);
    int pixelStride = plane.getPixelStride();
    int rowStride = plane.getRowStride();
//    int rowPadding = rowStride - pixelStride * image.getWidth();
//    return image.getWidth() + rowPadding / pixelStride;
    return rowStride/pixelStride;
  }

  private static int getBitmapHeight(Image image) {
    return image.getHeight();
  }

}

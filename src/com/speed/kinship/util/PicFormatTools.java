package com.speed.kinship.util;  
  
import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.InputStream;  
  


import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.graphics.Canvas;  
import android.graphics.PixelFormat;  
import android.graphics.drawable.BitmapDrawable;  
import android.graphics.drawable.Drawable;  

public class PicFormatTools {
	
    private static PicFormatTools instance;  
  
    private PicFormatTools() {
    	
    }
    
    public static PicFormatTools getInstance() {  
        if (instance == null) {  
            instance = new PicFormatTools();  
        }  
        return instance;  
    }  
  
    /**
     * convert bytes to input steam 
     * @param b
     * @return
     */
    public InputStream Byte2InputStream(byte[] b) {  
        ByteArrayInputStream bais = new ByteArrayInputStream(b);  
        return bais;  
    }  
  
    /**
     * convert input stream to bytes  
     * @param is
     * @return
     */
    public byte[] InputStream2Bytes(InputStream is) {  
        String str = "";  
        byte[] readByte = new byte[1024];  
        @SuppressWarnings("unused")
		int readCount = -1;  
        try {  
            while ((readCount = is.read(readByte, 0, 1024)) != -1) {  
                str += new String(readByte).trim();  
            }  
            return str.getBytes();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /**
     * convert bitmap to input stream
     * @param bm
     * @return
     */
    public InputStream Bitmap2InputStream(Bitmap bm) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
        InputStream is = new ByteArrayInputStream(baos.toByteArray());  
        return is;  
    }  
  
    /**
     * convert bitmap to input stream with quality
     * @param bm
     * @param quality
     * @return
     */
    public InputStream Bitmap2InputStream(Bitmap bm, int quality) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);  
        InputStream is = new ByteArrayInputStream(baos.toByteArray());  
        return is;  
    }  
  
    /**
     * convert input stream to bitmap 
     * @param is
     * @return
     */
    public Bitmap InputStream2Bitmap(InputStream is) {  
        return BitmapFactory.decodeStream(is);  
    }  
  
    /**
     * convert drawable to input stream 
     * @param d
     * @return
     */
    public InputStream Drawable2InputStream(Drawable d) {  
        Bitmap bitmap = this.drawable2Bitmap(d);  
        return this.Bitmap2InputStream(bitmap);  
    }  
  
    /**
     * convert input stream to drawable  
     * @param is
     * @return
     */
    public Drawable InputStream2Drawable(InputStream is) {  
        Bitmap bitmap = this.InputStream2Bitmap(is);  
        return this.bitmap2Drawable(bitmap);  
    }  
  
    /**
     * convert drawable to bytes 
     * @param d
     * @return
     */
    public byte[] Drawable2Bytes(Drawable d) {  
        Bitmap bitmap = this.drawable2Bitmap(d);  
        return this.Bitmap2Bytes(bitmap);  
    }  
  
    /**
     * convert bytes to drawable 
     * @param b
     * @return
     */
    public Drawable Bytes2Drawable(byte[] b) {  
        Bitmap bitmap = this.Bytes2Bitmap(b);  
        return this.bitmap2Drawable(bitmap);  
    }  
  
    /**
     * convert bitmap to bytes 
     * @param bm
     * @return
     */
    public byte[] Bitmap2Bytes(Bitmap bm) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);  
        return baos.toByteArray();  
    }  
  
    /**
     * convert bytes to bitmap  
     * @param b
     * @return
     */
    public Bitmap Bytes2Bitmap(byte[] b) {  
        if (b.length != 0) {  
            return BitmapFactory.decodeByteArray(b, 0, b.length);  
        }  
        return null;  
    }  
  
    /**
     * convert drawable to bitmap 
     * @param drawable
     * @return
     */
    public Bitmap drawable2Bitmap(Drawable drawable) {  
        Bitmap bitmap = Bitmap  
                .createBitmap(  
                        drawable.getIntrinsicWidth(),  
                        drawable.getIntrinsicHeight(),  
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888  
                                : Bitmap.Config.RGB_565);  
        Canvas canvas = new Canvas(bitmap);  
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),  
                drawable.getIntrinsicHeight());  
        drawable.draw(canvas);  
        return bitmap;  
    }  
  
    /**
     * convert bitmap to drawable 
     * @param bitmap
     * @return
     */
    public Drawable bitmap2Drawable(Bitmap bitmap) {  
        @SuppressWarnings("deprecation")
		BitmapDrawable bd = new BitmapDrawable(bitmap);  
        Drawable d = (Drawable) bd;  
        return d;  
    }  
} 
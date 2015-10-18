package com.night.chat.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by nightrain on 10/12/15.
 */
public class Avatar extends Model {
    private byte[] data;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Bitmap getBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }
}

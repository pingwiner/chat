package com.night.chat.model;

/**
 * Created by nightrain on 10/12/15.
 */
public class Attachment extends Model {
    public enum Type {IMAGE, VIDEO, AUDIO}

    public Type type;
    public String mime;
    public byte[] data;

}

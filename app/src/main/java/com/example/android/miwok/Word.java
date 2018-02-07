package com.example.android.miwok;

/**
 * Created by Bhati on 02-Feb-18.
 */

public class Word {

    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageResourceId;
    private int mAudioResourceId;

    public Word(String defaultTranslation,String miwokTranslation) {
      this(defaultTranslation,miwokTranslation,0);
    }

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId) {
      this(defaultTranslation,miwokTranslation,imageResourceId,0);
    }

    public Word(String mDefaultTranslation, String mMiwokTranslation, int mImageResourceId, int mAudioResourceId) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mImageResourceId = mImageResourceId;
        this.mAudioResourceId = mAudioResourceId;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getmAudioResourceId() {
        return mAudioResourceId;
    }
}

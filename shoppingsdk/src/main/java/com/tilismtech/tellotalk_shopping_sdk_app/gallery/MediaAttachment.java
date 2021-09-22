package com.tilismtech.tellotalk_shopping_sdk_app.gallery;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class MediaAttachment implements Parcelable {

    private Uri fileUri;
    private String mimeType;
    private String fileTag;
    private boolean isEdited = false;

    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileTag() {
        return fileTag;
    }

    public void setFileTag(String fileTag) {
        this.fileTag = fileTag;
    }

    public MediaAttachment() {
        fileUri = Uri.parse("");
        mimeType = "";
        fileTag = "";
    }

    private MediaAttachment(Parcel in) {
        fileUri = (Uri) in.readValue(Uri.class.getClassLoader());
        mimeType = in.readString();
        fileTag = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(fileUri);
        dest.writeString(mimeType);
        dest.writeString(fileTag);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof MediaAttachment) {
            return fileUri.toString().equalsIgnoreCase(((MediaAttachment) obj).getFileUri().toString());
        }
        return super.equals(obj);
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    @SuppressWarnings("unused")
    public static final Creator<MediaAttachment> CREATOR = new Creator<MediaAttachment>() {
        @Override
        public MediaAttachment createFromParcel(Parcel in) {
            return new MediaAttachment(in);
        }

        @Override
        public MediaAttachment[] newArray(int size) {
            return new MediaAttachment[size];
        }
    };
}

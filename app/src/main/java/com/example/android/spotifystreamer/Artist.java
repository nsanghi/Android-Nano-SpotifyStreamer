package com.example.android.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 22-06-2015.
 */
public class Artist implements Parcelable {

    private String id;
    private String name;
    private String url;

    public Artist(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Artist(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);
        this.id = data[0];
        this.name = data[1];
        this.url = data[2];
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray( new String[] {this.id, this.name, this.url});
    }

    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}

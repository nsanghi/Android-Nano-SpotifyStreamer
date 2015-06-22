package com.example.android.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 22-06-2015.
 */
public class ArtistTrack  implements Parcelable {

    private String trackName;
    private String albumName;
    private String smallImageUrl;
    private String largeImageUrl;
    private String previewUrl;

    public ArtistTrack(String trackName, String albumName, String smallImageUrl, String largeImageUrl, String previewUrl) {
        this.trackName = trackName;
        this.albumName = albumName;
        this.smallImageUrl = smallImageUrl;
        this.largeImageUrl = largeImageUrl;
        this.previewUrl = previewUrl;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public ArtistTrack(Parcel in) {
        String[] data = new String[5];
        in.readStringArray(data);
        this.trackName = data[0];
        this.albumName = data[1];
        this.smallImageUrl = data[2];
        this.largeImageUrl = data[3];
        this.previewUrl = data[4];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray( new String[] {this.trackName, this.albumName, this.smallImageUrl, this.largeImageUrl, this.previewUrl});

    }

    public static final Parcelable.Creator<ArtistTrack> CREATOR = new Parcelable.Creator<ArtistTrack>() {
        @Override
        public ArtistTrack createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public ArtistTrack[] newArray(int size) {
            return new ArtistTrack[size];
        }
    };
}

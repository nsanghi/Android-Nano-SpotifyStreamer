package com.example.android.spotifystreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by admin on 21-06-2015.
 */
public class TrackArrayAdapter extends ArrayAdapter<Track> {

    private String LOG_TAG = TrackArrayAdapter.class.getSimpleName();

    private Context context;
    private List<Track> tracks;

    public TrackArrayAdapter(Context context, int resource, List<Track> tracks) {
        super(context, resource, tracks);
        this.context = context;
        this.tracks = tracks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Track track = tracks.get(position);

        View listItemView = convertView;
        if (listItemView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItemView = inflater.inflate(R.layout.list_item_artist_tracks, null);
        }

        //populate artist name
        TextView trackName = (TextView) listItemView.findViewById(R.id.track_name);
        trackName.setText(track.name);

        TextView albumName = (TextView) listItemView.findViewById(R.id.album_name);
        albumName.setText(track.album.name);

        ImageView trackImage = (ImageView) listItemView.findViewById(R.id.track_image);

        //try to load the image with the smallest size first
        //progressively going to bigger images till full list is traversed
        String url = null;
        List<Image> images = track.album.images;
        if (images != null && !images.isEmpty()) {
            int idx = images.size() - 1;
            for (int i = idx; i >= 0; i--) {
                url = images.get(i).url;
                if (url != null)
                    break;
            }
        }
        //if (url != null && !url.isEmpty()) {
        Picasso.with(context).load(url).into(trackImage);
        //}

        return listItemView;
    }

}


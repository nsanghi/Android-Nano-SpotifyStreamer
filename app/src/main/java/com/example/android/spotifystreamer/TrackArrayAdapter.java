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


/**
 * Created by admin on 21-06-2015.
 */
public class TrackArrayAdapter extends ArrayAdapter<ArtistTrack> {

    private String LOG_TAG = TrackArrayAdapter.class.getSimpleName();

    private Context context;
    private List<ArtistTrack> tracks;

    public TrackArrayAdapter(Context context, int resource, List<ArtistTrack> tracks) {
        super(context, resource, tracks);
        this.context = context;
        this.tracks = tracks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArtistTrack track = tracks.get(position);

        View listItemView = convertView;
        if (listItemView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItemView = inflater.inflate(R.layout.list_item_artist_tracks, null);
        }

        //populate artist name
        TextView trackName = (TextView) listItemView.findViewById(R.id.track_name);
        trackName.setText(track.getTrackName());

        TextView albumName = (TextView) listItemView.findViewById(R.id.album_name);
        albumName.setText(track.getAlbumName());

        ImageView trackImage = (ImageView) listItemView.findViewById(R.id.track_image);
        Picasso.with(context).load(track.getSmallImageUrl()).into(trackImage);

        return listItemView;
    }

    public List<ArtistTrack> getTracks() {
        return tracks;
    }
}


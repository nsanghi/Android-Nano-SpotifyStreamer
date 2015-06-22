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
        ViewHolder holder;

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_artist_tracks, null);
            holder = new ViewHolder();
            holder.trackName = (TextView) view.findViewById(R.id.track_name);
            holder.albumName = (TextView) view.findViewById(R.id.album_name);
            holder.trackImage = (ImageView) view.findViewById(R.id.track_image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        //populate values
        holder.trackName.setText(track.getTrackName());
        holder.albumName.setText(track.getAlbumName());
        Picasso.with(context).load(track.getSmallImageUrl()).into(holder.trackImage);

        return view;
    }

    public List<ArtistTrack> getTracks() {
        return tracks;
    }

    static class ViewHolder {
        TextView trackName;
        TextView albumName;
        ImageView trackImage;
    }
}


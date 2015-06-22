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
 * Created by admin on 20-06-2015.
 */
public class ArtistArrayAdapter extends ArrayAdapter<Artist> {

    private String LOG_TAG = ArtistArrayAdapter.class.getSimpleName();

    private Context context;
    private List<Artist> artists;

    public ArtistArrayAdapter(Context context, int resource, List<Artist> artists) {
        super(context, resource, artists);
        this.context = context;
        this.artists = artists;
        //Log.d(LOG_TAG, "artists: \n"+this.artists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Artist artist = artists.get(position);
        //Log.d(LOG_TAG, "ArtistName: "+artist.name);

        View listItemView = convertView;
        if (listItemView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItemView = inflater.inflate(R.layout.list_item_artist_search, null);
        }

        //populate artist name
        TextView artistName = (TextView) listItemView.findViewById(R.id.artist_name);
        artistName.setText(artist.getName());

        ImageView artistImage = (ImageView) listItemView.findViewById(R.id.artist_image);
        Picasso.with(context).load(artist.getUrl()).into(artistImage);

        return listItemView;
    }

    public List<Artist> getArtists () {
        return artists;
    }

}

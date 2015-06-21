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

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;

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
        artistName.setText(artist.name);

        ImageView artistImage = (ImageView) listItemView.findViewById(R.id.artist_image);

        //try to load the image with the smallest size first
        //progressively going to bigger images till full list is traversed
        String url = null;
        List<Image> images = artist.images;
        if (images != null && !images.isEmpty()) {
            int idx = images.size() - 1;
            for (int i = idx; i >= 0; i--) {
                url = images.get(i).url;
                if (url != null)
                    break;
            }
        }
        //if (url != null && !url.isEmpty()) {
            Picasso.with(context).load(url).into(artistImage);
        //}

        return listItemView;
    }

}

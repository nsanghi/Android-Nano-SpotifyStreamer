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

        ViewHolder holder;

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_artist_search, null);
            holder = new ViewHolder();
            holder.artistName = (TextView) view.findViewById(R.id.artist_name);
            holder.artistImage = (ImageView) view.findViewById(R.id.artist_image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        holder.artistName.setText(artist.getName());
        Picasso.with(context).load(artist.getUrl()).into(holder.artistImage);

        return view;
    }

    public List<Artist> getArtists () {
        return artists;
    }

    static class ViewHolder {
        TextView artistName;
        ImageView artistImage;
    }

}

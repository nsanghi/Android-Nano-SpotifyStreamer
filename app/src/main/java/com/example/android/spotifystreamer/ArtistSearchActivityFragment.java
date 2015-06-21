package com.example.android.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistSearchActivityFragment extends Fragment {

    private final String LOG_TAG = ArtistSearchActivityFragment.class.getSimpleName();
    private final String EXTRA_ARTISTNAME= "com.example.android.spotifystreamer.artistname";
    private final String EXTRA_SPOTIFYID = "com.example.android.spotifystreamer.spotifyid";
    private ArtistArrayAdapter mArtistAdapter;
    private EditText mSearchText;
    private static Toast mToast;

    public ArtistSearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_artist_search, container,false);

        mArtistAdapter = new ArtistArrayAdapter(getActivity(),R.layout.list_item_artist_search,
                new ArrayList<Artist>());

        ListView listView = (ListView) rootView.findViewById(R.id.listview_artist_search);
        listView.setAdapter(mArtistAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist artist = mArtistAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), ArtistTracksActivity.class);
                intent.putExtra(EXTRA_ARTISTNAME, artist.name);
                intent.putExtra(EXTRA_SPOTIFYID, artist.id);
                startActivity(intent);
                //Toast.makeText(getActivity(), "To be Implemented", Toast.LENGTH_SHORT).show();
            }
        });

        mSearchText = (EditText) rootView.findViewById(R.id.artist_search_text);

        mSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.d(LOG_TAG,"onTextChange Called");
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Log.d(LOG_TAG, "searchTerm : " + s.toString());
                updateArtistList(s.toString());
            }
        });


        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        updateArtistList(mSearchText.getText().toString());

    }

    private void updateArtistList(String searchString) {
        FetchArtistTask artistTask = new FetchArtistTask();
        artistTask.execute(searchString);
    }

    public class FetchArtistTask extends AsyncTask<String, Void, List<Artist>> {

        private String LOG_TAG = FetchArtistTask.class.getSimpleName();

        @Override
        protected List<Artist> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String searchTerm = params[0];
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                //Log.d(LOG_TAG, "search term is empty");
                return null;
            }

            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            ArtistsPager artists = spotify.searchArtists(searchTerm);
            return artists.artists.items;
        }

        @Override
        protected void onPostExecute(List<Artist> artists) {
            mArtistAdapter.clear();
            if (artists != null && !artists.isEmpty()) {
                if (mToast != null) {
                    mToast.cancel();
                }
                mArtistAdapter.addAll(artists);
            } else if (!mSearchText.getText().toString().isEmpty())  {
                mToast = Toast.makeText(getActivity(), R.string.no_artists, Toast.LENGTH_SHORT);
                mToast.show();
            }
        }
    }
}

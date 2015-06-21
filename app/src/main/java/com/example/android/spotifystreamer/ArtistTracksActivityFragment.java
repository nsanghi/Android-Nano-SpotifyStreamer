package com.example.android.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistTracksActivityFragment extends Fragment {
    private final String LOG_TAG = ArtistTracksActivityFragment.class.getSimpleName();
    private final String EXTRA_SPOTIFYID = "com.example.android.spotifystreamer.spotifyid";
    private TrackArrayAdapter mTrackAdapter;
    private String mSpotifyId;

    public ArtistTracksActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_artist_tracks, container, false);

        //Get artist name and spotifyid from intent
        Intent intent = getActivity().getIntent();
        mSpotifyId = intent.getStringExtra(EXTRA_SPOTIFYID);
        mTrackAdapter = new TrackArrayAdapter(
                getActivity(),
                R.layout.list_item_artist_tracks,
                new ArrayList<Track>());

        ListView listView = (ListView)rootView.findViewById(R.id.listview_artist_tracks);
        listView.setAdapter(mTrackAdapter);
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        updateTracks();
    }

    private void updateTracks() {
        FetchTracksTask tracksTask = new FetchTracksTask();
        tracksTask.execute();

    }

    private class FetchTracksTask extends AsyncTask<Void, Void, List<Track>> {

        private String LOG_TAG = ArtistSearchActivityFragment.FetchArtistTask.class.getSimpleName();


        @Override
        protected List<Track> doInBackground(Void... params) {

            if (mSpotifyId == null || mSpotifyId.trim().isEmpty()) {
                Log.d(LOG_TAG, "spotifyId is missing or empty");
                return null;
            }

            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            Map<String,Object> options = new HashMap<String,Object>();
            options.put("country","US");
            Tracks tracks = spotify.getArtistTopTrack(mSpotifyId, options);
            return tracks.tracks;
        }

        @Override
        protected void onPostExecute(List<Track> tracks) {
            mTrackAdapter.clear();
            if (tracks != null && !tracks.isEmpty()) {
                mTrackAdapter.addAll(tracks);
            } else {
                Toast.makeText(getActivity(), R.string.no_tracks, Toast.LENGTH_SHORT).show();
            }

        }
    }
}

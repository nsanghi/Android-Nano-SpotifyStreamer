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
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.RetrofitError;


/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistTracksActivityFragment extends Fragment {
    private final String LOG_TAG = ArtistTracksActivityFragment.class.getSimpleName();
    private final String EXTRA_SPOTIFYID = "com.example.android.spotifystreamer.spotifyid";
    private final String EXTRA_TRACKLIST = "com.example.android.spotifystreamer.tracks";

    private TrackArrayAdapter mTrackAdapter;
    private String mSpotifyId;

    public ArtistTracksActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_artist_tracks, container, false);

        List<ArtistTrack> adapterData;

        if (savedInstanceState == null || !savedInstanceState.containsKey(EXTRA_TRACKLIST)) {
            adapterData = new ArrayList<ArtistTrack>();
        } else {
            adapterData = savedInstanceState.getParcelableArrayList(EXTRA_TRACKLIST);
        }



        //Get artist name and spotifyid from intent
        Intent intent = getActivity().getIntent();
        mSpotifyId = intent.getStringExtra(EXTRA_SPOTIFYID);
        mTrackAdapter = new TrackArrayAdapter(
                getActivity(),
                R.layout.list_item_artist_tracks,
                adapterData);

        ListView listView = (ListView)rootView.findViewById(R.id.listview_artist_tracks);
        listView.setAdapter(mTrackAdapter);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_TRACKLIST, (ArrayList) mTrackAdapter.getTracks());
        //Log.v(LOG_TAG, "Entered onSaveInstanceState" );
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

    private class FetchTracksTask extends AsyncTask<Void, Void, List<ArtistTrack>> {

        private String LOG_TAG = ArtistSearchActivityFragment.FetchArtistTask.class.getSimpleName();


        @Override
        protected List<ArtistTrack> doInBackground(Void... params) {

            if (mSpotifyId == null || mSpotifyId.trim().isEmpty()) {
                Log.d(LOG_TAG, "spotifyId is missing or empty");
                return null;
            }

            List<ArtistTrack> result = new ArrayList<ArtistTrack>();
            String trackName;
            String albumName;
            String smallImageUrl;
            String largeImageUrl;
            String previewURL;

            try {
                SpotifyApi api = new SpotifyApi();
                SpotifyService spotify = api.getService();
                Map<String,Object> options = new HashMap<String,Object>();
                options.put("country","US");
                Tracks tracks = spotify.getArtistTopTrack(mSpotifyId, options);
                for (Track track : tracks.tracks) {

                    trackName = track.name;
                    albumName = track.album.name;

                    smallImageUrl = null;
                    List<Image> images = track.album.images;
                    if (images != null && !images.isEmpty()) {
                        int idx = images.size() - 1;
                        for (int i = idx; i >= 0; i--) {
                            smallImageUrl = images.get(i).url;
                            if (smallImageUrl != null)
                                break;
                        }
                    }

                    largeImageUrl = null;
                    images = track.album.images;
                    if (images != null && !images.isEmpty()) {
                        int idx = images.size() - 1;
                        for (int i = 0; i >= idx; i++) {
                            largeImageUrl = images.get(i).url;
                            if (largeImageUrl != null)
                                break;
                        }
                    }
                    previewURL = track.preview_url;

                    result.add(new ArtistTrack(trackName,albumName,smallImageUrl,largeImageUrl,previewURL));
                }

            } catch (RetrofitError err) {
                Log.e(LOG_TAG, "error from fetching data:\n"+ err.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<ArtistTrack> tracks) {
            mTrackAdapter.clear();
            if (tracks != null && !tracks.isEmpty()) {
                mTrackAdapter.addAll(tracks);
            } else {
                Toast.makeText(getActivity(), R.string.no_tracks, Toast.LENGTH_SHORT).show();
            }

        }
    }
}

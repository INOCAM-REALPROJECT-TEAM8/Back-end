package com.example.backend.util.spotify;

import com.example.backend.track.dto.Track;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrackSpotifyRequest extends AbstractSpotifyRequest {
    public TrackSpotifyRequest(SpotifyTokenManager spotifyTokenManager) {
        super(spotifyTokenManager);
    }

    public List<Track> fetchDataTrackList(String trackIds) {
        return super.fetchDataFromSpotifyAPI(trackIds,  0);
    }

    public Track fetchDataOneTrack(String trackId) {
        return fetchSingleTrackFromSpotifyAPI(trackId);
    }
    private Track fetchSingleTrackFromSpotifyAPI(String parameter) {
        List<Track> tracks = super.fetchDataFromSpotifyAPI(parameter, 0);
        return tracks.get(0);
    }

    @Override
    protected String generateSpotifyUrl(String trackId) {
        return generateSpotifyUrl(new String[]{trackId});
    }

    protected String generateSpotifyUrl(String... trackIds) {
        return String.format("https://api.spotify.com/v1/tracks?ids=%s", String.join(",", trackIds));
    }

    @Override
    protected JsonNode extractTracksNode(JsonNode responseBody) {
        return responseBody.path("tracks");
    }
}

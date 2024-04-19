package com.example.spotify_encore;

public class Wrap {
    private String songName;
    private String artistName;
    private String albumName;

    public Wrap(String songName, String albumName, String artistName) {
        this.songName = songName;
        this.artistName = artistName;
        this.albumName = albumName;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

}
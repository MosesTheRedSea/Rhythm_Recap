package com.example.spotify_encore;

public class Wrap {
    private String songName;
    private String songUrl;

    public Wrap(String songName, String songUrl) {
        this.songName = songName;
        this.songUrl = songUrl;
    }

    public String getSongName() {
        return songName;
    }

    public String getSongUrl() {

        return songUrl;
    }
}
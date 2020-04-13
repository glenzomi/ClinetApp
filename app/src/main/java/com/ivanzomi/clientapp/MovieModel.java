package com.ivanzomi.clientapp;

public class MovieModel {

    public String movieName;
    public String movieImage;
    public String movieVideo;
    public String movieCategory;
    public int movieCount;
    public String createdAt;

    public MovieModel(String movieName, String movieImage, String movieVideo, String movieCategory, int movieCount, String createdAt) {
        this.movieName = movieName;
        this.movieImage = movieImage;
        this.movieVideo = movieVideo;
        this.movieCategory = movieCategory;
        this.movieCount = movieCount;
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public MovieModel(String movieName, String movieImage, String movieVideo, String movieCategory, int movieCount) {
        this.movieName = movieName;
        this.movieImage = movieImage;
        this.movieVideo = movieVideo;
        this.movieCategory = movieCategory;
        this.movieCount = movieCount;
    }

    public MovieModel(String movieName, String movieImage, String movieVideo, String movieCategory) {
        this.movieName = movieName;
        this.movieImage = movieImage;
        this.movieVideo = movieVideo;
        this.movieCategory = movieCategory;
    }

    public MovieModel() {
    }

    public String getMovieName() {
        return movieName;
    }

    public int getMovieCount() {
        return movieCount;
    }

    public void setMovieCount(int movieCount) {
        this.movieCount = movieCount;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovieVideo() {
        return movieVideo;
    }

    public void setMovieVideo(String movieVideo) {
        this.movieVideo = movieVideo;
    }

    public String getMovieCategory() {
        return movieCategory;
    }

    public void setMovieCategory(String movieCategory) {
        this.movieCategory = movieCategory;
    }
}

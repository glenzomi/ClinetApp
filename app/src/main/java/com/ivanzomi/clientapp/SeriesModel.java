package com.ivanzomi.clientapp;

public class SeriesModel {
    String seriesName,seriesImage,seriesCategory,createdAt;

    public SeriesModel(String seriesName, String seriesImage, String seriesCategory, String createdAt, int seriesCount) {
        this.seriesName = seriesName;
        this.seriesImage = seriesImage;
        this.seriesCategory = seriesCategory;
        this.createdAt = createdAt;
        this.seriesCount = seriesCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    int seriesCount;

    public int getSeriesCount() {
        return seriesCount;
    }

    public void setSeriesCount(int seriesCount) {
        this.seriesCount = seriesCount;
    }

    public SeriesModel(String seriesName, String seriesImage, String seriesCategory, int seriesCount) {
        this.seriesName = seriesName;
        this.seriesImage = seriesImage;
        this.seriesCategory = seriesCategory;
        this.seriesCount = seriesCount;
    }

    public SeriesModel(String seriesName, String seriesImage, String seriesCategory) {
        this.seriesName = seriesName;
        this.seriesImage = seriesImage;
        this.seriesCategory = seriesCategory;
    }

    public SeriesModel() {
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getSeriesImage() {
        return seriesImage;
    }

    public void setSeriesImage(String seriesImage) {
        this.seriesImage = seriesImage;
    }

    public String getSeriesCategory() {
        return seriesCategory;
    }

    public void setSeriesCategory(String seriesCategory) {
        this.seriesCategory = seriesCategory;
    }
}

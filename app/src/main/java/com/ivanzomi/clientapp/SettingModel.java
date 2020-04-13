package com.ivanzomi.clientapp;

public class SettingModel {

    public String
            useAdMob,
            findByCategory,
            useCategoryReplace,
            useSlideShow,
            appId,
            bannerId,
            interstitialId,
            nativeId,
            rewardId,
            slide1,
            slide2,
            slide3,
            slide4,
            slide5;

    public SettingModel(String useSlideShow, String findByCategory, String useCategoryReplace, String appId, String bannerId, String interstitialId, String nativeId, String rewardId, String slide1, String slide2, String slide3, String slide4, String slide5) {
        this.useSlideShow = useSlideShow;
        this.findByCategory = findByCategory;
        this.useCategoryReplace = useCategoryReplace;
        this.appId = appId;
        this.bannerId = bannerId;
        this.interstitialId = interstitialId;
        this.nativeId = nativeId;
        this.rewardId = rewardId;
        this.slide1 = slide1;
        this.slide2 = slide2;
        this.slide3 = slide3;
        this.slide4 = slide4;
        this.slide5 = slide5;
    }

    public SettingModel(String useAdMob, String findByCategory, String useCategoryReplace, String useSlideShow, String appId, String bannerId, String interstitialId, String nativeId, String rewardId, String slide1, String slide2, String slide3, String slide4, String slide5) {
        this.useAdMob = useAdMob;
        this.findByCategory = findByCategory;
        this.useCategoryReplace = useCategoryReplace;
        this.useSlideShow = useSlideShow;
        this.appId = appId;
        this.bannerId = bannerId;
        this.interstitialId = interstitialId;
        this.nativeId = nativeId;
        this.rewardId = rewardId;
        this.slide1 = slide1;
        this.slide2 = slide2;
        this.slide3 = slide3;
        this.slide4 = slide4;
        this.slide5 = slide5;
    }

    public SettingModel() {
    }

    public String getUseSlideShow() {
        return useSlideShow;
    }

    public void setUseSlideShow(String useSlideShow) {
        this.useSlideShow = useSlideShow;
    }

    public String getFindByCategory() {
        return findByCategory;
    }

    public void setFindByCategory(String findByCategory) {
        this.findByCategory = findByCategory;
    }

    public String getUseCategoryReplace() {
        return useCategoryReplace;
    }

    public void setUseCategoryReplace(String useCategoryReplace) {
        this.useCategoryReplace = useCategoryReplace;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getInterstitialId() {
        return interstitialId;
    }

    public void setInterstitialId(String interstitialId) {
        this.interstitialId = interstitialId;
    }

    public String getNativeId() {
        return nativeId;
    }

    public void setNativeId(String nativeId) {
        this.nativeId = nativeId;
    }

    public String getRewardId() {
        return rewardId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public String getSlide1() {
        return slide1;
    }

    public void setSlide1(String slide1) {
        this.slide1 = slide1;
    }

    public String getSlide2() {
        return slide2;
    }

    public void setSlide2(String slide2) {
        this.slide2 = slide2;
    }

    public String getSlide3() {
        return slide3;
    }

    public void setSlide3(String slide3) {
        this.slide3 = slide3;
    }

    public String getSlide4() {
        return slide4;
    }

    public void setSlide4(String slide4) {
        this.slide4 = slide4;
    }

    public String getSlide5() {
        return slide5;
    }

    public void setSlide5(String slide5) {
        this.slide5 = slide5;
    }

    public String getUseAdMob() {
        return useAdMob;
    }

    public void setUseAdMob(String useAdMob) {
        this.useAdMob = useAdMob;
    }
}

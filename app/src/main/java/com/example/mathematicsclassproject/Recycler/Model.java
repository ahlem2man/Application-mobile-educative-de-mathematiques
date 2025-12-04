package com.example.mathematicsclassproject.Recycler;

public class Model {
    String id, chapter, img, pdfLink, videoLink;

    public Model() {
    }

    public Model(String id, String chapter, String img, String pdfLink, String videoLink) {
        this.id = id;
        this.chapter = chapter;
        this.img = img;
        this.pdfLink = pdfLink;
        this.videoLink = videoLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}

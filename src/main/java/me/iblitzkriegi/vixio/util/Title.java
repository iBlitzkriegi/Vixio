package me.iblitzkriegi.vixio.util;

public class Title {

    private String text;
    private String url;

    public Title(String text, String url) {
        this.text = text;
        this.url = url;
    }

    public Title(String text) {
        this.text = text;
        this.url = null;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

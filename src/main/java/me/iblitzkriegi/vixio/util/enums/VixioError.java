package me.iblitzkriegi.vixio.util.enums;

public enum VixioError {

    BOT_NO_PERMISSION("The requested bot does not have enough permission to executed the requested action"),
    BOT_NOT_FOUND("Bot with the provided name could not be found");

    private final String text;

    VixioError(String s) {
        this.text = s;
    }

    @Override
    public String toString() {
        return text;
    }

}

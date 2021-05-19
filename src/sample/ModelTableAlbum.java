package sample;

public class ModelTableAlbum {

    String albumName,singerName;

    public ModelTableAlbum(String albumName, String singerName) {
        this.albumName = albumName;
        this.singerName = singerName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }
}

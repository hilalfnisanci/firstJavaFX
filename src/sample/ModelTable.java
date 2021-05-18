package sample;

public class ModelTable {

    String musicName,singerName,albumName,date,numofListening;

    public ModelTable(String musicName, String singerName, String albumName, String date, String numofListening) {
        this.musicName = musicName;
        this.singerName = singerName;
        this.albumName = albumName;
        this.date = date;
        this.numofListening = numofListening;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumofListening() {
        return numofListening;
    }

    public void setNumofListening(String numofListening) {
        this.numofListening = numofListening;
    }
}

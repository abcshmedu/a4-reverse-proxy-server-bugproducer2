package edu.hm.bugproducer.models;

public class Disc extends Medium {

    private String barcode;
    private String director;
    private int fsk;

    public Disc(String director, String barcode, String title, int fsk) {
        super(title);
        this.barcode = barcode;
        this.director = director;
        this.fsk = fsk;
    }

    public Disc() {
        super("");
        this.barcode = "";
        this.director = "";
        this.fsk = -1;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getDirector() {
        return director;
    }

    public int getFsk() {
        return fsk;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setFsk(int fsk) {
        this.fsk = fsk;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

package edu.hm.bugproducer.models;

/**
 * Disc Class.
 * @author Mark Tripolt
 * @author Johannes Arzt
 * @author Tom Maier
 * @author Patrick Kuntz
 */
public class Disc extends Medium {
    /**
     * unique number of a disc
     */
    private String barcode;
    /**
     * name of the director of the disc
     */
    private String director;
    /**
     * required age to watch the disc
     */
    private int fsk;

    /**
     * Disc Constructor with parameters.
     *
     * @param director who created the movie
     * @param barcode  unique number of a disc
     * @param title    name of a disc
     * @param fsk      required age to watch the disc
     */
    public Disc(String director, String barcode, String title, int fsk) {
        super(title);
        this.barcode = barcode;
        this.director = director;
        this.fsk = fsk;
    }

    /**
     * Disc Constructor.
     */
    public Disc() {
        super("");
        this.barcode = "";
        this.director = "";
        this.fsk = -1;
    }

    /**
     * getBarcode method.
     * gets the barcode of a disc
     *
     * @return barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * getDirector method.
     * gets the director of a disc
     *
     * @return director
     */
    public String getDirector() {
        return director;
    }

    /**
     * getFsk method.
     * gets the fsk of a disc
     *
     * @return fsk
     */
    public int getFsk() {
        return fsk;
    }

    /**
     * setBarcode method.
     * sets the barcode for a disc
     *
     * @param barcode unique number
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * setDirector method.
     * sets the diretctor for a disc
     *
     * @param director name of creator
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * setFsk method.
     * sets the required age for a disc
     *
     * @param fsk required age
     */
    public void setFsk(int fsk) {
        this.fsk = fsk;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

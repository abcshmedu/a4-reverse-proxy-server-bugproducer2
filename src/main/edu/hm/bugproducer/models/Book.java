package edu.hm.bugproducer.models;

/**
 * Book Class.
 * @author Mark Tripolt
 * @author Johannes Arzt
 * @author Tom Maier
 * @author Patrick Kuntz
 */
public class Book extends Medium {
    /** author of a book  */
    private String author;
    /** unique isbn of a book */
    private String isbn;

    /**
     * Book Constructor.
     * with empty title, empty author and empty isbn
     */
    public Book() {
        super("");
        this.author = "";
        this.isbn = "";
    }
    /**
     * Book Constructor.
     * @param author name of the writer
     * @param isbn unique identification number
     * @param title name of book
     */
    public Book(String author, String isbn, String title) {
        super(title);
        this.author = author;
        this.isbn = isbn;
    }

    /**
     * getAuthor method.
     * gives you the name of the author of a book
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * getIsbn method.
     * gives you the isbn of a book
     * @return isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * setAuthor method.
     * you can set the name of an author
     * @param author string variable
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * setIsbn method.
     * set the isbn you want
     * @param isbn string variable
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

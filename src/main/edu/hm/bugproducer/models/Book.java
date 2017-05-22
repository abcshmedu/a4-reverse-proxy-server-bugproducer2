package edu.hm.bugproducer.models;

public class Book extends Medium {

    private String author;
    private String isbn;

    public Book() {
        super("No Name");
    }

    public Book(String author, String isbn, String title) {
        super(title);
        this.author = author;
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

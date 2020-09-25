package com.mindtree.shoppingcart.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "BOOK")
public class Book extends Product implements Serializable,Comparable<Book>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "GENRE")
	private String genre;

	@Column(name = "AUTHOR")
	private String author;

	@Column(name = "PUBLICATION")
	private String publication;
	
	@Override
	public int compareTo(Book o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublication() {
		return publication;
	}

	public void setPublication(String publication) {
		this.publication = publication;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((publication == null) ? 0 : publication.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (publication == null) {
			if (other.publication != null)
				return false;
		} else if (!publication.equals(other.publication))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [genre=" + genre + ", author=" + author + ", publication=" + publication + "]";
	}

	public Book(int productId, String productName, int quantity, float price, List<CartProduct> cartProducts,
			String genre, String author, String publication) {
		super(productId, productName, quantity, price, cartProducts);
		this.genre = genre;
		this.author = author;
		this.publication = publication;
	}

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Book(int productId, String productName, int quantity, float price, List<CartProduct> cartProducts) {
		super(productId, productName, quantity, price, cartProducts);
		// TODO Auto-generated constructor stub
	}
	
	

}

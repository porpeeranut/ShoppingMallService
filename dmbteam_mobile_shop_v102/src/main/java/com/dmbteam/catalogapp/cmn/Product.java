package com.dmbteam.catalogapp.cmn;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import android.content.Context;

/**
 * The Class Product.
 */
public class Product implements Comparable<Product> {

	/** The id. */
	@Attribute(required = true)
	private int id;

	/** The category. */
	@Attribute(required = true)
	private int category;

	/** The price. */
	@Attribute(required = true)
	private double price;

	/** The discount. */
	@Attribute(required = false)
	private double discount;

	/** The date. */
	@Element(required = true)
	private String date;

	/** The title. */
	@Element(required = true)
	private String title;

	/** The photo. */
	@Element(required = false)
	private String photo;

	/** The description. */
	@Element(required = false)
	private String description;

	/** The condition. */
	@Element(required = true)
	private String condition;

	/** The color. */
	@Element(required = true)
	private String color;

    private String noValue;

	/** The discounted price. */
	private double discountedPrice;

    public Product(){}

    public Product(int id, int category, double price, String date, String title, String photo, String noValue) {
        this.id = id;
        this.category = category;
        this.price = price;
        this.date = date;
        this.title = title;
        this.photo = photo;
        this.noValue = noValue;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /* (non-Javadoc)
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
	@Override
	public int compareTo(Product anotherProduct) {
		return Integer.valueOf(id).compareTo(anotherProduct.id);
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Gets the discount.
	 *
	 * @return the discount
	 */
	public double getDiscount() {
		return discount;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the photo.
	 *
	 * @param context the context
	 * @return the photo
	 */
	public String getPhoto(Context context) {

		return photo;
	}

	/**
	 * Gets the drawable id.
	 *
	 * @param context the context
	 * @return the drawable id
	 */
	public int getDrawableId(Context context) {
		return context.getResources().getIdentifier(photo, "drawable",
				context.getPackageName());
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Checks if is network resource.
	 *
	 * @param resource the resource
	 * @return true, if is network resource
	 */
	public boolean isNetworkResource(String resource) {

		return resource.startsWith("http");
	}

	/**
	 * Checks if is network resource.
	 *
	 * @return true, if is network resource
	 */
	public boolean isNetworkResource() {

		return photo.startsWith("http");
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * Gets the condition.
	 *
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

    public String getNoValue() {
        return noValue;
    }

	/**
	 * Gets the discounted price.
	 *
	 * @return the discounted price
	 */
	public double getDiscountedPrice() {
		discountedPrice = getPrice() * (1 - getDiscount() / 100);

		return discountedPrice;
	}

}

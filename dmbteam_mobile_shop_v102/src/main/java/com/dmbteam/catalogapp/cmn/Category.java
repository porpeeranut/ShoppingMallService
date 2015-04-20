package com.dmbteam.catalogapp.cmn;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * The Class Category.
 */
public class Category implements Comparable<Category> {

	/**
	 * Instantiates a new category.
	 */
	public Category() {
		this.subCategoriesIds = new ArrayList<Integer>();
	}

	/** The sub categories ids. */
	private List<Integer> subCategoriesIds;
	
	/** The tree index. */
	private int treeIndex;

	/** The id. */
	@Attribute(required = true)
	private int id;

	/** The parent id. */
	@Attribute(required = false)
	private int parentId;

	/** The title. */
	@Element
	private String title;
	
	/** The Opened. */
	private boolean mOpened;
	
	/** The Is main. */
	private boolean mIsMain;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the parent id.
	 *
	 * @return the parent id
	 */
	public int getParentId() {
		return parentId;
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
	 * Gets the sub categories ids.
	 *
	 * @return the sub categories ids
	 */
	public List<Integer> getSubCategoriesIds() {
		return subCategoriesIds;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Category anotherCategory) {
		return Integer.valueOf(id).compareTo(anotherCategory.id);
	}

	/**
	 * Adds the sub category id.
	 *
	 * @param subCategoryId the sub category id
	 */
	public void addSubCategoryId(int subCategoryId) {
		this.subCategoriesIds.add(subCategoryId);
	}

	
	/**
	 * Sets the tree index.
	 *
	 * @param padding the new tree index
	 */
	public void setTreeIndex(int padding){
		this.treeIndex = padding;
	}

	/**
	 * Gets the tree index.
	 *
	 * @return the tree index
	 */
	public int getTreeIndex() {
		return treeIndex;
	}

	/**
	 * Sets the opened.
	 *
	 * @param opened the new opened
	 */
	public void setOpened(boolean opened) {
		mOpened = opened;
	}

	/**
	 * Checks if is opened.
	 *
	 * @return true, if is opened
	 */
	public boolean isOpened() {
		return mOpened;
	}

	/**
	 * Checks if is main.
	 *
	 * @return true, if is main
	 */
	public boolean isMain() {
		return mIsMain;
	}

	/**
	 * Sets the main.
	 *
	 * @param isMain the new main
	 */
	public void setMain(boolean isMain) {
		mIsMain = isMain;
	}

}
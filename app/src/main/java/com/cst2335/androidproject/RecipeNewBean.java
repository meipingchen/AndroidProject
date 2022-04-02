package com.cst2335.androidproject;

public class RecipeNewBean {
    private String ingredient;
    private String title;
    private String url;

    /**
     * constructor for instantiation
     *
     * @param title String
     * @param url   String
     */
    public RecipeNewBean(String ingredient,String title, String url) {
        super();
        this.ingredient = ingredient;
        this.title = title;
        this.url = url;
    }

    /**
     * to get the ingredient
     *
     * @return the ingredient
     */
    public String getIngredient() {
        return ingredient;
    }

    /**
     * to set the ingredient
     *
     * @param ingredient the ingredient to set
     */
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * to get the title
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * to set the title
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * to get the url
     *
     * @return url String
     */
    public String getURL() {
        return url;
    }

    /**
     * to set the url
     *
     * @param url to set
     */
    public void setURL(String url) {
        this.url = url;
    }
    /**
     *
     * the constructor
     */
    public RecipeNewBean() {
        super();
    }
}
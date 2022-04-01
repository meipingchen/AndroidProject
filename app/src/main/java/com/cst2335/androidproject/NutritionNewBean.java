package com.cst2335.androidproject;

public class NutritionNewBean {
    private String title;
    private String url;

    /**
     * constructor for instantiation
     *
     * @param title String
     * @param url   String
     */
    public NutritionNewBean(String title, String url) {
        super();
        this.title = title;
        this.url = url;
    }

    /**
     * to get the calories
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * to the calories
     *
     * @param title the calories to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * to get the fat
     *
     * @return url String
     */
    public String getURL() {
        return url;
    }

    /**
     * to set the fat
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
    public NutritionNewBean() {
        super();
    }
/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NutritionNewBean that = (NutritionNewBean) o;

        if (title.equals(that.title) != 0) return false;
        return String.compare(that.url, url) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        String temp;
        temp =title;
        result = (int) (temp ^ (temp >>> 32));
        temp = url;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }*/
}
package com.cst2335.androidproject;

public class NutritionNewBean {
    private double calories;
    private double fat;

    /**
     * constructor for instantiation
     *
     * @param calories double
     * @param fat      double
     */
    public NutritionNewBean(double calories, double fat) {
        super();
        this.calories = calories;
        this.fat = fat;
    }

    /**
     * to get the calories
     *
     * @return the calories double
     */
    public double getCalories() {
        return calories;
    }

    /**
     * to the calories
     *
     * @param calories the calories to set
     */
    public void setCalories(double calories) {
        this.calories = calories;
    }

    /**
     * to get the fat
     *
     * @return fat double
     */
    public double getFat() {
        return fat;
    }

    /**
     * to set the fat
     *
     * @param fat fat to set
     */
    public void setFat(double fat) {
        this.fat = fat;
    }
    /**
     *
     * the constructor
     */
    public NutritionNewBean() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NutritionNewBean that = (NutritionNewBean) o;

        if (Double.compare(that.calories, calories) != 0) return false;
        return Double.compare(that.fat, fat) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(calories);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(fat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
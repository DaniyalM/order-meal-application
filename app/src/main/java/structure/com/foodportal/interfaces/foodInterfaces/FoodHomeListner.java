package structure.com.foodportal.interfaces.foodInterfaces;

public interface FoodHomeListner {


    void onBlogClick(int pos);
    void popularrecipe(int pos);
    void recommendedrecipe(int pos);
    void featuredrecipe(int pos);
    void betterforurbites(int pos);
    void recentlyViewed(int pos);
    void categorySliderClick(int position);
    void masterTechniquesClick(int position);
    void onSaveRecipe(int slug);
    void onFavoriteRecipe(int slug);
    void onLatestVideoClick(int pos);
}

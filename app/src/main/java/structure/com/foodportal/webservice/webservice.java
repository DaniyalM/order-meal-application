package structure.com.foodportal.webservice;


import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.models.AllCategory;
import structure.com.foodportal.models.Brand;
import structure.com.foodportal.models.CartModule.CartProductMainClass;
import structure.com.foodportal.models.City;
import structure.com.foodportal.models.MakeModel;
import structure.com.foodportal.models.Notification;
import structure.com.foodportal.models.Order;
import structure.com.foodportal.models.ProductModelAPI;
import structure.com.foodportal.models.UserModel;
import structure.com.foodportal.models.foodModels.CategorySliderWrapper;
import structure.com.foodportal.models.foodModels.CommentsWrapper;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.HeaderWrapper;
import structure.com.foodportal.models.foodModels.RecipeWrapper;
import structure.com.foodportal.models.foodModels.SavedStoriesWrapper;
import structure.com.foodportal.models.foodModels.Section;
import structure.com.foodportal.models.foodModels.User;

/**
 * Created by khanubaid on 12/28/2017.
 */

public interface webservice {


    //Get Category
    @GET(AppConstant.ALL_CATEGORYS)
    Call<Api_Response<JsonObject>> getAllCategory();

    //Sub Category
    @GET(AppConstant.SUB_CATEGORYS)
    Call<Api_Array_Response<JsonObject>> getSubCategory(@Query("category_id") int category_id);

    @GET(AppConstant.SUB_CATEGORYS)
    Call<Api_Response<ArrayList<AllCategory>>> getAllSubCategory(@Query("category_id") int category_id);

    @GET(AppConstant.GET_BRANDS)
    Call<Api_Response<ArrayList<Brand>>> GetAllBrands(@Query("category_id") int category_id);

    @GET(AppConstant.GET_MAKE_MODELS)
    Call<Api_Response<ArrayList<MakeModel>>> GetAllMakeModels(@Query("brand_id") int brand_id);

    @GET(AppConstant.GET_PRODUCTS)
    Call<Api_Response<ArrayList<ProductModelAPI>>> getAllProducts(@Query("country_id") String country_id, @Query("city_id") Integer city_id, @Query("sub_category_id") Integer sub_category_id, @Query("category_id") Integer category_id, @Query("brand_id") Integer brand_id, @Query("user_id") Integer user_id);

    @FormUrlEncoded
//    @POST(AppConstant.ADD_PRODUCTS)
    Call<Api_Response<JsonObject>> addProducts(@Field("body") String body, @Field("images") File images);

    @Multipart
    @POST(AppConstant.ADD_PRODUCTS)
    Call<Api_Response> addProducts2(
            @PartMap Map<String, RequestBody> CampaignBody,
            @Part ArrayList<MultipartBody.Part> images
    );

    @Multipart
    @POST(AppConstant.EDIT_PRODUCT)
    Call<Api_Response> editProduct(
            @PartMap Map<String, RequestBody> CampaignBody,
            @Part ArrayList<MultipartBody.Part> images
    );

    @Multipart
    @POST(AppConstant.USER_SIGNUP)
    Call<Api_Response<UserModel>> registerUser(@PartMap Map<String, RequestBody> body,
                                               @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST(AppConstant.USER_LOGIN)
    Call<Api_Response<UserModel>> loginUser(@Field("email") String email,
                                            @Field("password") String password,
                                            @Field("role_id") String role_id,
                                            @Field("device_type") String device_type,
                                            @Field("device_id") String device_id);

    @Multipart
    @POST(AppConstant.USER_VERIFY_CODE)
    Call<Api_Response<UserModel>> verifyUserCode(@PartMap Map<String, RequestBody> body);

    @Multipart
    @POST(AppConstant.USER_UPDATE)
    Call<Api_Response<UserModel>> updateUser(@PartMap Map<String, RequestBody> body,
                                             @Part MultipartBody.Part image);

    @GET(AppConstant.GET_MY_PRODUCTS)
    Call<Api_Response<ArrayList<ProductModelAPI>>> getUserProducts(@Query("user_id") Integer user_id);


    @POST(AppConstant.USER_LOGOUT)
    Call<Api_Response> logout(@Query("user_id") int user_id, @Query("device_id") String device_id);

    @POST(AppConstant.FORGOT_PASSWORD)
    Call<Api_Response<StringWarpper>> forgotPasswordEmail(@Query("email") String email);

    @GET(AppConstant.GET_COUNTRIES)
    Call<Api_Response<ArrayList<structure.com.foodportal.models.Country>>> getCountires();

    @GET(AppConstant.GET_CITIES)
    Call<Api_Response<ArrayList<City>>> getCities(@Query("country_id") String country_id);

    @POST(AppConstant.DELETE_PRODUCT)
    Call<Api_Response> deleteProduct(@Query("product_id") int product_id, @Query("user_id") int user_id);

    @POST(AppConstant.RESEND_CODE)
    Call<Api_Response> resendCode(@Query("email") String email);

    @POST(AppConstant.UPDATE_PASSWORD)
    Call<Api_Response> forgotChangePassword(@Query("email") String email,
                                            @Query("reset_code") String reset_code,
                                            @Query("password") String password);

    @POST(AppConstant.USER_CHANGE_PASSWORD)
    Call<Api_Response> changepassword(@Query("user_id") int user_id,
                                      @Query("new_password") String new_password,
                                      @Query("old_password") String old_password);

    @POST(AppConstant.ADD_TO_FAV)
    Call<Api_Response> addtoFavorite(@Query("product_id") String product_id, @Query("user_id") String user_id);

    @POST(AppConstant.MARKVIEWED)
    Call<Api_Response> MarkViewed(@Query("product_id") String product_id, @Query("user_id") String user_id, @Query("device_id") String device_id);

    //   Call<Api_Response> MarkViewed(@Query("product_id") String product_id, @Query("user_id") String user_id);

    @GET(AppConstant.GET_CART)
    Call<Api_Response<ArrayList<CartProductMainClass>>> getCart(@Query("user_id") String user_id);

    @POST(AppConstant.ADD_TO_CART)
    Call<Api_Response> addProductToCart(@Query("product_id") String product_id, @Query("user_id") String user_id, @Query("device_id") String device_id, @Query("rent_type_id") String rent_type_id);


    @POST(AppConstant.REMOVE_FROM_CART)
    Call<Api_Response> removeProductfromCart(@Query("product_id") String product_id, @Query("user_id") String user_id, @Query("device_id") String device_id);

    @POST(AppConstant.CART_CHECKOUT)
    Call<Api_Response> checkout(@Query("product_id") String product_id,
                                @Query("user_id") String user_id,
                                @Query("quantity") String quantity,
                                @Query("unit_price") String unit_price,
                                @Query("rent_type_id") String rent_type_id,
                                @Query("payment_method_id") String payment_method_id);

    @GET(AppConstant.GET_ORDERS)
    Call<Api_Response<ArrayList<Order>>> GetOrders(@Query("user_id") String user_id);

    @POST(AppConstant.RATE_ORDER)
    Call<Api_Response> RateOrder(
            @Query("product_id") String product_id,
            @Query("product_rating") String product_rating,
            @Query("device_id") String device_id,
            @Query("user_id") String user_id,
            @Query("product_user_id") String product_user_id,
            @Query("product_user_rating") String product_user_rating,
            @Query("order_id") String order_id
    );


    @GET(AppConstant.GET_NOTIFICATIONS)
    Call<Api_Response<ArrayList<Notification>>> GetNotifications(@Query("user_id") String user_id);


    @POST(AppConstant.MARK_ORDER)
    Call<Api_Response> acceptOrder(@Query("order_id") String order_id, @Query("status") int status, @Query("pickup_date") String pickup_date);

    //////////////////////////////////////////////////////////////////////////
//*my apis*//
    @Headers("Accept: application/json")
    @POST(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_LOGIN)
    @FormUrlEncoded
    Call<Api_Response<JsonObject>> userlogin(@Field("email") String email,
                                             @Field("password") String password,
                                             @Field("device_type") String device_type,
                                             @Field("device_token") String device_token);

    @Headers("Accept: application/json")
    @POST(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SAVE_STORY)
    @FormUrlEncoded
    Call<Api_Response> sacvestory(@Field("user_id") String email,
                                  @Field("type") String type,
                                  @Field("feature_type_id") String feature_type_id,
                                  @Field("story_id") String story_id);

    @Headers("Accept: application/json")
    @POST(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_MARK_FAVORITE)
    @FormUrlEncoded
    Call<Api_Response> markfavorite(@Field("user_id") String email,
                                    @Field("type") String type,
                                    @Field("feature_type_id") String feature_type_id,
                                    @Field("story_id") String story_id);

    @Headers("Accept: application/json")
    @POST(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_SOCIAL_LOGIN_FACEBOOK)
    @FormUrlEncoded
    Call<Api_Response> LoginFACEBOOK(@Field("email") String email, @Field("provider_id") String provider_id,
                                     @Field("name") String name, @Field("from") String from, @Field("device_type") String device_type,
                                     @Field("device_token") String device_token);

    @Headers("Accept: application/json")
    @POST(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_SOCIAL_LOGIN_GOOGLE)
    @FormUrlEncoded
    Call<Api_Response> LoginGOOGLE(@Field("email") String email, @Field("provider_id") String provider_id,
                                   @Field("name") String name, @Field("from") String from, @Field("avatar_original") String avatar_original, @Field("device_type") String device_type,
                                   @Field("device_token") String device_token);


    @GET(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_DETAILS)
    Call<Api_Response<JsonObject>> getfooddetail(@Query("story_slug") String story_slug, @Query("user_id") String user_id);


    @GET(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_ALL_REVIEW)
    Call<Api_Response<CommentsWrapper>> getAlReviews(@Query("story_id") String story_id, @Query("type") String type);


    @POST(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SEND_REVIEW)
    Call<Api_Response<JsonObject>> sendreview(@Query("user_id") int user_id,
                                              @Query("type") String type,
                                              @Query("feature_type_id") int feature_type_id,
                                              @Query("story_id") int story_id,
                                              @Query("reviews") String reviews,
                                              @Query("parent_id") int parent_id
    );

    @POST(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SEND_REPLY)
    Call<Api_Response<JsonObject>> sendreply(@Query("user_id") int user_id,
                                             @Query("type") String type,
                                             @Query("feature_type_id") int feature_type_id,
                                             @Query("story_id") int story_id,
                                             @Query("reviews") String reviews,
                                             @Query("parent_id") int parent_id
    );

    @POST(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_USER_SIGNUP)
    Call<Api_Response<User>> userSignUp(@Query("name") String name,
                                        @Query("email") String email,
                                        @Query("contact_no") String contact_no,
                                        @Query("password") String password,
                                        @Query("account_type") int account_type
    );


    @GET(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_DETAILS)
    Call<Api_Response<JsonObject>> getfoodtutorialdetail(@Query("story_slug") String story_slug);


    @GET(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_DETAILS)
    Call<Api_Response<JsonObject>> getfoodblog(@Query("story_slug") String story_slug);

    @GET(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SPECIAL_RECIPE)
    Call<Api_Response<FoodDetailModelWrapper>> getfoodSpecialblog(@Query("story_slug") String story_slug, @Query("user_id") String user_id);

    @GET(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HOME)
    Call<Api_Response<JsonObject>> gethome(@Query("user_id") String user_id, @Query("limit") int limit);

    @GET(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_HOME)
    Call<Api_Response<JsonObject>> gettutorial(@Query("slug") String slug);

    @GET(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_TUTORIAL_HOME)
    Call<Api_Response<JsonObject>> gettutorial(@Query("slug") String slug, @Query("page") int page);

    @GET(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HEADER)
    Call<Api_Array_Response<HeaderWrapper>> getHeader();


    @GET(AppConstant.FOODPORTAL_FOOD_DETAILS.SUB_CATEGORY)
    Call<Api_Response<CategorySliderWrapper>> getSubCategory(@Query("category_slug") String category_slug);

    @GET(AppConstant.FOODPORTAL_FOOD_DETAILS.SUB_CATEGORY_RECIPE)
    Call<Api_Response<RecipeWrapper>> getRecipeCategory(@Query("category_slug") String category_slug);

    @GET(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SAVED_RECIPES)
    Call<Api_Response<SavedStoriesWrapper>> getSavedRecipes(@Query("id") int id);

    @GET(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_RECENTLYVIEWED_RECIPES)
    Call<Api_Response<SavedStoriesWrapper>> getRecentlyViewedRecipes(@Query("id") int id, @Query("feature_type_id") int featured_type_id);

    @GET(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_POPULAR)
    Call<Api_Response<Section>> getPopularRecipes(@Query("page") int page, @Query("limit") int limit);

    @GET(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_FEATURED)
    Call<Api_Response<Section>> getFeaturedRecipes(@Query("page") int page, @Query("limit") int limit);

    @GET(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_RECOMMENDED)
    Call<Api_Response<Section>> getRecommendedRecipes(@Query("page") int page, @Query("limit") int limit);

    @POST(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_HOME_SEARCH)
    Call<Api_Array_Response<FoodDetailModel>> getSearchResult(@Query("search") String search);


    @POST(AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_NOTIFICATION)
    Call<Api_Array_Response> notificationSwitch(@Query("notification_status ") int notification_status);


}





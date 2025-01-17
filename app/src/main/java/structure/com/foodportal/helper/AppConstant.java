package structure.com.foodportal.helper;

import java.util.ArrayList;

/**
 * Created by Addi.
 */
public class AppConstant {
    //  public static final int CAMERA_PIC_REQUEST = 0;
    public static final int CAMERA_PIC_REQUEST = 2;

    public static final int FB_REQ_CODE = 64206;
    public static final int G_REQ_CODE = 103;

    //  public static final String BASE_URL = "http://renting.stagingic.com/api/";
    //  public static final String BASE_URL = "http://renting.stagingic.com/api/";

    // public static final String BASE_URL = "http://renting.localapp.com/api/";
    //   public static final String ALL_CATEGORYS = "category/allcategories";
    //  public static final String SUB_CATEGORYS = "category/subcategories";
//     public static final String GET_PRODUCTS = "product/getproducts";
    public static final String GET_MY_PRODUCTS = "product/getmyproducts";
    public static final String ALL_CATEGORYS = "category/allcategories";
    public static final String SUB_CATEGORYS = "category/subcategories";
    public static final String GET_BRANDS = "category/brands";
    public static final String GET_MAKE_MODELS = "category/makemodels";
    public static final String GET_PRODUCTS = "product/getproducts";
    public static final String ADD_PRODUCTS = "product/addproduct";
    public static final String DELETE_PRODUCT = "product/deleteproduct";
    public static final String EDIT_PRODUCT = "product/editproduct";

    public static final String GET_COUNTRIES = "general/getcountries";
    public static final String GET_CITIES = "general/getcities";

    public static final String USER_SIGNUP = "user/register";
    public static final String USER_LOGIN = "login";
    public static final String DEVICE_TYPE = "android";
    public static final String USER_LOGOUT = "user/logout";
    public static final String USER_UPDATE = "user/update";
    public static final String CONTACT_US = "user/contactus";

    public static final String USER_VERIFY_CODE = "user/verifycode";
    public static final String FORGOT_PASSWORD = "forgotpassword";
    public static final String RESEND_CODE = "resendcode";
    public static final String CHANGE_PASS = "changephone";

    public static final String VERIFY_RESET_CODE = "user/verifypasswordcode";
    public static final String UPDATE_PASSWORD = "user/updatepassword";
    public static final String USER_CHANGE_PASSWORD = "user/changepassword";

    public static final String TOGGLE_NOTIFICATION = "user/togglenotification";
    public static final String GET_NOTIFICATIONS = "user/getnotifications";
    public static final String GET_UNREAD_NOTIFICATIONS_COUNT = "user/getunreadnotificationscount";
    public static final String MARK_NOTIFICATIONS_READ = "user/marknotificationread";
    public static final String DELETE_NOTIFICATION = "user/deletenotification";
    public static final String ADD_TO_FAV = "product/addtofavourite";
    public static final String MARKVIEWED = "product/markview";

    public static final String SERVER_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    public static final String GET_CART = "product/getcart";
    public static final String ADD_TO_CART = "product/addtocart";
    public static final String REMOVE_FROM_CART = "product/removecart";
    public static final String CART_CHECKOUT = "product/checkoutproduct";
    public static final String GET_ORDERS = "product/getorders";
    public static final String RATE_ORDER = "product/rateorder";

    public static final String CURRENCY_UNIT = "AED";
    public static final String MARK_ORDER = "product/markorderstatus";

    public static final int YES = 1;
    public static final int NO = 0;

    static public int CAMERA_PERMISSION = 555;

    public static final int MINIMUM_AGE = 1;

    public class ProductOn {
        public static final int RENT = 1;
        public static final int SALE = 2;
    }

    public class RentTypes {
        public static final int PER_HOUR = 1;
        public static final int PER_DAY = 2;
        public static final int PER_WEEK = 3;
        public static final int PER_MONTH = 4;
    }

    public class AccessoriesTypes {
        public static final int ORIGINAL = 1;
        public static final int REPLACEMENT = 2;
        public static final int NONE = 3;
    }

    public final static String[] SCREEN_SIZES = {
            "2.5", "2.6", "2.7", "2.8", "2.9",
            "3.0", "3.1", "3.2", "3.3", "3.4", "3.5", "3.6", "3.7", "3.8", "3.9",
            "4.0", "4.1", "4.2", "4.3", "4.4", "4.5", "4.6", "4.7", "4.8", "4.9",
            "5.0", "5.1", "5.2", "5.3", "5.4", "5.5", "5.6", "5.7", "5.8", "5.9",
            "6.0", "6.1", "6.2"
    };

    public static boolean[] checkedDays = new boolean[]{
            false,
            false,
            false,
            false,
            false,
            false,
            false
    };

    public static boolean[] clearCheckedDays = new boolean[]{
            false,
            false,
            false,
            false,
            false,
            false,
            false
    };

    public static ArrayList<String> selectedDays = new ArrayList<>();

    public class DaysIndexes {
        public static final int SUNDAY = 0;
        public static final int MONDAY = 1;
        public static final int TUESDAY = 2;
        public static final int WEDNESDAY = 3;
        public static final int THURSDAY = 4;
        public static final int FRIDAY = 5;
        public static final int SATURDAY = 6;
    }

    public class ProfilePictureParameters {
        public static final int MIN_WINDOW_WIDTH = 200;
        public static final int MIN_WINDOW_HEIGHT = 200;
        public static final int ASPECT_RATIO_X = 1;
        public static final int ASPECT_RATIO_Y = 1;
    }

    public class ProductPictureParameters {
        public static final int MIN_WINDOW_WIDTH = 640;
        public static final int MIN_WINDOW_HEIGHT = 480;
        public static final int ASPECT_RATIO_X = 4;
        public static final int ASPECT_RATIO_Y = 3;
    }

    public class OrderStatuses {
        public static final int CANCELLED = 0;
        public static final int PENDING = 1;
        public static final int ACCEPTED = 2;
        public static final int COMPLETED = 3;
    }

    public class FcmHelper {
        public static final String FROM_SYSTEM_TRAY = "fromSystemTray";
        public static final String FCM_DATA_PAYLOAD = "fcmDataPayload";
        public static final String ACTION_TYPE_JOB = "Job";
        public static final String ACTION_TYPE_PRODUCT = "Product";
        public static final String CANCELLED = "Cancelled";
        public static final String PENDING = "Pending";
        public static final String ACCEPTED = "Accepted";
        public static final String COMPLETED = "Completed";
    }

    public class CategoriesIds {
        public static final int AUTOMOBILES = 40;
        public static final int ELECTRONICS = 41;
        public static final int PROPERTIES = 43;
        public static final int MACHINERYEQUIPMENT = 44;
        public static final int CLASSIFIED = 45;

        public static final int CAR = 48;
        public static final int MOTORBIKE = 68;
        public static final int AC_WATER_LEAK = 88;
        public static final int HEAVY_VEHICLES = 92;
        public static final int BOATS = 116;
        public static final int AUTO_PARTS = 125;
        public static final int PLATES = 126;

        public static final int HOME_APPLIANCES = 49;
        public static final int CAMERAS = 69;
        public static final int MOBILE_PHONES = 70;
        public static final int TELEVISIONS_SCREENS = 94;
        public static final int TABLETS_LAPTOPS = 103;
        public static final int DIGITAL = 104;

        public static final int APARTMENT = 54;
        public static final int VILLA = 75;
        public static final int HOUSE = 76;
        public static final int PLOT = 106;
        public static final int OFFICE = 107;
        public static final int WAREHOUSE = 108;

        public static final int INDUSTRIAL_EQUIPMENT = 77;
        public static final int OFFICE_EQUIPMENT = 78;
        public static final int SPORTS_EQUIPMENT = 79;
        public static final int MACHINERY = 127;

        public static final int FURNITURE = 80;
        public static final int CLOTHING = 81;
        public static final int BABY_ITEMS = 82;
        public static final int BOOKS = 89;
        public static final int GAMING = 90;
        public static final int PETS = 128;
        public static final int OTHER = 129;
    }

    public static final String BASE_URL = "https://food.tribune.com.pk/api/";
    // public static final String BASE_URL = "https://staging-food.tribune.com.pk/api/";
    public static final String BASE_URL_IMAGE = "https://cdn.recipesofpakistan.com/";
    // public static final String VIDEO_URL = "https://cdn.recipesofpakistan.com/";
    public static final String VIDEO_URL = "https://cdn-food.tribune.com.pk/";

    public class FOODPORTAL_FOOD_DETAILS {

        public static final String FOOD_NOTIFICATION = "user/notification-on-off";
        public static final String FOOD_SUPPORT_EMAIL = "food@tribune.com.pk";
        public static final String FOOD_HOME_SEARCH = "home/search";
        public static final String FOOD_SEND_REVIEW = "story/submit-review";
        public static final String FOOD_SEND_REPLY = "story/submit-review";
        public static final String FOOD_ALL_REVIEW = "story/all-review";
        public static final String FOOD_MY_REVIEWS = "get-user-reviews";
        public static final String FOOD_SAVED_RECIPES = "get-user-saved-stories";
        public static final String FOOD_FAVORITE_RECIPES = "get-user-favorite";
        public static final String FOOD_RECENTLY_VIEWED_RECIPES = "get-user-recently-view-stories";
        public static final String FOOD_COOKING_GUIDES = "get-user-saved-cooking-guide";
        public static final String FOOD_DETAILS = "story";
        public static final String FOOD_SPECIAL_RECIPE = "special-story";
        public static final String FOOD_TUTORIAL_DETAILS = "story/tutorial-detail";
        public static final String FOOD_BLOG_DETAILS = "blogstory";
        public static final String FOOD_HOME = "home";
        public static final String FOOD_TUTORIAL_HOME = "get-home-data-by-feature";
        public static final String FOOD_HEADER = "header/menu";
        public static final String SUB_CATEGORY = "category/all-category";
        public static final String SUB_CATEGORY_RECIPE = "category/detail";
        public static final String FOOD_USER_SIGNUP = "user/register";
        public static final String FOOD_USER_LOGIN = "user/login";
        public static final String FOOD_USER_SOCIAL_LOGIN_FACEBOOK = "user/social-login/facebook";
        public static final String FOOD_USER_SOCIAL_LOGIN_GOOGLE = "user/social-login/google";
        public static final String FOOD_SAVE_STORY = "user/save-story";
        public static final String FOOD_MARK_FAVORITE = "user/story-favorite-status";
        public static final String FOOD_POPULAR = "home/popular";
        public static final String FOOD_FEATURED = "home/featured";
        public static final String FOOD_RECOMMENDED = "home/recommended";


        public static final String HOME = "Home";
        public static final String RECIPES = "Recipes";
        public static final String TUTORIALS = "Tutorials";
        public static final String CLEANING = "Cleaning";
        public static final String BLOG = "Blog";

        public static final String SLUG_RECIPES = "recipes";
        public static final String SLUG_TUTORIAL = "tutorial";


    }

    public class DrawerItemType {
        public static final String ITEM = "item";
        public static final String GROUP = "group";
    }

    public class Language {
        public static final int ENGLISH = 0;
        public static final int URDU = 1;
    }
}

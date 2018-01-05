package vztrack.gls.com.vztrack_user.utils;

/**
 * Created by sandeep on 3/3/16.
 */
public class URL {
    public static final String LOGIN = "/api/userLogin";
    public static final String NOTICES = "/api/notices";
    public static final String SEND_NOTICE = "/api/sendNotices";
    public static final String VISITORS = "/api/visitors?pageNo=";
    public static final String VISIT_LIST = "/api/visitorDetails?mobileNo=";
    public static final String CHANGE_PASSWORD = "/api/changePassword?newPassword=";
    public static final String LOGOUT = "/api/logout";

    // SOS Urls
    public static final String SOS = "/api/sos";                                                    // GET Method
    public static final String FEEDBACK = "/api/feedback";                                            // POST Method

    // Rating Urls
    public static final String SAVE_RATINGS = "/saveRatings";
    public static final String PENDING_RATINGS = "/getPendingRatings";
    public static final String CANCLE_RATING = "/cancelRating";
    public static final String PROVIDER_LIST = "/getProviderList";
    public static final String PROVIDERS_DATA = "/getProviderData";
    public static final String PROVIDER_DETAIL_DATA = "/getProviderDetailData";

    // Complain Urls
    public static final String ADD_COMPLAINS = "/api/addSocietyComplain";
    public static final String GET_COMPLAINS = "/api/getSocietyComplains?pageNo=";
    public static final String CLOSE_COMPLAINS = "/api/closeSocietyComplain?soc_complain_id=";
    public static final String GET__COMPLAIN_CATEGORY = "/api/getSocietyComplainCategory";

    // Incorrect visitor mark
    public static final String MARK_ERROR = "/api/markError?id=";
    public static final String VEHICLE_NO_PATTERN = "/api/vehicleNoPattern";
    public static final String SEARCH_VEHICLE = "/api/getSearchFlatVehicle?vehicleNumber=";
    public static final String ADD_VEHICLE = "/api/addFlatVehicle";                                 // GET METHOD
    public static final String GET_FLAT_VEHICLES = "/api/getFlatVehicles";                          // GET METHOD
    public static final String DELETE_FLAT_VEHICLES = "/api/deleteFlatVehicle";                     // GET METHOD
    public static final String MESSAGE = "/api/getFlatMessages?pageNo=";

    public static final String GET_ALL_FLATS = "/api/availFlats";
    public static final String GET_ALL_GROUPS = "/api/getAllGroups";
    public static final String SEND_MESSAGE = "/api/addNewMessage";
    public static final String SEND_MESSAGE_NOTIFICATION = "/api/sendMessageNotification";

    public static final String LOCAL_STORES = "/api/getMyLocalStores";
    public static final String STORE_CALL_LOG = "/api/loggStoreCallsHistory";
}

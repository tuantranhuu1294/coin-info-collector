package net.cglcapital.coininfo.common.constant;
import net.cglcapital.coininfo.common.exception.Error;

public final class ErrorCode {

    public static final String SERVICE_CODE = "CIC";

    /* List of error codes */
    public static final Error BAD_REQUEST = new Error(400, "001", SERVICE_CODE, "Bad Request");
    public static final Error WRONG_BODY_FORMAT = new Error(400, "012", SERVICE_CODE, "Wrong request's body format");

    public static final Error UNAUTHORIZED = new Error(401, "005", SERVICE_CODE, "Unauthorized");
    public static final Error AUTH_REQUIRED = new Error(401, "006", SERVICE_CODE, "Auth required");
    public static final Error INVALID_ACCESS_TOKEN = new Error(401, "007", SERVICE_CODE, "Invalid access token");

    public static final Error PERMISSION_DENIED = new Error(403, "008", SERVICE_CODE, "Permission Denied");

    public static final Error RESOURCE_NOT_FOUND = new Error(404, "009", SERVICE_CODE, "Resource Not Found");

    public static final Error INTERNAL_SERVER_ERROR = new Error(500, "010", SERVICE_CODE, "Internal server error");
    public static final Error EXTERNAL_SERVICE_AUTH_ERROR = new Error(500, "011", SERVICE_CODE, "External service auth error");

}

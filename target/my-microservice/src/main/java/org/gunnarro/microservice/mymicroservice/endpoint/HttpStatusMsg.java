package org.gunnarro.microservice.mymicroservice.endpoint;

/**
 * @see https://github.com/gunnarro/microservice-archetype/wiki/Microservice+REST+Guidelines
 */
public class HttpStatusMsg {

    private HttpStatusMsg() {
    }

    public static final int HTTP_200_CODE = 200;
    public static final String HTTP_200_MSG = "OK";

    public static final int HTTP_201_CODE = 201;
    public static final String HTTP_201_MSG = "Created";

    public static final int HTTP_204_CODE = 204;
    public static final String HTTP_204_MSG = "No content";

    public static final int HTTP_400_CODE = 400;
    public static final String HTTP_400_MSG = "Bad Request";

    public static final int HTTP_401_CODE = 401;
    public static final String HTTP_401_MSG = "You are not authorized to view the resource";

    public static final int HTTP_403_CODE = 403;
    public static final String HTTP_403_MSG = "Accessing the resource you were trying to reach is forbidden";

    public static final int HTTP_404_CODE = 404;
    public static final String HTTP_404_MSG = "Not Found";

    public static final int HTTP_410_CODE = 410;
    public static final String HTTP_410_MSG = "Gone";

    public static final int HTTP_429_CODE = 429;
    public static final String HTTP_429_MSG = "Too Many Requests";

    public static final int HTTP_500_CODE = 500;
    public static final String HTTP_500_MSG = "Internal Server Error";

    public static final int HTTP_503_CODE = 503;
    public static final String HTTP_503_MSG = "Service Unavailable";

}

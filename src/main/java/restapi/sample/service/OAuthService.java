package restapi.sample.service;

import restapi.sample.entity.RestApiConstants;
import restapi.sample.util.RandomString;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SDS on 2016-05-27.
 */
public class OAuthService {
    private static final OAuthService INSTANCE = new OAuthService();
    public static OAuthService getInstance() {
        return INSTANCE;
    }
    private OAuthService(){
    }



    private static final String TEST_GRANT_TYPE = "client_credentials";
    private static final String TEST_CLIENT_ID = "4a60f7b68f9e47d89eb73f0dc93e2ea6";
    private static final String TEST_CLIENT_SECRET = "0543c3ca1e7043a18719eb9f73c0f63c";

    private final static String TEST_TOKEN_TYPE = "Bearer";

    public Map<String, Object> getToken(String grant_type, String client_id, String client_sercret,
                                        HttpServletRequest request){
        Map<String, Object> returnMap = new HashMap<String, Object>();


        if(TEST_GRANT_TYPE.equals(grant_type) && TEST_CLIENT_ID.equals(client_id) && TEST_CLIENT_SECRET.equals(client_sercret)){
            String accessToken = RandomString.random(RestApiConstants.LENGTH_OF_RANDOM_STRING);

            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(RestApiConstants.SESSION_MAX_INACTIVE_INTERVAL);
            session.setAttribute(RestApiConstants.ACCESS_TOKEN_KEY, accessToken);

            returnMap.put(RestApiConstants.ACCESS_TOKEN_KEY, accessToken);
            returnMap.put("token_type", TEST_TOKEN_TYPE);
        }else{
            returnMap.put("msg", "Wrong Inputs.");
        }


        return returnMap;
    }

    public boolean isRightToken(HttpServletRequest request){
        String headerAuth = request.getHeader(RestApiConstants.AUTH_HEADER);
        String sessionAccessToken = (String) request.getSession().getAttribute(RestApiConstants.ACCESS_TOKEN_KEY);
        if( headerAuth!=null && sessionAccessToken!=null ){
            String sessionAuth = new StringBuilder().append(TEST_TOKEN_TYPE).append(' ')
                    .append(sessionAccessToken).toString();
            if(headerAuth.equals(sessionAuth)){
                return true;
            }
        }
        return false;
    }


}

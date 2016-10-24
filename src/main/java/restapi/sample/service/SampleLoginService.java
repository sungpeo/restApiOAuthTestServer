package restapi.sample.service;

import restapi.sample.entity.RestApiConstants;
import restapi.sample.entity.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SDS on 2015-11-20.
 */
public class SampleLoginService {
    private static final SampleLoginService INSTANCE = new SampleLoginService();
    public static SampleLoginService getInstance() {
        return INSTANCE;
    }
    private SampleLoginService(){
    }

    public Map<String, String> checkAuthByUsernameAndPassword(HttpServletRequest request, HttpServletResponse response,
                                                              UserInfo user){
        Map<String, String> returnMap = new HashMap<String, String>();
        //id, passwrod 체크
        if ( user.isRightUsernameAndPassword() ) {
            returnMap.put(RestApiConstants.ACCESS_TOKEN_KEY, user.getAccessToken(request));

        } else {
            response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
            returnMap.put("msg","It's an invalid username or password.");
        }
        return returnMap;
    }


}

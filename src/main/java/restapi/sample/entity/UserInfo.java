package restapi.sample.entity;

import restapi.sample.util.RandomString;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by SDS on 2015-11-20.
 */
public class UserInfo {
    private String username;
    private String password;

    public UserInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static boolean isRightUserSession(HttpServletRequest request) {
        String userAccessToken = request.getHeader(RestApiConstants.AUTH_HEADER);
        return userAccessToken != null
                && userAccessToken.equals(request.getSession().getAttribute(RestApiConstants.ACCESS_TOKEN_KEY));

    }

    public boolean isRightUsernameAndPassword() {
        return RestApiConstants.AVAILABLE_USERNAME.equals(this.username) && RestApiConstants.AVAILABLE_PASSWORD.equals(this.password);
    }

    public String getAccessToken(HttpServletRequest request){
        HttpSession session = request.getSession();

        if( session.getAttribute(RestApiConstants.ACCESS_TOKEN_KEY) != null
                && this.username.equals(session.getAttribute("username"))
                && this.password.equals(session.getAttribute("password"))
                ){

            //정상(세션과 동일한 username, password로 로그인)인데 재로그인 시도인 경우
            String currentAccessToken = (String) session.getAttribute(RestApiConstants.ACCESS_TOKEN_KEY);
            setAccessTokenToSession(request, currentAccessToken);
            return currentAccessToken;
        } else {
            return createAccessToken(request);
        }

    }

    private String createAccessToken(HttpServletRequest request){
        //랜덤 스트링 20자리 생성 후, 세션에 들고 있기

        String token = RandomString.random(RestApiConstants.LENGTH_OF_RANDOM_STRING);
        setAccessTokenToSession(request, token);
        return token;
    }

    private void setAccessTokenToSession(HttpServletRequest request, String accessToken){
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(RestApiConstants.SESSION_MAX_INACTIVE_INTERVAL);
        session.setAttribute(RestApiConstants.ACCESS_TOKEN_KEY, accessToken);
        session.setAttribute("username", this.username);
        session.setAttribute("password", this.password);
    }

}

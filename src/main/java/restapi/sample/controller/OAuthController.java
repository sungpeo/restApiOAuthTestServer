package restapi.sample.controller;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import restapi.sample.entity.RestApiConstants;
import restapi.sample.service.OAuthService;
import restapi.sample.service.SampleRestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SDS on 2016-05-27.
 */
@Controller
@RequestMapping("/restapi")
public class OAuthController {
    private static OAuthService oAuthService = OAuthService.getInstance();
    private static SampleRestService sampleRestService = SampleRestService.getInstance();

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(OAuthController.class);


    @RequestMapping(value = "/token", method= RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getToken(@RequestParam(value="grant_type", required=true) String grant_type,
                            @RequestParam(value="client_id", required=true) String client_id,
                            @RequestParam(value="client_secret", required=true) String client_sercret,
                            HttpServletRequest request, HttpServletResponse response) {

        logger.info("############################################################################");
        logger.info("/restapi/token :: client_id="+client_id+", client_sercret="+client_sercret);

        return oAuthService.getToken(grant_type, client_id, client_sercret, request);
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> getData(@RequestParam(value="from", required=true) String from,
                                                               @RequestParam(value="to", required=true) String to,
                                                               @RequestParam(value="limit", defaultValue = "-1") int limit,
                                                               @RequestParam(value="starting", defaultValue = RestApiConstants.EMPTY_STRING) String starting,
                                                               HttpServletRequest request, HttpServletResponse response) {


        if(oAuthService.isRightToken(request)){
            boolean isOldVersionSession = false;
            return sampleRestService.getSampleJson(request, response, from, to, limit, starting, isOldVersionSession);
        }else{
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("msg", "It's a wrong token.");
            return returnMap;
        }
    }

}

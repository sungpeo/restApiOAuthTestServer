package restapi.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import restapi.sample.entity.RestApiConstants;
import restapi.sample.entity.UserInfo;
import restapi.sample.service.SampleLoginService;
import restapi.sample.service.SampleRestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by SDS on 2015-11-19.
 */
@Controller
@RequestMapping("/sample")
public class SampleController {

    private SampleRestService sampleRestService = SampleRestService.getInstance();
    private SampleLoginService sampleLoginService = SampleLoginService.getInstance();

    @RequestMapping(method= RequestMethod.GET)
    public @ResponseBody Map<String, Object> get(@RequestParam(value="from", required=true) String from,
                                     @RequestParam(value="to", required=true) String to,
                                     @RequestParam(value="limit", defaultValue = "-1") int limit,
                                     @RequestParam(value="starting", defaultValue = RestApiConstants.EMPTY_STRING) String starting,
                                     HttpServletRequest request, HttpServletResponse response) {


        return sampleRestService.getSampleJson(request, response, from, to, limit, starting);
    }


    @RequestMapping(value = "login", method= {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, String> getAuth(@RequestParam(value="username") String username,
                                       @RequestParam(value="password") String password,
                                       HttpServletRequest request, HttpServletResponse response) {

        UserInfo user = new UserInfo(username, password);
        return sampleLoginService.checkAuthByUsernameAndPassword(request, response, user);
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> getWithoutSession(@RequestParam(value="from", required=true) String from,
                                                 @RequestParam(value="to", required=true) String to,
                                                 @RequestParam(value="limit", defaultValue = "-1") int limit,
                                                 @RequestParam(value="starting", defaultValue = RestApiConstants.EMPTY_STRING) String starting,
                                                 HttpServletRequest request, HttpServletResponse response) {

        return sampleRestService.getSampleJson(request, response, from, to, limit, starting, false);
    }
}

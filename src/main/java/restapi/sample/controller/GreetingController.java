package restapi.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import restapi.sample.service.GreetingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by SDS on 2015-11-24.
 */
@Controller
@RequestMapping("/greeting/test")
public class GreetingController {

    private GreetingService greetingService = GreetingService.getInstance();


    @RequestMapping(method= RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> get (@RequestParam(value="from",  defaultValue = "0") long from,
                             @RequestParam(value="to",  defaultValue = "10") long to,
                             HttpServletRequest request, HttpServletResponse response) {

        return greetingService.get(request, from, to);
    }

    @RequestMapping(method= RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> post (@RequestParam(value="from",  defaultValue = "0") long from,
                              @RequestParam(value="to",  defaultValue = "10") long to) {

        return greetingService.post(from, to);
    }

    @RequestMapping(method= RequestMethod.PUT)
    public @ResponseBody
    Map<String, Object> put (@RequestParam(value="from",  defaultValue = "0") long from,
                            @RequestParam(value="to",  defaultValue = "10") long to) {

        return greetingService.put(from, to);
    }

    @RequestMapping(method= RequestMethod.DELETE)
    public @ResponseBody
    Map<String, Object> delete (@RequestParam(value="from",  defaultValue = "0") long from,
                                @RequestParam(value="to",  defaultValue = "10") long to) {

        return greetingService.delete(from, to);
    }
}

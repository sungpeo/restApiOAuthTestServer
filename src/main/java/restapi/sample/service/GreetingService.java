package restapi.sample.service;

import restapi.sample.entity.POJO.Result;
import restapi.sample.entity.RestApiConstants;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by SDS on 2015-11-24.
 */
public class GreetingService {
    private static final GreetingService INSTANCE = new GreetingService();
    public static GreetingService getInstance() {
        return INSTANCE;
    }
    private GreetingService(){
    }

    private class Something {
        private int seq = 0;
        private int v = 0;

        public Something(int seq, int v) {
            this.seq = seq;
            this.v = v;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }


    }

    public Map<String, Object> get (HttpServletRequest request, long from, long to) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("msg","get complete.");
        int somethingArrayLength = (int)(to - from);
        Something[] result = new Something[somethingArrayLength];
        for (int i=0; i<somethingArrayLength; i++) {
            result[i] = new Something((int)from+i+1, (int)Math.floor(Math.random()*1000));
        }
        returnMap.put("result", result);
        putInputToMap(returnMap, from, to);
        return returnMap;
    }

    public Map<String, Object> post ( long from, long to ) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("msg","post complete.");
        putInputToMap(returnMap, from, to);
        return returnMap;
    }

    public Map<String, Object> put ( long from, long to ) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("msg","put complete.");
        putInputToMap(returnMap, from, to);
        return returnMap;
    }

    public Map<String, Object> delete ( long from, long to ) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("msg","delete complete.");
        putInputToMap(returnMap, from, to);
        return returnMap;
    }

    private void putInputToMap ( Map map, long from, long to ) {
        map.put("from", from);
        map.put("to", to);
        map.put("currentTimeMillis", System.currentTimeMillis() );
    }


}

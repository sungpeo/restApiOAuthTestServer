package restapi.sample.service;

import restapi.sample.entity.POJO.Result;
import restapi.sample.entity.RestApiConstants;
import restapi.sample.entity.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by SDS on 2015-11-20.
 */
public class SampleRestService {
    private static final SampleRestService INSTANCE = new SampleRestService();
    public static SampleRestService getInstance() {
        return INSTANCE;
    }
    private SampleRestService(){
    }

    public Map<String, Object> getSampleJson(HttpServletRequest request, HttpServletResponse response,
                                             String from, String to, int limit, String starting) {
        return getSampleJson(request, response, from, to, limit, starting, true);
    }

    public Map<String, Object> getSampleJson(HttpServletRequest request, HttpServletResponse response,
                                             String from, String to, int limit, String starting, boolean sessionCheckYn) {

        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("from", from);
        returnMap.put("to", to);

//        //from, to should be timestamp
//        if(from.length()!=14 || from.length()!=14){
//            returnMap.put("msg", "from과 to는 타임스탬프(yyyymmddhhmiss) 형식이어야합니다.");
//            return returnMap;
//        }


        if( UserInfo.isRightUserSession(request) || !sessionCheckYn) {
            //session 정상
            returnMap.put("result",  getResultArrayBySeqAndToAndY(request, returnMap, from, to, limit, starting));
        } else {
            response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
            returnMap.put("msg", "Please Login First, If you did it, check your Authorization.");
        }

        return returnMap;
    }

    private Result[] getResultArrayBySeqAndToAndY(HttpServletRequest request, Map<String, Object> returnMap,
                                                  String from, String to, int limit, String starting) {


        //날짜형이 아니라 long이었음 ㅜㅜㅜ 2016-05-27 수정
        //starting 20151125124615-1/1
        String startTimestamp = null;
        String[] currentAndWholeLength = null;

        //array 갯수(0.3.1) : 무작위로 10-500
//        int randomArrayLength = (int) Math.floor(Math.random() * 491) + 10;
        //2016-06-09 무작위 없이 무조건 444개
        int randomArrayLength = 444;
        int remainArrayLength = 0;

        if( RestApiConstants.EMPTY_STRING.equals(starting) || !starting.contains("-") ) { //starting defaultValue=""
            startTimestamp = from;
            currentAndWholeLength = new String[2];
            currentAndWholeLength[0] = String.valueOf(1);
            currentAndWholeLength[1] = String.valueOf(randomArrayLength);
            remainArrayLength = randomArrayLength;
        } else {
            //limit 진행중
            startTimestamp = starting.split("-")[0];
            currentAndWholeLength = starting.split("-")[1].split("/");
            remainArrayLength = Integer.valueOf(currentAndWholeLength[1]) - Integer.valueOf(currentAndWholeLength[0]);
        }


        //실제로 출력할 갯수
        //limit이 없거나, limit보다 남은 갯수가 작으면 그대로 출력, 아니면 limit만큼
        int outputResultLength = remainArrayLength;
        if( limit > 0 && limit < remainArrayLength) {
            outputResultLength = limit;
            randomArrayLength = Integer.valueOf(currentAndWholeLength[1]);
        }
        Result[] tempResultArray = new Result[randomArrayLength];
        Result[] resultArray = new Result[outputResultLength];

        // from과 to 사이의 날짜수를 정하고 구간 랜덤으로 outputResultLength 만큼 개수를 뽑음. 정렬된 상태로
        long startLong = Long.parseLong(startTimestamp);
        long interval = Long.parseLong(to) - startLong;
//        DateFormat formatter = null;
//        try {
//            formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//            fromDate = formatter.parse(startTimestamp);
//            Date toDate = formatter.parse(to);
//        } catch (ParseException pe) {
//            pe.printStackTrace();
//        }
//        Date fromDate = null;

        //무조건 arrayLength 갯수만큼, 시작일자에 맞춰 일단 랜덤을 돌려놓고..
        for (int i=0; i<randomArrayLength; i++) {
            long randomIntervalSec = (long) Math.floor( Math.random() * interval ) + 1;
            long timeStamp = startLong + randomIntervalSec;

            long v = (long) Math.floor(Math.random() * 1000);

            tempResultArray[i] = new Result( String.valueOf(timeStamp), v );
        }
        Arrays.sort(tempResultArray);
        //전체 랜덤 한 것을 소팅한 후 limit 갯수만큼만 꺼내가기
        for(int i=0; i<outputResultLength; i++) {
            resultArray[i] = tempResultArray[i];
        }
        tempResultArray = null; //할당된 array 해제

        if( limit > 0 && limit < remainArrayLength){
            returnMap.put("done", "false");

            String nextStartTimestamp = resultArray[outputResultLength-1].getTs();

            StringBuilder sb = new StringBuilder(request.getRequestURL());
            sb.append("?from=").append(from).append("&to=").append(to)
                    .append("&limit=").append(limit)
                    .append("&starting=").append(nextStartTimestamp).append("-")
                    .append(Integer.valueOf(currentAndWholeLength[0])+limit).append("/").append(randomArrayLength);

            returnMap.put("nextUrl", sb.toString());
        } else {
            returnMap.put("done", "true");
        }

        return resultArray;
    }

}

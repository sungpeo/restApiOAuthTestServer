package restapi.sample.entity.POJO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by SDS on 2015-11-19.
 */
public class Result implements Comparable<Result> {
    private String ts;
    private long v;

//    @JsonIgnore
//    private DateFormat formatter = null;

    public Result(String ts, long v){
        this.ts = ts;
        this.v = v;
//        this.formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;

    }

    public long getV() {
        return v;
    }

    public void setV(long v) {
        this.v = v;
    }

    @Override
    public int compareTo(Result o) {
        long thisTs = Long.parseLong(this.ts);
        long targetTs = Long.parseLong(o.ts);
        if(thisTs == targetTs){
            return 0;
        }else if(thisTs < targetTs){
            return -1;
        }else{
            return 1;
        }
//      return (int) (Long.parseLong(ts) - Long.parseLong(o.ts));
//        try {
//            ((formatter.parse(ts).getTime() - formatter.parse(o.ts).getTime());
//        } catch (ParseException pe) {
//            pe.printStackTrace();
//        }
//        return -1;
    }
}

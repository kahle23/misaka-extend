package misaka.data.ip.support.ipapi;

import artoria.action.ActionUtils;
import artoria.data.json.JsonUtils;
import artoria.data.json.support.FastJsonProvider;
import com.alibaba.fastjson.JSON;
import misaka.data.ip.IpQuery;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class IpApiIpQueryHandlerTest {
    private static Logger log = LoggerFactory.getLogger(IpApiIpQueryHandlerTest.class);

    @Test
    public void test1() {
        JsonUtils.setJsonProvider(new FastJsonProvider());
        ActionUtils.registerHandler(IpQuery.class, new IpApiIpActionHandler());

        IpQuery query = new IpQuery("223.98.40.191");
        IpApiIpLocation ipLocation = ActionUtils.execute(query, IpApiIpLocation.class);
        log.info("{}", JSON.toJSONString(ipLocation, true));

        query = new IpQuery("106.57.23.1");
        ipLocation = ActionUtils.execute(query, IpApiIpLocation.class);
        log.info("{}", JSON.toJSONString(ipLocation, true));

        /*query = new IpQuery("120.231.22.221");
        ipLocation = ActionUtils.execute(query, IpApiIpLocation.class);
        log.info("{}", JSON.toJSONString(ipLocation, true));

        query = new IpQuery("220.243.135.165");
        ipLocation = ActionUtils.execute(query, IpApiIpLocation.class);
        log.info("{}", JSON.toJSONString(ipLocation, true));

        query = new IpQuery("117.136.7.206");
        ipLocation = ActionUtils.execute(query, IpApiIpLocation.class);
        log.info("{}", JSON.toJSONString(ipLocation, true));

        query = new IpQuery("122.238.172.155");
        ipLocation = ActionUtils.execute(query, IpApiIpLocation.class);
        log.info("{}", JSON.toJSONString(ipLocation, true));*/
        log.info("{}", ipLocation);
    }

}

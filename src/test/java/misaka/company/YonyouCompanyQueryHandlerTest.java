package misaka.company;

import artoria.action.ActionUtils;
import artoria.exchange.JacksonProvider;
import artoria.exchange.JsonUtils;
import artoria.logging.Logger;
import artoria.logging.LoggerFactory;
import com.alibaba.fastjson.JSON;
import misaka.company.yonyou.YonyouCompanyActionHandler;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

@Ignore
public class YonyouCompanyQueryHandlerTest {
    private static Logger log = LoggerFactory.getLogger(YonyouCompanyQueryHandlerTest.class);

    static {
        String baseInfoApiCode = "";
        String searchApiCode = "";
        Integer timeout = 3000;
        ActionUtils.registerHandler(CompanyQuery.class,
                new YonyouCompanyActionHandler(baseInfoApiCode, searchApiCode, timeout));
        JsonUtils.setJsonProvider(new JacksonProvider());
    }

    @Test
    public void testInfo() {
        CompanyQuery query = new CompanyQuery("微软（中国）有限公司上海分公司");
        Company company = ActionUtils.info(query, Company.class);
        log.info(JSON.toJSONString(company, Boolean.TRUE));
    }

    @Test
    public void testSearch() {
        CompanyQuery query = new CompanyQuery("Microsoft");
        List<Company> companyList = ActionUtils.search(query, Company.class);
        log.info(JSON.toJSONString(companyList, Boolean.TRUE));
    }

}

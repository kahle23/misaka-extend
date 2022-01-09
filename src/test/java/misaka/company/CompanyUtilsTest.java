package misaka.company;

import artoria.exchange.JacksonProvider;
import artoria.exchange.JsonUtils;
import artoria.logging.Logger;
import artoria.logging.LoggerFactory;
import com.alibaba.fastjson.JSON;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

@Ignore
public class CompanyUtilsTest {
    private static Logger log = LoggerFactory.getLogger(CompanyUtilsTest.class);

    static {
        String baseInfoApiCode = "";
        String searchApiCode = "";
        Integer timeout = 3000;
        CompanyUtils.setCompanyProvider(
                new YonyouCompanyProvider(baseInfoApiCode, searchApiCode, timeout)
        );
        JsonUtils.setJsonProvider(new JacksonProvider());
    }

    @Test
    public void testFindByCompanyName() {
        Company company = CompanyUtils.info("微软（中国）有限公司上海分公司");
        log.info(JSON.toJSONString(company, Boolean.TRUE));
    }

    @Test
    public void testFindListByCompanyName() {
        List<Company> companyList = CompanyUtils.search("Microsoft");
        log.info(JSON.toJSONString(companyList, Boolean.TRUE));
    }

}

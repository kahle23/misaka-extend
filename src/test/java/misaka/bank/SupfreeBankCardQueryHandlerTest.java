package misaka.bank;

import artoria.exchange.FastJsonProvider;
import artoria.exchange.JsonUtils;
import artoria.query.QueryUtils;
import com.alibaba.fastjson.JSON;
import misaka.bank.supfree.SupfreeBankCardQueryHandler;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class SupfreeBankCardQueryHandlerTest {
    private static Logger log = LoggerFactory.getLogger(SupfreeBankCardQueryHandlerTest.class);

    @Test
    public void test1() {
        JsonUtils.setJsonProvider(new FastJsonProvider());
        QueryUtils.registerHandler(BankCardQuery.class, new SupfreeBankCardQueryHandler());

        BankCardQuery cardQuery = new BankCardQuery("622600687501042806");
        BankCard bankCard = QueryUtils.info(cardQuery, BankCard.class);
        log.info("{}", JSON.toJSONString(bankCard, true));

        cardQuery = new BankCardQuery("6230960288002899254");
        BankCard bankCard1 = QueryUtils.info(cardQuery, BankCard.class);
        log.info("{}", JSON.toJSONString(bankCard1));

        cardQuery = new BankCardQuery("6217994000264606028");
        BankCard bankCard2 = QueryUtils.info(cardQuery, BankCard.class);
        log.info("{}", JSON.toJSONString(bankCard2));

        cardQuery = new BankCardQuery("6230666046001759766");
        BankCard bankCard3 = QueryUtils.info(cardQuery, BankCard.class);
        log.info("{}", JSON.toJSONString(bankCard3));
    }

}

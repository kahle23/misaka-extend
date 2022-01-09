package misaka.bank;

import artoria.exchange.FastJsonProvider;
import artoria.exchange.JsonUtils;
import com.alibaba.fastjson.JSON;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class BankCardIssuerUtilsTest {
    private static Logger log = LoggerFactory.getLogger(BankCardIssuerUtilsTest.class);

    @Test
    public void test1() {
        JsonUtils.setJsonProvider(new FastJsonProvider());
        BankCardUtils.setBankCardIssuerProvider(new SupfreeNetBankCardIssuerProvider());
//        BankCardUtils.setBankCardIssuerProvider(new FileBasedBankCardIssuerProvider());
//        BankCardUtils.setBankCardIssuerProvider(new FileBasedBankCardIssuerProvider("bank_card_info.data"));
        BankCardIssuer bankCardIssuer = BankCardUtils.issuerInfo("622600687501042806");
        log.info("{}", JSON.toJSONString(bankCardIssuer));
        BankCardIssuer bankCardIssuer1 = BankCardUtils.issuerInfo("6230960288002899254");
        log.info("{}", JSON.toJSONString(bankCardIssuer1));
        BankCardIssuer bankCardIssuer2 = BankCardUtils.issuerInfo("6217994000264606028");
        log.info("{}", JSON.toJSONString(bankCardIssuer2));
        BankCardIssuer bankCardIssuer3 = BankCardUtils.issuerInfo("6230666046001759766");
        log.info("{}", JSON.toJSONString(bankCardIssuer3));
    }

}

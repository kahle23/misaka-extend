package misaka.bank;

import artoria.beans.BeanUtils;
import artoria.exception.ExceptionUtils;
import artoria.file.Csv;
import artoria.file.FileUtils;
import artoria.util.Assert;
import artoria.util.CollectionUtils;
import artoria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static artoria.common.Constants.NEWLINE;
import static artoria.common.Constants.UTF_8;

/**
 * File based bank card information provider.
 * @author Kahle
 */
public class FileBasedBankCardIssuerProvider implements BankCardIssuerProvider {
    private static final String DEFAULT_FILE_NAME = "bank_card_info.data";
    private static Logger log = LoggerFactory.getLogger(FileBasedBankCardIssuerProvider.class);
    private Map<String, BankCardIssuer> bankCardIssuerMap = new ConcurrentHashMap<String, BankCardIssuer>();
    private String filePath;
    private String charset;

    public FileBasedBankCardIssuerProvider() {

        this(null, null);
    }

    public FileBasedBankCardIssuerProvider(String filePath, String charset) {
        this.charset = StringUtils.isNotBlank(charset) ? charset : UTF_8;
        this.filePath = filePath;
        Csv csv = new Csv();
        csv.setCharset(this.charset);
        try {
            if (StringUtils.isNotBlank(filePath)) {
                File file = new File(filePath);
                if (!file.exists()) {
                    FileUtils.createNewFile(file);
                }
                csv.readFromFile(new File(filePath));
            }
            else {
                csv.readFromClasspath(DEFAULT_FILE_NAME);
            }
        }
        catch (IOException e) {
            throw ExceptionUtils.wrap(e);
        }
        initialize(csv);
    }

    public FileBasedBankCardIssuerProvider(Csv csv) {

        initialize(csv);
    }

    protected void initialize(Csv csv) {
        if (csv == null) { return; }
        List<BankCardIssuer> list = BeanUtils.mapToBeanInList(csv.toMapList(), BankCardIssuer.class);
        if (CollectionUtils.isEmpty(list)) { return; }
        for (BankCardIssuer bankCardIssuer : list) {
            if (bankCardIssuer == null) { continue; }
            String iin = bankCardIssuer.getIssuerIdentificationNumber();
            if (StringUtils.isBlank(iin)) { continue; }
            bankCardIssuerMap.put(iin, bankCardIssuer);
        }
    }

    protected void saveBankCardIssuer() {
        try {
            if (StringUtils.isBlank(filePath)) { return; }
            Collection<BankCardIssuer> values = bankCardIssuerMap.values();
            List<BankCardIssuer> list = sortBankCardIssuer(values);
            Csv saveCsv = new Csv();
            saveCsv.setCharset(charset);
            saveCsv.addHeader("bankName", "bankName");
            saveCsv.addHeader("organizationCode", "organizationCode");
            saveCsv.addHeader("bankCardName", "bankCardName");
            saveCsv.addHeader("bankCardType", "bankCardType");
            saveCsv.addHeader("issuerIdentificationNumber", "issuerIdentificationNumber");
            saveCsv.addHeader("bankCardNumberLength", "bankCardNumberLength");
            saveCsv.fromMapList(BeanUtils.beanToMapInList(list));
            saveCsv.writeToFile(new File(filePath));
        }
        catch (Exception e) {
            log.info(
                    "Failed to save bank card information to \"{}\". {}{}"
                    , filePath
                    , NEWLINE
                    , ExceptionUtils.toString(e)
            );
        }
    }

    protected List<BankCardIssuer> sortBankCardIssuer(Collection<BankCardIssuer> bankCardIssuerColl) {
        Map<String, BankCardIssuer> cache = new HashMap<String, BankCardIssuer>(bankCardIssuerColl.size());
        List<String> iinList = new ArrayList<String>();
        for (BankCardIssuer bankCardIssuer : bankCardIssuerColl) {
            if (bankCardIssuer == null) { continue; }
            String iin = bankCardIssuer.getIssuerIdentificationNumber();
            if (StringUtils.isBlank(iin)) { continue; }
            cache.put(iin, bankCardIssuer);
            iinList.add(iin);
        }
        Collections.sort(iinList);
        List<BankCardIssuer> result = new ArrayList<BankCardIssuer>();
        for (String iin : iinList) {
            BankCardIssuer bankCardIssuer = cache.get(iin);
            result.add(bankCardIssuer);
        }
        return result;
    }

    protected void addBankCardIssuer(BankCardIssuer bankCardIssuer) {
        Assert.notNull(bankCardIssuer, "Parameter \"bankCardIssuer\" must not null. ");
        String iin = bankCardIssuer.getIssuerIdentificationNumber();
        bankCardIssuerMap.put(iin, bankCardIssuer);
    }

    protected BankCardIssuer findBankCardIssuer(String bankCardNumber, int length, int endIndex) {
        if (length < endIndex) { return null; }
        String iin = bankCardNumber.substring(0, endIndex);
        BankCardIssuer bankCardIssuer = bankCardIssuerMap.get(iin);
        if (bankCardIssuer != null) {
            BankCardIssuer result = BeanUtils.beanToBean(bankCardIssuer, BankCardIssuer.class);
            result.setBankCardNumber(bankCardNumber);
            result.setBankCardNumberLength(String.valueOf(length));
            return result;
        }
        return null;
    }

    @Override
    public BankCardIssuer issuerInfo(String bankCardNumber) {
        if (StringUtils.isBlank(bankCardNumber)) { return null; }
        int length = bankCardNumber.length();
        BankCardIssuer bankCardIssuer;
        if ((bankCardIssuer = findBankCardIssuer(bankCardNumber, length, 6)) != null) {
            return bankCardIssuer;
        }
        else if ((bankCardIssuer = findBankCardIssuer(bankCardNumber, length, 7)) != null) {
            return bankCardIssuer;
        }
        else if ((bankCardIssuer = findBankCardIssuer(bankCardNumber, length, 8)) != null) {
            return bankCardIssuer;
        }
        else if ((bankCardIssuer = findBankCardIssuer(bankCardNumber, length, 9)) != null) {
            return bankCardIssuer;
        }
        else if ((bankCardIssuer = findBankCardIssuer(bankCardNumber, length, 5)) != null) {
            return bankCardIssuer;
        }
        else if ((bankCardIssuer = findBankCardIssuer(bankCardNumber, length, 4)) != null) {
            return bankCardIssuer;
        }
        else if ((bankCardIssuer = findBankCardIssuer(bankCardNumber, length, 10)) != null) {
            return bankCardIssuer;
        }
        else if ((bankCardIssuer = findBankCardIssuer(bankCardNumber, length, 3)) != null) {
            return bankCardIssuer;
        }
        else {
            return null;
        }
    }

}

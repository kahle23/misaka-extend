package misaka.whois;

import artoria.exception.ExceptionUtils;
import artoria.net.*;

public class IanaWhoisServerProvider implements WhoisServerProvider {
    // https://www.iana.org/whois

    @Override
    public WhoisServer findByDomainName(String domainName) {
        try {
            HttpRequest httpRequest = new HttpRequest();
            String accessAddress = "https://www.iana.org/whois?q=" + domainName;
            httpRequest.setUrl(accessAddress);
            httpRequest.setMethod(HttpMethod.GET);
            httpRequest.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
            httpRequest.addHeader("Host", "www.iana.org");
            httpRequest.addHeader("Referer", accessAddress);
            HttpClient httpClient = HttpUtils.getHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            return null;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

}

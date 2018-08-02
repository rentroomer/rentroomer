package rentroomer.roomreview.support;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class RequestBuilder {
    private MultiValueMap<String, String> headers;
    private MultiValueMap<String, Object> params;


    public RequestBuilder() {
        headers = new LinkedMultiValueMap<>();
        params = new LinkedMultiValueMap<>();
    }

    public RequestBuilder addParam(String key, Object value) {
        params.add(key, value);
        return this;
    }

    public RequestBuilder addHeader(String key, String value) {
        headers.add(key, value);
        return this;
    }

    public HttpEntity<MultiValueMap<String, Object>> build() {
        HttpHeaders header = new HttpHeaders();
        header.addAll(headers);
        return new HttpEntity<>(params, header);
    }
}

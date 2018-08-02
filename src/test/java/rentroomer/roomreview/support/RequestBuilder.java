package rentroomer.roomreview.support;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class RequestBuilder {

    private MultiValueMap<String, String> headers;

    public RequestBuilder() {
        headers = new LinkedMultiValueMap<>();
    }

    public RequestBuilder addHeader(String key, String value) {
        headers.add(key, value);
        return this;
    }

    public HttpEntity<String> build() {
        HttpHeaders header = new HttpHeaders();
        header.addAll(headers);
        return new HttpEntity<>(header);
    }
}

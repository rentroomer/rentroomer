package rentroomer.roomreview.dto;

import java.util.Map;

public class KakaoProperty implements SocialProperty {
    private static final String PROPERTY_NAME_KEY = "nickname";
    private Long id;
    private Map<String, String> properties;

    public KakaoProperty() {
    }

    public KakaoProperty setId(Long id) {
        this.id = id;
        return this;
    }
    
    public KakaoProperty setProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String getName() {
        return properties.get(PROPERTY_NAME_KEY);
    }

    @Override
    public Long getSocialId() {
        return id;
    }
}

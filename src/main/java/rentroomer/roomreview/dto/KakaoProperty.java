package rentroomer.roomreview.dto;

public class KakaoProperty implements SocialProperty {

    private Long id;
    private String nickname;

    public KakaoProperty() {
    }

    public Long getId() {
        return id;
    }

    public KakaoProperty setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    @Override
    public String getName() {
        return nickname;
    }

    @Override
    public Long getSocialID() {
        return id;
    }
}

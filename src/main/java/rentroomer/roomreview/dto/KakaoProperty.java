package rentroomer.roomreview.dto;

public class KakaoProperty implements SocialProperty {

    private String nickname;

    public KakaoProperty() {
    }

    public KakaoProperty setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    @Override
    public String getName() {
        return nickname;
    }
}

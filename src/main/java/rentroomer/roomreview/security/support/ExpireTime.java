package rentroomer.roomreview.security.support;

import java.util.Date;

public enum ExpireTime {
    THREE_HOURS(new Date(System.currentTimeMillis() + 10800000));

    private Date expire;

    ExpireTime(Date expire) {
        this.expire = expire;
    }

    public static Date getExpirationDate(ExpireTime expireTime) {
        return expireTime.expire;
    }


}

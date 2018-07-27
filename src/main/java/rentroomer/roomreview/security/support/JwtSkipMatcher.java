package rentroomer.roomreview.security.support;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class JwtSkipMatcher implements RequestMatcher {
    private RequestMatcher matcher;
    private OrRequestMatcher orRequestMatcher;

    public JwtSkipMatcher(String processingPath, List<String> skipPath) {
        matcher = new AntPathRequestMatcher(processingPath);
        orRequestMatcher = new OrRequestMatcher(skipPath.stream().map(AntPathRequestMatcher::new).collect(toList()));
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return matcher.matches(request) && !orRequestMatcher.matches(request);
    }
}

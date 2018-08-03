package rentroomer.roomreview.security.support;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class JWTSkipMatcher implements RequestMatcher {
    private OrRequestMatcher requestMatcher;

    public JWTSkipMatcher(List<String> processingPath) {
        requestMatcher = new OrRequestMatcher(processingPath.stream().map(AntPathRequestMatcher::new).collect(toList()));
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return requestMatcher.matches(request);
    }
}

package com.funeat.auth.util;

import com.funeat.auth.exception.AuthErrorCode;
import com.funeat.auth.exception.AuthException.NotLoggedInException;
import java.util.Objects;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UriTemplate;

@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {

    private static final String GET = "GET";
    private static final UriTemplate SORTING_REVIEW_URI_TEMPLATE = new UriTemplate("/api/products/{productId}/reviews");

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        if (GET.equals(request.getMethod()) && SORTING_REVIEW_URI_TEMPLATE.matches(request.getRequestURI())) {
            return true;
        }

        final HttpSession session = request.getSession(false);

        if (Objects.isNull(session)) {
            throw new NotLoggedInException(AuthErrorCode.LOGIN_MEMBER_NOT_FOUND);
        }

        return true;
    }
}

package com.funeat.admin.util;

import static com.funeat.auth.exception.AuthErrorCode.LOGIN_ADMIN_NOT_FOUND;

import com.funeat.admin.application.AdminChecker;
import com.funeat.admin.domain.AdminAuthInfo;
import com.funeat.auth.exception.AuthException.NotLoggedInException;
import java.util.Objects;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminCheckInterceptor implements HandlerInterceptor {

    private final AdminChecker adminChecker;

    public AdminCheckInterceptor(final AdminChecker adminChecker) {
        this.adminChecker = adminChecker;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        final HttpSession session = request.getSession(false);

        if (Objects.isNull(session)) {
            throw new NotLoggedInException(LOGIN_ADMIN_NOT_FOUND);
        }

        final String authId = String.valueOf(session.getAttribute("authId"));
        final String authKey = String.valueOf(session.getAttribute("authKey"));

        if (Objects.isNull(authId) || Objects.isNull(authKey)) {
            throw new NotLoggedInException(LOGIN_ADMIN_NOT_FOUND);
        }

        return adminChecker.check(new AdminAuthInfo(authId, authKey));
    }
}

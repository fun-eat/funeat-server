package com.funeat.recipe.util;

import com.funeat.auth.exception.AuthErrorCode;
import com.funeat.auth.exception.AuthException.NotLoggedInException;
import java.util.Objects;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RecipeDetailHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        final HttpSession session = request.getSession(false);

        if (Objects.isNull(session)) {
            throw new NotLoggedInException(AuthErrorCode.LOGIN_MEMBER_NOT_FOUND);
        }

        return true;
    }
}

package admin.adminsiteserver.authentication.ui;

import admin.adminsiteserver.authentication.domain.MemberAdapter;
import admin.adminsiteserver.authentication.util.AuthenticationPrincipal;
import admin.adminsiteserver.common.dto.LoginMember;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
        boolean hasLoginMemberType = LoginMember.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && hasLoginMemberType;
    }

    @Override
    public LoginMember resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        MemberAdapter memberAdapter = (MemberAdapter) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return LoginMember.from(memberAdapter);
    }
}

package admin.adminsiteserver.member.auth.util;

import admin.adminsiteserver.member.auth.domain.MemberAdapter;
import admin.adminsiteserver.member.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class LoginUserInfoArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasMemberInfoAnnotation = parameter.hasParameterAnnotation(LoginUser.class);
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());
        return hasMemberInfoAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        MemberAdapter memberAdapter = (MemberAdapter) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return memberAdapter.getMember();
    }
}

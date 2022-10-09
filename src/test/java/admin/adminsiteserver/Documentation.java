package admin.adminsiteserver;

import admin.adminsiteserver.authentication.domain.MemberAdapter;
import admin.adminsiteserver.authentication.util.JwtTokenProvider;
import admin.adminsiteserver.common.config.SecurityConfig;
import admin.adminsiteserver.member.domain.Member;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@Import(SecurityConfig.class)
@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("test")
public class Documentation {
    private static final String SCHEME = "https";
    private static final String HOST = "admin-site-server.com";

    @MockBean
    protected AuthenticationEntryPoint authenticationEntryPoint;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        RestAssuredMockMvc.webAppContextSetup(context, documentationConfiguration(restDocumentation));
    }

    protected static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(modifyUris().scheme(SCHEME).host(HOST).removePort(), prettyPrint());
    }

    protected static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }

    protected void setJwtTokenProvider(Member member, String token, boolean validateResult) {
        MemberAdapter memberAdapter = new MemberAdapter(member);
        var authenticationToken = new UsernamePasswordAuthenticationToken(memberAdapter, "", memberAdapter.getAuthorities());
        when(jwtTokenProvider.getAuthentication(any())).thenReturn(authenticationToken);
        when(jwtTokenProvider.extractToken(any())).thenReturn(token);
        when(jwtTokenProvider.validateToken(any())).thenReturn(validateResult);
    }
}

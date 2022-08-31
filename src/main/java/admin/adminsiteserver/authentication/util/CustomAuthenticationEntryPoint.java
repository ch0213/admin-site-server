package admin.adminsiteserver.authentication.util;

import admin.adminsiteserver.common.exception.ErrorResponse;
import admin.adminsiteserver.authentication.exception.PermissionDeniedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.fasterxml.jackson.core.json.JsonWriteFeature.ESCAPE_NON_ASCII;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String APPLICATION_JSON = "application/json; charset:UTF-8";
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        createErrorResponse(response);
    }

    public void createErrorResponse(HttpServletResponse response) throws IOException {
        response.setStatus(FORBIDDEN.value());
        response.setContentType(APPLICATION_JSON);
        objectMapper.getFactory().configure(ESCAPE_NON_ASCII.mappedFeature(), true);
        String responseData = objectMapper.writeValueAsString(ErrorResponse.from(new PermissionDeniedException()));
        response.getWriter().write(responseData);
    }
}

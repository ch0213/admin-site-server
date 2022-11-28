package admin.adminsiteserver.common.config;

import admin.adminsiteserver.authentication.ui.JwtAuthenticationFilter;
import admin.adminsiteserver.authentication.util.JwtTokenProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

public class AdminSiteAuthorization {
    private static final String[] managers = {"ADMIN", "PRESIDENT", "OFFICER"};
    private static final String[] members = {"ADMIN", "PRESIDENT", "OFFICER", "MEMBER"};

    private final HttpSecurity http;

    public AdminSiteAuthorization(HttpSecurity http, JwtTokenProvider jwtTokenProvider) throws Exception {
        this.http = http;
        http
                .cors().and()
                .csrf().disable()
                .httpBasic().disable()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(STATELESS);
    }

    public AdminSiteAuthorization announcements() throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/announcements/**/comments", "/announcements/**/comments/**").hasAnyRole(members)
                .antMatchers(POST, "/announcements", "/announcements/**").hasAnyRole(managers)
                .antMatchers(GET, "/announcements", "/announcements/**").permitAll()
                .antMatchers(PUT, "/announcements/**").hasAnyRole(managers)
                .antMatchers(DELETE, "/announcements/**").hasAnyRole(managers);
        return this;
    }

    public AdminSiteAuthorization authentication() throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll();
        return this;
    }

    public AdminSiteAuthorization calendars() throws Exception {
        http
                .authorizeRequests()
                .antMatchers(POST, "/calendars").hasAnyRole(managers)
                .antMatchers(GET, "/calendars").permitAll()
                .antMatchers(PUT, "/calendars/**").hasAnyRole(managers)
                .antMatchers(DELETE, "/calendars/**").hasAnyRole(managers);
        return this;
    }

    public AdminSiteAuthorization members() throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/signup", "/members", "/members/**").permitAll();
        return this;
    }

    public SecurityFilterChain build(AuthenticationEntryPoint authenticationEntryPoint) throws Exception {
        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);
        return http.build();
    }
}

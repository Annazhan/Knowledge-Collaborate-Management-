package com.example.knw.handler;

import com.example.knw.utils.RepeatableRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author qanna
 * @date 2021-05-01
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CachingContentFilter extends HttpFilter{
    private static final String FORM_CONTENT_TYPE = "multipart/form-data";


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String contentType = request.getContentType();
        if (request instanceof HttpServletRequest) {
            RepeatableRequestWrapper requestWrapper = new RepeatableRequestWrapper ((HttpServletRequest) request);
            // #1
            if (contentType != null && contentType.contains(FORM_CONTENT_TYPE)) {
                chain.doFilter(request, response);
            } else {
                log.info("change request type: RepeatableBody");
                chain.doFilter(requestWrapper, response);
            }
            return;
        }
        chain.doFilter(request, response);
    }
}

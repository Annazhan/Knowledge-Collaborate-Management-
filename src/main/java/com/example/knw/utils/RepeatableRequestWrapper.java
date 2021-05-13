package com.example.knw.utils;

import org.springframework.http.HttpRequest;
import org.springframework.util.StreamUtils;


import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;

/**
 * 可重复读body的request
 *
 * @author qanna
 * @date 2021-05-02
 */
public class RepeatableRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;
    private String bodyString;

    public RepeatableRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.bodyString = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        body = bodyString.getBytes("UTF-8");
    }

    public String getBodyString() {
        return this.bodyString;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }
}

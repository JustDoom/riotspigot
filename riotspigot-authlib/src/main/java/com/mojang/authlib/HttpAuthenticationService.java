package com.mojang.authlib;

import de.dytanic.$;
import de.dytanic.log.DytanicLogger;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Map;

public abstract class HttpAuthenticationService extends BaseAuthenticationService
{
    private static final DytanicLogger LOGGER = DytanicLogger.getInstance();
    private final Proxy proxy;
    
    protected HttpAuthenticationService(final Proxy proxy) {
        super();
        Validate.notNull((Object)proxy);
        this.proxy = proxy;
    }
    
    public Proxy getProxy() {
        return this.proxy;
    }
    
    protected HttpURLConnection createUrlConnection(final URL url) throws IOException {
        Validate.notNull((Object)url);
        HttpAuthenticationService.LOGGER.debug("Opening connection to " + url);
        final HttpURLConnection connection = (HttpURLConnection)url.openConnection(this.proxy);
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);
        connection.setUseCaches(false);
        return connection;
    }
    
    public String performPostRequest(final URL url, final String post, final String contentType) throws IOException {
        Validate.notNull((Object)url);
        Validate.notNull((Object)post);
        Validate.notNull((Object)contentType);
        final HttpURLConnection connection = this.createUrlConnection(url);
        final byte[] postAsBytes = post.getBytes(Charsets.UTF_8);
        connection.setRequestProperty("Content-Type", contentType + "; charset=utf-8");
        connection.setRequestProperty("Content-Length", $.EMPTY_STRING + postAsBytes.length);
        connection.setDoOutput(true);
        HttpAuthenticationService.LOGGER.debug("Writing POST data to " + url + ": " + post);
        OutputStream outputStream = null;
        try {
            outputStream = connection.getOutputStream();
            IOUtils.write(postAsBytes, outputStream);
        }
        finally {
            IOUtils.closeQuietly(outputStream);
        }
        HttpAuthenticationService.LOGGER.debug("Reading data from " + url);
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
            final String result = IOUtils.toString(inputStream, Charsets.UTF_8);
            HttpAuthenticationService.LOGGER.debug("Successful read, server response was " + connection.getResponseCode());
            HttpAuthenticationService.LOGGER.debug("Response: " + result);
            return result;
        }
        catch (IOException e) {
            IOUtils.closeQuietly(inputStream);
            inputStream = connection.getErrorStream();
            if (inputStream != null) {
                HttpAuthenticationService.LOGGER.debug("Reading error page from " + url);
                final String result2 = IOUtils.toString(inputStream, Charsets.UTF_8);
                HttpAuthenticationService.LOGGER.debug("Successful read, server response was " + connection.getResponseCode());
                HttpAuthenticationService.LOGGER.debug("Response: " + result2);
                return result2;
            }
            HttpAuthenticationService.LOGGER.debug("Request failed", (Throwable)e);
            throw e;
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
    
    public String performGetRequest(final URL url) throws IOException {
        Validate.notNull((Object)url);
        final HttpURLConnection connection = this.createUrlConnection(url);
        HttpAuthenticationService.LOGGER.debug("Reading data from " + url);
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
            final String result = IOUtils.toString(inputStream, Charsets.UTF_8);
            HttpAuthenticationService.LOGGER.debug("Successful read, server response was " + connection.getResponseCode());
            HttpAuthenticationService.LOGGER.debug("Response: " + result);
            return result;
        }
        catch (IOException e) {
            IOUtils.closeQuietly(inputStream);
            inputStream = connection.getErrorStream();
            if (inputStream != null) {
                HttpAuthenticationService.LOGGER.debug("Reading error page from " + url);
                final String result2 = IOUtils.toString(inputStream, Charsets.UTF_8);
                HttpAuthenticationService.LOGGER.debug("Successful read, server response was " + connection.getResponseCode());
                HttpAuthenticationService.LOGGER.debug("Response: " + result2);
                return result2;
            }
            HttpAuthenticationService.LOGGER.debug("Request failed", (Throwable)e);
            throw e;
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
    
    public static URL constantURL(final String url) {
        try {
            return new URL(url);
        }
        catch (MalformedURLException ex) {
            throw new Error("Couldn't create constant for " + url, ex);
        }
    }
    
    public static String buildQuery(final Map<String, Object> query) {
        if (query == null) {
            return $.EMPTY_STRING;
        }
        final StringBuilder builder = new StringBuilder();
        for (final Map.Entry<String, Object> entry : query.entrySet()) {
            if (builder.length() > 0) {
                builder.append('&');
            }
            try {
                builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            }
            catch (UnsupportedEncodingException e) {
                HttpAuthenticationService.LOGGER.error("Unexpected exception building query", (Throwable)e);
            }
            if (entry.getValue() != null) {
                builder.append('=');
                try {
                    builder.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
                }
                catch (UnsupportedEncodingException e) {
                    HttpAuthenticationService.LOGGER.error("Unexpected exception building query", (Throwable)e);
                }
            }
        }
        return builder.toString();
    }
    
    public static URL concatenateURL(final URL url, final String query) {
        try {
            if (url.getQuery() != null && url.getQuery().length() > 0) {
                return new URL(url.getProtocol(), url.getHost(), url.getPort(), url.getFile() + "&" + query);
            }
            return new URL(url.getProtocol(), url.getHost(), url.getPort(), url.getFile() + "?" + query);
        }
        catch (MalformedURLException ex) {
            throw new IllegalArgumentException("Could not concatenate given URL with GET arguments!", ex);
        }
    }
}

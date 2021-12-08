package com.du.lostfoundmasterfk.config.request;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import reactor.util.annotation.Nullable;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Description 用于为RestTemplate提供将Map转换为UrlEncoded的能力
@Configuration
public class MyFormHttpMessageConverter implements HttpMessageConverter<Map<String,?>> {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final Charset charset = DEFAULT_CHARSET;
    private static final MediaType DEFAULT_FORM_DATA_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_FORM_URLENCODED,DEFAULT_CHARSET);

    @Override
    public boolean canRead(Class<?> aClass, MediaType mediaType) {
        if (!Map.class.isAssignableFrom(aClass)){
            return false;
        }
        if (mediaType == null){
            return true;
        }
        for (MediaType supportedMediaType : getSupportedMediaTypes()) {
            if (supportedMediaType.includes(mediaType)){
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean canWrite(Class<?> aClass, MediaType mediaType) {
        if (!Map.class.isAssignableFrom(aClass)){
            return false;
        }
        if (mediaType == null || MediaType.ALL.equals(mediaType)){
            return true;
        }
        for (MediaType supportedMediaType : getSupportedMediaTypes()) {
            if (supportedMediaType.isCompatibleWith(mediaType)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED);
    }

    @Override
    public Map<String, ?> read(Class<? extends Map<String, ?>> aClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        MediaType contentType = inputMessage.getHeaders().getContentType();
        Charset charset = (contentType != null && contentType.getCharset() != null ? contentType.getCharset() : this.charset);

        String body = StreamUtils.copyToString(inputMessage.getBody(), charset);

        String[] strings = StringUtils.tokenizeToStringArray(body, "&");
        Map<String,String> result = new HashMap<>(strings.length);
        for (String string : strings) {
            int idx = string.indexOf('=');
            if (idx == -1){
                result.put(URLDecoder.decode(string,charset.name()),null);
            }
            else {
                String name = URLDecoder.decode(string.substring(0,idx),charset.name());
                String value = URLDecoder.decode(string.substring(idx+1),charset.name());
                result.put(name,value);
            }
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void write(Map<String, ?> stringMap, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        writeForm((Map<String,Object>) stringMap,mediaType,httpOutputMessage) ;

    }

    public void writeForm(Map<String, Object> formData, @Nullable MediaType mediaType, HttpOutputMessage outputMessage) throws IOException{
        mediaType = getFormContentType(mediaType);
        outputMessage.getHeaders().setContentType(mediaType);

        Charset charset = mediaType.getCharset();
        Assert.notNull(charset,"No charSet");

        byte[] bytes = serializeForm(formData,charset).getBytes(charset);
        outputMessage.getHeaders().setContentLength(bytes.length);

        if (outputMessage instanceof StreamingHttpOutputMessage){
            StreamingHttpOutputMessage streamingHttpOutputMessage = (StreamingHttpOutputMessage) outputMessage;
            streamingHttpOutputMessage.setBody(outputStream -> StreamUtils.copy(bytes,outputStream));
        }
        else {
            StreamUtils.copy(bytes, outputMessage.getBody());
        }
    }

    protected MediaType getFormContentType(@Nullable MediaType mediaType){
        if (mediaType == null){
            return DEFAULT_FORM_DATA_MEDIA_TYPE;
        }
        else if (mediaType.getCharset() == null){
            return new MediaType(mediaType, this.charset);
        }
        else {
            return mediaType;
        }
    }

    protected String serializeForm(Map<String, Object> formData, Charset charset) {
        StringBuilder builder = new StringBuilder();
        formData.forEach((name, value) -> {
            if (name == null) {
                Assert.isTrue(value == null, "Null name in form data: " + formData);
                return;
            }
            try {
                if (builder.length() != 0) {
                    builder.append('&');
                }
                builder.append(URLEncoder.encode(name, charset.name()));
                if (value != null) {
                    builder.append('=');
                    builder.append(URLEncoder.encode(String.valueOf(value), charset.name()));
                }
            }
            catch (UnsupportedEncodingException ex) {
                throw new IllegalStateException(ex);
            }
        });

        return builder.toString();
    }

}

package com.lc.compiler.global;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lc.compiler.util.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 1:17 PM
 */
@Slf4j
@RestControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {
    private ObjectMapper objectMapper;
    /**
     * getGenericParameterType.getTypeName() 返回 ResultData<\?>
     * class.getTypeName() 返回 ResultData
     */
    private static final String NAME_RESULT_DATA_CLASS = ResultData.class.getTypeName() + "<?>";

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 如果返回的Object是Result不用invoke beforeBodyWrite 方法
        return !NAME_RESULT_DATA_CLASS.equals(returnType.getGenericParameterType().getTypeName());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(ResultData.success(null, body));
            } catch (JsonProcessingException ignore) {
            }
        } else if (body instanceof LinkedHashMap<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) body;
            return ResultData.result((Integer) map.get("status"), (String) map.get("error"), map.get("message"));
        } else if (body instanceof ResultData) {
            return body;
        }

        return ResultData.success(null, body);
    }
}

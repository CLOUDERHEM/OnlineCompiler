package com.lc;

import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

// @SpringBootTest(classes = CompilerApplicationTests.class)
class CompilerApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(DigestUtils.md5DigestAsHex(String.valueOf(1657455504).getBytes(StandardCharsets.UTF_8)));
    }

}

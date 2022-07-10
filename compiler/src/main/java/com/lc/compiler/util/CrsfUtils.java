package com.lc.compiler.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author Aaron Yeung
 * @date 7/10/2022 7:23 PM
 * <p>
 * crsf_token =  md5(prefix + timestamp/1000) // 当前秒数, 误差在period内有效
 */
@Slf4j
public class CrsfUtils {

    private static final String PREFIX = "I_am_useless_";
    private static final int PERIOD = 1;

    public static boolean match(String crsfToken) {
        long sec = System.currentTimeMillis() / 1000;
        if (log.isDebugEnabled()) {
            log.debug("sec = {}, crsfToken ={}", sec, crsfToken);
        }
        for (long i = sec - PERIOD; i <= sec + PERIOD; i++) {
            String raw = DigestUtils.md5DigestAsHex((PREFIX + i).getBytes(StandardCharsets.UTF_8));
            if (log.isDebugEnabled()) {
                log.debug("each raw = {}", raw);
            }
            if (raw.equals(crsfToken)) {
                return true;
            }
        }

        return false;
    }
}

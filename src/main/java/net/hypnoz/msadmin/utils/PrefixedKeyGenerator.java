package net.hypnoz.msadmin.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class PrefixedKeyGenerator implements KeyGenerator {

    private final String prefix;

    /**
     * <p>Constructor for PrefixedKeyGenerator.</p>
     *
     * @param gitProperties a {@link GitProperties} object.
     * @param buildProperties a {@link BuildProperties} object.
     */
    public PrefixedKeyGenerator(GitProperties gitProperties, BuildProperties buildProperties) {

        prefix = generatePrefix(gitProperties, buildProperties);
    }

    String getPrefix() {
        return prefix;
    }

    private String generatePrefix(GitProperties gitProperties, BuildProperties buildProperties) {

        String shortCommitId = null;
        if (Objects.nonNull(gitProperties)) {
            shortCommitId = gitProperties.getShortCommitId();
        }

        Instant time = null;
        String version = null;
        if (Objects.nonNull(buildProperties)) {
            time = buildProperties.getTime();
            version = buildProperties.getVersion();
        }
        SecureRandom secureRandom = new SecureRandom();
        String secureRandomAlphanumeric = new BigInteger(70, secureRandom).toString(32);
        Object p = ObjectUtils.firstNonNull(shortCommitId, time, version, secureRandomAlphanumeric);
        if (p instanceof Instant instant) {
            return DateTimeFormatter.ISO_INSTANT.format(instant);
        }
        return p.toString();
    }


    /** {@inheritDoc} */
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return new PrefixedSimpleKey(prefix, method.getName(), params);
    }
}


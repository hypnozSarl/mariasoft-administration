package net.hypnoz.msadmin.utils;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;

public class PrefixedSimpleKey implements Serializable {

    private final String prefix;
    private final Serializable[] params;
    private final String methodName;
    private int hashCode;

    /**
     * <p>Constructor for PrefixedSimpleKey.</p>
     *
     * @param prefix a {@link String} object.
     * @param methodName a {@link String} object.
     * @param elements a {@link Object} object.
     */
    public PrefixedSimpleKey(String prefix, String methodName, Object... elements) {
        Assert.notNull(prefix, "Prefix must not be null");
        Assert.notNull(elements, "Elements must not be null");
        this.prefix = prefix;
        this.methodName = methodName;
        params = new Serializable[elements.length];
        System.arraycopy(elements, 0, params, 0, elements.length);
        hashCode = prefix.hashCode();
        hashCode = 31 * hashCode + methodName.hashCode();
        hashCode = 31 * hashCode + Arrays.deepHashCode(params);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        return (this == other ||
                (other instanceof PrefixedSimpleKey otherAsPrefixedSimpleKey &&
                        prefix.equals(otherAsPrefixedSimpleKey.prefix) &&
                        methodName.equals(otherAsPrefixedSimpleKey.methodName) &&
                        Arrays.deepEquals(params, otherAsPrefixedSimpleKey.params)));
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
        return hashCode;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return prefix + " " + getClass().getSimpleName() + methodName + " [" + StringUtils.arrayToCommaDelimitedString(
                params) + "]";
    }
}

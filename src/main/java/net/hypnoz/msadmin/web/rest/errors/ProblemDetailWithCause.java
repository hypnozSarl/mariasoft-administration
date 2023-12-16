package net.hypnoz.msadmin.web.rest.errors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class ProblemDetailWithCause  extends ProblemDetail {
    private ProblemDetailWithCause cause;

    ProblemDetailWithCause(int rawStatus) {
        super(rawStatus);
    }

    ProblemDetailWithCause(int rawStatus, ProblemDetailWithCause cause) {
        super(rawStatus);
        this.cause = cause;
    }

    // The missing builder from Spring
    public static class ProblemDetailWithCauseBuilder {
        private static final URI BLANK_TYPE = URI.create("about:blank");
        // From Springs Problem Detail
        private URI type = BLANK_TYPE;
        private String title;
        private int status;
        private String detail;
        private URI instance;
        private Map<String, Object> properties = new HashMap<>();
        private ProblemDetailWithCause cause;

        public static ProblemDetailWithCauseBuilder instance(){
            return new ProblemDetailWithCauseBuilder();
        }

        public ProblemDetailWithCauseBuilder withType(URI type) {
            this.type = type;
            return this;
        }

        public ProblemDetailWithCauseBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public ProblemDetailWithCauseBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public ProblemDetailWithCauseBuilder withDetail(String detail) {
            this.detail = detail;
            return this;
        }

        public ProblemDetailWithCauseBuilder withInstance(URI instance) {
            this.instance = instance;
            return this;
        }

        public ProblemDetailWithCauseBuilder withCause(ProblemDetailWithCause cause) {
            this.cause = cause;
            return this;
        }

        public ProblemDetailWithCauseBuilder withProperties(Map<String, Object> properties) {
            this.properties = properties;
            return this;
        }

        public ProblemDetailWithCauseBuilder withProperty(String key, Object value) {
            this.properties.put(key, value);
            return this;
        }

        public ProblemDetailWithCause build() {
            ProblemDetailWithCause detailWithCause = new ProblemDetailWithCause(this.status);
            detailWithCause.setType(this.type);
            detailWithCause.setTitle(this.title);
            detailWithCause.setDetail(this.detail);
            detailWithCause.setInstance(this.instance);
            this.properties.forEach(detailWithCause::setProperty);
            detailWithCause.setCause(this.cause);
            return detailWithCause;
        }
    }
}


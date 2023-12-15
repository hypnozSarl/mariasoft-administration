package net.hypnoz.msadmin.utils;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.core.Ordered;

public class MariasoftOpenApiCustomizer implements OpenApiCustomizer, Ordered {

    /**
     * The default order for the customizer.
     */
    public static final int DEFAULT_ORDER = 0;

    private int order = DEFAULT_ORDER;

    private final MariasoftProperies.ApiDocs properties;

    /**
     * <p>Constructor for JHipsterOpenApiCustomizer.</p>
     *
     * @param properties a {@link MariasoftProperies.ApiDocs} object.
     */
    public MariasoftOpenApiCustomizer(MariasoftProperies.ApiDocs properties) {
        this.properties = properties;
    }

    /** {@inheritDoc} */
    @Override
    public void customise(OpenAPI openAPI) {
        Contact contact = new Contact()
                .name(properties.getContactName())
                .url(properties.getContactUrl())
                .email(properties.getContactEmail());

        openAPI.info(new Info()
                .contact(contact)
                .title(properties.getTitle())
                .description(properties.getDescription())
                .version(properties.getVersion())
                .termsOfService(properties.getTermsOfServiceUrl())
                .license(new License().name(properties.getLicense()).url(properties.getLicenseUrl()))
        );

        for (MariasoftProperies.ApiDocs.Server server : properties.getServers()) {
            openAPI.addServersItem(new Server().url(server.getUrl()).description(server.getDescription()));
        }
    }

    /**
     * <p>Setter for the field <code>order</code>.</p>
     *
     * @param order a int.
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /** {@inheritDoc} */
    @Override
    public int getOrder() {
        return order;
    }
}

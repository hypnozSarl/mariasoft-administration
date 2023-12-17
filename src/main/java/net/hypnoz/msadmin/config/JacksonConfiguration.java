
/*
 * Mariasoft - Enterprise Management Software
 * Copyright (c) [Year of creation] Hypnoz Technologie. All rights reserved.
 *
 * This software and associated documentation files (the "Software") is the
 * proprietary and confidential information of Hypnoz Technologie and is subject
 * to a license agreement. The Software is protected by international copyright
 * and other intellectual property laws and treaties.
 *
 * No part of this Software may be copied, modified, distributed, or reproduced
 * in any form or by any means without prior written permission from Hypnoz Technologie.
 *
 * ANY USE OF THE SOFTWARE NOT EXPRESSLY PERMITTED BY THE TERMS OF THE LICENSE
 * AGREEMENT IS A VIOLATION OF COPYRIGHT LAW AND MAY RESULT IN SEVERE
 * CIVIL AND CRIMINAL PENALTIES.
 */
package net.hypnoz.msadmin.config;

import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module.Feature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

    /**
     * Support for Java date and time API.
     * @return the corresponding Jackson module.
     */
    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    @Bean
    public Jdk8Module jdk8TimeModule() {
        return new Jdk8Module();
    }

    /*
     * Support for Hibernate types in Jackson.
     */
    @Bean
    public Hibernate6Module hibernate6Module() {
        return new Hibernate6Module().configure(Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
    }
}

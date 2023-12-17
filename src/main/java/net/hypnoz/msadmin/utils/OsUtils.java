/*
 *
 *  * Mariasoft - Enterprise Management Software
 *  * Copyright (c) 2023. Hypnoz Technologie. All rights reserved.
 *  *
 *  * This software and associated documentation files (the "Software") is the
 *  * proprietary and confidential information of Hypnoz Technologie and is subject
 *  * to a license agreement. The Software is protected by international copyright
 *  * and other intellectual property laws and treaties.
 *  *
 *  * No part of this Software may be copied, modified, distributed, or reproduced
 *  * in any form or by any means without prior written permission from Hypnoz Technologie.
 *  *
 *  * ANY USE OF THE SOFTWARE NOT EXPRESSLY PERMITTED BY THE TERMS OF THE LICENSE
 *  * AGREEMENT IS A VIOLATION OF COPYRIGHT LAW AND MAY RESULT IN SEVERE
 *  * CIVIL AND CRIMINAL PENALTIES.
 *
 */

package net.hypnoz.msadmin.utils;

import java.io.File;
import java.util.Map;
import java.util.Optional;

public class OsUtils {
    private OsUtils() {
    }

    private static OSType cachedOsType;
    private static String cachedHomeDir;

    private static final Map<String, OSType> osTypeMap = Map.of(
            "linux", OSType.LINUX,
            "windows", OSType.WINDOWS,
            "mac os", OSType.MAC,
            "macos", OSType.MAC,
            "darwin", OSType.MAC,
            "solaris", OSType.SCOLARIS,
            "sunos", OSType.SCOLARIS
    );

    public static OSType getOsType() {
        if (cachedOsType == null) {
            String osName = Optional.ofNullable(System.getProperty("os.name")).orElse("").toLowerCase();

            cachedOsType = osTypeMap.entrySet().stream()
                    .filter(e -> osName.contains(e.getKey()))
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .orElse(OSType.OTHERS);
        }
        return cachedOsType;
    }

    public static String getOsHomeDir() {
        if (cachedHomeDir == null) {
            String homeDir = System.getProperty("user.home");
            cachedHomeDir = homeDir + File.separator + MsConstants.DEFAULT_DOC_SERVEUR_NAME + File.separator;
        }

        return cachedHomeDir;
    }

    public static String getOsPath(String p) {
        return p.replace("\\\\", "/").replaceAll("(?<=.)/?$", "/");
    }

    public static String getTempDir() {
        return getOsHomeDir() + MsConstants.DIR_TEMP;
    }
}

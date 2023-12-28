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

package net.hypnoz.msadmin.utils.validators;

public class ConstantLogger {
    public static final String GROUPE_NOT_FOUND_MSG ="Failed to find Groupe id: {}" ;
    public static final String USER_NOT_FOUND_MSG ="Failed to find User id: {}" ;
    public static final String STRUCTURE_NOT_FOUND_MSG = "Failed to find Structure id: {}";
    public static final String USER_FONCTIONNALITE_NOT_LINKED_MSG = "Failed to find User Fonctionnalite not linked";
    public static final String ATTEMPT_UPDATE_MSG = "Attempting to update Structure with info: {}";
    public static final String ATTEMPT_CREATE_MSG = "Attempting to create Structure with info: {}";
    public static final String ATTEMPT_DELETE_MSG = "Attempting to delete Structure with info: {}";
    public static final String ATTEMPT_GET_MSG = "Attempting to get Structure by id: {}";
    public static final String ATTEMPT_UPLOAD_LOGO_MSG = "Attempting to upload a logo for structure ID: {}";
    public static final String UPLOAD_LOGO_ERROR_MSG = "Error while uploading logo for structure ID: {}";
    public static final String DIR_CREATED_MSG = "Directory 'structures-logo' didn't exist and was created";
    public static final String LOGO_DELETION_MSG = "Previous logo has been deleted";

    public static final String MODULE_NOT_FOUND_MSG = "Module not found";
    public static final String APPLICATION_NOT_FOUND_MSG = "Application not found";
    public static final String MODULE_LINKED_STRUCTURE_MSG = "Module is already linked with the structure";
}

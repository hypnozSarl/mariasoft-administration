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

package net.hypnoz.msadmin.service.structres;


import net.hypnoz.msadmin.dtos.StructuresDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IStructureService {
    /**
     * Creates a new structure.
     *
     * @param structuresDto The structure data.
     * @return The created structure.
     */
    StructuresDto creationStructure(StructuresDto structuresDto);

    /**
     * Updates an existing structure.
     *
     * @param structuresDto The structure data.
     * @return The updated structure.
     */
    StructuresDto updateStructure(StructuresDto structuresDto);

    /**
     * Deletes an existing structure.
     *
     * @param sid The structure ID.
     * @return The deleted structure.
     */
    void deleteStructure(Long sid);

    /**
     * Returns a structure by its ID.
     *
     * @param sid The structure ID.
     * @return The requested structure.
     */
    StructuresDto getStructure(Long sid);

    /**
     * Uploads a logo for the structure.
     *
     * @param structuresDto The structure ID.
     * @param file The logo file.
     */
    void uploadStructureLogo(StructuresDto structuresDto, MultipartFile file);
}
package net.hypnoz.msadmin.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormeJuridiqueEnumTest {
    /**
     * Method under test: {@link FormeJuridiqueEnum#getDescription()}
     */
    @Test
    void testGetDescription() {
        assertEquals("Entreprise Unipersonnelle A Responsabilités Limitées",
                FormeJuridiqueEnum.valueOf("EURL").getDescription());
    }
}

package me.jishuna.jishlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import me.jishuna.jishlib.enums.Dye;

class DyeEnumTest {

    @ParameterizedTest
    @EnumSource(Dye.class)
    void testGetters(Dye dye) {
        assertNotNull(dye.getColor());
        assertNotNull(dye.getDyeColor());
        assertNotNull(dye.getChatColor());
        assertNotNull(dye.getDye());
        assertNotNull(dye.getWool());
        assertNotNull(dye.getTerracotta());
        assertNotNull(dye.getConcretePowder());
        assertNotNull(dye.getConcrete());
        assertNotNull(dye.getStainedGlass());
        assertNotNull(dye.getStainedGlassPane());
        assertNotNull(dye.getGlazedTerracotta());
        assertNotNull(dye.getBed());
        assertNotNull(dye.getCarpet());
        assertNotNull(dye.getShulkerBox());
        assertNotNull(dye.getCandle());
        assertNotNull(dye.getCandleCake());
        assertNotNull(dye.getBanner());
        assertNotNull(dye.getWallBanner());
    }

    @ParameterizedTest
    @EnumSource(DyeColor.class)
    void testGetByDyecolor(DyeColor color) {
        assertNotNull(Dye.fromDyeColor(color));
    }

    @Test
    void testGetByMaterial() {
        int count = 0;
        for (Material material : Material.values()) {
            if (Dye.fromMaterial(material) != null) {
                count++;
            }
        }

        assertEquals(15 * 16, count);
    }
}

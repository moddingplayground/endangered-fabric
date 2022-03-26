package net.moddingplayground.endangered.impl.data;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.endangered.api.Endangered;
import net.moddingplayground.endangered.api.tag.EndangeredItemTags;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;

import static net.minecraft.item.Items.*;

public class ItemTagGenerator extends AbstractTagGenerator<Item> {
    public ItemTagGenerator() {
        super(Endangered.MOD_ID, Registry.ITEM);
    }

    @Override
    public void generate() {
        this.add(EndangeredItemTags.PANGOLIN_LIKED_ITEMS, SPIDER_EYE);
    }
}

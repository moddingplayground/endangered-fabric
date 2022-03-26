package net.moddingplayground.endangered.api.tag;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.endangered.api.Endangered;

public interface EndangeredItemTags {
    TagKey<Item> PANGOLIN_LIKED_ITEMS = create("pangolin_liked_items");

    private static TagKey<Item> create(String id) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(Endangered.MOD_ID, id));
    }
}

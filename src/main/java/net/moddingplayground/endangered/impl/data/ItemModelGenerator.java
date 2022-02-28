package net.moddingplayground.endangered.impl.data;

import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.endangered.api.Endangered;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.item.AbstractItemModelGenerator;

import java.util.Objects;

public class ItemModelGenerator extends AbstractItemModelGenerator implements Endangered {
    public ItemModelGenerator() {
        super(MOD_ID);
    }

    @Override
    public void generate() {
        for (EntityType<?> type : Registry.ENTITY_TYPE) {
            Identifier id = Registry.ENTITY_TYPE.getId(type);
            if (Objects.equals(id.getNamespace(), MOD_ID)) this.add(SpawnEggItem.forEntity(type), i -> this.spawnEgg());
        }
    }
}

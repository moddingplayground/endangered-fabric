package net.moddingplayground.endangered.api.entity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.endangered.api.Endangered;
import net.moddingplayground.frame.api.items.v0.SortedSpawnEggItem;

import java.util.Optional;

public interface EndangeredEntityType extends Endangered, ModInitializer {
    EntityType<PangolinEntity> PANGOLIN = register("pangolin", 0x845A3F, 0x393231,
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(PangolinEntity::new).spawnGroup(SpawnGroup.CREATURE)
                               .dimensions(EntityDimensions.fixed(0.8F, 0.8F))
                               .defaultAttributes(PangolinEntity::createPangolinAttributes)
                               .trackRangeChunks(8)
    );

    private static <T extends MobEntity> EntityType<T> register(String id, int primary, int secondary, SpawnEggFactory egg, FabricEntityTypeBuilder<T> type) {
        EntityType<T> built = type.build();
        Optional.ofNullable(egg).ifPresent(f -> {
            Item.Settings settings = new FabricItemSettings().maxCount(64).group(ItemGroup.MISC);
            Item item = f.apply(built, primary, secondary, settings);
            Registry.register(Registry.ITEM,  new Identifier(MOD_ID, "%s_spawn_egg".formatted(id)), item);
        });
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(MOD_ID, id), built);
    }

    private static <T extends MobEntity> EntityType<T> register(String id, int primary, int secondary, FabricEntityTypeBuilder<T> entityType) {
        return register(id, primary, secondary, SortedSpawnEggItem::new, entityType);
    }

    @FunctionalInterface interface SpawnEggFactory { SpawnEggItem apply(EntityType<? extends MobEntity> type, int primaryColor, int secondaryColor, Item.Settings settings); }
}

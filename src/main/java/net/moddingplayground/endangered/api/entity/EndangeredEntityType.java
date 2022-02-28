package net.moddingplayground.endangered.api.entity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
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
    EntityType<PangolinEntity> PANGOLIN = register(
        "pangolin",
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(PangolinEntity::new).spawnGroup(SpawnGroup.CREATURE)
                               .dimensions(EntityDimensions.fixed(0.8F, 0.8F))
                               .defaultAttributes(
                                   () -> MobEntity.createMobAttributes()
                                                  .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
                                                  .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1D)
                               )
                               .trackRangeChunks(8),
        0x2864C7, 0xFBCA0C
    );

    @SuppressWarnings("unchecked")
    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> type, int primary, int secondary, SpawnEggFactory egg) {
        EntityType<T> built = type.build();
        Optional.ofNullable(egg).ifPresent(f -> {
            Item.Settings settings = new FabricItemSettings().maxCount(64).group(ItemGroup.MISC);
            Item item = f.apply((EntityType<? extends MobEntity>) built, primary, secondary, settings);
            Registry.register(Registry.ITEM,  new Identifier(MOD_ID, "%s_spawn_egg".formatted(id)), item);
        });
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(MOD_ID, id), built);
    }

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entityType, int primary, int secondary) {
        return register(id, entityType, primary, secondary, SortedSpawnEggItem::new);
    }

    @FunctionalInterface interface SpawnEggFactory { SpawnEggItem apply(EntityType<? extends MobEntity> type, int primaryColor, int secondaryColor, Item.Settings settings); }
}

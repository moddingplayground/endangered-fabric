package net.moddingplayground.endangered.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.moddingplayground.endangered.api.entity.PangolinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract boolean isConnectedThroughVehicle(Entity entity);

    /**
     * Overrides collision logic for falling blocks and pangolins.
     */
    @Inject(method = "collidesWith", at = @At("HEAD"), cancellable = true)
    private void onCollidesWith(Entity other, CallbackInfoReturnable<Boolean> cir) {
        Entity that = Entity.class.cast(this);
        if (that instanceof FallingBlockEntity && other instanceof PangolinEntity) cir.setReturnValue(false);
    }
}

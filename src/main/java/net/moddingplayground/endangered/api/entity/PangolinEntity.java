package net.moddingplayground.endangered.api.entity;

import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TrackOwnerAttackerGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.moddingplayground.endangered.api.entity.ai.goal.ConditionalLookAroundGoal;
import net.moddingplayground.endangered.api.entity.ai.goal.ConditionalLookAtEntityGoal;
import net.moddingplayground.endangered.api.tag.EndangeredItemTags;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.function.Predicate;

public class PangolinEntity extends TameableEntity implements IAnimatable {
    public static final List<DamageSource> RETREATED_DAMAGE_SOURCE_BYPASSERS = List.of(
        DamageSource.OUT_OF_WORLD,
        DamageSource.FALLING_BLOCK,
        DamageSource.ANVIL,
        DamageSource.FREEZE,
        DamageSource.IN_WALL,
        DamageSource.CRAMMING,
        DamageSource.LAVA,
        DamageSource.HOT_FLOOR,
        DamageSource.LIGHTNING_BOLT,
        DamageSource.IN_FIRE,
        DamageSource.ON_FIRE
    );

    public static final Ingredient PANGOLIN_LIKED_ITEMS_INGREDIENT = Ingredient.fromTag(EndangeredItemTags.PANGOLIN_LIKED_ITEMS);
    public static final float WALKING_THRESHOLD = 0.06F;

    private final AnimationFactory animation;

    protected PangolinEntity(EntityType<? extends PangolinEntity> type, World world) {
        super(type, world);
        this.animation = new AnimationFactory(this);
    }

    /* Initialization */

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(7, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(10, new ConditionalLookAtEntityGoal<>(this, PlayerEntity.class, 8.0f, Predicate.not(TameableEntity::isSitting)));
        this.goalSelector.add(10, new ConditionalLookAroundGoal<>(this, Predicate.not(TameableEntity::isSitting)));
        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
    }

    public static DefaultAttributeContainer.Builder createPangolinAttributes() {
        return MobEntity.createMobAttributes()
                        .add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0D)
                        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1D);
    }

    /* Animation */

    @Override
    public AnimationFactory getFactory() {
        return this.animation;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 5, this::animate));
    }

    public <E extends IAnimatable> PlayState animate(AnimationEvent<E> event) {
        AnimationController<?> controller = event.getController();

        if (this.isInSittingPose()) return PlayState.STOP;

        float limbd = event.getLimbSwingAmount();
        boolean moving = !(limbd > -WALKING_THRESHOLD && limbd < WALKING_THRESHOLD);
        controller.setAnimation(new AnimationBuilder().addAnimation( "animation.pangolin." + (moving ? "walk" : "idle"), true));
        return PlayState.CONTINUE;
    }

    /* Ticking */

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!this.isTamed()) {
            if (PANGOLIN_LIKED_ITEMS_INGREDIENT.test(stack)) {
                if (!player.getAbilities().creativeMode) stack.decrement(1);

                if (!this.isSilent()) {
                    this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PARROT_EAT, this.getSoundCategory(), 1.0f, 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f);
                }

                if (!this.world.isClient) {
                    if (this.random.nextInt(3) == 0) {
                        this.setOwner(player);
                        this.navigation.stop();
                        this.setTarget(null);
                        this.world.sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
                    } else this.world.sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
                }

                return ActionResult.success(this.world.isClient);
            }
        } else {
            if (this.isOwner(player)) {
                if (!this.world.isClient) this.setSitting(!this.isSitting());
                return ActionResult.success(this.world.isClient);
            }
        }

        return super.interactMob(player, hand);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (this.isSitting() && !RETREATED_DAMAGE_SOURCE_BYPASSERS.contains(source)) return true;
        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean isCollidable() {
        return this.isInSittingPose();
    }

    /* Breeding */

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}

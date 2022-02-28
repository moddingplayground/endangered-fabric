package net.moddingplayground.endangered.impl.data;

import net.moddingplayground.endangered.api.Endangered;
import net.moddingplayground.frame.api.toymaker.v0.ToymakerEntrypoint;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.ItemModelGeneratorStore;

public final class EndangeredToymakerImpl implements Endangered, ToymakerEntrypoint {
    @Override
    public void onInitializeToymaker() {
        ItemModelGeneratorStore.register(ItemModelGenerator::new);
    }
}

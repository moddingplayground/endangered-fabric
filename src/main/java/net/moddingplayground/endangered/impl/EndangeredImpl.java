package net.moddingplayground.endangered.impl;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.moddingplayground.endangered.api.Endangered;
import net.moddingplayground.endangered.api.entity.EndangeredEntityType;
import net.moddingplayground.frame.api.util.InitializationLogger;
import software.bernie.geckolib3.GeckoLib;

public final class EndangeredImpl implements Endangered, ModInitializer {
	private static EndangeredImpl instance;
	private final InitializationLogger initializer;

	public EndangeredImpl() {
		this.initializer = new InitializationLogger(LOGGER, MOD_NAME);
		instance = this;
	}

	@SuppressWarnings("UnstableApiUsage")
	@Override
	public void onInitialize() {
		this.initializer.start();

		GeckoLib.initialize();
		Reflection.initialize(EndangeredEntityType.class);

		this.initializer.finish();
	}

	public static EndangeredImpl getInstance() {
		return instance;
	}
}

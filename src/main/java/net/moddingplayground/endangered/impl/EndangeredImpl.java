package net.moddingplayground.endangered.impl;

import net.fabricmc.api.ModInitializer;
import net.moddingplayground.endangered.api.Endangered;
import net.moddingplayground.frame.api.util.InitializationLogger;

public final class EndangeredImpl implements Endangered, ModInitializer {
	private static EndangeredImpl instance;
	private final InitializationLogger initializer;

	public EndangeredImpl() {
		this.initializer = new InitializationLogger(LOGGER, MOD_NAME);
		instance = this;
	}

	@Override
	public void onInitialize() {
		this.initializer.start();

		//

		this.initializer.finish();
	}

	public static EndangeredImpl getInstance() {
		return instance;
	}
}

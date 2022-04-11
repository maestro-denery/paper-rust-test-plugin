package io.denery.rustplugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class RustPlugin extends JavaPlugin {
    private static final NativeBootstrap bootstrap = new NativeBootstrap("rustplugin");
    static {
        try {
            bootstrap.registerLibrary("librust-plugin.so");
            bootstrap.load("/META-INF/natives/linux/x86_64");
        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong while loading native Rust library...", e);
        }
    }

    @Override
    public void onEnable() {
        nativeCall();
    }

    public native void nativeCall();
}

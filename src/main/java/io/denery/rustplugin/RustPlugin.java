package io.denery.rustplugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class RustPlugin extends JavaPlugin {

    static {
        try {
            final String libname = "librust-plugin.so";
            final Path natives = Path.of("plugins/rustplugin/natives/" + libname);
            final File nativesFile = new File("plugins/rustplugin/natives");
            final File rustPlugin = nativesFile.getParentFile();
            if (!rustPlugin.exists()) {
                rustPlugin.mkdir();
            } else if (!rustPlugin.isDirectory())
                throw new IllegalStateException("rustplugin in plugins/ is not a directory!");

            if (!nativesFile.exists()) {
                nativesFile.mkdir();
            } else if (!nativesFile.isDirectory())
                throw new IllegalStateException("natives in plugins/rustplugin/ is not a directory!");

            Files.copy(
                    RustPlugin.class.getResourceAsStream("/META-INF/natives/linux/x86_64/" + libname),
                    natives,
                    StandardCopyOption.REPLACE_EXISTING
            );
            System.load(nativesFile.getAbsolutePath() + "/" + libname);
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

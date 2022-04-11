package io.denery.rustplugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * A Bootstrap class helping to deal with unpacking and loading Rust libraries for usage in plugins.
 */
public final class NativeBootstrap {
    private final String pluginName;
    private final List<String> libraries = new ArrayList<>();
    private final File nativesFile;

    /**
     * Initializes Bootstrap and checks for packages in plugins directory.
     * @param pluginName name of your plugin.
     */
    public NativeBootstrap(final String pluginName) {
        this.pluginName = pluginName;
        this.nativesFile = new File("plugins/" + pluginName + "/natives");
        final File rustPlugin = nativesFile.getParentFile();
        if (!rustPlugin.exists()) {
            rustPlugin.mkdir();
        } else if (!rustPlugin.isDirectory())
            throw new IllegalStateException(pluginName + " in \"plugins/\" is not a directory!");

        if (!nativesFile.exists()) {
            nativesFile.mkdir();
        } else if (!nativesFile.isDirectory())
            throw new IllegalStateException("\"natives\" in plugins/" + pluginName + "/ is not a directory!");
    }

    /**
     * Register the library, later you can load all of those libraries with {@link #load(String)} method.
     * @param libraryName name of the library you want to load. (basically name of file in Jar resources)
     */
    public void registerLibrary(final String libraryName) {
        this.libraries.add(libraryName);
    }

    /**
     * Loads specified library by specified architecture path. It is a path to library in Jar resources with library itself excluded.
     * @param architecturePath path in Jar resources without library itself.
     * @throws IOException Throws when copy operation to the folder happened with an exception.
     */
    public void load(final String architecturePath) throws IOException {
        for (String libraryName : libraries) {
            final Path libraryPath = Path.of("plugins/" + pluginName + "/natives/" + libraryName);
            if (libraryPath.toFile().exists()) {
                System.load(nativesFile.getAbsolutePath() + "/" + libraryName);
                continue;
            }

            final InputStream libraryResourceStream = RustPlugin.class.getResourceAsStream(architecturePath + "/" + libraries);
            if (libraryResourceStream == null)
                throw new IllegalStateException("Architecture path is wrong, or library doesn't exist!");
            Files.copy(
                    libraryResourceStream,
                    libraryPath,
                    StandardCopyOption.REPLACE_EXISTING
            );
            System.load(nativesFile.getAbsolutePath() + "/" + libraryName);
        }
    }
}

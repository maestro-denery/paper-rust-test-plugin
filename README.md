# paper-rust-test-plugin
It is a simple example of Java and Rust Gradle setup for making Paper plugins with JNI. \
It is mainly a copy of [This example](https://github.com/stardust-enterprises/gradle-rust-example) adapted for Paper plugins.

## Details
Output Jar contains native `.so` file (depending on your system) and it extracts it in `plugins/rustplugin/natives` directory and loads 
through `System.load()`. If you have some issues with running, check Jar internals, look at `META-INF` and change `RustPlugin` class variables
to your OS / Architecture etc.

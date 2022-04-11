use jni::JNIEnv;

#[no_mangle]
pub extern "system" fn Java_io_denery_rustplugin_RustPlugin_nativeCall(_env: JNIEnv) {
    println!("Heya from Rust!");
}

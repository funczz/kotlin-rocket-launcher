import com.github.funczz.gradle.plugin.payara_micro.payaraMicro

/**
 * plugins
 */
plugins {
    war
}
apply(plugin = "payara-micro-gradle-plugin")

/**
 * plugin: payara-micro-gradle-plugin
 */
payaraMicro {
    //command line options
    options = "--nocluster --port 8080 --contextroot /".split(" ")
}

/**
 * buildscript
 */
buildscript {
    /**
     * repositories
     */
    repositories {
        maven { setUrl("https://funczz.github.io/payara-micro-gradle-plugin")}
    }
    dependencies {
        classpath("com.github.funczz:payara-micro-gradle-plugin:1.0.0")
    }
}

/**
 * repositories
 */
repositories {
    maven { setUrl("https://funczz.github.io/payara-micro-gradle-plugin") }
}

/**
 * dependencies
 */
dependencies {
    implementation(project(":rocket-launcher-core"))

    //logger
    implementation("ch.qos.logback:logback-classic:1.1.7")
    implementation("org.fusesource.jansi:jansi:1.18")
    implementation("org.slf4j:slf4j-api:1.7.21")
    implementation("org.slf4j:jcl-over-slf4j:1.7.21")

    //payara-micro
    testRuntimeOnly("fish.payara.extras:payara-micro:5.2021.9")

    //jakartaee 8
    "jakarta.platform:jakarta.jakartaee-api:8.0.0".also {
        compileOnly(it)
        testCompileOnly(it)
    }
}

/**
 * Application
 */
object Application {
    const val mainClass = "com.github.funczz.rocket_launcher.entrypoint.MainClass"
}

/**
 * plugins
 */
plugins {
    application
}

/**
 * buildscript
 */
buildscript {
    dependencies {
    }
}

/**
 * dependencies
 */
dependencies {
    implementation(project(":rocket-launcher-core"))
    implementation("com.github.funczz:channel:1.0.0")
}

/**
 * plugin: application
 */
application {
    mainClass.set(Application.mainClass)
}

fun Manifest.setApplicationAttributes() {
    this.apply {
        attributes["Main-Class"] = Application.mainClass
    }
}

val run by tasks.getting(JavaExec::class) {
    if (project.hasProperty("args")) {
        args = (project.property("args") as String).split("""\s+""".toRegex())
    }
}

/**
 * task: jar
 */
val jar by tasks.getting(Jar::class) {
    manifest.setApplicationAttributes()
}

/**
 * task: fatJar
 */
val fatJar = task("fatJar", type = Jar::class) {
    group = "Build"
    description = "Assembles a fat jar archive."
    archiveBaseName.set("${archiveBaseName.get()}-fat")
    manifest.setApplicationAttributes()
    with(tasks["jar"] as CopySpec)
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
}

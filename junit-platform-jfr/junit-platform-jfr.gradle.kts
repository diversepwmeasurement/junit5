plugins {
	id("junitbuild.java-library-conventions")
}

description = "JUnit Platform Flight Recorder Support"

dependencies {
	api(platform(projects.junitBom))
	api(projects.junitPlatformLauncher)

	compileOnlyApi(libs.apiguardian)

	if (java.toolchain.implementation.orNull == JvmImplementation.J9) {
		compileOnly(libs.jfrPolyfill) {
			because("OpenJ9 does not include JFR")
		}
	}

	osgiVerification(projects.junitJupiterEngine)
	osgiVerification(projects.junitPlatformLauncher)
}

javaLibrary {
	// --release 8 does not support jdk.jfr even though it was backported
	configureRelease = false
}

tasks {
	compileJava {
		javaCompiler = project.javaToolchains.compilerFor {
			languageVersion = JavaLanguageVersion.of(8)
		}
	}
	compileModule {
		options.release = 11
	}
}

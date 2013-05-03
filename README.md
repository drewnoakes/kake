kake
====

A Kotlin build tool of spike quality, hurriedly built during a Kotlin workshop in London

## Example

Create a `build.kt` file:

    kake() {

        val sourceFiles = files("src/Test.java")

        val compile = javac(sourceFiles)

        val bundle = jar("build.jar", compile)

        export("bundle" to bundle)

    }

Then from the command line:

    kake bundle

import Kake.*

// TODO use a kotlin script (.kts) to remove the need for a main function

fun main(args: Array<String>) {

    kake(args) {
        val sourceFiles = files("src/test.java")

        val compile = javac(sourceFiles)

        val bundle = jar("build.jar", compile)

        export("bundle" to bundle)
    }

}
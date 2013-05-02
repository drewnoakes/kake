import Kake.kake

fun main(args: Array<String>) {

    kake(args) {
        val sourceFiles = files("src/test.java")

        val compile = javac(sourceFiles)

        val bundle = jar("build.jar", compile)

        export("bundle" to bundle)
    }

}
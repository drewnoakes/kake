import Kake.kake

fun main(args: Array<String>) {


    kake(args) {
//        val userlist = file("userlist")
//
//        val passwd = transformFile(userlist)
//        {
//            outstream.write(....)
//        }

        val sourceFiles = files("src/test.java")

        val compile = javac(sourceFiles)

        val bundle = jar("build.jar", compile)
//        val secondBundle = jar("build2.jar", compile)

//       val all = depend(bundle, test, deploy)

//        defaultExport(all)
//        export("all" to all)
        export("bundle" to bundle)
//        export("secondBundle" to secondBundle)
    }



}
package Kake

import java.util.HashMap
import java.io.BufferedReader
import java.io.InputStreamReader
import Kake.FileSet.FixedFileSet

fun Iterable<String>.join(sep: String = ","): String {
    val sb = StringBuilder()
    for (s in this) {
        if (sb.size != 0)
            sb.append(sep)
        sb.append(s)
    }
    return sb.toString()
}

fun kake(args: Array<String>, userCode: Kake.() -> Unit)
{
    val k = Kake()
    k.userCode()  // provides exports
    k.execute(args) // runs any tasks requested
}

trait FileSet
{
    fun getFiles(): List<String>

    fun withExtension(oldExt: String, newExt: String): FileSet {
        return FixedFileSet(getFiles() map { it.replace(oldExt, newExt) })
    }
    fun toString(sep: String): String {
        return getFiles().join(sep)
    }

    class FixedFileSet(val filename: List<String>): FileSet {
        override fun getFiles(): List<String> = filename
    }
}

trait Outcome
{
    fun execute();
}

class JavaCTask(val sourceFiles: FileSet)
: Outcome,
  FileSet by sourceFiles.withExtension("java", "class")
{
    override fun execute() {
        val process = Runtime.getRuntime().exec("javac ${sourceFiles.toString(" ")}")
        // TODO deal with non-zero return code
        println("javac return code: ${process.waitFor()}")
    }
}

class JarTask(val jarFileName:String, val contentFiles: FileSet) : Outcome
{
    override fun execute() {
        val process = Runtime.getRuntime().exec("jar cf $jarFileName ${contentFiles.toString(" ")}")
        // TODO deal with non-zero return code
        println("jar return code: ${process.waitFor()}")
    }
}

class Kake
{
    private val exports: MutableMap<String, Outcome> = HashMap<String, Outcome>()

    fun export(pair: Pair<String, Outcome>)
    {
        exports.set(pair.first, pair.second)
    }

    fun execute(args: Array<String>)
    {
        // TODO default and checking
        // TODO proper dependency tree walk
        args.forEach { exports.get(it)!!.execute() }
    }

    fun files(vararg filename: String): FileSet
    {
        return FixedFileSet(filename.toList());
    }

    fun javac(sourceFiles: FileSet): JavaCTask
    {
        return JavaCTask(sourceFiles);
    }

    fun jar(jarFileName:String, contentFiles: FileSet): JarTask
    {
        if (contentFiles is Outcome)
            contentFiles.execute()

        return JarTask(jarFileName, contentFiles)
    }
}
import java.util.concurrent.TimeUnit

object Git {

    fun describe(): DescribeResult {
        try {
            ProcessBuilder()
                .command("git", "fetch")
                .start()
        } catch (exception: Exception) {
            throw DescribeException("Unable to run command 'git toggle'", exception)
        }

        val process = try {
            ProcessBuilder()
                .command("git", "describe", "--tags", "--long", "--abbrev=100", "--dirty")
                .start()
        } catch (exception: Exception) {
            throw DescribeException("Unable to run command 'git describe'", exception)
        }

        if (!process.waitFor(1, TimeUnit.SECONDS)) {
            throw DescribeException("Command 'git describe' timed out.")
        }

        if (process.exitValue() != 0) {
            val error = process.errorStream
                .bufferedReader()
                .readText()

            throw DescribeException("Command 'git describe' exited with an error: $error")
        }

        val output = process.inputStream
            .bufferedReader()
            .readText()
            .trim()

        val regex = Regex("""^v?(.*?)-(\d+)-g([0-9a-f]+)(-dirty)?$""")
        val match = regex.matchEntire(output)!!

        val (tag, ahead, hash, dirty) = match.destructured

        return DescribeResult(
            tag = tag,
            commitsAhead = ahead.toInt(),
            commitHash = hash,
            dirty = dirty.isNotEmpty()
        )
    }

    class DescribeException(
        message: String,
        cause: Throwable? = null
    ) : Exception(message, cause)

    data class DescribeResult(
        val tag: String,
        val commitsAhead: Int,
        val commitHash: String,
        val dirty: Boolean
    )
}

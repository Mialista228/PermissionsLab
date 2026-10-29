package uz.ttpu.permissionslab

class PermissionModel {
    private var denials = 0
    var granted = false
        private set

    val rationale get() = !granted && denials == 1
    val dialogShows get() = !granted && denials < 2

    fun request(allow: Boolean): String {
        if (!dialogShows) return "no dialog, denied immediately"
        if (allow) granted = true else denials++
        return "dialog shown, " + if (allow) "ALLOW" else "DENY"
    }
    override fun toString() = "granted=$granted rationale=$rationale"
}

fun main() {
    val camera = PermissionModel()
    println("start: $camera")
    repeat(2) { println(camera.request(allow = false) + " -> $camera") }
    println(camera.request(allow = true))
}

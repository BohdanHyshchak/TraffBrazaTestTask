package com.example.traffbraza

sealed class NavRoute(val path: String) {

    object FirstScreen : NavRoute("first_screen")

    object GameScreen : NavRoute("game_screen")

    object WebViewScreen : NavRoute("web_view_screen") {
        const val urlParams = "urlParams"
    }

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                if (arg.isNotBlank()) {
                    append("/$arg")
                } else {
                    append("/test_campaign") // TODO: remove on production
                }
            }
        }
    }
}

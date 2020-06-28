package br.com.alexandremarcondes.egginc.companion.ui

/**
 * Class defining the screens we have in the app: home, article details and interests
 */
sealed class Screen(val id: ScreenName) {
    object Home : Screen(
        ScreenName.HOME
    )
    object UserList : Screen(
        ScreenName.USER_LIST
    )
    object CoopList : Screen(
        ScreenName.COOP_LIST
    )
    data class User(val userId: String) : Screen(
        ScreenName.USER
    )
    data class Coop(val coopId: String) : Screen(
        ScreenName.COOP
    )
}
package br.com.alexandremarcondes.egginc.companion.data

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val dataRepository: DataRepository
}
package br.com.alexandremarcondes.egginc.companion.data

/**
 * Dependency Injection container at the application level.
 */
interface IAppContainer {
    val dataRepository: DataRepository
    val database: AppDatabase
}
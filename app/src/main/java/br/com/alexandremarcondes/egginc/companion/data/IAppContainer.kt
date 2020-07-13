package br.com.alexandremarcondes.egginc.companion.data

import br.com.alexandremarcondes.egginc.companion.data.model.LegacyContracts

/**
 * Dependency Injection container at the application level.
 */
interface IAppContainer {
    val dataRepository: IDataRepository
    val database: AppDatabase
    val legacyContracts: LegacyContracts
}
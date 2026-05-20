package com.wohnjagd.app.core.di

import com.wohnjagd.app.data.sources.DemoConnector
import com.wohnjagd.app.data.sources.SourceConnector
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

/**
 * Hilt-multibindings для коннекторов источников.
 * Все @Binds @IntoSet функции собираются в Set<SourceConnector>,
 * который инжектится в RefreshSourceUseCase.
 *
 * По мере добавления реальных коннекторов (Kleinanzeigen, Immowelt,
 * Genossenschaften и т.д.) — добавляются новые @Binds @IntoSet в этот модуль.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    @IntoSet
    abstract fun bindDemoConnector(impl: DemoConnector): SourceConnector
}
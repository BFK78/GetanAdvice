package com.example.getanadvice.get_advice_feature.di

import com.example.getanadvice.get_advice_feature.data.network.api.AdviceService
import com.example.getanadvice.get_advice_feature.data.network.api.AdviceServiceImplementation
import com.example.getanadvice.get_advice_feature.data.network.repository.AdviceRepositoryImplementation
import com.example.getanadvice.get_advice_feature.domain.repository.AdviceRepository
import com.example.getanadvice.get_advice_feature.domain.use_cases.GetAnAdviceUserCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import javax.inject.Singleton

@Module
@InstallIn
object AdviceModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }

    @Provides
    @Singleton
    fun provideAdviceService(client: HttpClient): AdviceService {
        return AdviceServiceImplementation(client = client)
    }

    @Provides
    @Singleton
    fun provideAdviceRepository(service: AdviceService): AdviceRepository {
        return AdviceRepositoryImplementation(service = service)
    }

    @Provides
    @Singleton
    fun provideAdviceUseCase(repository: AdviceRepository): GetAnAdviceUserCase {
        return GetAnAdviceUserCase(repository = repository)
    }

}
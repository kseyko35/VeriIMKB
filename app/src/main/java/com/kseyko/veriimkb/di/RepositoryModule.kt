package com.kseyko.veriimkb.di

import com.kseyko.veriimkb.data.repository.ImkbRepository
import com.kseyko.veriimkb.data.repository.ImkbRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      01,December,2021      ║
╚════════════════════════════╝
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideImkbRepo(imkbRepositoryImpl: ImkbRepositoryImpl): ImkbRepository

}
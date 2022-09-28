package com.example.app.driveyourday.di.modules

import com.example.app.driveyourday.data.repository.DriveTimerGroupsRepositoryImpl
import com.example.app.driveyourday.domain.repository.DriveTimerGroupsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsDriveTimerGroupsRepositoryImpl(repository: DriveTimerGroupsRepositoryImpl): DriveTimerGroupsRepository

}
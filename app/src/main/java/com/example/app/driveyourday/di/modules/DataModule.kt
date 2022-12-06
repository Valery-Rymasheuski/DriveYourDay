package com.example.app.driveyourday.di.modules

import com.example.app.driveyourday.data.repository.DriveTimerGroupsRepositoryImpl
import com.example.app.driveyourday.data.repository.DriveTimersRepositoryImpl
import com.example.app.driveyourday.data.repository.login.LoginRepositoryImpl
import com.example.app.driveyourday.domain.repository.DriveTimerGroupsRepository
import com.example.app.driveyourday.domain.repository.DriveTimersRepository
import com.example.app.driveyourday.domain.repository.login.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsDriveTimerGroupsRepositoryImpl(repository: DriveTimerGroupsRepositoryImpl): DriveTimerGroupsRepository

    @Binds
    abstract fun bindsDriveTimerRepository(repository: DriveTimersRepositoryImpl): DriveTimersRepository

    @Binds
    abstract fun bindsLoginRepository(repository: LoginRepositoryImpl): LoginRepository
}
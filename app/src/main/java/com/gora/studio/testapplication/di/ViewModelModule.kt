package com.gora.studio.testapplication.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gora.studio.testapplication.ui.counter.CounterViewModel
import com.gora.studio.testapplication.ui.soundRecorder.SoundRecordingViewModel
import com.gora.studio.testapplication.ui.room.RoomViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(RoomViewModel::class)
    abstract fun bindRoomViewModel(welcomeViewModel: RoomViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CounterViewModel::class)
    abstract fun bindsCounterViewModel(registrationViewModel: CounterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SoundRecordingViewModel::class)
    abstract fun bindSoundRecordingViewModel(soundRecordingViewModel: SoundRecordingViewModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
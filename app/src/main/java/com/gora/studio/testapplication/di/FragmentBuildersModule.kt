package com.gora.studio.testapplication.di

import com.gora.studio.testapplication.ui.counter.CounterFragment
import com.gora.studio.testapplication.ui.soundRecorder.SoundRecordingFragment
import com.gora.studio.testapplication.ui.room.RoomFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSoundRecordingFragment(): SoundRecordingFragment

    @ContributesAndroidInjector
    abstract fun contributeCounterFragment(): CounterFragment

    @ContributesAndroidInjector
    abstract fun contributeRoomFragment(): RoomFragment

}
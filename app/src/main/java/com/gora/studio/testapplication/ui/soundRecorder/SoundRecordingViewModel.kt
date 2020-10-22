package com.gora.studio.testapplication.ui.soundRecorder

import android.media.MediaRecorder
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SoundRecordingViewModel
@Inject constructor(): ViewModel() {
    var mediaRecorder: MediaRecorder? = null
}
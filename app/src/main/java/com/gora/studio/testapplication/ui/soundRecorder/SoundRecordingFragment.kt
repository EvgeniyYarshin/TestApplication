package com.gora.studio.testapplication.ui.soundRecorder

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.gora.studio.testapplication.R
import com.gora.studio.testapplication.api.TestApi
import com.gora.studio.testapplication.di.Injectable
import java.io.File
import javax.inject.Inject


class SoundRecordingFragment: Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var api: TestApi

    private val viewModel: SoundRecordingViewModel by viewModels {
        viewModelFactory
    }


    lateinit var buttonStart: Button
    lateinit var buttonStop: Button
    lateinit var buttonPlay: Button
    lateinit var buttonPause: Button

    lateinit var progressBar: ProgressBar
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_sound_recording, container, false)
        buttonStart = root.findViewById<Button>(R.id.button_start)
        buttonStop = root.findViewById<Button>(R.id.button_stop)
        buttonPlay = root.findViewById<Button>(R.id.button_play)
        buttonPause = root.findViewById<Button>(R.id.button_pause)
        progressBar = root.findViewById(R.id.progress_bar)
        progressBar.visibility = View.INVISIBLE
        buttonStart.setOnClickListener {
            checkPermission(0)
        }
        buttonStop.setOnClickListener {
            checkPermission(1)
        }
        buttonPlay.setOnClickListener {
            checkPermission(2)
        }
        buttonPause.setOnClickListener {
            checkPermission(3)
        }
        return root
    }

    private var mediaPlayer: MediaPlayer? = null
    private var fileName: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fileName = requireContext().filesDir.path + "/record.3gpp"
    }


    private fun recordStart() {
        progressBar.visibility = View.VISIBLE
        buttonPlay.isClickable = false
        buttonPause.isClickable = false
        buttonStop.isClickable = true
        try {
            releaseRecorder()
            val outFile = File(fileName!!)
            if (outFile.exists()) {
                outFile.delete()
            }
            viewModel.mediaRecorder = MediaRecorder()
            viewModel.mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            viewModel.mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            viewModel.mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            viewModel.mediaRecorder!!.setOutputFile(fileName)
            viewModel.mediaRecorder!!.prepare()
            viewModel.mediaRecorder!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun recordStop() {
        buttonPlay.isClickable = true
        buttonPause.isClickable = true
        buttonStop.isClickable = true
        progressBar.visibility = View.INVISIBLE
        try {
            if (viewModel.mediaRecorder != null) {
                viewModel.mediaRecorder!!.stop()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun playStart() {
        buttonStart.isClickable = false
        buttonPause.isClickable = true
        buttonStop.isClickable = false
        try {
            releasePlayer()
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setDataSource(fileName)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun playStop() {
        buttonPlay.isClickable = true
        buttonStop.isClickable = true
        buttonStart.isClickable = true
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
        }
    }

    private fun releaseRecorder() {
        if (viewModel.mediaRecorder != null) {
            viewModel.mediaRecorder!!.release()
            viewModel.mediaRecorder = null
        }
    }

    private fun releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }

    private fun checkPermission(action: Int) {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            val permissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(requireActivity(), permissions,0)
        }
        else {
            when(action) {
                0 -> recordStart()
                1 -> recordStop()
                2 -> playStart()
                3 -> playStop()
            }
        }
    }
}
package com.gora.studio.testapplication.ui.counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.gora.studio.testapplication.R
import com.gora.studio.testapplication.di.Injectable
import javax.inject.Inject

class CounterFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CounterViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_counter, container, false)
        val textView = root.findViewById<TextView>(R.id.text_count)
        textView.text = viewModel.count.toString()
        root.findViewById<Button>(R.id.button_add).setOnClickListener {
            viewModel.count = viewModel.count + 1
            Toast.makeText(requireContext(), viewModel.count.toString(), Toast.LENGTH_SHORT).show()
            textView.text = viewModel.count.toString()
        }
        return root
    }
}
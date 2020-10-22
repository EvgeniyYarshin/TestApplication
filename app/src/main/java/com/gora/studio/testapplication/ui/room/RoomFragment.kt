package com.gora.studio.testapplication.ui.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gora.studio.testapplication.R
import com.gora.studio.testapplication.api.Status
import com.gora.studio.testapplication.di.Injectable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: RoomViewModel by viewModels {
        viewModelFactory
    }

    lateinit var textView: TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_room, container, false)
        textView = root.findViewById(R.id.text_json)
        root.findViewById<Button>(R.id.button_set_json).setOnClickListener {
            viewModel.retrySetJson()
        }

        root.findViewById<Button>(R.id.button_get_json).setOnClickListener {
            viewModel.retryGetJson()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getJson.observe(viewLifecycleOwner, Observer {
            if(it.status == Status.SUCCESS) {
                if(it.data != null) {
                    textView.text = it.data.json
                }
                else {
                    Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
                }
            }
            else if(it.status == Status.ERROR) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.setJson.observe(viewLifecycleOwner, Observer {
            if(it.status == Status.ERROR) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
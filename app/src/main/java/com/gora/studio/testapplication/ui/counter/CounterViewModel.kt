package com.gora.studio.testapplication.ui.counter

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class CounterViewModel
@Inject constructor(): ViewModel() {
   var count = 0
}
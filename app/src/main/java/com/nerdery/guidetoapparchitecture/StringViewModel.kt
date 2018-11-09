package com.nerdery.guidetoapparchitecture

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel

class StringViewModel: ViewModel() {

    fun setStringValue(string: String) {
        StringRepository.string.value = string
    }

    fun subscribeToStringChanges(activity: LifecycleOwner, onStringChange: (String?) -> Unit) {
        val stringObserver = Observer<String> { newString ->
            onStringChange(newString)
        }

        StringRepository.string.observe(activity, stringObserver)
    }
}
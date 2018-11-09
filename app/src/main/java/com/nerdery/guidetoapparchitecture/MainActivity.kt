package com.nerdery.guidetoapparchitecture

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var stringViewModel: StringViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        stringViewModel = ViewModelProviders.of(this).get(StringViewModel::class.java)

        stringViewModel.subscribeToStringChanges(this) { newString ->
            characterCountView.text = newString?.length.toString()
        }

        stringViewModel.subscribeToStringChanges(this) { newString ->
            oddEvenView.text = if (newString?.length?.rem(2) == 0) "even" else "odd"
        }

        editTextView.addTextChangedListener(Watcher())
    }

    private inner class Watcher: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            stringViewModel.setStringValue(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }
}

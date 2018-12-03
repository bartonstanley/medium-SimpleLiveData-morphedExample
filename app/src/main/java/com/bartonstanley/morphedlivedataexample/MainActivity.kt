package com.bartonstanley.morphedlivedataexample

import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity that allows the user to enter a string and to immediately see the length of the string as each character is
 * entered (or deleted).
 *
 * This version of this Activity uses a View Model that publishes the length of the string each time it changes.
 */
class MainActivity : AppCompatActivity() {


    private lateinit var stringViewModel: StringViewModel  // Initialized in onCreate()

    /**
     * Subscribe to changes in the length of the string and request user input events for the string.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Create an instance of our view model to be used here and in the Watcher.
        stringViewModel = ViewModelProviders.of(this).get(StringViewModel::class.java)

        // Subscribe to changes in string length
        stringViewModel.subscribeToLengthChanges(this) { newLength ->

            // New length published by view model.  Populate the layout with it.
            characterCountView.text = newLength
        }

        // Request user input events
        editTextView.addTextChangedListener(Watcher())
    }

    /**
     * TextWatcher that reports user edits to the view model.
     */
    private inner class Watcher: TextWatcher {
        override fun afterTextChanged(s: Editable?) {

            // Report the new string to the view model.  The view model will in turn publish the length of this string.
            stringViewModel.setStringValue(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Not needed.
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Not needed.
        }
    }
}

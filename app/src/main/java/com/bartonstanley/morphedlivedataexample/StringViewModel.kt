package com.bartonstanley.morphedlivedataexample

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

/**
 * ViewModel for the string that the user enters.
 *
 * Allows the Repository string value to be updated and for publishing changes in the length of the string to subscribers.
 */
class StringViewModel: ViewModel() {

    /**
     * Update the value of the string in the Repository.
     *
     * @param string new value of string to be placed in the Repository.
     */
    fun setString(string: String) {
        StringRepository.setString(string)
    }

    /**
     * Request to be notified of changes in the length of the string.
     *
     * Subscription will remain active for as long as the life cycle owner exists.  Subscription will be paused when
     * the life cycle owner is paused and will be canceled when the life cycle owner is destroyed.
     *
     * @param lifecycleOwner the Activity or Fragment whose life cycle will govern the life cycle of this subscription.
     * @param onLengthChange lambda that will be invoked once with the initial length of the string and thereafter
     *        will be invoked when the length of the string changes.  Its parameter is the current length of the string.
     */
    fun subscribeToLengthChanges(lifecycleOwner: LifecycleOwner, onLengthChange: (String) -> Unit) {

        // Observer that will publish the new length of the string to subscribers when the string changes
        val lengthObserver = Observer<String> { newString ->
            onLengthChange(newString.length.toString())
        }

        // Attach observer to LiveData object in Repository
        StringRepository.observableString.observe(lifecycleOwner, lengthObserver)

        // publish initial length to subscribers
        onLengthChange(StringRepository.string?:"".length.toString())
    }
}
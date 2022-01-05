package com.example.beerappkoltin.core.commons

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun setVisibilidadeVisible(tornarViewsVisible: Boolean?, vararg ets: View?) {
    for (et in ets) {
        et?.visibility = if (tornarViewsVisible == true) View.VISIBLE else View.GONE
    }
}

fun setVisibilidadeVisibleView(et: View?, value: Boolean?) = setVisibilidadeVisible(value, et)

fun <T> LiveData<T>.handleObserver(viewLifecycle: LifecycleOwner, action: (T) -> Unit) =
    this.observe(viewLifecycle, {
        action.invoke(it)
    })

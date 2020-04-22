package com.arthur.marvelapp.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.AnimRes
import com.arthur.marvelapp.R
import com.google.android.material.textfield.TextInputEditText

/**
 * Arquivo kotlin responsável por funções utilitárias por toda a aplicação
 * Utilizando as Extensions Functions.
 * */

/*
* Método genérico para todas as classes hedeiras de Activity
* */
inline fun <reified T : Activity> Context.openActivity(
    options: Bundle? = null,
    finishWhenOpen: Boolean = false,
    @AnimRes enterAnim: Int = R.anim.anim_fade_in,
    @AnimRes exitAnim: Int = R.anim.anim_fade_out,
    noinline f: Intent.() -> Unit = {}
) {

    val intent = Intent(this, T::class.java)
    intent.f()

    startActivity(intent, options)

    if (finishWhenOpen) (this as Activity).finish()
    (this as Activity).overridePendingTransition(enterAnim, exitAnim)
}

/**
 * Método que valida se o Edit Text está null ou vazio
 * */
fun TextInputEditText.isValid(): Boolean {
    return text.isNullOrEmpty()
}

/**
 * Método que seta uma margin (Top,Bottom,Left ou Right) programaticamente
 * */
fun View.setMargin(
    leftMargin: Int? = null, topMargin: Int? = null,
    rightMargin: Int? = null, bottomMargin: Int? = null
) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(
        leftMargin ?: params.leftMargin,
        topMargin ?: params.topMargin,
        rightMargin ?: params.rightMargin,
        bottomMargin ?: params.bottomMargin
    )
    layoutParams = params
}

/**
 * Método que pega a opção após a escrita.
 * */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

/**
 * Método que seta a visibilidade de uma View.
 * */
fun View.setVisible(visible: Boolean) {
    visibility = if (visible)
        View.VISIBLE
    else
        View.GONE
}


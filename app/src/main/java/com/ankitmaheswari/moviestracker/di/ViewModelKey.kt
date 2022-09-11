package com.ankitmaheswari.moviestracker.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by Ankit Maheswari on 11/09/22.
 */

@MapKey
@Target(AnnotationTarget.FUNCTION)
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
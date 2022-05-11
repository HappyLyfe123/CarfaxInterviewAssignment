package com.carfax.library_ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

val Fragment.viewLifecycleScope
    get() = viewLifecycleOwner.lifecycleScope
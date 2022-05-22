package com.carfax.library_ui

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

val Fragment.viewLifecycleScope
    get() = viewLifecycleOwner.lifecycleScope
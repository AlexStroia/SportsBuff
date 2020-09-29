package com.buffup.app.feature

import com.buffup.app.R
import com.buffup.app.core.SportsBuffError
import com.buffup.app.shared.autoCleared


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


typealias OnSnackbarDismissed = (dismissEvent: Int) -> Unit

abstract class SportsBuffFragment<VB : ViewDataBinding, VM : ViewModel>(@LayoutRes private val layoutResourceId: Int) : Fragment() {

    protected var binding by autoCleared<VB>()
    protected abstract val viewModel: VM?

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        DataBindingUtil.inflate<VB>(inflater, layoutResourceId, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.setVariable(BR.viewModel, viewModel)
            binding = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // There are multiple glitches throughout the app where insets wouldn't get applied and the UI elements wouldn't be positioned properly. I'm not
        // sure why this is happening, but until we investigate, this solves the issue:
        with(binding.root) { post { requestLayout() } }
    }

    protected fun showSnackBar(
        @StringRes textRes: Int,
        duration: Int = Snackbar.LENGTH_LONG,
        anchorView: View? = null,
        dismissCallback: OnSnackbarDismissed? = null
    ) {
        showSnackBar(getString(textRes), duration, anchorView, dismissCallback)
    }

    protected fun showSnackBar(text: String, duration: Int = Snackbar.LENGTH_LONG, anchorView: View? = null, dismissCallback: OnSnackbarDismissed? = null) {
        Snackbar.make(binding.root, text, duration).apply {
            anchorView?.let { setAnchorView(it) }
            dismissCallback?.let {
                addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    override fun onDismissed(transientBottomBar: Snackbar?, @DismissEvent event: Int) {
                        dismissCallback(event)
                    }
                })
            }
        }.show()
    }

    protected inline fun showSnackBar(text: String, @StringRes actionRes: Int = R.string.retry, anchorView: View? = null, crossinline action: () -> Unit) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).setAction(actionRes) { action() }.setAnchorView(anchorView).show()
    }

    protected fun handleError(error: SportsBuffError, anchorView: View? = null, action: (() -> Unit)? = null) {
        val errorMessage = when (error) {
            is SportsBuffError.NoInternet -> getString(R.string.no_internet_error)
            is SportsBuffError.Unknown -> getString(R.string.unknown_error)
            is SportsBuffError.Api -> error.message
        }
        if (action != null) showSnackBar(errorMessage, anchorView = anchorView, action = action) else showSnackBar(errorMessage, anchorView = anchorView)
    }
}
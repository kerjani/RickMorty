package com.kernacs.rickmorty.base

import androidx.annotation.CallSuper
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics


abstract class BaseFragment : Fragment() {

    abstract val trackedScreenName: String

    @CallSuper
    override fun onResume() {
        super.onResume()
        FirebaseAnalytics.getInstance(requireContext()).logEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            bundleOf(FirebaseAnalytics.Param.SCREEN_NAME to trackedScreenName)
        )
    }
}
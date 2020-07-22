package br.com.alexandremarcondes.egginc.companion.util

import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.util.Size
import android.view.WindowInsets
import android.view.WindowManager

fun getDimensions(windowManager: WindowManager): Size {
    val windowSize: Size

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val metrics = windowManager.currentWindowMetrics

        // Gets all excluding insets
        val windowInsets: WindowInsets = metrics.windowInsets
        var insets = windowInsets.getInsets(WindowInsets.Type.navigationBars())
        val cutout = windowInsets.displayCutout

        if (cutout != null) {
            val cutoutSafeInsets = Insets.of(
                cutout.safeInsetLeft,
                cutout.safeInsetTop,
                cutout.safeInsetRight,
                cutout.safeInsetBottom
            )

            insets = Insets.max(insets, cutoutSafeInsets)
        }

        val insetsWidth = insets.right + insets.left
        val insetsHeight = insets.top + insets.bottom

        // Legacy size that Display#getSize reports
        windowSize = Size(
            metrics.bounds.width() - insetsWidth,
            metrics.bounds.height() - insetsHeight
        )
    } else {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        windowSize = Size(
            displayMetrics.widthPixels,
            displayMetrics.heightPixels
        )
    }
    return windowSize
}
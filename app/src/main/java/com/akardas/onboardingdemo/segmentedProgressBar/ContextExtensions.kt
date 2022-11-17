package com.akardas.onboardingdemo.segmentedProgressBar

import android.content.Context

fun Context.dp(valueInDp: Int): Int = (valueInDp * resources.displayMetrics.density).toInt()

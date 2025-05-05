package com.malabar.malabarmoviesapp.ui.details.video

import android.R.attr.onClick
import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.malabar.core.R
import com.malabar.core.ui.CommonToolbarBackNav
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun PlayYTVideo(
    id: String?,
    navController: NavController
) {
    var isFullScreen by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = context as Activity

    Column(modifier = if (isFullScreen) Modifier.fillMaxSize() else Modifier.fillMaxWidth()) {

        if (!isFullScreen) {
            CommonToolbarBackNav(
                title = stringResource(R.string.video),
                onClick = { navController.popBackStack() },
                navController
            )
        }
        if (isFullScreen) {
            FullScreenDialog(onDismiss = { isFullScreen = false }) {
                YoutubePlayerCompose(videoId = id, onFullScreenChange = { isFullScreen = it })
            }
        } else {
            YoutubePlayerCompose(videoId = id, onFullScreenChange = { isFullScreen = it })
        }
    }
}

@Composable
fun YoutubePlayerCompose(videoId: String?, onFullScreenChange: (Boolean) -> Unit) {
    val context = LocalContext.current
    val activity = context as Activity
    var youTubePlayerView: YouTubePlayerView? = null

    val lifecycleOwner = LocalLifecycleOwner.current
    var youTubePlayer: YouTubePlayer? = null

    AndroidView(
        factory = { context ->
            youTubePlayerView = YouTubePlayerView(context).apply {
                enableAutomaticInitialization = false
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            youTubePlayerView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(player: YouTubePlayer) {
                    youTubePlayer = player
                    if (videoId != null) {
                        player.loadVideo(videoId, 0f)
                    }
                }
                override fun onStateChange(player: YouTubePlayer, state: PlayerConstants.PlayerState) {
                    super.onStateChange(player, state)
                    Log.i("State changed", state.name)
                }
            })
            youTubePlayerView.addFullscreenListener(object : FullscreenListener {
                override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                    onFullScreenChange(true)
                    val decorView = activity.window.decorView as FrameLayout
                    decorView.addView(fullscreenView)
                    activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }

                override fun onExitFullscreen() {
                    onFullScreenChange(false)
                    val decorView = activity.window.decorView as FrameLayout
                    decorView.removeAllViews()
                    activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
            })
            youTubePlayerView
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f),
        update = {
            it.enableAutomaticInitialization = true
            lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
                override fun onStateChanged(source: androidx.lifecycle.LifecycleOwner, event: Lifecycle.Event) {
                    when (event) {
                        Lifecycle.Event.ON_DESTROY -> it.release()
                        else -> {}
                    }
                }
            })
        }
    )
    LaunchedEffect(key1 = youTubePlayerView) {
        youTubePlayerView?.let { view ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        }
    }
    DisposableEffect(key1 = lifecycleOwner) {
        onDispose {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}
@Composable
fun FullScreenDialog(onDismiss: () -> Unit, content: @Composable () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        content()
    }
}
import kotlinx.browser.window
import kotlinx.coroutines.*
import react.*
import react.dom.div
import react.dom.h1
import react.dom.h3

@JsExport
class App : RComponent<RProps, AppState>() {
    override fun AppState.init() {
        unwatchedVideos = listOf()
        watchedVideos = listOf()

        val mainScope = MainScope()
        mainScope.launch {
            val videos = fetchVideos()
            setState{
                unwatchedVideos = videos
            }
        }
    }

    override fun RBuilder.render() {
        h1 {
            +"Kotlin Explorer"
        }
        div {
            h3 {
                +"Videos to watch"
            }
            // Using  lambdas with receivers implementation, try to use it in the future
            videoList {
                videos = state.unwatchedVideos
                selectedVideo = state.currentVideo
                onSelectedVideo = { video ->
                    setState {
                        currentVideo = video
                    }
                }
            }
            h3 {
                +"Videos watched"
            }
            // normal implementation
            child(VideoList::class) {
                attrs.videos = state.watchedVideos
                attrs.selectedVideo = state.currentVideo
                attrs.onSelectedVideo = { video ->
                    setState{
                        currentVideo = video
                    }
                }
            }
        }
        // the video player will only show if there is a video selected
        state.currentVideo?.let { currentVideo ->
            videoPlayer {
                video = currentVideo
                unwatchedVideo = currentVideo in state.unwatchedVideos
                onWatchedButtonPressed = {
                    if (video in state.unwatchedVideos){
                        setState {
                            unwatchedVideos -= video
                            watchedVideos += video
                        }
                    } else {
                        setState {
                            watchedVideos -= video
                            unwatchedVideos += video
                        }
                    }
                }
            }
        }
    }
}

suspend fun fetchVideos(): List<Video> = coroutineScope {
    (1..25).map { id ->
        async {
            fetchVideo(id)
        }
    }.awaitAll()
}

suspend fun fetchVideo(id: Int): Video {
    val response = window
        .fetch("https://my-json-server.typicode.com/kotlin-hands-on/kotlinconf-json/videos/$id")
        .await()
        .json()
        .await()
    return response as Video
}

// extension function of  lambdas with receivers implementation
fun RBuilder.videoList(handler: VideoListProps.() -> Unit): ReactElement {
    return child(VideoList::class) {
        this.attrs(handler)
    }
}

external interface AppState : RState {
    var currentVideo: Video?
    var unwatchedVideos: List<Video>
    var watchedVideos: List<Video>
}
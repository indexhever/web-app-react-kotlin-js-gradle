import react.*
import react.dom.div
import react.dom.h1
import react.dom.h3

@JsExport
class App : RComponent<RProps, AppState>() {
    override fun AppState.init() {
        unwatchedVideos = listOf(
            KotlinVideo(1, "Building and breaking things", "John Doe", "https://youtu.be/PsaFVLr8t4E"),
            KotlinVideo(2, "The development process", "Jane Smith", "https://youtu.be/PsaFVLr8t4E"),
            KotlinVideo(3, "The Web 7.0", "Matt Miller", "https://youtu.be/PsaFVLr8t4E")
        )
        watchedVideos = listOf(
            KotlinVideo(4, "Mouseless development", "Tom Jerry", "https://youtu.be/PsaFVLr8t4E")
        )
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
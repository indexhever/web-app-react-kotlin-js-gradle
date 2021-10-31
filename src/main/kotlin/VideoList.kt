import kotlinx.browser.window
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.p

@JsExport
class VideoList : RComponent<VideoListProps, VideoListState>() {
    override fun RBuilder.render() {
        for (video in props.videos){
            p {
                key = video.id.toString()
                attrs {
                    // Defining an onClickFunction directly as a lambda is concise and very useful for
                    // prototyping. However, due to the way equality currently works in Kotlin/JS, it is
                    // not the most performance-optimized way of passing click handlers. If you want to
                    // optimize rendering performance, consider storing your functions in a variable and
                    // passing them.
                    onClickFunction = {
                        setState {
                            selectedVideo = video
                        }
                    }
                }
                if (video == state.selectedVideo){
                    +"▶ "
                }
                +"${video.speaker}: ${video.title}"
            }
        }
    }
}

external interface VideoListProps: RProps {
    var videos: List<Video>
}

external interface VideoListState: RState {
    var selectedVideo: Video?
}
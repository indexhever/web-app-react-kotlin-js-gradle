import kotlinx.css.*
import react.*
import react.dom.div
import react.dom.h1
import react.dom.h3
import react.dom.img
import styled.css
import styled.styledDiv

@JsExport
class App : RComponent<RProps, AppState>() {
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
                videos = unwatchedVideos
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
                attrs.videos = watchedVideos
                attrs.selectedVideo = state.currentVideo
                attrs.onSelectedVideo = { video ->
                    setState{
                        currentVideo = video
                    }
                }
            }
        }
        styledDiv {
            css {
                position = Position.absolute
                top = 10.px
                right = 10.px
            }
            h3 {
                +"John Doe: Building and breaking things"
            }
            img {
                attrs {
                    src = "https://via.placeholder.com/640x360.png?text=Video+Player+Placeholder"
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
}
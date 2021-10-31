import kotlinx.browser.document
import kotlinx.css.*
import react.RBuilder
import react.ReactElement
import react.dom.*
import styled.css
import styled.styledDiv

val unwatchedVideos = listOf(
    KotlinVideo(1, "Building and breaking things", "John Doe", "https://youtu.be/PsaFVLr8t4E"),
    KotlinVideo(2, "The development process", "Jane Smith", "https://youtu.be/PsaFVLr8t4E"),
    KotlinVideo(3, "The Web 7.0", "Matt Miller", "https://youtu.be/PsaFVLr8t4E")
)

val watchedVideos = listOf(
    KotlinVideo(4, "Mouseless development", "Tom Jerry", "https://youtu.be/PsaFVLr8t4E"),
    KotlinVideo(5, "Testando novo título", "Heverton Sarah", "https://youtu.be/PsaFVLr8t4E")
)

fun main() {
    render(document.getElementById("root")){
        child(App::class) {}
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
            }
            h3 {
                +"Videos watched"
            }
            // normal implementation
            child(VideoList::class) {
                attrs.videos = watchedVideos
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
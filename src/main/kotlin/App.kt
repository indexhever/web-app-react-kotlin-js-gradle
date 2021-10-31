import react.RBuilder
import react.RComponent
import react.RProps
import react.RState

@JsExport
class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        // typesafe HTML goes here!
    }
}
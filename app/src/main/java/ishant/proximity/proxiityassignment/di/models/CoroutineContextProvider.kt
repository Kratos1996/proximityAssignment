package ishant.proximity.proxiityassignment.di.models

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class CoroutineContextProvider internal constructor() {

    val Main: CoroutineContext get() = Dispatchers.Main

    val IO: CoroutineContext get() = Dispatchers.IO

    val Default: CoroutineContext get() = Dispatchers.Default
}

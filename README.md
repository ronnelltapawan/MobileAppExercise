Android app that uses the Flickr image search API and shows the results in a list

## Libraries/Tools
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [Coroutines](https://developer.android.com/kotlin/coroutines)
- [Retrofit](https://square.github.io/retrofit/)
- [OkHttp](https://square.github.io/okhttp/)
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Coil](https://coil-kt.github.io/coil/)
- [ListAdapter](https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter)
- [View Binding](https://developer.android.com/topic/libraries/view-binding)

## API
- [Flickr](https://www.flickr.com/)

## Additional Notes
* This uses MVVM Architecture
* This supports pagination via a callback interface; a proper paging library would have been better but I don't know how to actually implement it
* This has both instrumented and unit tests
* This supports dark mode


## Sample example how `FlutterView` in a android activity can be used

![alt text](https://raw.githubusercontent.com/Kassan424kh/flutterviewInAndroidActivity/master/Designs/FlutterViewInAndroidActivity.png)

### Finded info locations:
  1. https://github.com/flutter/flutter/tree/master/examples/flutter_view
  2. https://github.com/flutter/flutter/wiki/Experimental:-Launch-Flutter-with-non-main-entrypoint


### How do it?
  1. Get open the `Flutter` project.
  2. Open the `main.dart` file and write inside it the function that your would use to show on the android activity.
  ```DART
    @pragma('vm:entry-point') // say the release that this function must alos in the project used and it must not deleted
    void myMainDartMethod() => runApp(MyApp()); // I use this function to show in the android activity

    class MyApp extends StatelessWidget {
      @override
      Widget build(BuildContext context) {
        return MaterialApp(
          home: Scaffold(
            body: Container(height: 100, width: 100, color: Colors.red),
          ),
        );
      }
    }
 ```
  3. Open the android folder in new window in AndroidStudio to compile it.
  4. Check if you'r android project to androidx migrated, if not migrate it 
  https://flutter.dev/docs/development/androidx-migration, alse would this not woking for you.
  5. After compilation is finished, open the `app/build.gradle` and inside the dependencies block insert this library
  and click then on `sync now` button on the right top in the yellow block.
    ```GRADLE
        implementation 'androidx.appcompat:appcompat:1.1.0'
    ```
  6. Now create a new KotlinActivity "you can use Java I would use Kotlin in this case because it is easier to read"
     and inside it write this code
  
   ```KOTLIN
    package com.flutterview.test.flutterview_app

    import android.content.Intent
    import android.os.Bundle
    import androidx.appcompat.app.AppCompatActivity
    import io.flutter.embedding.android.FlutterView
    import io.flutter.embedding.engine.FlutterEngine
    import io.flutter.embedding.engine.dart.DartExecutor.DartEntrypoint
    import io.flutter.view.FlutterMain
    import java.util.ArrayList

    class ExampleActivity : AppCompatActivity() {

        private var flutterView: FlutterView? = null

        // to get and check returned intent
        private fun getArgsFromIntent(intent: Intent): Array<String>? {
            // Before adding more entries to this list, consider that arbitrary
            // Android applications can generate intents with extra data and that
            // there are many security-sensitive args in the binary.
            val args = ArrayList<String>()
            if (intent.getBooleanExtra("trace-startup", false)) {
                args.add("--trace-startup")
            }
            if (intent.getBooleanExtra("start-paused", false)) {
                args.add("--start-paused")
            }
            if (intent.getBooleanExtra("enable-dart-profiling", false)) {
                args.add("--enable-dart-profiling")
            }
            if (!args.isEmpty()) {
                return args.toTypedArray()
            }
            return null
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            val args = getArgsFromIntent(intent)

            // check if flutterEngine is null
            if (flutterEngine == null) {
                println(args)
                flutterEngine = FlutterEngine(this, args)
                flutterEngine!!.dartExecutor.executeDartEntrypoint(
                        // set which of dart methode will be used here
                        DartEntrypoint(FlutterMain.findAppBundlePath(),"myMainDartMethod")
                        // to set here the main methode you can use this function to do this
                        // inteade of DartEntrypoint(FlutterMain.findAppBundlePath(),"myMainDartMethod")
                        // write this mdethode DartEntrypoint.createDefault()
                )
            }

            setContentView(R.layout.flutter_view_layout) // <- the layout that i use to show the flutterActivity inside it

            flutterView = findViewById(R.id.flutter_view)
            flutterView!!.attachToFlutterEngine(flutterEngine!!)
        }

        // hire will be tested if the channel lifecycle is resumed
        override fun onResume() {
            super.onResume()
            flutterEngine!!.lifecycleChannel.appIsResumed()
        }

        // hire will be tested if the channel lifecycle is paused
        override fun onPause() {
            super.onPause()
            flutterEngine!!.lifecycleChannel.appIsInactive()
        }

        // hire will be tested if the channel lifecycle is stoped
        override fun onStop() {
            super.onStop()
            flutterEngine!!.lifecycleChannel.appIsPaused()
        }

        // hire will be tested if the channel lifecycle is destroied
        override fun onDestroy() {
            flutterView!!.detachFromFlutterEngine()
            super.onDestroy()
        }

        companion object {
            // 
            private var flutterEngine: FlutterEngine? = null
        }
    }
   ```
  7. In this step create your oun `layout` how you would show the flutterActivity in this android Activity, as example:
  
  ```XML
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      android:orientation="vertical"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      >

      <io.flutter.embedding.android.FlutterView
        android:id="@+id/flutter_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        />
    </LinearLayout>
  ```
  8. In the `app/main/AndroidMainifest.xml` set `@style/Theme.AppCompat` instead of `@style/FooBar`
  9. Start the app and look if all things work's fine.
  10. if: 
        🎉
      else:
        write me a message or 😅 search on google after the problem.
        My Mail address is -> dev.khalilkhalil@gmail.com

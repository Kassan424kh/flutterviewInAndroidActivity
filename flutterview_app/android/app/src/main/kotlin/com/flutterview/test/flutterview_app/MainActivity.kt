package com.flutterview.test.flutterview_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor.DartEntrypoint
import io.flutter.view.FlutterMain
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

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
            )
        }

        // check if flutterEngine2 is null
        if (flutterEngine2 == null) {
            println(args)
            flutterEngine2 = FlutterEngine(this, args)
            flutterEngine2!!.dartExecutor.executeDartEntrypoint(
                    // set which of dart methode will be used here
                    DartEntrypoint(FlutterMain.findAppBundlePath(),"myMainDartMethod2")
            )
        }

        setContentView(R.layout.flutter_view_layout)


        flutterView = findViewById(R.id.flutter_view)
        flutterView!!.attachToFlutterEngine(flutterEngine!!)

        flutterView = findViewById(R.id.flutter_view2)
        flutterView!!.attachToFlutterEngine(flutterEngine2!!)

    }

    override fun onResume() {
        super.onResume()
        flutterEngine!!.lifecycleChannel.appIsResumed()
    }

    override fun onPause() {
        super.onPause()
        flutterEngine!!.lifecycleChannel.appIsInactive()
    }

    override fun onStop() {
        super.onStop()
        flutterEngine!!.lifecycleChannel.appIsPaused()
    }

    override fun onDestroy() {
        flutterView!!.detachFromFlutterEngine()
        super.onDestroy()
    }

    companion object {
        private var flutterEngine: FlutterEngine? = null
        private var flutterEngine2: FlutterEngine? = null
    }
}

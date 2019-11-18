import 'package:flutter/material.dart';

@pragma('vm:entry-point') // to get the project not crashes after release, and say it that this method will be used
void myMainDartMethod() => runApp(MyApp2());

class MyApp2 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Container(height: 100, width: 100, color: Colors.red),
      ),
    );
  }
}

@pragma('vm:entry-point')
void myMainDartMethod2() => runApp(MyApp3());

class MyApp3 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Container(height: 100, width: 100, color: Colors.green),
      ),
    );
  }
}
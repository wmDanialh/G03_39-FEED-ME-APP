import 'package:feedmeapp1/src/pages/home_page.dart';
import 'package:feedmeapp1/src/screen/main_screen.dart';
import 'package:flutter/material.dart';


class App extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: "Food Delivery App",
      theme: ThemeData(
          primaryColor: Colors.blueAccent
      ),
      home: MainScreen(),
    );

  }

}
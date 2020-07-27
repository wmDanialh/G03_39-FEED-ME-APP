import 'package:flutter/material.dart';

class BoughtFood extends StatefulWidget{
  @override
  _BoughtFoodState createState() => _BoughtFoodState();
}

class _BoughtFoodState extends State<BoughtFood>{
  @override
  Widget build(BuildContext context) {
    return Stack(
      children: <Widget>[
        Container(
        height:200.0,
        width:340.0,
        child: Image.asset('assets/images/breakfast.jpeg'),
        ),
      ],
    );
  }
}
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class ExplorePage extends StatefulWidget{
  @override
  _ExplorePageState createState() => _ExplorePageState();

}

class _ExplorePageState extends State<ExplorePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        padding: EdgeInsets.symmetric(horizontal: 16.0, vertical: 50.0),
        child: Column(
          children: <Widget>[
            Text("All Food D Items", style: TextStyle(
              fontSize: 20.0,
              fontWeight: FontWeight.bold,
            ),),
            Card(
              margin: EdgeInsets.symmetric(vertical: 10.0),
              ),
          ],
        ),
      ),
    );
  }
}
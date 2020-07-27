import 'package:flutter/material.dart';


class HomeTopInfo extends StatelessWidget {

  final textstyle = TextStyle(fontSize: 32.0,fontWeight: FontWeight.bold);

  @override
  Widget build(BuildContext context) {
    return Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Text("What would", style: textstyle ),
                  Text("you like to eat?",style: textstyle)
                ],
              ),
              Icon(Icons.notifications_none, size: 50.0,color: Theme.of(context).primaryColor)
            ],
          );
  }
}


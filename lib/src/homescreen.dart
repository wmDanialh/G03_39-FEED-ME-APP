import 'package:feedmeapp1/widgets/bought_foods.dart';
import 'package:feedmeapp1/widgets/food_category.dart';
import 'package:flutter/material.dart';
import 'package:feedmeapp1/widgets/bought_foods.dart';
import 'package:feedmeapp1/widgets/home_top_info.dart';
import 'package:feedmeapp1/widgets/search_field.dart';


class HomeScreen extends StatefulWidget{
  @override
  _HomeScreenState createState() => _HomeScreenState();

}

class _HomeScreenState extends State<HomeScreen>{
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: ListView(
        padding: EdgeInsets.only(top: 50.0, left: 20.0, right: 20.0),
        children: <Widget>[
          HomeTopInfo(),
          FoodCategory(),
          SizedBox(height: 20.0,),
          SearchField(),
          SizedBox(height: 20.0,),
          Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: <Widget>[
          Text(
            "Frequently Bought Foods",
            style: TextStyle(
              fontSize: 18.0,
              fontWeight: FontWeight.bold,
              ),
            ),
      GestureDetector(
        onTap: (){},
        child: Text(
          "View All",
          style: TextStyle(
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            color: Colors.orangeAccent,
                ),
               ),
              ),
            ],
          ),
          Container(
            child: BoughtFood(),
          )
        ],
      ),
    );
  }
}


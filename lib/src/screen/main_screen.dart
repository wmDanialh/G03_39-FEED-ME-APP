import 'package:feedmeapp1/src/scoped-model/food_model.dart';
import 'package:flutter/material.dart';
import 'package:feedmeapp1/src/pages/home_page.dart';
import 'package:feedmeapp1/src/pages/order_page.dart';
import 'package:feedmeapp1/src/pages/profile_page.dart';
import 'package:feedmeapp1/src/pages/favourite_page.dart';

class MainScreen extends StatefulWidget{

  final FoodModel foodModel;

  MainScreen({this.foodModel});

  @override
  _MainScreenState createState() => _MainScreenState();
  }

class _MainScreenState extends State<MainScreen> {

  HomePage homePage;
  OrderPage orderPage;
  FavouritePage favouritePage;
  ProfilePage profilePage;

  int currentTabIndex = 0;

  List<Widget> pages;
  Widget currentPage;

  @override
  void initState() {

    //call the fetch method on food
    widget.foodModel.fetchFoods();

    homePage = HomePage();
    favouritePage = FavouritePage();
    orderPage = OrderPage();
    profilePage = ProfilePage();
    pages = [homePage,favouritePage,orderPage,profilePage];

    currentPage = homePage;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
   return Scaffold(
     bottomNavigationBar: BottomNavigationBar(
       onTap: (int index){
         setState(() {
           currentTabIndex = index;
           currentPage = pages[index];
         });
       },
       currentIndex: currentTabIndex,
       type: BottomNavigationBarType.fixed,
       items: <BottomNavigationBarItem>[
         BottomNavigationBarItem(
           icon: Icon(Icons.home),
           title: Text("Home")
         ),
         BottomNavigationBarItem(
             icon: Icon(Icons.explore),
             title: Text("Explore")
         ),
         BottomNavigationBarItem(
           icon: Icon(Icons.shopping_cart),
           title: Text("Orders")
         ),
         BottomNavigationBarItem(
             icon: Icon(Icons.person),
             title: Text("Profile")
         ),
       ],
     ),
     body: currentPage,
    // appBar: currentTabIndex == 1?AppBar(
    //title: Text("Your food cart", style: TextStyle(color: Colors.black),),
    //centerTitle: true,
    //backgroundColor: Colors.white,
    //elevation: 0.0,

   );
  }
}


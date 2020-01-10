package com.example.coffeeordering.Admin

class Order(var user_id:String, var orderid: String, var name:String, var telp:String, var alamat: String, var total:String)
{
    //empty constrcutor
    constructor() : this("","","","","",""){

    }

}
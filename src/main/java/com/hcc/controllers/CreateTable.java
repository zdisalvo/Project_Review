//package com.hcc.controllers;
//
//import java.sql.Connection;
//import java.sql.Statement;
//
//public class CreateTable {
//
//    public static void main(String[] args) {
//
//        Connection connection = null;
//        Statement statement = null;
//
//        ConnectDB obj_ConnectDB = new ConnectDB();
//
//        connection = obj_ConnectDB.get_connection();
//
//        try {
//
//            String query="insert into greetings(Id, greeting_message) values(232, 'hello')";
//            statement = connection.createStatement();
//            statement.executeUpdate(query);
//            System.out.println("finished");
//
////            String query="create table greetings(Id SERIAL primary key, greeting_message varchar(200))";
////            statement = connection.createStatement();
////            statement.executeUpdate(query);
////            System.out.println("finished");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//}

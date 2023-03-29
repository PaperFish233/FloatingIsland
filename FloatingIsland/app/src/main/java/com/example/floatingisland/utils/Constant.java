package com.example.floatingisland.utils;

public class Constant {

    //private static String IP = "http://192.168.31.125:8080/";
    private static String IP = "http://10.25.38.87:8080/";
    public static String getPosts = IP+"FloatingIslandService/PostsServlet?action=GetPosts";
    public static String insertPosts = IP+"FloatingIslandService/PostsServlet?action=InsertPosts";

    public static String like= IP+"FloatingIslandService/PostsLikeServlet?action=Like";
    public static String selectlike= IP+"FloatingIslandService/PostsLikeServlet?action=SelectLike";

    public static String collection= IP+"FloatingIslandService/PostsCollectionServlet?action=Collection";
    public static String selectcollection= IP+"FloatingIslandService/PostsCollectionServlet?action=SelectCollection";

    public static String getUsers = IP+"FloatingIslandService/UsersServlet?action=GetUsers";

}

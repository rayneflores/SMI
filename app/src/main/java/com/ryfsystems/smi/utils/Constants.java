package com.ryfsystems.smi.utils;

public class Constants {

    //General Address of BackEnd Server
    public static String INFRA_SERVER_ADDRESS = "http://magallanes.ddns.net/smi/";

    //Users Querys
    public static String GET_ALL_USERS = "getUsers.php";
    public static String GET_USER = "getUser.php?id=";
    public static String SAVE_USER = "save.php";
    public static String UPDATE_USER = "updateUser.php";
    public static String DELETE_USER = "deleteUser.php";
    //End User Querys

    //Products Querys
    public static String GET_PRODUCTS_COUNT = "getProductsCount.php?codSucursal=";
    //public static String GET_PRODUCTS_LABEL = "getProductsLabel.php?id_suc=";
    public static String GET_PRODUCTS_LABEL2 = "getProductsLabel2.php";
    public static String GET_PRODUCTS_LABEL3 = "getProductsLabel3.php";
    public static String GET_PRODUCTS_FOLLOW = "getProductsFollow.php?codSucursal=";
    public static String GET_PRODUCTS_REQUEST = "getProductsRequest.php?codSucursal=";
    public static String GET_PRODUCTS_DEFECT = "getProductsDefect.php?codSucursal=";
    public static String GET_PRODUCT = "getProduct.php?cbarra=";
    public static String GET_PRODUCT2 = "getProduct2.php?cbarra=";
    public static String GET_PRODUCT3 = "getProduct3.php?cbarra=";
    public static String GET_COUNT_EXISTENCE = "getCountExistence.php?ean_13=";
    public static String GET_QUERY_PRODUCT1 = "getQueryProduct1.php?detalle=";
    public static String GET_QUERY_PRODUCT2 = "getQueryProduct2.php?detalle=";
    public static String GET_QUERY_PRODUCT3 = "getQueryProduct3.php?detalle=";
    public static String GET_REAL_EXISTENCE = "getRealExistence.php?cbarra=";
    public static String SET_PRODUCT_FULL = "setProductFull.php";
    public static String SET_EXPIRED_PRODUCT = "setExpiredProduct.php";
    public static String SET_COUNT_PRODUCT = "setCountProduct.php";
    public static String SET_LABEL_PRODUCT = "setLabelProduct.php";
    public static String SET_LABEL_PRODUCT2 = "setLabelProduct2.php";
    public static String SET_LABEL_PRODUCT3 = "setLabelProduct3.php";
    public static String SET_SEGUI_PRODUCT = "setSeguiProduct.php";

    //Login
    public static String LOGIN = "login.php";

    //Send Data
    public static String SEND_COUNT_DATA = "sendCountData.php?codSucursal=";
    //public static String SEND_LABEL_DATA = "sendLabelData.php";
    public static String SEND_LABEL_DATA2 = "sendLabelData2.php";
    public static String SEND_LABEL_DATA3 = "sendLabelData3.php";
    public static String SEND_FOLLOW_DATA = "sendFollowData.php?codSucursal=";
    public static String SEND_REQUEST_DATA = "sendRequestData.php?codSucursal=";
    public static String SEND_DEFECT_DATA = "sendDefectData.php?codSucursal=";


    //NEW QUERYS
    public static String NEW_SEARCH_PRODUCT_BY_CODE = "productByCodeBar.php?codeBar=";
    public static String NEW_SET_LABEL_PRODUCT = "newSetLabelProduct.php";
    public static String NEW_GET_QUERY_PRODUCT = "newGetQueryProduct.php?detalle=";
    public static String NEW_GET_PRODUCTS_LABEL = "newGetProductsLabel.php?id_suc=";
    public static String NEW_INVENTARIO_CICLICO = "listInvCic.php?id_suc=";
    public static String SEND_LABEL_DATA = "sendLabelData.php?id_suc=";

    //Constants to Manage Duplicated Strings
    public static final String SERVER_ID = "serverId";
    public static final String MODULE = "module";
}

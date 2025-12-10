package com.dasafodata.Login_Java;

import com.dasafodata.Login_Java.igu.LoginInicial;


public class App {
    public static void main(String[] args) {
    	//Creamos una instancia de la pagina principal que arranque con el main/app
    	
    	LoginInicial princ = new LoginInicial();
    	//la hacemos visible
    	princ.setVisible(true);
    	princ.setLocationRelativeTo(null);

    }
}

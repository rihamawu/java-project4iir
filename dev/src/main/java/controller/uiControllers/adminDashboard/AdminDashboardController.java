package controller.uiControllers.adminDashboard;

import view.pages.AdminDashboard.AdminDashboard;
import view.pages.LoginPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardController {

    AdminDashboard view;
 public  AdminDashboardController(AdminDashboard view) {
     this.view = view;
     addAdminDashboardEvents();
 }
 public void addAdminDashboardEvents(){

     addLogoutEvent();

 }
 public  void addLogoutEvent(){
   this.view.getLogoutButton().addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             view.dispose();
             new LoginPage();

         }
     });
 }



}

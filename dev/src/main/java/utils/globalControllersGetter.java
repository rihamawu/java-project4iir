package utils;

import controller.AuditsController;
import controller.OrganizationsController;
import controller.AccountsController;

public class globalControllersGetter {
     public static OrganizationsController organizationsController = new OrganizationsController();
     public static AccountsController accountsController = new AccountsController();

     public static AuditsController auditsController = new AuditsController();
}

package utils;

import controller.businessControllers.AuditsController;
import controller.businessControllers.ResponsiblesController;
import controller.businessControllers.account.AccountsController;
import controller.businessControllers.organization.OrganizationsController;
import model.Accounts.AccountToken;
import utils.interfaces.objectConverter.AuditConverter;

public class ControllersGetter {
     public static AccountsController accountsController = new AccountsController();
     public static AccountToken currentAccountSession=null;
     public static OrganizationsController organizationsController = new OrganizationsController();
     public static ResponsiblesController responsiblesController = new ResponsiblesController();
     public static AuditsController auditsController = new AuditsController();


}

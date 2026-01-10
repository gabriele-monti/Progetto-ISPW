package it.foodmood.view.ui;

import it.foodmood.bean.ActorBean;
import it.foodmood.bean.TableSessionBean;
import it.foodmood.view.ui.cli.HomeCustomerPages;
import it.foodmood.view.ui.cli.MenuCustomerPages;

public interface CustomerUi extends BaseUi {
    ActorBean showGuestView();
    TableSessionBean showTableSession();
    MenuCustomerPages showMenuCustumerView();
    HomeCustomerPages showHomeCustumerView(ActorBean actorBean, TableSessionBean tableSessionBean);
    void showPageNotImplemented();
    void showDigitalMenuCustumerView();
    void showCustumerRecapOrderView(TableSessionBean tableSessionBean);
    void showAccountCustumerView(ActorBean actorBean);
    void showCustumerOrderCustomizationView();
}

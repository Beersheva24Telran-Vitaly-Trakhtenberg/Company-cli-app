package telran.employees;

import telran.io.Persistable;
import telran.view.*;

public class Main
{
    public static void main(String[] args)
    {
        Company company = new CompanyImpl();
        if (company instanceof Persistable persistable) {
            persistable.restoreFromFile("employees.data");
        }
        Item[] items = CompanyItems.getItems(company);
        Menu menu = new Menu("Company's Employees Management application", items);
        menu.perform(new StandardInputOutput());
    }
}

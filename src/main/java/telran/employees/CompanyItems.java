package telran.employees;

import telran.view.*;

public class CompanyItems 
{
    private static Company company;

    public static Item[] getItems(Company company)
    {
        CompanyItems.company = company;

        Item[] hireSubmenuItems = {
                Item.of("Hire Employee", CompanyItems::hire_employee),
                Item.of("Hire Wage Employee", CompanyItems::hire_wage_employee),
                Item.of("Hire Sales Person", CompanyItems::hire_sales_person),
                Item.of("Return to Main menu", io -> new Menu("Company's Employees Management application", getItems(company)).perform(io))
        };

        Item[] displaySubmenuItems = {
                Item.of("Display Employee data", CompanyItems::display_employee_by_id),
                Item.of("Department Salary Budget", CompanyItems::department_salary_budget),
                Item.of("List of Departments", CompanyItems::departments_list),
                Item.of("Display Managers with Most Factor", CompanyItems::display_managers_most_factors),
                Item.of("Return to Main menu", io -> new Menu("Company's Employees Management application", getItems(company)).perform(io))
        };

        Item[] items = {
                createMenuItemWithSubmenu("Hire...", hireSubmenuItems),
                Item.of("Fire Employee", CompanyItems::fire_employee_by_id),
                createMenuItemWithSubmenu("Display...", displaySubmenuItems),
                Item.of("Save & Exit", CompanyItems::save_on_exit),
                Item.ofExit()
        };

        return items;
    }

    private static void display_employee_by_id(InputOutput io) 
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyItems.display_employee_by_id() not implemented yet");
    }

    private static void fire_employee_by_id(InputOutput io) 
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyItems.fire_employee_by_id() not implemented yet");
    }

    private static void department_salary_budget(InputOutput io) 
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyItems.department_salary_budget() not implemented yet");
    }

    private static void departments_list(InputOutput io) 
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyItems.departments_list() not implemented yet");
    }

    private static void display_managers_most_factors(InputOutput io) 
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyItems.display_managers_most_factors() not implemented yet");
    }

    private static void save_on_exit(InputOutput io) 
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyItems.save_on_exit() not implemented yet");
    }

    private static void hire_employee(InputOutput io) 
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyItems.hire_employee() not implemented yet");
    }

    private static void hire_wage_employee(InputOutput io) 
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyItems.hire_wage_employee() not implemented yet");
    }

    private static void hire_sales_person(InputOutput io) 
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyItems.hire_sales_person() not implemented yet");
    }

    private static Item createMenuItemWithSubmenu(String item_title, Item[] submenu_items)
    {
        return Item.of(item_title, io -> {
            Menu submenu = new Menu(item_title, submenu_items);
            submenu.perform(io);
            for (Item item : submenu_items) {
                if (item.isExit()) {
                    return;
                }
            }
        });
    }
}

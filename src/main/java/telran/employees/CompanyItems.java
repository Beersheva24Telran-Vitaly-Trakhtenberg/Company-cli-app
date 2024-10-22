package telran.employees;

import telran.io.Persistable;
import telran.view.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

public class CompanyItems 
{
    private static Company company;

    final static long MIN_EMPLOYEE_ID = 100000;
    final static long MAX_EMPLOYEE_ID = 999999;
    final static int MIN_BASIC_SALARY = 5000;
    final static int MAX_BASIC_SALARY = 30000;
    final static String[] DEPARTMENTS = { "QA", "Audit", "Development", "Management" };
    final static int MIN_EMPLOYEE_AGE = 18;
    final static int MAX_EMPLOYEE_AGE = 70;
    final static int MIN_WAGE = 5;
    final static int MAX_WAGE = 50;
    final static int MIN_HOURS = 1;
    final static int MAX_HOURS = 40;
    final static float MIN_PERCENT = 10;
    final static float MAX_PERCENT = 100;
    final static long MIN_SALES = 5;
    final static long MAX_SALES = 99999;
    final static double MIN_FACTOR = 0.1;
    final static double MAX_FACTOR = 1.0;

    public static Item[] getItems(Company company)
    {
        CompanyItems.company = company;

        Item[] hireSubmenuItems = {
                Item.of("Hire Employee", CompanyItems::hire_employee),
                Item.of("Hire Wage Employee", CompanyItems::hire_wage_employee),
                Item.of("Hire Sales Person", CompanyItems::hire_sales_person),
                Item.of("Return to Main menu...", _ -> {}, true)
        };

        Item[] displaySubmenuItems = {
                Item.of("Display Employee data", CompanyItems::display_employee_by_id),
                Item.of("Department Salary Budget", CompanyItems::department_salary_budget),
                Item.of("List of Departments", CompanyItems::departments_list),
                Item.of("Display Managers with Most Factor", CompanyItems::display_managers_most_factors),
                Item.of("Return to Main menu...", _ -> {}, true)
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
        Employee employee = company.getEmployee(io.readNumberRange(
                "Enter Eployee ID:",
                        String.format("%s\n%s %d...%d",
                        "Wrong ID",
                        "Correct ID is",
                        MIN_EMPLOYEE_ID,
                        MAX_EMPLOYEE_ID),
                MIN_EMPLOYEE_ID,
                MAX_EMPLOYEE_ID
        ).longValue());
        if (employee != null) {
            io.writeLine(employee);
        } else {
            io.writeLine("No Employee in the company");
        }
    }

    private static void fire_employee_by_id(InputOutput io) 
    {
        Employee employee = company.removeEmployee(io.readNumberRange(
                "Enter Eployee ID to be fired:",
                String.format("%s\n%s %d...%d",
                        "Wrong ID",
                        "Correct ID is",
                        MIN_EMPLOYEE_ID,
                        MAX_EMPLOYEE_ID),
                MIN_EMPLOYEE_ID,
                MAX_EMPLOYEE_ID
        ).longValue());
        if (employee != null) {
            io.writeLine(employee);
        } else {
            io.writeLine("No Employee in the company");
        }
    }

    private static void department_salary_budget(InputOutput io) 
    {
        String department = io.readStringOptions(
                "Enter Department",
                "Wrong Department",
                new HashSet<>(Arrays.asList(DEPARTMENTS))
        );
        int department_budget = company.getDepartmentBudget(department);
        io.writeLine(String.format("Department '%s' has salary budget is %d", department, department_budget));
    }

    private static void departments_list(InputOutput io) 
    {
        String[] departments = company.getDepartments();
        if (departments.length > 0) {
            io.writeLine(String.join(", ", departments));
        } else {
            io.writeLine("No departments available.");
        }
    }

    private static void display_managers_most_factors(InputOutput io) 
    {
        Manager[] managers = company.getManagersWithMostFactor();
        if (managers.length > 0) {
            io.writeLine("Managers with the most factors:");
            for (Manager manager : managers) {
                io.writeLine(manager.toString());
            }
        } else {
            io.writeLine("No managers found with the most factors.");
        }
    }

    private static void save_on_exit(InputOutput io) 
    {
        if (company instanceof Persistable persistableCompany) {
            try {
                ((CompanyImpl) company).saveToFile("employees.data");
                io.writeLine("Company data saved successfully.");
            } catch (RuntimeException e) {
                io.writeLine(String.format("Error saving company data: %s", e.getMessage()));
            }
        } else {
            io.writeLine("Company data could not be saved. The company is not persistable.");
        }
        io.writeLine("Exiting the application...");
        System.exit(0);
    }

    private static void hire_employee(InputOutput io) 
    {
        Employee employee = io.readObject("Enter the Employee data in the format: \n" +
                        "[#id]#[Salary]#[Department]",
                "Wrong format for Employee data", str -> {
                    String[] tokens = str.split("#");
                    return new Employee(Long.parseLong(tokens[0]), Integer.parseInt(tokens[1]), tokens[2]);
                });
        io.writeLine("You are entered the following Employee data");
        io.writeLine(employee);
        company.addEmployee(employee);
    }

    private static void hire_wage_employee(InputOutput io) 
    {
        WageEmployee wage_employee = io.readObject("Enter the Employee data in the format: \n" +
                        "[#id]#[Salary]#[Department]#[wage]#[hours]",
                "Wrong format for Employee data", str -> {
                    String[] tokens = str.split("#");
                    return new WageEmployee(Long.parseLong(tokens[0]), Integer.parseInt(tokens[1]), tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
                });
        io.writeLine("You are entered the following WageEmployee data");
        io.writeLine(wage_employee);
        company.addEmployee(wage_employee);
    }

    private static void hire_sales_person(InputOutput io) 
    {
        SalesPerson sales_person = io.readObject("Enter the Employee data in the format: \n" +
                        "[#id]#[Salary]#[Department]#[wage]#[hours]#[percents]#[sales]",
                "Wrong format for Employee data", str -> {
                    String[] tokens = str.split("#");
                    return new SalesPerson(
                            Long.parseLong(tokens[0]),
                            Integer.parseInt(tokens[1]),
                            tokens[2],
                            Integer.parseInt(tokens[3]),
                            Integer.parseInt(tokens[4]),
                            Float.parseFloat(tokens[5]),
                            Long.parseLong(tokens[6]));
                });
        io.writeLine("You are entered the following Sales Person data");
        io.writeLine(sales_person);
        company.addEmployee(sales_person);
    }

    private static Item createMenuItemWithSubmenu(String title, Item[] submenuItems) {
        return Item.of(title, io -> {
            Menu submenu = new Menu(title, submenuItems);
            submenu.perform(io);
        });
    }
}

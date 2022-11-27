package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        DarbasSuFailais darbasSuFailais = null;

        try {
            darbasSuFailais = new DarbasSuFailais("C:\\Users\\aurel\\IdeaProjects\\untitled\\src\\com\\company\\PacientuIrGydytojuSistema\\UsersList.txt");
        } catch (FileNotFoundException e) {
            throw new Error("Negalima pasiekti tekstinio failo");
        }
        String ivestis = "";

        while (!ivestis.equals("3")) {
            printMainMenu();
            ivestis = sc.nextLine();

            switch (ivestis) {
                case "1":
                    Scanner scanner = new Scanner(System.in);

                    System.out.println("Įveskite prisijungimo vardą: ");
                    String prisijungimoVardas = scanner.nextLine();
                    System.out.println("Įveskite slaptažodį: ");
                    String ivestasSlaptazodis = scanner.nextLine();

                    try {
                        User user = darbasSuFailais.getUser(prisijungimoVardas, ivestasSlaptazodis);
                        if (user.getRole().equals(Role.PACIENTAS)) {
                            System.out.println("Sveiki prisijungę!");
                            patientMenu(user, darbasSuFailais);

                        } else if (user.getRole().equals(Role.ADMIN)) {
                            adminMenu(user, darbasSuFailais);

                        } else if (user.getRole().equals(Role.GYDYTOJAS)) {
                            Visit visit = darbasSuFailais.getVisitFoDoctor(prisijungimoVardas);
                            doctorMenu(visit, darbasSuFailais);
                        }
                    } catch (UserExeption e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "2":
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.print("Sukurkite prisijungimo vardą: ");
                    String username = scanner1.nextLine();
                    System.out.print("Sukurkite slaptažodį: ");
                    String password = scanner1.nextLine();
                    System.out.print("Įveskite vardą: ");
                    String vardas = scanner1.nextLine();
                    System.out.print("Įveskite pavardę: ");
                    String pavarde = scanner1.nextLine();
                    System.out.print("Įveskite amžių: ");
                    int amzius = scanner1.nextInt();
                    scanner1.nextLine();

                    try {
                        User savedUser = darbasSuFailais.addUser(new User(username, password, Role.PACIENTAS, vardas, pavarde, amzius));
                        System.out.println("Vartotojas sėkmingai užregistruotas");
                        patientMenu(savedUser, darbasSuFailais);
                    } catch (UserExeption e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "3":
                    break;
                default:
                    System.out.println("Neatpažinta įvestis");
                    break;
            }
        }
    }

    private static void patientMenu(User user, DarbasSuFailais darbasSuFailais) {
        Scanner scanner = new Scanner(System.in);
        String ivestis = "";

        while (!ivestis.equals("4")) {
            printPatientMenu();
            ivestis = scanner.nextLine();

            switch (ivestis) {
                case "1":
                    System.out.println("Prisijungimo vardas: " + user.getUsername());
                    System.out.println("Vardas: " + user.getVardas());
                    System.out.println("Pavardė: " + user.getPavarde());
                    System.out.println("Amžius: " + user.getAmzius());
                    break;
                case "2":
                    try {
                        Visit visit = darbasSuFailais.getVisitByName(user);
                        System.out.println(visit.getVizitoIšvados());
                    } catch (UserExeption e) {
                        System.out.println(e.getMessage());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "3":
                    System.out.println("Įrašykite pasirinktą gydytją (gydTomauskas, gydSauliene, gyd...): ");
                    String ivestasGydytojas = scanner.nextLine();
                    String vardas = user.getVardas();
                    String pavarde = user.getPavarde();
                    System.out.println("Įveskite norimą datą: ");
                    String ivestaData = scanner.nextLine();
                    System.out.println("Įveskite norimą laiką (galimi laikai: 9:00, 9:30, 10:00, 10:30, 11:00, 11:30, 12:00, 12:30, 13:00, 13:30, 14:00, 14:30) : ");
                    String ivestasLaikas = scanner.nextLine();
                    System.out.println("Įrašykite vizito priežastį: ");
                    String isvados = scanner.nextLine();

                    try {
                        Visit naujasVizitas = darbasSuFailais.addVisit(new Visit(ivestasGydytojas, vardas, pavarde, ivestaData, ivestasLaikas, isvados));
                        System.out.println("Vizitas sėkmingai užregistruotas");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (RegistrationExeption e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "4":
                    break;
                default:
                    System.out.println("Neatpažinta įvestis");
                    break;
            }
        }
    }

    private static void doctorMenu(Visit visit, DarbasSuFailais darbasSuFailais) {
        Scanner scanner = new Scanner(System.in);
        String ivestis = "";

        while (!ivestis.equals("4")) {
            printDoctorMenu();
            ivestis = scanner.nextLine();

            switch (ivestis) {
                case "1":
                    try {
                        ArrayList<Visit> visits = darbasSuFailais.getAllVisits();
                        for (Visit visitToPrint : visits) {
                            if (visitToPrint.getGydytojas().equals(visit.getGydytojas()))
                            printVisitForDoctor(visitToPrint);
                        }
                    } catch (FileNotFoundException e) {
                        throw new Error("Negalima pasiekti tekstinio failo");
                    }
                    break;
                case "2":
                    Scanner sc = new Scanner(System.in);
                    String gydVardas = visit.getGydytojas();
                    System.out.println("Įveskite pildomo paciento vardą: ");
                    String vardas = sc.nextLine();
                    System.out.println("Įveskite pildomo paciento pavardę: ");
                    String pavarde = sc.nextLine();
                    System.out.println("Įrašykite apsilankymo išvadas: ");
                    String isvados = sc.nextLine();

                    try {
                        darbasSuFailais.addSummary(gydVardas, vardas, pavarde, isvados);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (UserExeption e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "3":
                    String docusername = visit.getGydytojas();
                    System.out.println("Įveskite norimo ištrinti vizito datą: ");
                    String dataToDelete = scanner.nextLine();
                    System.out.println("Įveskite norimo ištrinti vizito laiką: ");
                    String timeToDelete = scanner.nextLine();

                    try {
                        darbasSuFailais.deleteVisitByData(docusername, dataToDelete, timeToDelete);
                        System.out.println("Vizitas sėkmingai pašalintas");
                    } catch (UserExeption e) {
                        System.out.println(e.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "4":
                    break;
                default:
                    System.out.println("Neatpažinta įvestis");
                    break;

            }
        }
    }

    private static void adminMenu(User user, DarbasSuFailais darbasSuFailais) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String ivestis = "";

        while (!ivestis.equals("6")) {
            printAdminMenu();
            ivestis = scanner.nextLine();

            switch (ivestis) {

                case "1":
                    ArrayList<User> allUsers = darbasSuFailais.getAllUsers();

                    System.out.println("-----------");
                    for (User userToPrint : allUsers) {
                        printUserForAdmin(userToPrint);
                        System.out.println();
                        System.out.println("-----------");
                    }
                    break;
                case "2":
                    Scanner sc = new Scanner(System.in);
                    System.out.print("Sukurkite prisijungimo vardą: ");
                    String username = sc.nextLine();
                    System.out.print("Sukurkite slaptažodį: ");
                    String password = sc.nextLine();
                    Role role = getRole();
                    System.out.print("Įveskite vardą: ");
                    String vardas = sc.nextLine();
                    System.out.print("Įveskite pavardę: ");
                    String pavarde = sc.nextLine();
                    System.out.print("Įveskite amžių: ");
                    int amzius = sc.nextInt();
                    sc.nextLine();

                    try {
                        darbasSuFailais.addUser(new User(username, password, role, vardas, pavarde, amzius));
                        System.out.println("Vartotojas sėkmingai užregistruotas");
                    } catch (UserExeption e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println();
                    break;

                case "3":
                    System.out.println("Įveskite vartotojo, kurį norite pašalinti, prisijungimo vardą: ");
                    String usernameToDelete = scanner.nextLine();

                    try {
                        darbasSuFailais.deleteUserByUsername(usernameToDelete);
                        System.out.println("Vartotojas sėkmingai pašalintas");
                    } catch (UserExeption e) {
                        System.out.println(e.getMessage());
                        ;
                    }
                    break;
                case "4":
                    System.out.println("Įrašykite pasirinktą gydytją (gydTomauskas, gydSauliene, gyd...): ");
                    String ivestasGydytojas = scanner.nextLine();
                    System.out.println("Įveskite paciento vardą: ");
                    String vardas1 = scanner.nextLine();
                    System.out.println("Įveskite paciento pavardę: ");
                    String pavarde1 = scanner.nextLine();
                    System.out.println("Įveskite norimą datą: ");
                    String ivestaData = scanner.nextLine();
                    System.out.println("Įveskite norimą laiką (galimi laikai: 9:00, 9:30, 10:00, 10:30, 11:00, 11:30, 12:00, 12:30, 13:00, 13:30, 14:00, 14:30) : ");
                    String ivestasLaikas = scanner.nextLine();
                    String isvados = "-";

                    try {
                        Visit naujasVizitas = darbasSuFailais.addVisit(new Visit(ivestasGydytojas, vardas1, pavarde1, ivestaData, ivestasLaikas, isvados));
                        System.out.println("Vizitas sėkmingai užregistruotas");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (RegistrationExeption e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "5":
                    System.out.println("Įveskite gydytojo, kuriam norite pašalinti vizitą, prisijungimo vardą: ");
                    String docusername = scanner.nextLine();
                    System.out.println("Įveskite norimo ištrinti vizito datą: ");
                    String dataToDelete = scanner.nextLine();
                    System.out.println("Įveskite norimo ištrinti vizito laiką: ");
                    String timeToDelete = scanner.nextLine();

                    try {
                        darbasSuFailais.deleteVisitByData(docusername, dataToDelete, timeToDelete);
                        System.out.println("Vizitas sėkmingai pašalintas");
                    } catch (UserExeption e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "6":
                    break;
                default:
                    System.out.println("neatpažinta įvestis");
                    break;
            }
        }
    }

    private static Role getRole() {

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Pasirinkite rolę");
            System.out.println("[1] Pacientas");
            System.out.println("[2] Admin");
            System.out.println("[3] Gydytojas");
            String ivestis = sc.nextLine();

            switch (ivestis) {
                case "1":
                    return Role.PACIENTAS;
                case "2":
                    return Role.ADMIN;
                case "3":
                    return Role.GYDYTOJAS;
                default:
                    System.out.println("Neteisinga įvestis");
                    System.out.println();
                    break;
            }
        }
    }

    private static void printPatientMenu() {
        System.out.println("[1] Peržiūrėti vartotojo informaciją");
        System.out.println("[2] Peržiūrėti savo sveikatos informaciją");
        System.out.println("[3] Registruotis vizitui pas gydytoją");
        System.out.println("[4] EXIT");
    }

    private static void printVisitForDoctor(Visit visit) {
        System.out.println("Paciento vardas: " + visit.getPacientoVardas());
        System.out.println("Paciento pavardė: " + visit.getPacientoPavarde());
        System.out.println("Užsirašymo data : " + visit.getData());
        System.out.println("Užsirašymo laikas: " + visit.getLaikas());
        System.out.println("Pastabos/išvados: " + visit.getVizitoIšvados());
        System.out.println("----------------");
    }

    private static void printUserForAdmin(User user) {
        System.out.println("Prisijungimo vardas: " + user.getUsername());
        System.out.println("Rolė: " + user.getRole());
        System.out.println("Vardas: " + user.getVardas());
        System.out.println("Pavardė: " + user.getPavarde());
        System.out.println("Amžius: " + user.getAmzius());
    }

    private static void printDoctorMenu() {
        System.out.println("[1] Peržiūrėti vizitus\n" +
                "[2] Pridėti apsilankymo išvadas\n" +
                "[3] Atšaukti vizitą\n" +
                "[4] Atsijungti");
    }

    private static void printAdminMenu() {
        System.out.println("[1] Peržiūrėti visų vartotojų informaciją\n" +
                "[2] Pridėti naują vartotoją\n" +
                "[3] Ištrinti egzistuojantį vartotoją\n" +
                "[4] Pridėti vizitą\n" +
                "[5] Ištrinti vizitą\n" +
                "[6] Atsijungti");
    }

    private static void printMainMenu() {
        System.out.println("---------------------\n" +
                "[1] Prisijungti\n" +
                "[2] Registruotis\n" +
                "[3] EXIT");
    }

}

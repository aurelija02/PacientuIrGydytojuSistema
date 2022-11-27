package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DarbasSuFailais {

    private String path;

    public DarbasSuFailais(String path) throws FileNotFoundException {
        this.path = path;
        File file = new File(path);
        new Scanner(file);
    }

    public User getUser(String username, String password) throws FileNotFoundException, UserExeption {

        ArrayList<User> users = getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new UserExeption("Neteisingi prisijungimo duomenys");
    }

    public ArrayList<User> getAllUsers() throws FileNotFoundException {
        File file = new File(path);
        Scanner sc = new Scanner(file);

        ArrayList<User> users = new ArrayList<>();
        while (sc.hasNextLine()) {

            String username = sc.nextLine();
            String password = sc.nextLine();
            String role = sc.nextLine();
            String vardas = sc.nextLine();
            String pavarde = sc.nextLine();
            int amzius = sc.nextInt();
            sc.nextLine();
            sc.nextLine();

            users.add(new User(username, password, Role.valueOf(role), vardas, pavarde, amzius));
        }
        return users;
    }

    public User addUser(User user) throws IOException, UserExeption {

        ArrayList<User> users = getAllUsers();

        for (User usertoCheck : users) {
            if (usertoCheck.getUsername().equals(user.getUsername())) {
                throw new UserExeption("Toks vartotojo vardas jau egzistuoja. Bandykite iš naujo.");
            }
        }
        writeUser(user);
        return (user);
    }

    private void writeUser(User user) throws IOException {
        FileWriter fw = new FileWriter(path, true);
        PrintWriter writer = new PrintWriter(fw);

        writer.println(user.getUsername());
        writer.println(user.getPassword());
        writer.println(user.getRole());
        writer.println(user.getVardas());
        writer.println(user.getPavarde());
        writer.println(user.getAmzius());
        writer.println();
        writer.close();
    }

    public Visit addVisit(Visit visit) throws IOException, RegistrationExeption {

        ArrayList<Visit> visits = getAllVisits();

        for (Visit visitToCheck : visits) {
            if (visitToCheck.getGydytojas().equals(visit.getGydytojas()) && visitToCheck.getData().equals(visit.getData()) && visitToCheck.getLaikas().equals(visit.getLaikas())) {
                throw new RegistrationExeption("Toks laikas jau užimtas");
            }
        }
        writeVisit(visit);
        return (visit);
    }

    private void writeVisit(Visit visit) throws IOException {
        FileWriter fw = new FileWriter("C:\\Users\\aurel\\IdeaProjects\\untitled\\src\\com\\company\\PacientuIrGydytojuSistema\\Registracijos.txt", true);
        PrintWriter writer = new PrintWriter(fw);

        writer.println(visit.getGydytojas());
        writer.println(visit.getPacientoVardas());
        writer.println(visit.getPacientoPavarde());
        writer.println(visit.getData());
        writer.println(visit.getLaikas());
        writer.println(visit.getVizitoIšvados());
        writer.println();
        writer.close();
    }

    public Visit getVisitFoDoctor(String prisijungimoVardas) throws FileNotFoundException, UserExeption {

        ArrayList<Visit> visits = getAllVisits();
        for (Visit visit : visits) {
            if (visit.getGydytojas().equals(prisijungimoVardas)) {
                return visit;
            }
        }
        throw new UserExeption("Neteisingi prisijungimo duomenys");
    }

    public Visit getVisitByName(User user) throws UserExeption, FileNotFoundException {

        ArrayList<Visit> visits = getAllVisits();
        for (Visit visit : visits) {
            if (visit.getPacientoVardas().equals(user.getVardas()) && visit.getPacientoPavarde().equals(user.getPavarde())) {
                return visit;
            }
        }
        throw new UserExeption("Neteisingi prisijungimo duomenys");
    }

    public ArrayList<Visit> getAllVisits() throws FileNotFoundException {
        File file = new File("C:\\Users\\aurel\\IdeaProjects\\untitled\\src\\com\\company\\PacientuIrGydytojuSistema\\Registracijos.txt");
        Scanner sc = new Scanner(file);

        ArrayList<Visit> visits = new ArrayList<>();
        while (sc.hasNextLine()) {
            String gydytojas = sc.nextLine();
            String pacientoVardas = sc.nextLine();
            String pacientoPavarde = sc.nextLine();
            String data = sc.nextLine();
            String laikas = sc.nextLine();
            String vizitoIsvados = sc.nextLine();
            sc.nextLine();

            visits.add(new Visit(gydytojas, pacientoVardas, pacientoPavarde, data, laikas, vizitoIsvados));
        }
        return visits;
    }

    public void deleteUserByUsername(String usernameToDelete) throws IOException, UserExeption {
        ArrayList<User> users = getAllUsers();

        User userToDelete = getUserToDelete(usernameToDelete, users);
        users.remove(userToDelete);

        flushUserFile();
        for (User userToWrite : users) {
            writeUser(userToWrite);
        }
    }

    private void flushUserFile() throws IOException {
        new FileWriter(path, false);
    }

    private User getUserToDelete(String usernameToDelete, ArrayList<User> users) throws UserExeption {
        for (User user : users) {
            if (user.getUsername().equals(usernameToDelete)) {
                if (user.getRole().equals(Role.ADMIN)) {
                    throw new UserExeption("ADMIN vartotojas negali būti ištrinamas!");
                }
                return user;
            }
        }
        throw new UserExeption("Vartotojas su prisijungimo vardu neegzistuoja");
    }

    public void deleteVisitByData(String docusername, String dataToDelete, String timeToDelete) throws IOException, UserExeption {
        ArrayList<Visit> visits = getAllVisits();

        Visit visitToDelete = getVisitToDelete(docusername, dataToDelete, timeToDelete, visits);
        visits.remove(visitToDelete);
        rewriteVisits(visits);
    }

    private void flushVisitFile() throws IOException {
        new FileWriter("C:\\Users\\aurel\\IdeaProjects\\untitled\\src\\com\\company\\PacientuIrGydytojuSistema\\Registracijos.txt", false);
    }

    private Visit getVisitToDelete(String docusername, String dataToDelete, String timeToDelete, ArrayList<Visit> visits) throws UserExeption {
        for (Visit visit : visits) {
            if (visit.getGydytojas().equals(docusername) && visit.getData().equals(dataToDelete) && visit.getLaikas().equals(timeToDelete)) {
                return visit;
            }
        }
        throw new UserExeption("Neteisingi duomenys");
    }

    public void rewriteVisits(ArrayList<Visit> visits) throws IOException {

        flushVisitFile();
        for (Visit visitToWrite : visits) {
            writeVisit(visitToWrite);
        }
    }

    public void addSummary(String gydVardas, String vardas, String pavarde, String isvados) throws IOException, UserExeption {
        ArrayList<Visit> visits = getAllVisits();
        for (Visit visitToChange : visits) {
            if (visitToChange.getGydytojas().equals(gydVardas) && visitToChange.getPacientoVardas().equals(vardas) && visitToChange.getPacientoPavarde().equals(pavarde)) {
                visitToChange.setVizitoIšvados(isvados);
                System.out.println("Vizito išvados papildytos");
                System.out.println();
            }
            rewriteVisits(visits);
        }
        throw new UserExeption("Pasirinktas netinkamas pacientas");
    }

}

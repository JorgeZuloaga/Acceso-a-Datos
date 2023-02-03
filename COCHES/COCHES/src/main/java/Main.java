import model.Coche;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<Coche> listaCoches= new ArrayList();

    private static Scanner scannerInt = new Scanner(System.in);
    private static Scanner scannerStr = new Scanner(System.in);

    private static String nombreArchivoCochesDAT = "src\\main\\resources\\Coches.dat";
    private static String nombreArchivoCochesCSV = "src\\main\\resources\\Coches.csv";
    private static File archivoCochesDAT = new File(nombreArchivoCochesDAT);
    private static File archivoCochesCSV = new File(nombreArchivoCochesCSV);

    private static int maxId = 1;

    private static boolean existeFicheroDAT() {
        return archivoCochesDAT.exists();
    }

    private static boolean existeFicheroCSV() {
        return archivoCochesCSV.exists();
    }

    private static void cargarArrayListDAT() throws IOException {
        ObjectInputStream archivoCochesDAT = new ObjectInputStream(new FileInputStream(nombreArchivoCochesDAT));
        try {
            listaCoches = (ArrayList<Coche>) (archivoCochesDAT.readObject());
            actualizarMaxId(1 + listaCoches.size());
            archivoCochesDAT.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void cargarArrayListCSV() throws FileNotFoundException {
        Scanner lector = new Scanner(archivoCochesCSV);
        String linea= null;
        while (lector.hasNextLine()){
            linea = lector.nextLine();
            listaCoches.add(new Coche(Integer.parseInt(linea.split(";")[0]),
                    linea.split(";")[1],
                    linea.split(";")[2],
                    linea.split(";")[3],
                    linea.split(";")[4]));
        }
        if(linea!=null)
            actualizarMaxId(1 + Integer.parseInt(linea.split(";")[0]));
        lector.close();
    }

    private static void actualizarMaxId(int newMaxId){
        maxId = newMaxId;
    }

    private static void pintarMenu(){
        System.out.println("1. Anadir nuevo coche");
        System.out.println("2. Borrar coche por id");
        System.out.println("3. Consulta coche por id");
        System.out.println("4. Listado de coches");
        System.out.println("5. Terminar el programa");
        System.out.println("6. Exportar a CSV");
    }

    private static int leerOpcionTeclado(){
        return leerIntTeclado();
    }

    private static void anadirNuevoCoche(){
        try {
            listaCoches.add(leerCoche());
            actualizarMaxId(maxId+1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean existeMatricula(String matricula){
        boolean existe= false;
        int i = 0;
        while(!existe && i< listaCoches.size()){
            if(listaCoches.get(i).getMatricula().equals(matricula))
                existe= true;
            i++;
        }
        return existe;
    }

    private static Coche leerCoche() throws Exception {
        pedirInfoPantalla("Matricula?");
        String matricula = leerLineaTeclado();
        if (existeMatricula(matricula)){
            throw new Exception("Error! Matricula ya existente..");
        }
        pedirInfoPantalla("Marca?");
        String marca = leerLineaTeclado();
        pedirInfoPantalla("Modelo?");
        String modelo = leerLineaTeclado();
        pedirInfoPantalla("Color?");
        String color = leerLineaTeclado();
        return new Coche(maxId, matricula.trim(), marca.trim(), modelo.trim(), color.trim());
    }

    private static void pedirInfoPantalla(String mensaje){
        System.out.println(mensaje);
    }

    private static String leerLineaTeclado(){
        return scannerStr.nextLine();
    }

    private static int leerIntTeclado(){
        return scannerInt.nextInt();
    }

    private static void consultarCochePorId(){
        pedirInfoPantalla("Id?");
        buscarCocheLista(leerIntTeclado());
    }

    private static void buscarCocheLista(int id){
        for(Coche coche : listaCoches)
            if(coche.getId()==id)
                System.out.println(coche);
    }

    private static void eliminarCocheLista(int id){
        int indice= -1;
        int i = 0;
        for(Coche coche : listaCoches) {
            if (coche.getId() == id)
                indice = i;
            i++;
        }
        if(indice!=-1)
            listaCoches.remove(indice);
    }

    private static void eliminarCoche(){
        pedirInfoPantalla("Id?");
        eliminarCocheLista(leerIntTeclado());
    }

    private static int tratarOpcion(int opcion) throws IOException {
        switch(opcion){
            case 1:
                anadirNuevoCoche();
                break;
            case 2:
                eliminarCoche();
                break;
            case 3:
                consultarCochePorId();
                break;
            case 4:
                mostrarListadoCoches();
                break;
            case 5:
                if (!existeFicheroDAT())
                    crearFicheroDAT();
                volcarArrayDAT();
                break;
            case 6:
                if (!existeFicheroCSV())
                    crearFicheroCSV();
                volcarArrayCSV();
                break;
            default:
                System.out.println("Opcion no valida!");
                break;
        }
        return opcion;
    }

    private static void mostrarListadoCoches(){
        for(Coche coche : listaCoches)
            System.out.println(coche);
    }

    private static void crearFicheroCSV() throws IOException {
        archivoCochesCSV.createNewFile();
    }

    private static void crearFicheroDAT() throws IOException {
        archivoCochesDAT.createNewFile();
    }

    private static void volcarArrayDAT() throws IOException {
        ObjectOutputStream archivoCochesWDAT = new ObjectOutputStream(new FileOutputStream(nombreArchivoCochesDAT));
        try {
            archivoCochesWDAT.writeObject(listaCoches);
            archivoCochesWDAT.flush();
            archivoCochesWDAT.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void volcarArrayCSV() throws IOException {
        FileWriter archivoCochesWCSV = new FileWriter(nombreArchivoCochesCSV);
        PrintWriter escritor = new PrintWriter(archivoCochesWCSV);
        try {
            for(Coche coche : listaCoches)
                escritor.println(coche);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != archivoCochesWCSV)
                    archivoCochesWCSV.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void main (String[] args) throws IOException {


        //1. verificamos que exista el archivo .dat
        if (existeFicheroDAT())
        //2. si si existe cargamos su contenido en el arraylist
            cargarArrayListDAT();

        do {
            //3. mostramos el menu con las opciones
            pintarMenu();
            //4. leemos por teclado la opion
            //5. tratamos la opcion seleccionada
        }while (tratarOpcion(leerOpcionTeclado())!=5);

    }

}

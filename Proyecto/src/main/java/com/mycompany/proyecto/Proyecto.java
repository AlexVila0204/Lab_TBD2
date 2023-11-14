package com.mycompany.proyecto;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class Proyecto {

    static AmazonDynamoDB cliente = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "local"))
            .build();
    static DynamoDB dynamoDB = new DynamoDB(cliente);

    public static void main(String[] args) {
        LeerAtributosTabla("Vehiculos");
    }
    //metodo universal para crear un item

    public static void crearItem(String nombreTabla) {
        //verificar si la tabla existe
        try {
            Table tabla = dynamoDB.getTable(nombreTabla);
            if (tabla.describe() == null) {
                throw new Exception("La tabla no existe");
            }

            switch (nombreTabla) {
                case "Vehiculos": {
                    //creacion de items en vehiculo
                    //vehiculo tiene como llave primaria matricula (string), tipo_lic(string), categoria_lujo(string),tarifa_base(int)

                    //creacion de un item
                    Item item = new Item()
                            .withPrimaryKey("matricula", "HCH213")
                            .withString("tipo_lic", "A")
                            .withString("categoria_lujo", "Si")
                            .withInt("tarifa_base", 5000);
                    tabla.putItem(item);
                }
                break;
                case "Profesores": {
                    //creacion de items en profesores
                    //profesores tiene como llave primaria cedula(string), nombre(string), apellido_1(string), apellido_2(string)

                    //creacion de un item
                    Item item = new Item()
                            .withPrimaryKey("cedula", "0319200200038")
                            .withString("nombre", "ElTigre")
                            .withString("apellido_1", "Sandoval")
                            .withString("apellido_2", "Benites");
                    tabla.putItem(item);
                }
                break;
                case "Alumno": {
                    //creacion de items en alumnos
                    //alumnos tiene como llave primaria cedula(string), nombre(string), apellido_1(string), apellido_2(string) y tipo_lic(string)

                    //creacion de un item
                    Item item = new Item()
                            .withPrimaryKey("cedula", "1234567")
                            .withString("nombre", "Wilmer")
                            .withString("apellido_1", "Antonio")
                            .withString("apellido_2", "Zuniga")
                            .withString("tipo_lic", "B");
                    tabla.putItem(item);
                }
                break;
                case "Documentacion": {
                    //creacion de items en documentacion
                    //Documentacion tiene como llave primaria tipo_doc(stirng), tipo_lic(string)

                    //creacion de un item
                    Item item = new Item()
                            .withPrimaryKey("tipo_doc", "Cedula")
                            .withString("tipo_lic", "B");
                    tabla.putItem(item);

                }
                break;
                case "Matriculas": {
                    //creacion de items en matriculas
                    //Matriculas tiene como llave primaria n_matricula(int), dni_alumno(string), tipo_lic(string), fecha_matricula(date)

                    //creacion de un item
                    Item item = new Item()
                            .withPrimaryKey("n_matricula", 1)
                            .withString("dni_alumno", "1234567")
                            .withString("tipo_lic", "B")
                            .withString("fecha_matricula", "2020-01-01");
                    tabla.putItem(item);
                }
                break;
                case "Clases": {
                    //creacion de items en clases
                    //Clases tiene como la combinacion de llaves primaria de fecha_clase(date), hora_inicio(time), hora_fin(time), matricula(string), y dni_profesor(string) ademas tiene un atributo llamado informe(string)

                    //creacion de un item
                    Item item = new Item()
                            .withPrimaryKey("id_clase", 1)
                            .withString("fecha", "12-11-23")
                            .withString("hora_inicio", "10:00:00")
                            .withString("hora_fin", "11:00:00")
                            .withString("matricula", "HCH213")
                            .withString("dni_profesor", "0319200200038")
                            .withString("informe", "El alumno no sabe manejar");
                    tabla.putItem(item);

                }
                break;
                case "Examenes": {
                    //creacion de items en examenes
                    //Examenes tiene como la combinacion de llaves primarias de fecha_examen(date), tipo_examen(string), matricula(string), y n_examen(int) ademas tiene un atributo llamado informe(string) y tiene como atributo resultado(boolean)

                    //creacion de un item
                    Item item = new Item()
                            .withPrimaryKey("n_examen", "2020-01-01")
                            .withString("tipo_examen", "Practico")
                            .withString("matricula", "HCH213")
                            .withString("fecha", "10-11-23")
                            .withString("informe", "El alumno no sabe manejar")
                            .withBoolean("resultado", false);
                    tabla.putItem(item);

                }
                break;
                default: {
                    throw new Exception("La tabla no existe");
                }

            }
        } catch (Exception e) {
            System.err.println("Error al crear el item: " + e.getMessage());
        }

    }

    //metodo universal para leer un item
    public static void LeerAtributosTabla(String nombreTabla) {
        try {
            Table tabla = dynamoDB.getTable(nombreTabla);
            if (tabla.describe() == null) {
                throw new Exception("La tabla no existe");
            }

            //listar los items de la tabla vehiculos
            System.out.println("Listando los items de la tabla: " + nombreTabla);
            tabla.scan().forEach(System.out::println);

        } catch (Exception e) {
            System.err.println("Error al leer los atributos de la tabla: " + e.getMessage());
        }
    }
    //metodo para ver todas las tablas de la base de datos 

    public static void VerTablas() {
        try {
            List<String> tablas = new ArrayList<String>();
            tablas = cliente.listTables().getTableNames();
            System.out.println("Las tablas de la base de datos son: ");
            for (String tabla : tablas) {
                System.out.println(tabla);
            }
        } catch (Exception e) {
            System.err.println("Error al listar las tablas: " + e.getMessage());
        }
    }

    //metodo universal para eliminar un item
    public static void EliminarItem(String nombreTabla, String llavePrimaria) {
        //verificar si la tabla existe
        try {
            Table tabla = dynamoDB.getTable(nombreTabla);
            if (tabla.describe() == null) {
                throw new Exception("La tabla no existe");
            }
            switch (nombreTabla) {
                case "Vehiculos": {
                    //Eliminacion de items en vehiculo
                    /*La clase vehiculo esta relacionada con la tabla Clases Una clase se imparte con un vehículo. 
                    La relación se expresa mediante la partición secundaria matricula en la tabla clases,
                    en este caso al eliminar un vehiculo se eliminarara todos los items de esa clase dado el hecho que no hay vehiculo*/
                    //verificar si la llave primaria existe en la tabla Vehiculos
                    if (tabla.getItem("matricula", llavePrimaria) == null) {
                        throw new Exception("La llave primaria no existe");
                    }
                    Table tablaClases = dynamoDB.getTable("Clases");
                    System.out.println("Listando los items de la tabla Clases");
                    
                    JSONArray jsonArray = new JSONArray();
                    tablaClases.scan().forEach(item -> {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("fecha_clase", item.get("fecha_clase"));
                        jsonObject.put("hora_inicio", item.get("hora_inicio"));
                        jsonObject.put("hora_fin", item.get("hora_fin"));
                        jsonObject.put("matricula", item.get("matricula"));
                        jsonObject.put("dni_profesor", item.get("dni_profesor"));
                        jsonObject.put("informe", item.get("informe"));
                        jsonArray.put(jsonObject);
                        if (item.get("matricula").equals(llavePrimaria)) {
                            tablaClases.deleteItem("fecha_clase", item.get("fecha_clase"));
                            System.out.println("Se elimino la clase con fecha: " + item.get("fecha_clase"));
                        }
                    });
                    //imprimir los items de la clase a eliminar
                    System.out.println(tabla.getItem("matricula", llavePrimaria));
                    tabla.deleteItem("matricula", llavePrimaria);
                    System.out.println("Se elimino el vehiculo con matricula: " + llavePrimaria);
                }
                break;
                case "Profesores": {
                    //Eliminacion de items en profesores
                    if (tabla.getItem("cedula", llavePrimaria) == null) {
                        throw new Exception("La llave primaria no existe");
                    }
                    //Listar item que van a ser eliminados
                    System.out.println(tabla.getItem("cedula", llavePrimaria));
                    tabla.deleteItem("cedula", llavePrimaria);
                    System.out.println("Se elimino el profesor con cedula: " + llavePrimaria);
                }

                break;
                case "Alumno": {
                    //Eliminacion de items en alumnos
                    if (tabla.getItem("cedula", llavePrimaria) == null) {
                        throw new Exception("La llave primaria no existe");
                        
                    }
                    //Listar item que van a ser eliminados
                    System.out.println(tabla.getItem("cedula", llavePrimaria));
                    tabla.deleteItem("cedula", llavePrimaria);
                    System.out.println("Se elimino el alumno con cedula: " + llavePrimaria);
                }
                break;
                case "Documentacion": {
                    //Eliminacion de items en documentacion
                    if (tabla.getItem("tipo_doc", llavePrimaria) == null) {
                        throw new Exception("La llave primaria no existe");
                    }
                    //Listar item que van a ser eliminados
                    System.out.println(tabla.getItem("tipo_doc", llavePrimaria));
                    tabla.deleteItem("tipo_doc", llavePrimaria);
                    System.out.println("Se elimino el documento con tipo: " + llavePrimaria);

                }
                break;
                case "Matriculas": {
                    //Eliminacion de items en matriculas
                    if (tabla.getItem("n_matricula", llavePrimaria) == null) {
                        throw new Exception("La llave primaria no existe");
                    }
                    //Listar item que van a ser eliminados
                    System.out.println(tabla.getItem("n_matricula", llavePrimaria));
                    tabla.deleteItem("n_matricula", llavePrimaria);
                    System.out.println("Se elimino la matricula con numero: " + llavePrimaria);
                    
                }
                break;
                case "Clases": {
                    //Eliminacion de items en clases
                    if (tabla.getItem("id_clase", llavePrimaria) == null) {
                        throw new Exception("La llave primaria no existe");
                    }

                    //Listar item que van a ser eliminados
                    System.out.println(tabla.getItem("id_clase", llavePrimaria));
                    tabla.deleteItem("id_clase", llavePrimaria);
                    System.out.println("Se elimino la clase con id: " + llavePrimaria);

                }
                break;
                case "Examenes": {
                   

                }
                break;
                default: {
                    throw new Exception("La tabla no existe");
                }
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar el item: " + e.getMessage());
        }

    }

}

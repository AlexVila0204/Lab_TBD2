package com.mycompany.proyecto;

import proyectofinal.NewJFrame;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import java.net.URI;
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

    public static void main(String[] args) {
        
        new NewJFrame().setVisible(true);

    }
    
    public static void crearItem(String nombreTabla, Item Item) {
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
                    tabla.putItem(Item);
                }
                break;
                case "Profesores": {
                    //creacion de items en profesores
                    //profesores tiene como llave primaria cedula(string), nombre(string), apellido_1(string), apellido_2(string)
                    tabla.putItem(Item);
                }
                

                break;
                case "Alumno": {
                    ///creacion de items en alumnos
                    //alumnos tiene como llave primaria cedula(string), nombre(string), apellido_1(string), apellido_2(string) y tipo_lic(string)
                    tabla.putItem(Item);
                }
                break;
                case "Documentacion": {
                    //creacion de items en documentacion
                    //Documentacion tiene como llave primaria tipo_doc(stirng), tipo_lic(string)
                    tabla.putItem(Item);

                }
                break;
                case "Matriculas": {
                    //creacion de items en matriculas
                    //Matriculas tiene como llave primaria n_matricula(int), dni_alumno(string), tipo_lic(string), fecha_matricula(date)
                    tabla.putItem(Item);
                }
                break;
                case "Clases": {
                    //creacion de items en clases
                    //Clases tiene como la combinacion de llaves primaria de fecha_clase(date), hora_inicio(time), hora_fin(time), matricula(string), y dni_profesor(string) ademas tiene un atributo llamado informe(string)

                    //creacion de un item
                    Item item = new Item()
                            .withPrimaryKey("fecha_clase", "2020-01-01")
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
                            .withPrimaryKey("fecha_examen", "2020-01-01")
                            .withString("tipo_examen", "Practico")
                            .withString("matricula", "HCH213")
                            .withInt("n_examen", 1)
                            .withString("informe", "El alumno no sabe manejar")
                            .withBoolean("resultado", false);
                    tabla.putItem(item);

                }
                break;
                default :{ 
                    throw new Exception("La tabla no existe");
                }

            }
        } catch (Exception e) {
            System.err.println("Error al crear el item: " + e.getMessage());
        }

    }
    static AmazonDynamoDB cliente = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "local"))
            .build();
    static DynamoDB dynamoDB = new DynamoDB(cliente);
}

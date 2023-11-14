package Clases;
import java.io.Serializable;


/**
 *
 * @author wilme
 */
public class Persona implements Serializable{

    public String Usuario;
    public String Contraseña;
    public int Edad;
    public int ID;
    public String Nombre;
    public String Correo;
    public String Genero;
    public String Administrador;

    public Persona() {
    }

    public Persona(String Usuario, String Contraseña, int Edad, int ID, String Nombre, String Correo, String Genero, String Administrador) {
        this.Usuario = Usuario;
        this.Contraseña = Contraseña;
        this.Edad = Edad;
        this.ID = ID;
        this.Nombre = Nombre;
        this.Correo = Correo;
        this.Genero = Genero;
        this.Administrador = Administrador;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String Contraseña) {
        this.Contraseña = Contraseña;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int Edad) {
        this.Edad = Edad;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String Genero) {
        this.Genero = Genero;
    }

    public String getAdministrador() {
        return Administrador;
    }

    public void setAdministrador(String Administrador) {
        this.Administrador = Administrador;
    }

    @Override
    public String toString() {
        return Usuario;
    }

  



    
}

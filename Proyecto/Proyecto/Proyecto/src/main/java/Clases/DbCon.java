/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.SQLException;

public class DbCon {

    private String connectionUrl;
    private Connection con;

    public DbCon(String bd) {
        connectionUrl
                = "jdbc:sqlserver://proyectodb.cq2qae56bgqw.us-east-1.rds.amazonaws.com:1433;"
                + "databaseName=" + bd + ";"
                + "user=admin;"
                + "password=cosacosa;"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        try {
            con = DriverManager.getConnection(connectionUrl);
        } catch (Exception ex) {
            System.out.println("Error");
        }
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public ResultSet Select(String table) {
        ResultSet rs = null;
        try {
            CallableStatement callSt = con.prepareCall("Select" + table);;
            rs = callSt.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public ResultSet SelectById(String table, int id) {
        ResultSet rs = null;
        try {
            CallableStatement callSt = null;

            switch (table) {
                case "Tienda":
                    callSt = con.prepareCall("SELECT * FROM " + table + " WHERE id_tienda = ?");
                    break;
                case "Producto":
                    callSt = con.prepareCall("SELECT * FROM " + table + " WHERE UPC = ?");
                    break;
                case "Vendedor":
                    callSt = con.prepareCall("SELECT * FROM " + table + " WHERE id_vendedor = ?");
                    break;
                case "Factura":
                    callSt = con.prepareCall("SELECT * FROM " + table + " WHERE numero = ?");
                    break;
                case "Cliente":
                    callSt = con.prepareCall("SELECT * FROM " + table + " WHERE id_cliente = ?");
                    break;
                default:
                    callSt = con.prepareCall("SELECT * FROM " + table + " WHERE II = ?");

            }
            callSt.setInt(1, id);
            rs = callSt.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public void Delete(String table, int id) {
        try {
            CallableStatement callSt = con.prepareCall("{call Delete" + table + "(?)}");;
            callSt.setInt(1, id);
            callSt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void Update(String table, int id, ArrayList<Object> atributes) {
        try {
            CallableStatement callSt = null;
            switch (table) {
                case "Tienda":
                    callSt = con.prepareCall("{call UpdateTienda(?,?,?)}");
                    callSt.setInt("@id_tienda", id);
                    callSt.setString("@nombre_tienda", (String) atributes.get(0));
                    callSt.setString("@horario", (String) atributes.get(1));
                    break;
                case "Producto":
                    callSt = con.prepareCall("{call UpdateProducto(?,?,?,?,?)}");
                    callSt.setInt("@UPC", id);
                    callSt.setString("@nombre_producto", (String) atributes.get(0));
                    callSt.setString("@tamaño", (String) atributes.get(1));
                    callSt.setString("@embalaje", (String) atributes.get(2));
                    callSt.setString("@marca", (String) atributes.get(3));
                    break;
                case "Vendedor":
                    callSt = con.prepareCall("{call UpdateVendedor(?,?)}");
                    callSt.setInt("@id_vendedor", id);
                    callSt.setString("@nombre", (String) atributes.get(0));
                    break;
                case "Factura":
                    callSt = con.prepareCall("{call UpdateFactura(?,?,?,?,?,?,?)}");
                    callSt.setInt("@numero", id);
                    callSt.setInt("@id_cliente", Integer.parseInt((String) atributes.get(0)));
                    callSt.setInt("@id_tienda", (Integer) atributes.get(1));
                    callSt.setString("@fecha", (String) atributes.get(2));
                    callSt.setInt("@subtotal", (Integer) atributes.get(3));
                    callSt.setInt("@ISV", (Integer) atributes.get(4));
                    callSt.setInt("@total", (Integer) atributes.get(5));
                    break;
                case "Cliente":
                    callSt = con.prepareCall("{call UpdateCliente(?,?,?)}");
                    callSt.setInt("@id_cliente", id);
                    callSt.setString("@nombre_cliente", (String) atributes.get(0));
                    callSt.setString("@coreoElectronico", (String) atributes.get(1));
                    break;
                default:
                    return;
            }
            callSt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void Insert(String table, ArrayList<Object> atributes) {
        try {
            CallableStatement callSt = null;
            switch (table) {
                case "Tienda":
                    callSt = con.prepareCall("{call InsertTienda(?,?)}");
                    break;
                case "Producto":
                    callSt = con.prepareCall("{call InsertProducto(?,?,?,?)}");
                    break;
                case "Vendedor":
                    callSt = con.prepareCall("{call InsertVendedor(?)}");
                    break;
                case "Factura":
                    callSt = con.prepareCall("{call InsertFactura(?,?,?,?,?,?)}");
                    break;
                case "Cliente":
                    callSt = con.prepareCall("{call InsertCliente(?,?)}");
                    break;
                default:
                    return;
            }
            for (int i = 1; i <= atributes.size(); i++) {
                callSt.setObject(i, atributes.get(i - 1));
            }
            callSt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ResultSet Inventario(int id) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "Select * from Inventario where TiendaID = " + id);
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet HistorialVentas(int id) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "Select * from HistorialVentas where TiendaID = " + id);
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet ProductosPorPais(String ubicacion) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "Select TOP (20) * from ProductosPorPais where ubicacion = '" + ubicacion + "' order by Ventas DESC");
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet ComprasPorCliente(int id) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "Select * from ComprasPorCliente where ClienteID = " + id);
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet Coso(int id) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT p.UPC , p.nombre_producto, t.precio FROM Producto AS p JOIN Tiene AS T ON T.UPC=p.UPC where T.id_tienda = " + id);
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet Prod1MasQueProd2(String menos, String mas) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "with \n"
                    + "menos as(\n"
                    + "	Select * from Prod1MasQueProd2 where nombre_producto='" + menos + "'\n"
                    + "),\n"
                    + "mas as(\n"
                    + "	Select * from Prod1MasQueProd2 where nombre_producto='" + mas + "'\n"
                    + ")\n"
                    + "\n"
                    + "Select P2.id_tienda, P2.nombre_producto, P2.nombre_tienda, P2.Ventas\n"
                    + "from menos as P1 , mas as P2\n"
                    + "where P1.id_tienda=P2.id_tienda and P1.Ventas<P2.Ventas");
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet Top3TiposProductosCompradosExceptLacteo() {
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT TOP 3 id_tipo, nombre_tipo, total_compras "
                    + "FROM Top3TiposProductosCompradosExceptLacteo "
                    + "ORDER BY total_compras DESC");
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void insertarDetalleFactura(int numero, int UPC) {
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO Detalle_Factura (numero, UPC) VALUES (?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, numero);
            stmt.setInt(2, UPC);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet Top20ProductosVendidosPorTienda(int id_tienda) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT Top 20 id_tienda, UPC, nombre_producto, cantidad_vendida, ranking\n"
                    + "FROM Top20ProductosVendidosPorTienda\n"
                    + "WHERE id_tienda = " + id_tienda + ";");
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet Top5TiendasConMasVentas() {
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT id_tienda, nombre_tienda, ventas_totales\n"
                    + "FROM Top5TiendasConMasVentas;\n"
                    + "");
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet bitacora_producto() {
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * from bitacora_producto");
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet bitacora_factura() {
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * from bitacora_factura");
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet bitacora_cliente() {
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * from bitacora_cliente");
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet bitacora_tienda() {
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * from bitacora_tienda");
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet bitacora_vendedor() {
        ResultSet rs = null;
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * from bitacora_vendedor");
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int cosoMierdoso() {
        ResultSet rs = null;
        PreparedStatement ps = null; // Declarar la variable fuera del bloque try

        try {
            ps = con.prepareStatement(
                    "SELECT numero FROM ("
                    + "  SELECT numero, ROW_NUMBER() OVER (ORDER BY numero DESC) AS rn FROM Factura"
                    + ") AS subquery"
                    + " WHERE rn = 1");
            rs = ps.executeQuery();

            if (rs.next()) { // Mover el cursor a la primera fila
                int coso = rs.getInt("numero");
                return coso;
            } else {
                // No se encontraron filas
                return 0; // O un valor predeterminado adecuado
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // O un valor predeterminado adecuado en caso de error
        } finally {
            // Asegúrate de cerrar el ResultSet y la PreparedStatement
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

package org.fhi.reporteador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
//import com.mysql.jdbc.PreparedStatement;
import java.sql.Statement;


public class mysqlConnection {
	private Connection connect;
    private Statement statement;
    //private PreparedStatement preparedStatement ;
    

	public mysqlConnection() {
		try {
			// This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://18.191.244.236:3306/odk_prod?"
                            + "user=root&password=1234");
			System.out.print(":)");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	
	
	//Metodo que cierra la conexion a la DB
	public int Cerrar() {
		try {
			connect.close();
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	
	
	//este metodo realiza la consulta que devuelve la tabla
	public String consulta(String condicion) throws SQLException {
		statement = connect.createStatement();
		int TotalColumna =0;
		String strTabla = "";
		String strConsulta = "select no.id as 'Código' ,no.nombre as \"Nombre\" ,(select count(username) from BITÁCORA_CORE v1 WHERE v1.username = no.id ) AS \"Registro de <br> visitas\",\r\n" + 
				"			  (select count(username) from INSTRUMENTO_CAPACITACION_CORE v1 WHERE v1.username = no.id ) AS \"Registro de <br> Capacitación\",\r\n" + 
				"			  (select count(username) from INSTRUMENTO_A_TECNICA_CORE v1 WHERE v1.username = no.id ) AS \"Acompañamiento a <br> Comunidades\",\r\n" + 
				"              (select count(username) from  INICIO_ACOMPANAMIENTO_SEDE_SABADO_CORE v1 WHERE v1.username = no.id ) AS \"Inicio A. Sábados\",\r\n" + 
				"              (select count(username) from  REPORTAR_ACOMPANAMIENTO_SEDE_SABADO_CORE v1 WHERE v1.username = no.id ) AS \"Reportar A. <br> Sábados\",\r\n" + 
				"              (select count(username) from  FIN_ACOMPANAMIENTO_SEDE_SABADO_CORE v1 WHERE v1.username = no.id ) AS \"Fin A. Sábados\",\r\n" + 
				"              (select count(username) from  INSTRUMENTO_REUNION_MINEDUC_CORE v1 WHERE v1.username = no.id ) AS \"Reuniones <br> MINEDUC\",\r\n" + 
				"              (select count(username) from  SESIONES_TRABAJO_FHI_CORE v1 WHERE v1.username = no.id ) AS \"Sesiones <br> FHI\",\r\n" + 
				"              (select count(username) from  INSTRUMENTO_A_TECNICA_REDES_CORE v1 WHERE v1.username = no.id ) AS \"Acompañamiento a <br> Redes\"             \r\n" + 
				" from nomina no\r\n" + 
				" \r\n" + 
				" where ((select count(username) from BITÁCORA_CORE v1 WHERE v1.username = no.id ) > 0 or \r\n" + 
				" (select count(username) from INSTRUMENTO_CAPACITACION_CORE v1 WHERE v1.username = no.id ) > 0 or\r\n" + 
				" (select count(username) from INSTRUMENTO_A_TECNICA_CORE v1 WHERE v1.username = no.id ) > 0 or\r\n" + 
				" (select count(username) from  INICIO_ACOMPANAMIENTO_SEDE_SABADO_CORE v1 WHERE v1.username = no.id ) > 0 or\r\n" + 
				" (select count(username) from  REPORTAR_ACOMPANAMIENTO_SEDE_SABADO_CORE v1 WHERE v1.username = no.id ) > 0 or\r\n" + 
				" (select count(username) from  FIN_ACOMPANAMIENTO_SEDE_SABADO_CORE v1 WHERE v1.username = no.id ) > 0 or\r\n" + 
				" (select count(username) from  INSTRUMENTO_REUNION_MINEDUC_CORE v1 WHERE v1.username = no.id )  > 0 or\r\n" + 
				" (select count(username) from  SESIONES_TRABAJO_FHI_CORE v1 WHERE v1.username = no.id ) > 0 or\r\n" + 
				" (select count(username) from  INSTRUMENTO_A_TECNICA_REDES_CORE v1 WHERE v1.username = no.id ) > 0) \r\n";
		
		if(condicion == "") {
			strConsulta += ";";
		}else {
			strConsulta += condicion + ";";
		}
		
		ResultSet resultSet = statement.executeQuery(strConsulta);

		TotalColumna = resultSet.getMetaData().getColumnCount();

		strTabla += "<table border= \"3\" align = \"center\" style=\"font-size:10px\"><thead align = \"center\" style=\"font-size:10px\"> <tr align = \"center\" style=\"font-size:10px\">";
		for (int i = 1; i <= TotalColumna; i++) {
			strTabla += "<th align = \"center\" style=\"font-size:10px\">" + resultSet.getMetaData().getColumnName(i) + "</th>";
		}
		strTabla += "</thead></tr>";
		Object strCampo;
		while (resultSet.next()) {
			strTabla += "</tbody><tr align = \"center\" > ";
			for (int i = 1; i <= TotalColumna; i++) {
				strCampo = resultSet.getObject(i);
				if (strCampo == null) {
					strTabla += "<td  align = \"center\" style=\"font-size:10px\">" + "--" + "</td>";
				} else {
					strTabla += "<td  align = \"center\" style=\"font-size:10px\">" + strCampo.toString() + "</td>";
				}
			}
			strTabla += "</tbody></tr>";
		}
		
		strTabla += "</table>";

		statement.close();
		resultSet.close();
		connect.close();
		return strTabla;

	}
	
	public String Deptos() {
		String strSelect = "<select id=\"listaDepto\" class=\"form-control\">";
		try {
			statement = connect.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM departamento;");

			strSelect += "<tr>";
			strSelect += "<option value=\"0\"> Todos</option>";
			while (resultSet.next()) {
				strSelect += "<option value=\"" + resultSet.getObject(1) + "\">" + resultSet.getObject(2)
						+ "</option>";
			}
		

			resultSet.close();
			statement.close();
			connect.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		strSelect += "</select>";
		return strSelect;
	}
	
	public String Munis(String depto) {
		System.out.print(depto);
		String strSelect = "<select id=\"listasede\" class=\"form-control\">";
		try {
			statement = connect.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM sede where id_depto = '" + depto + "';");

			strSelect += "<tr>";
			strSelect += "<option value=\"0\"> Todos</option>";
			while (resultSet.next()) {
				strSelect += "<option value=\"" + resultSet.getObject(2) + "\">" + resultSet.getObject(3)
						+ "</option>";
			}		

			resultSet.close();
			statement.close();
			connect.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		strSelect += "</select>";
		return strSelect;
	}
}

import java.sql.*;

public class CallableStatement01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1. Adım: Driver'a kaydol
        Class.forName("org.postgresql.Driver");

        //2. Adım: Datbase'e bağlan
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/techproed", "postgres", "723546");

        //3. Adım: Statement oluştur.
        Statement st = con.createStatement();

/*
Java'da method'lar return type sahibi olsa da olmasa da method olarak adlandırılır.
SQL'de ise data return ediyorsa "function" denir. Return yapmiyorsa "procedure" olarak adlandırilir
 */
        String sql1 = "CREATE OR REPLACE FUNCTION  toplamaF(x NUMERIC, y NUMERIC)\n" +
                "RETURNS NUMERIC\n" +
                "LANGUAGE  plpgsql\n" +
                "AS\n" +
                "$$\n" +
                "BEGIN \n" +
                "\n" +
                "RETURN X+Y;\n" +
                "end\n" +
                "$$";
        //2.adım functıon u caliştır
        st.execute(sql1);
//3.Adim functıonu calıstır...
        CallableStatement cst1 = con.prepareCall("{? = call toplamaF(?,?)}");

//4. Adım: Return için registerOurParameter() methodunu, parametreler için ise set() ... methodlarını uygula.

        cst1.registerOutParameter(1, Types.NUMERIC);
        cst1.setInt(2, 6);
        cst1.setInt(3, 4);
        cst1.execute();
        cst1.getBigDecimal(1);
        System.out.println(cst1.getBigDecimal(1));
        //2. Örnek: Koninin hacmini hesaplayan bir function yazın.
////2. Örnek: Koninin hacmini hesaplayan bir function yazın.
////1.Adım: Function kodunu yaz:
        String sql2 = "CREATE OR REPLACE FUNCTION  konininHacmiF(r NUMERIC, h NUMERIC)\n" +
                "RETURNS NUMERIC\n" +
                "LANGUAGE plpgsql\n" +
                "AS\n" +
                "$$\n" +
                "BEGIN\n" +
                "\n" +
                "RETURN 3.14*r*r*h/3;\n" +
                "\n" +
                "END\n" +
                "$$";

//2. Adım: Function'ı çalıştır.
        st.execute(sql2);

//3. Adım: Fonction'ı çağır.
        CallableStatement cst2 = con.prepareCall("{? = call konininHacmiF(?, ?)}");

//4. Adım: Return için registerOurParameter() methodunu, parametreler için ise set() ... methodlarını uygula.
        cst2.registerOutParameter(1, Types.NUMERIC);
        cst2.setInt(2, 1);
        cst2.setInt(3, 6);

//5. Adım: execute() methodu ile CallableStatement'ı çalıştır.
        cst2.execute();
        //6. ADIM : Sonucu cagirmak icin return data type tipine gore
        System.out.println(String.format("%.2f", cst2.getBigDecimal(1)));

    }
}
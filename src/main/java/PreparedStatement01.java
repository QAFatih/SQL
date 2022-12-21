import java.sql.*;

public class PreparedStatement01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        //1. Adım: Driver'a kaydol
        Class.forName("org.postgresql.Driver");

        //2. Adım: Datbase'e bağlan
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/techproed", "postgres", "723546");

        //3. Adım: Statement oluştur.
        Statement st = con.createStatement();


     /*
PreparedStatement interface, birden cok kez calistirilabilen onceden derlenmis bir SQL kodunu temsil eder.
Parametrelendirilmis SQL sorgulari(query) ile calisir. Bu sorguyu 0 (sifir) veya daha fazla parametre ile kullanabiliriz.
 */

        //1. Örnek: Prepared statement kullanarak company adı IBM olan number_of_employees
        // değerini 9999 olarak güncelleyin.

        //1.Adım : PreparedStatement query'sini olusturalım
        String sql1 = "update companies set number_of_employees = ? where company= ? ";

        //2.Adım : PreparedStatement objesini olusturalım
        PreparedStatement pst1 = con.prepareStatement(sql1);//donusturduk preparedStatement e


        //3.Adım : set int(),setString .. methdlarini kullanarak soru isaretlerinin yerine deger girelim
        pst1.setInt(1, 9999);
        pst1.setString(2, "IBM");

        //4.Adim Query'i calistir...

        int guncellenensatirSayisi = pst1.executeUpdate();

        System.out.println("guncellenensatirSayisi = " + guncellenensatirSayisi);

        String sql2 = "Select *From companies";
        ResultSet rs1 = st.executeQuery(sql2);
        while (rs1.next()) {
            System.out.println(rs1.getInt(1) + "--" + rs1.getString(2) + "--" + rs1.getInt(3));
        }
        //2. Örnek: Prepared statement kullanarak company
        // adı GOOGLE olan number_of_employees değerini 5555 olarak güncelleyin.
        pst1.setInt(1, 5555);
        pst1.setString(2, "GOOGLE");

        int guncellenensatirSayisi2 = pst1.executeUpdate();

        System.out.println("guncellenensatirSayisi2 = " + guncellenensatirSayisi2);
        ResultSet rs2 =  st.executeQuery(sql2);

        while(rs2.next()){
            System.out.println(rs2.getInt(1)+"--"+rs2.getString(2)+"--"+rs2.getInt(3));
        }
   con.close();
   rs1.close();
   rs2.close();
   pst1.close();
       }


      }


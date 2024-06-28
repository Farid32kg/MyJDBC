import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("Əməliyyatı seçin");
            System.out.println("1. İşçi əlavə etmək");
            System.out.println("2. İşçilərin siyahısı");
            System.out.println("3. İşçiləri yenilə");
            System.out.println("4. İşçini sil");
            System.out.println("5. Çıxış");
            System.out.print("Seçiminiz :");
            int secim = scanner.nextInt();
            scanner.nextLine();

            switch (secim){
                case 1:
                    elaveEt(scanner);
                    break;
                case 2:
                    siyahi();
                    break;
                case 3:
                    yenile(scanner);
                    break;
                case 4:
                    sil(scanner);
                    break;
                case 5:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Yalnış seçim etdiniz");
            }
        }

    }
    public static void elaveEt(Scanner scanner){
        System.out.print("Ad və Soyad :");
        String ad = scanner.nextLine();
        System.out.print("Vəzifə :");
        String vezife = scanner.nextLine();
        System.out.print("Əmək haqqı :");
        double emekhaqqi = scanner.nextDouble();

        String sql = "INSERT INTO işçilər (işçi, vəzifə, əməkhaqqı) VALUES(?,?,?)";

        try (Connection connection = DriverManager.getConnection(Data.URL, Data.USER, Data.PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setString(1,ad);
            preparedStatement.setString(2,vezife);
            preparedStatement.setDouble(3,emekhaqqi);
            preparedStatement.executeUpdate();

            System.out.println("İşçi uğurla əlavə edildi.");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void siyahi(){
        String sql = "SELECT * FROM işçilər";

        try (Connection connection = DriverManager.getConnection(Data.URL, Data.USER, Data.PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet= preparedStatement.executeQuery())
         {
            while (resultSet.next()){
                System.out.println("ID : "+ resultSet.getInt("id"));
                System.out.println("İşçi: "+ resultSet.getString("işçi"));
                System.out.println("Vəzifə: "+resultSet.getString("vəzifə"));
                System.out.println("Əmək haqqı :" + resultSet.getDouble("əməkhaqqı"));
                System.out.println("----------------");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void yenile(Scanner scanner){
        System.out.print("Yeniləmək istədiyiniz işçinin İD nömrəsi :");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Yenilə (ad & soyad) :");
        String ad = scanner.nextLine();
        System.out.print("Yenilə (vəzifə) :");
        String vezife = scanner.nextLine();
        System.out.print("Yenilə (Əmək haqqı)");
        double emekhaqqi = scanner.nextDouble();

        String sql ="UPDATE işçilər SET işçi =?, vəzifə=?, əməkhaqqı=? WHERE id=?";

        try(Connection con = DriverManager.getConnection(Data.URL, Data.USER, Data.PASS);
            PreparedStatement pr=con.prepareStatement(sql)){
            pr.setString(1,ad);
            pr.setString(2,vezife);
            pr.setDouble(3,emekhaqqi);
            pr.setInt(4,id);
            pr.executeUpdate();
            System.out.println("İşçi uğurla yeniləndi");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public static void sil(Scanner scanner){
        System.out.print("Silmək istədiyiniz işçinin İD nömrəsi :");
        int id = scanner.nextInt();
        String sql = "DELETE FROM işçilər WHERE id=?";
        try(Connection con = DriverManager.getConnection(Data.URL,Data.USER,Data.PASS);
        PreparedStatement pr = con.prepareStatement(sql)){
            pr.setInt(1,id);
            pr.executeUpdate();

            System.out.println("Işçi uğurla silindi");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
package rvt.ProduktiDB;

public class Main {
    public static void main(String[] args) {
        Produktdb db = new Produktdb("jdbc:sqlite:produktdb.db");
        System.out.println("Savienojums ar databazi izveidots.");
        new UserInterface(db).start();
        db.close();
        System.out.println("Beigas.");
    }
}

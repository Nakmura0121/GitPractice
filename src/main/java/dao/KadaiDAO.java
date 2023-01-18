package dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.Account;
import dto.Kadai15;
import util.GenerateHashedPw;
import util.GenerateSalt;

public class KadaiDAO {
	private static Connection getConnection() throws URISyntaxException, SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

		return DriverManager.getConnection(dbUrl, username, password);
	}

	public static int registerKadai15(Kadai15 kadai) {
		String sql = "INSERT INTO kadai VALUES(default, ?, ?, ?, ?, ?, ?, ?)";
		int result = 0;

		String salt = GenerateSalt.getSalt(32);

		String hashedPw = GenerateHashedPw.getSafetyPassword(kadai.getPass(), salt);

		System.out.println("生成されたソルト:" + salt);
		System.out.println("生成されたハッシュ値:" + hashedPw);

		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, kadai.getName());
			pstmt.setString(2, kadai.getAge());
			pstmt.setString(3, kadai.getGender());
			pstmt.setString(4, kadai.getTel());
			pstmt.setString(5, kadai.getMail());
			pstmt.setString(6, salt);
			pstmt.setString(7, hashedPw);
			

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			System.out.println(result + "件更新しました。");
		}
		return result;
	}

	public static String getSalt(String mail) {
		String sql = "SELECT salt FROM kadai WHERE mail = ?";

		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, mail);

			try (ResultSet rs = pstmt.executeQuery()){

				if(rs.next()) {
					String salt = rs.getString("salt");
					return salt;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//課題ログイン追記
	public static Kadai15 login(String mail, String hashedPw) {
		String sql = "SELECT * FROM kadai WHERE mail = ? AND pass = ?";
		
		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, mail);
			pstmt.setString(2, hashedPw);

			try (ResultSet rs = pstmt.executeQuery()){
				
				if(rs.next()) {
					String name = rs.getString("name");
					String age = rs.getString("age");
					String gender = rs.getString("gender");
					String tel = rs.getString("tel");
					String email = rs.getString("mail");
					String salt = rs.getString("salt");
					String createdAt = rs.getString("created_at");
					
					return new Kadai15(name, age, gender, tel, email, salt, null, null);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

}

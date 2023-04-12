package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jdbc12_select_join01 {

	public static void main(String[] args) {

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String dbId = "square";
		String dbPwd = "1234";

		Connection con = null;

		PreparedStatement ps = null;

		ResultSet rs = null;

		String sql;

		try {
			Class.forName(driver);
			System.out.println("드라이버 로드 성공!");

			con = DriverManager.getConnection(url, dbId, dbPwd);
			System.out.println("커넥션 객체 생성 성공");

			sql = "select os.order_id, os.cust_id, os.book_id, b.book_name, os.order_date, b.publisher, b.price ";
			sql += " from book b, orders os";
			sql += " where b.book_id = os.book_id";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getInt("order_id") + "\t" +
									rs.getInt("cust_id") + "\t"+
									rs.getInt("book_id") + "\t" +
									rs.getString("book_name") + "\t" +
									rs.getDate("order_date") + "\t"+
									rs.getString("publisher") + "\t" +
									rs.getInt("price"));
			}

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 ERR!: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQL ERR!: " + e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("자원반납 ERR!: " + e.getMessage());
			}
		}
	} // main e

} // class e

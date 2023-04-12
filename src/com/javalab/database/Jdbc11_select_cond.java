package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jdbc11_select_cond {

	public static void main(String[] args) {

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String dbId = "square";
		String dbPwd = "1234";

		// 데이터베이스에 연결하는 다리(bridge)와 같다.
		Connection con = null;

		// 쿼리문에 인자를 전달해서 SQL 구문을 실행해주는 객체
		PreparedStatement pstmt = null;

		// 실행된 결과를 받아오는 객체
		ResultSet rs = null;

		String sql;

		try {
			Class.forName(driver);
			System.out.println("드라이버 로드 성공!");

			con = DriverManager.getConnection(url, dbId, dbPwd);
			System.out.println("커넥션 객체 생성 성공");

			String str = "역도";

			sql = "select b.book_id, b.book_name, b.price, b.publisher";
			sql += " from book b";
			sql += " where b.book_name like concat('%' || ?, '%')";
			sql += " and b.price >= 10000";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, str);
			rs = pstmt.executeQuery();

			System.out.println("조회하신 도서는?");

			while (rs.next()) {
				System.out.println(rs.getInt("book_id") + "\t" + rs.getString("book_name") + "\t" + rs.getInt("price")
						+ "\t" + rs.getString("publisher"));
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
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("자원닫기 ERR: " + e.getMessage());
			}
		}
	} // main e

} // class e

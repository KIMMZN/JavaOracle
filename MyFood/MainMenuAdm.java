package MyFood;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class MainMenuAdm {
	
	MainMenuAdm() {
		
		init1();
		insert();
		delete();
		edit();
		list();
	}
	
	
	private void init1() {
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("로드");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void insert() {
		FoodOneDTO food = new FoodOneDTO();
		food.setName("햄버거");
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
			System.out.println("커넥션 자원 획득");
			String sql = "insert into myfood1 values(?,default)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//매핑
			pstmt.setString(1, food.getName());
			//실행 후 리턴 값 가져오기
			int result = pstmt.executeUpdate();
			if(result ==0) {
				conn.rollback();
			}else {
				conn.commit();
			}
			System.out.println("음식 등록 완료ㄴ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void delete() {
		Scanner in = new Scanner(System.in);
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "system";
		String pass = "11111111";
		
		try {
			conn = DriverManager.getConnection(url, user, pass);
			System.out.println("커넥션 자원 획득");
			System.out.println("삭제할 음식의 이름을 입력하시오");
			String tempName = in.nextLine();
			String sql = "delete from myfood1 where name = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//매핑
			pstmt.setString(1, tempName); // 바인딩
			//실행 후 리턴 값 가져오기
			int result = pstmt.executeUpdate();
			if(result == 0) {
				conn.rollback();
			}else {
				conn.commit();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void edit() {
		Scanner in = new Scanner(System.in);
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "system";
		String pass = "11111111";
		
		try {
			conn = DriverManager.getConnection(url, user, pass);
			System.out.println("커넥션 자원 획득");
			System.out.println("음식명을 수정합니다");
			System.out.println("음식명을 입력하시오");
			String tempName = in.nextLine();
			System.out.println("수정할 음식명을 입력하시오");
			String editName = in.nextLine();
			String sql = "update myfood1 set name = ? where name = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//매핑
			
			pstmt.setString(2, tempName); // 바인딩
			pstmt.setString(1, editName );
			//실행 후 리턴 값 가져오기
			int result = pstmt.executeUpdate();
			
			if(result == 0) {
				conn.rollback();
			}else {
				conn.commit();
			}
			System.out.println("수정완료");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private void list() {
		
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "system";
		String pass = "11111111";
		
		try {
			conn = DriverManager.getConnection(url, user, pass);
			System.out.println("커넥션 자원 획득");
			String sql = "select * from myfood1";
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					System.out.println("음식의 이름: " + rs.getString("name"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//매핑
		
			//실행 후 리턴 값 가져오기
			int result = pstmt.executeUpdate();
			if(result == 0) {
				conn.rollback();
			}else {
				conn.commit();
			}
			System.out.println("수정완료");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	
	
	
}

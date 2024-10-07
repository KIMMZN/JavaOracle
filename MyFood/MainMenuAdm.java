package MyFood;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class MainMenuAdm {
	
	MainMenuAdm() {
		
		//init1();
		//insert();
		//delete();
		//edit();
		//list();
		menu();
		
	}
	
	private void menu() {
		init1();
		Scanner in = new Scanner(System.in);
		
		
		while(true) {
			menusel();
			int temp = in.nextInt();
			in.nextLine();
			
			if(temp == 1) {
				insert();
				
			}else if (temp ==2) {
				delete();
				
			}else if(temp ==3) {
				edit();
				
			}else if(temp == 4) {
				list();
				
			}else if(temp == 0) {
				System.out.println("종료");
				break;
			}
			else {
				System.out.println("잘못된접근");
			}
	   }
		
	}
	
	private void menusel() {
		System.out.println("1.음식 등록");
		System.out.println("2.음식 삭제");
		System.out.println("3.음식 수정");
		System.out.println("4.전체보기");
		System.out.println("0.종료");
		System.out.println(">> 숫자를 입력하시오");
		
	}
	
	
	private void init1() {
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 데이터베이스 드라이버를 로드하는 작업
			System.out.println("로드");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void insert() { // db, name 에 unique 설정해 둬서 중복된거 안들어감
		FoodOneDTO food = new FoodOneDTO(); //DTO 객체 생성
		Scanner in = new Scanner(System.in);
		System.out.println("등록할 음식명을 입력하시오");
		String fname = in.nextLine();
		food.setName(fname);
		
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
			System.out.println("음식 등록 완료");
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
		return;
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
					System.out.println("음식의 이름: " + rs.getString("name")+ "  indate: " + rs.getTimestamp("indate"));
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
			System.out.println("-----------------");
			
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

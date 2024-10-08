package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.FishDTO;

//Fishdata table CRUD
public class FishDAO {
	// "jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111"
	private String username= "system";
	private String password= "11111111";
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String driverName= "oracle.jdbc.driver.OracleDriver";
	private Connection conn = null;
	
	public FishDAO() {
		init();
	}
	private void init() { // 드라이버 로드
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 데이터베이스 드라이버를 로드하는 작업
			System.out.println("로드");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private boolean conn() { // 커넥션 가져오는 공통 코드를 메서드로 정의
		;
			try {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
						"system", "11111111");				
				System.out.println("커넥션 자원 획득");
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
	}
	
	public ArrayList<FishDTO> selectAll() {
		ArrayList<FishDTO> flist = new ArrayList<>();
		if(conn()) {
			try {
				String sql="select * from fishdata";
				PreparedStatement psmt = conn.prepareStatement(sql);
				ResultSet rs = psmt.executeQuery();
				//Resultset은 테이블 형식으로 가져온다고 이해합니다.
				while(rs.next()) {//next()메서드는 rs에서 참조하는 테이블에서
									//튜플을 순차적으로 하나씩 접근하는 메서드
					FishDTO fishTemp = new FishDTO();
					//rs.getString("id") rs가 접근한 튜플에서 id컬럼의 값을 가져옴
					fishTemp.setId(rs.getString("id"));
					fishTemp.setPwd(rs.getString("pwd"));
					fishTemp.setIndate(rs.getString("indate"));
					flist.add(fishTemp);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}
		}
		return flist;
	}
	
	public FishDTO selectOne(String findId) {
		//if(!conn() ) {
		//	return null;
		//}
		if(conn()) {
			try {
				String sql = "select * from fishdata where id=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, findId);
				ResultSet rs = psmt.executeQuery();
				if(rs.next()) { //쿼리 결과가 튜플 하나일 경우는 이렇게 해도 됨
					FishDTO fishTemp = new FishDTO();
					//rs.getstring("id) rs가 접근한 튜플에서 id칼럼의 값을 가져옴
					fishTemp.setId(rs.getString("id"));
					fishTemp.setPwd(rs.getString("pwd"));
					fishTemp.setIndate(rs.getString("indate"));
					//객체 생성하여 만들고 값을 가져와 셋한뒤
					//객체를 리턴
					return fishTemp; // 
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//finally에 conn자원 반납코드 추가
			finally {
				try {
					if(conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			//return null;
		}
		return null;
		
	}
	public void delete(String delId) {
		if(conn()) {
			try {
				String sql = "delete from fishdata where id=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, delId);
				psmt.executeUpdate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					if(conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public void add(FishDTO fdto) {
		if(conn()) {
			
			try {
				String sql = "insert into fishdata values (?,?,default)";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, fdto.getId());
				psmt.setString(2, fdto.getPwd());
				//쿼리실행
				//psmt.executeUpdate();
				//쿼리 실행 리턴을 받아서 다음 처리 작업 정의
				int resultInt = psmt.executeUpdate();
				
				//트랜잭션 처리
				if(resultInt > 0) {
					conn.commit();
				}else {
					conn.rollback();
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			System.out.println("데이터베이스 개선실패");
		}
	}
	public void update(FishDTO fdto) {
		if(conn()) {
			try {
				String sql = "update fishdata set pwd=? where id=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(2, fdto.getId());
				psmt.setString(1, fdto.getPwd());
				psmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					if(conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		
	}
	
	
	

}

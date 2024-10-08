package service;

import java.util.ArrayList;
import java.util.Scanner;

import dao.FishDAO;
import dto.FishDTO;

public class FishService {
	//Fishdata 테이블에 데이터를 입력하기 위해서는 fishDAO 객체에 의존한다.
	FishDAO fishdao = new FishDAO();
	
	public void menu() {
		Scanner in = new Scanner(System.in);
		while(true) {
			System.out.println("1.등록");
			System.out.println("2.삭제");
			System.out.println("3.검색");
			System.out.println("4.전체보기");
			System.out.println("5.수정");
			System.out.println("6.종료");
			int selNum = in.nextInt(); in.nextLine();
			if(selNum ==1 ) {
				System.out.println("1.등록");
				add();
			}else if(selNum ==2) {
				del();
			}else if(selNum ==3) {
				search();
			}else if(selNum ==4) {
				list();
			}else if(selNum ==5) {
				update();
			}else if(selNum ==6) {
				break;
			}
		}
		
		
	}
	
	private void add() {
		Scanner in = new Scanner(System.in);
		System.out.println("신규 Fish 등록");
		System.out.println("아이디를 입력");
		String id = in.nextLine();
		System.out.println("암호를 입력");
		String pass = in.nextLine();
		//DTO에 저장
		FishDTO fishdto = new FishDTO();
		fishdto.setId(id);
		fishdto.setPwd(pass);
		
		fishdao.add(fishdto);
		
	}
	
	
	
	private void del() {
		Scanner in = new Scanner(System.in);
		System.out.println("삭제할 아이디를 입력하시오");
		String delId = in.nextLine();
		// 삭제하기 위한 쿼리문 delete from fishdata where id=?
		// 커리에 ?가 존재, ?는 위에서 입력받은 ㄱ밧을 저장하는 delId
		fishdao.delete(delId);
		
	}
	private void search() {
		Scanner in = new Scanner(System.in);
		System.out.println("검색할 아이디를 입력하시오");
		String findId = in.nextLine();
		//fishdao.selectAll();
		//FishDTO FF = fishdao.selectOne(findId);
		FishDTO f = fishdao.selectOne(findId);
		if(f != null) {
			System.out.println(f.toString());
		}
		
	}
	private void list() {
		//fisadao.selectall() 메서드 실행시 리턴받은 list의 주소값을 변수명 list<fishdto>f에 저장
		ArrayList<FishDTO> f = fishdao.selectAll();
		//db에 저장된 정보를 모두 출력..
		System.out.println(f.size()+" 마리의 물고기가 있습니다.");
		for(FishDTO tempf : f) {
			System.out.println(tempf.toString());
		}
		
	}
	private void update() {
		Scanner in = new Scanner(System.in);
		System.out.println("검색할 아이디를 입력하시오");
		String findId = in.nextLine();
		//시나리오.. 먼저 아이디를 입력 받아서 해당 정보를 가져온다.
		FishDTO f = fishdao.selectOne(findId);
		if(f != null) {
			System.out.println("수정할 정보는 다음과 같습니다.");
			System.out.println(f.toString());
		}
		System.out.println("비밀번호를 수정시오");
		String delPass = in.nextLine();
		f.setPwd(delPass);
		//이제 DTO를 DAO로 넘겨서 UPDATE쿼리문을 실행시키면 된다.
		fishdao.update(f);
		
	}

}

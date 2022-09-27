package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.java.BAM.dto.Member;
import com.koreaIT.java.BAM.util.Util;

public class MemberController extends Controller {
	private List<Member> members;
	private Scanner sc;
	
	

	public MemberController(Scanner sc) {
		this.members = new ArrayList<>();
		this.sc = sc;

	}

	@Override
	public void doAction(String cmd, String methodName) {
		

		switch (methodName) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		case "profile":
			showProfile();
			break;

		default:
			System.out.println("존재하지 않는 명령문입니다.");
			break;
		}
	}

	private void doLogout() {
		loginedMember = null;
		System.out.println("로그아웃 되었습니다");

	}

	private void showProfile() {
			System.out.println("== 내정보 ==");
			System.out.printf("로그인 아이디:%s\n", loginedMember.loginId);
			System.out.printf("이름 :%s\n", loginedMember.name);
		
	}

	private void doJoin() {
		int id = members.size() + 1;
		String loginId = null;
		String regDate = Util.getNowDateStr();
		while (true) {
			System.out.printf("로그인 아이디 :");
			loginId = sc.nextLine();
			if (loginIdchk(loginId) == false) {
				System.out.printf("%S은(는)이미 사용중인 아이디입니다\n", loginId);
				continue;
			}
			System.out.printf("%s은(는)사용 가능한 아이디입니다\n", loginId);
			break;
		}
		String loginPw = null;
		String loginPwChk = null;

		while (true) {
			System.out.printf("로그인 비밀번호 :");
			loginPw = sc.nextLine();
			System.out.printf("비밀번호 확인 :");
			loginPwChk = sc.nextLine();

			if (loginPw.equals(loginPwChk) == false) {
				System.out.println("비밀번호를 다시 입력해주세요");
				continue;
			}
			break;
		}

		System.out.printf("이름 :");
		String Name = sc.nextLine();

		Member member = new Member(id, regDate, loginId, loginPw, Name);

		members.add(member);

		System.out.printf("%s 회원님 환영합니다\n", loginId);
	}

	private void doLogin() {
		if (isLoigned()) {
			System.out.println("이미 로그인 상태입니다.");
			return;
		}
		System.out.printf("로그인 입력 :");
		String loginId = sc.nextLine();
		System.out.printf("비밀번호 입력 :");
		String loginPw = sc.nextLine();
		// 사용자의 입력 아아디와 일치하는 회원이 있는지 확인
		Member member = getMemberByLoginId(loginId);

		if (member == null) {
			System.out.println("일치하는 회원이 없습니다");
			return;
		}
		if (member.loginPw.equals(loginPw) == false) {
			System.out.println("비밀번호를 확인해주세요");
			return;
		}

		loginedMember = member;

		System.out.printf("로그인 성공! %s님 환영합니다\n", loginedMember.name);
	}


	public void makeTestData() {
		System.out.println("테스트를 위한 회원 데이터를 생성합니다");
		members.add(new Member(1, Util.getNowDateStr(), "test1", "test1", "김철수"));
		members.add(new Member(1, Util.getNowDateStr(), "test2", "test2", "김영희"));
		members.add(new Member(1, Util.getNowDateStr(), "test3", "test3", "박영수"));

	}

	private Member getMemberByLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);

		if (index != -1) {
			return members.get(index);
		}

		return null;
	}

	private boolean loginIdchk(String loginId) {
		int index = getMemberIndexByLoginId(loginId);

		if (index == -1) {
			return true;
		}

		return false;

	}

	private int getMemberIndexByLoginId(String loginId) {
		int i = 0;
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}
		return -1;

	}

}

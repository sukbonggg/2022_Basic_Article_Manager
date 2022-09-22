package controller;

import java.util.List;
import java.util.Scanner;
import com.koreaIT.java.BAM.dto.Member;
import com.koreaIT.java.BAM.util.Util;

public class MemberController extends Controller{
	List<Member> members;
	Scanner sc;
	String cmd;
	public MemberController(List<Member> members, Scanner sc) {
		this.members = members;
		this.sc = sc;
		
	}
	
	@Override
	public void doAction(String cmd) {
		// 
		this.cmd =cmd;
	}

	public void doJoin() {
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

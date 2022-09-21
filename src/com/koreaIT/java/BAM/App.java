package com.koreaIT.java.BAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.java.BAM.dto.Article;
import com.koreaIT.java.BAM.dto.Member;
import com.koreaIT.java.BAM.util.Util;

public class App {
	private List<Article> articles;
	private List<Member> members;

	App() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void run() {

		System.out.println("== 프로그램 시작 ==");

		makeTestData();

		Scanner sc = new Scanner(System.in);

		while (true) {

			System.out.printf("명령어) ");
			String cmd = sc.nextLine().trim();

			if (cmd.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}

			if (cmd.equals("exit")) {
				break;
			}

			if (cmd.equals("member join")) {
				int id = members.size() + 1;
				String loginId=null;
				String regDate = Util.getNowDateStr();
				while (true) {
					System.out.printf("로그인 아이디 :");
					 loginId = sc.nextLine();
					if (loginIdchk(loginId) == false) {
						System.out.printf("%S은(는)이미 사용중인 아이디입니다\n", loginId);
						continue;
					}
					System.out.printf("%s은(는)사용 가능한 아이디입니다\n",loginId);
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

			} else if (cmd.equals("article write")) {
				int id = articles.size() + 1;

				String regDate = Util.getNowDateStr();
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();

				Article article = new Article(id, regDate, title, body);

				articles.add(article);

				System.out.printf("%d번 글이 생성되었습니다\n", id);

			} else if (cmd.startsWith("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시물이 없습니다");
					continue;
				}

				List<Article> forPrintArticles = articles;

				String searchKeyword = cmd.substring("article list".length()).trim();
				System.out.println("검색어 : " + searchKeyword);
				if (searchKeyword.length() > 0) {
					forPrintArticles = new ArrayList<>();

					for (Article article : articles) {
						if (article.title.contains(searchKeyword)) {
							forPrintArticles.add(article);
						}
					}
					if (forPrintArticles.size() == 0) {
						System.out.println("검색결과 없음");
						continue;
					}
				}

				System.out.println("번호	|	제목	|	날짜			|	조회");
				for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
					Article article = forPrintArticles.get(i);
					System.out.printf("%d	|	%s	|	%s\n", article.id, article.title, article.regDate,
							article.viewCnt);
				}
			} else if (cmd.startsWith("article detail ")) {

				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;

				}

				foundArticle.addViewCnt();

				System.out.printf("번호 : %d\n", foundArticle.id);
				System.out.printf("날짜 : %s\n", foundArticle.regDate);
				System.out.printf("제목 : %s\n", foundArticle.title);
				System.out.printf("내용 : %s\n", foundArticle.body);
				System.out.printf("조회 : %d\n", foundArticle.viewCnt);

			} else if (cmd.startsWith("article delete ")) {

				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);

				int foundIndex = getArticleIndexById(id);

				if (foundIndex == -1) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}

				articles.remove(foundIndex);

				System.out.printf("%d번 게시물이 삭제되었습니다\n", id);

			} else if (cmd.startsWith("article modify ")) {

				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}
				System.out.printf("수정할 제목 : ");
				String title = sc.nextLine();
				System.out.printf("수정할 내용 : ");
				String body = sc.nextLine();

				foundArticle.title = title;
				foundArticle.body = body;

				System.out.printf("%d번 글이 수정되었습니다\n", id);

			} else {
				System.out.println("존재하지 않는 명령어 입니다");
			}
		}

		System.out.println("== 프로그램 끝 ==");

		sc.close();
	}

	private boolean loginIdchk(String loginId) {
		int index =getMemberIndexByLoginId(loginId);
		
		if(index ==-1) {
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
	
	

	private int getArticleIndexById(int id) {
		int i = 0;
		for (Article article : articles) {
			if (article.id == id) {
				return i;
			}
			i++;
		}
		return -1;

	}

	private Article getArticleById(int id) {

		int index = getArticleIndexById(id);

		if (index != -1) {
			return articles.get(index);
		}
		return null;

	}

	private void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다");
		articles.add(new Article(1, Util.getNowDateStr(), "제목1", "내용1", 11));
		articles.add(new Article(2, Util.getNowDateStr(), "제목2", "내용2", 11));
		articles.add(new Article(3, Util.getNowDateStr(), "제목3", "내용3", 11));
	}

}

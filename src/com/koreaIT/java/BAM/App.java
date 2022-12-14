package com.koreaIT.java.BAM;

import java.util.Scanner;

import controller.ArticleController;
import controller.Controller;
import controller.MemberController;

public class App {
	public void run() {

		System.out.println("== 프로그램 시작 ==");

		Scanner sc = new Scanner(System.in);

		MemberController memberController = new MemberController(sc);

		ArticleController articleController = new ArticleController(sc);

		articleController.makeTestData();
		memberController.makeTestData();
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

			String[] cmdBits = cmd.split(" ");

			if (cmdBits.length == 1) {
				System.out.println("명령어를 확인해주세요");
				continue;
			}
			String controllerName = cmdBits[0];// article
			String methodName = cmdBits[1];// list

			Controller controller = null;

			if (controllerName.equals("article")) {
				controller = articleController;
			} else if (controllerName.equals("member")) {
				controller = memberController;

			} else {
				System.out.println("존재하지 않는 명령어 입니다");
				continue;
			}
//			String actionName = controllerName + "/" + methodName;
			switch (methodName) {
			case "write":
			case "modify":
			case "delete":
			case "logout":
			case "profile":
				if (Controller.isLoigned() == false) {
					System.out.println("로그인 후 이용해주세요");
					continue;
				}
				break;
			case "login":
			case "join":
				if (Controller.isLoigned()) {
					System.out.println("로그아웃 후 이용해주세요");
					continue;
				}
				break;
			}

			controller.doAction(cmd, methodName);
		}

//			if (cmd.equals("member join")) {
//				memberController.doJoin();
//			} else if (cmd.equals("article write")) {
//				articleController.doWrite();							 
//			} else if (cmd.startsWith("article list")) {
//				articleController.showList(cmd);
//			} else if (cmd.startsWith("article detail ")) {
//				articleController.showDetial(cmd);
//			} else if (cmd.startsWith("article delete ")) {
//				articleController.doDelete(cmd);
//			} else if (cmd.startsWith("article modify ")) {
//				articleController.showModify(cmd);
//			} else {
//				System.out.println("존재하지 않는 명령어 입니다");

		System.out.println("== 프로그램 끝 ==");

		sc.close();
	}

}

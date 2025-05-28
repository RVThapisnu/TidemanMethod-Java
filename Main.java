class Main {

	public static void main(String args[]) {
		// Tests //
		MyTest.OneCandOneVoter();
		MyTest.OneCandMultiVoter();
		MyTest.MultiCandOneVoter();
		MyTest.MultiCandMultiVoter();
		MyTest.AllTie();
		MyTest.SomeTie();
		MyTest.Example1();
	}

}

class MyTest {

	static VotingSystem election = new VotingSystem();

	static void OneCandOneVoter() {

		String Title = "One Cand One Voter";

		String[] cand = { "Alice" };

		String[][] voters = {
			{ cand[0] }
		};

		String expected = "Alice";

		performTest(Title,voters,cand,expected);

	}

	static void OneCandMultiVoter() {

		String Title = "One Cand Multiple Voter";

		String[] cand = { "Alice" };

		String[][] voters = {
			{ cand[0] },
			{ cand[0] }
		};

		String expected = "Alice";

		performTest(Title,voters,cand,expected);

	}

	static void MultiCandOneVoter() {

		String Title = "Multi Cand One Voter";

		String[] cand = { "Alice" , "Bob" };

		String[][] voters = {
			{ cand[0] , cand[1] }
		};

		String expected = "Alice";

		performTest(Title,voters,cand,expected);

	}

	static void MultiCandMultiVoter() {

		String Title = "Multi Cand Multi Voter";

		String[] cand = { "Alice" , "Bob" };

		String[][] voters = {
			{ cand[0] , cand[1] },
			{ cand[0] , cand[1] },
			{ cand[1] , cand[0] }
		};

		String expected = "Alice";

		performTest(Title,voters,cand,expected);

	}

	static void AllTie() {

		String Title = "All Tie";

		String[] cand = { "Alice" , "Bob" };

		String[][] voters = {
			{ cand[0] , cand[1] },
			{ cand[1] , cand[0] },
		};

		String expected = "Tie";

		performTest(Title,voters,cand,expected);

	}

	static void SomeTie() {

		String Title = "Some Tie";

		String[] cand = { "Alice" , "Bob" , "Charlie" };

		String[][] voters = {
			{ cand[2] , cand[1] , cand[0] },
			{ cand[2] , cand[0] , cand[1] },
		};

		String expected = "Charlie";

		performTest(Title,voters,cand,expected);

	}

	static void Example1() {

		String Title = "Example 1";

		String[] cand = { "Alice" , "Bob" , "Charlie" };

		String[][] voters = {
			{ cand[0] , cand[1] , cand[2] },
			{ cand[0] , cand[1] , cand[2] },
			{ cand[0] , cand[1] , cand[2] },
			{ cand[1] , cand[2] , cand[0] },
			{ cand[1] , cand[2] , cand[0] },
			{ cand[2] , cand[0] , cand[1] },
			{ cand[2] , cand[0] , cand[1] },
			{ cand[2] , cand[0] , cand[1] },
			{ cand[2] , cand[0] , cand[1] }
		};

		String expected = "Charlie";

		performTest(Title,voters,cand,expected);

	}

	static void performTest(String title,String[][] voters,String[] cands,String expected) {

		Helper.println("--- " + title + " ---");

		election.setVoters(voters);
		election.setCandidates(cands);

		String winner = election.findWinner();

		if(winner.equals(expected)) {
			Helper.println(title + " Test Success !!!!");
		} else {
			Helper.println(title + " Test Failed !!!!");
			Helper.println("Winner = " + winner);
		}

	}

}
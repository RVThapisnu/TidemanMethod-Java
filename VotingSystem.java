import java.util.Scanner;
import java.util.Arrays;

class VotingSystem {

	private String[] candidates;
	private String[][] votes;

	public VotingSystem() {}

	public VotingSystem(String[] candidates,String[][] votes) {
		setCandidates(candidates);
		setVoters(votes);
	}

	public void setCandidates(String[] candidates) {
		this.candidates = candidates;
	}

	public void setVoters(String[][] votes) {
		this.votes = votes;
	}

	public String findWinner() {
		if (candidates.length == 1) {
			return candidates[0];
		}
		Candidates cands = new Candidates(candidates);
		Pairs pairs = new Pairs(votes,cands);
		if(pairs.isTie()) {
			return "Tie";
		}
		pairs.sortPairs();
		Graph locks = pairs.lockPairs();
		String winner = locks.findUnlocked();
		return winner;
		//Helper.println("Winner : " + winner);
		//pairs.showPairs();
	}

}

class Pairs {
	private Candidates candidates;
	private Pair[] pairs;
	private int length;
	private int ties;

	public Pairs(String[][] votes,Candidates candidates) {
		int cand_count = candidates.getCount();
		this.ties = 0;
		this.candidates = candidates;
		this.length = cand_count * (cand_count - 1) / 2;
		Helper.println("Length : " + length);
		this.pairs = new Pair[length];
		createPairs(votes);
	}

	public void createPairs(String[][] votes) {
		int count = 0;
		int cand_count = candidates.getCount();

		for(int i = 0,l = cand_count - 1 ; i < l ; i ++) {
			for(int j = i+1 ; j < cand_count ; j ++) {
				count = createPair(i,j,count,votes);
			}
		}

		showPairs();
	}

	public int createPair(int i1,int i2,int count,String[][] votes) {
		Candidate c1 = candidates.getCandidate(i1);
		Candidate c2 = candidates.getCandidate(i2);
		pairs[count] = new Pair(c1,c2,votes);

		if(pairs[count].tie) {
			ties ++;
		}

		count ++;
		return count;
	}

	public void showPairs() {
		for(Pair p:pairs) {
			int index = p.tie ? -1 : p.winner.index;
			String name = p.tie ? "Tie" : p.winner.name;

			String winnerInfo = index + " : " + name;

			Helper.println(winnerInfo + " = " + p.marks[0]);
		}
	}

	public boolean isTie() {
		return ties == length;
	}

	public void sortPairs() {

		for(int i = 0,l = length - 1 ; i < l ; i ++) {

			for(int j = i + 1 ; j < length ; j ++) {

				Pair p1 = pairs[i];
				Pair p2 = pairs[j];

				if(p1.marks[0] < p2.marks[0] && !p2.tie) {
					pairs[i] = p2;
					pairs[j] = p1;
				}

			}

		}

	}

	public Graph lockPairs() {
		Graph graph = new Graph(candidates);
		for(Pair pair:pairs) {
			if(!pair.tie) {
				graph.lock(pair.winner.index,pair.loser.index);
				//Helper.println(pair.winner.name + " -> "+ pair.loser.name);
			}
		}
		// graph.showLocks();
		return graph;
	}

}

class Graph {
	private Node[] nodes;
	private int count;
	private int[][] matrix;

	public Graph(Candidates candidates) {
		int size = candidates.getCount();
		this.nodes = new Node[size];
		this.count = 0;
		this.matrix = new int[size][size];
		fillMatrix();
		fillNodes(candidates);
	}

	public void fillNodes(Candidates candidates) {
		Candidate[] cands = candidates.getCandidates();
		for(Candidate candidate : cands) {
			addNode(candidate.name);
		}
	}

	public void addNode(String name) {
		nodes[count] = new Node(count,name);
		count ++;
	}

	public void lock(int src,int dest) {
		matrix[src][dest] = 1;
		if(isDeadLock(src,dest,src)) {
			matrix[src][dest] = 0;
		} else {
			nodes[dest].lockedBy ++;
		}
	}

	public boolean isLocked(int src,int dest) {
		boolean locked = matrix[src][dest] == 1 ? true : false;
		return locked;
	}

	public boolean isDeadLock(int w,int l,int cv) {
		if(isLocked(l,cv)) {
			return true;
		}
		for(int i=0;i < matrix[l].length;i ++) {
			if(isLocked(l,i)){
				return isDeadLock(l,i,cv);
			}
		}
		return false;
	}

	public void showLocks() {
		for(int r=0;r < count;r ++) {
			for(int c=0;c < count;c ++) {
				if(matrix[r][c] == 1) {
					Helper.println(nodes[r].name + " locked " + nodes[c].name);
				}
			}
		}
	}

	public String findUnlocked() {
		for(int r=0;r < count;r ++) {
			if(nodes[r].lockedBy == 0) {
				return nodes[r].name;
			}
		}
		return "";
	}

	public void fillMatrix() {
		for(int[] m:matrix) {
			Arrays.fill(m,0);
		}
	}
}

class Node {
	public int index;
	public String name;
	public int lockedBy;

	Node(int index,String name) {
		this.index = index;
		this.name = name;
		this.lockedBy = 0;
	}
}

class Pair {
	public Candidate winner;
	public Candidate loser;
	public int[] marks;
	public boolean tie;

	public Pair(Candidate c1,Candidate c2,String[][] votes) {
		this.marks = new int[2];
		this.tie = false;
		compareCandidates(c1,c2,votes);

	}

	public void compareCandidates(Candidate c1,Candidate c2,String[][] votes) {

		for(String[] v1:votes) {
			for(String v2:v1) {
				boolean r1 = v2.equals(c1.name);
				boolean r2 = v2.equals(c2.name);
				if(r1) {
					marks[0] += 1;
					break;
				}
				else if(r2) {
					marks[1] += 1;
					break;
				} else {
					continue;
				}
			}
		}

		Helper.println(c1.name + " = " + marks[0]);
		Helper.println(c2.name + " = " + marks[1]);

		winner = marks[0] > marks[1] ? c1 : c2;
		loser = marks[0] > marks[1] ? c2 : c1;
		tie = marks[0] == marks[1];

		if (winner.name.equals(c2.name)) {

			int temp = marks[0];

			marks[0] = marks[1];
			marks[1] = temp;

		}

		//if(marks[0] > marks[1]) {
			//winner = c1;
			//loser = c2;
		//}
		//else if(marks[1] > marks[0]) {
			//winner = c2;
			//loser = c1;
			//int temp = marks[0];
			//marks[0] = marks[1];
			//marks[1] = temp;
		//}
		//else if(marks[0] == marks[1]) {
			//winner = c1;
			//loser = c2;
			//this.tie = true;
		//}
	}
}

class Candidates {
	private Candidate[] candidates;
	private int count;

	Candidates(String[] candidates) {
		this.candidates = new Candidate[candidates.length];
		fillCandidates(candidates);
	}

	void fillCandidates(String[] candidates) {
		for(String candidate : candidates) {
			this.candidates[count] = new Candidate(count,candidate);
			this.count ++;
		}
	}

	String getName(int index) {
		return candidates[index].name;
	}

	int getIndex(int index) {
		return candidates[index].index;
	}

	Candidate getCandidate(int index) {
		return candidates[index];
	}

	Candidate[] getCandidates() {
		return candidates;
	}

	public int getCount() {
		return count;
	}
}

class Candidate {
	public int index;
	public String name;

	Candidate(int index,String name) {
		this.index = index;
		this.name = name;
	}
}

class Helper {
	static Scanner scan = new Scanner(System.in);

	static <T> void print(T msg) {
		System.out.print(msg);
	}
	static <T> void println(T msg) {
		System.out.println(msg);
	}
	static String input(String msg) {
		String output = "";
		print(msg);
		output = scan.nextLine();
		return output;
	}
	static void close_input() {
		scan.close();
	}
}
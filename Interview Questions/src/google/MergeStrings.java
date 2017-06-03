package google;

public class MergeStrings {
	public static void main(String args[]) {
		String s1 = "one";
		String s2 = "two";
		
		mergeStrings(new StringBuilder(s1), new StringBuilder(s2), new StringBuilder());
	}

	private static void mergeStrings(StringBuilder s1, StringBuilder s2,
			StringBuilder prefix) {
		
		if(s1.length() == 0 || s2.length() == 0) {
			StringBuilder out = new StringBuilder(prefix);
			out.append(s1);
			out.append(s2);
			System.out.println(out);
			return;
		}
		
		char c1 = s1.charAt(0);
		prefix.append(c1);
		s1.deleteCharAt(0);
		mergeStrings(s1,s2,prefix);
		s1.insert(0,c1);
		prefix.deleteCharAt(prefix.length()-1);
		
		char c2 = s2.charAt(0);
		prefix.append(c2);
		s2.deleteCharAt(0);
		mergeStrings(s1,s2,prefix);
		s2.insert(0,c2);
		prefix.deleteCharAt(prefix.length()-1);
	}
}

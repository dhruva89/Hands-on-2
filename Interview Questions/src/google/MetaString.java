package google;

public class MetaString {
	public static void main(String args[]) {
		String s1 = "Converse";
		String s2 = "Conserve";

		System.out.println(isMetaString(s1, s2));
	}

	private static boolean isMetaString(String string1, String string2) {
		String s1 = string1.toLowerCase();
		String s2 = string2.toLowerCase();
		int diff = 0;
		int diffIndex = 0;
		if (s1.length() != s2.length() || s1.length() == 0) {
			return false;
		}
		for (int i = 0; i < s1.length(); i++) {
			if (s1.charAt(i) == s2.charAt(i)) {
				continue;
			}
			diff++;
			if (diff > 2) {
				break;
			} else if (diff == 1) {
				diffIndex = i;
			} else {
				if (s2.charAt(i) == s1.charAt(diffIndex)) {
					continue;
				} else {
					diff++;
					break;
				}
			}
		}

		if (diff > 2) {
			return false;
		}
		return true;
	}
}

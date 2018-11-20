package squaredungeon.main;

public class initItems {
	private static String difficulty;

	public static void setDif(String dif, boolean firstInit, boolean switchDif) {
		difficulty = dif;
		setConstants(dif, firstInit, switchDif);
	}

	public static String getDif() {
		return difficulty;
	}

	private static void setConstants(String dif, boolean firstInit, boolean changeDif) {

	}

	@SuppressWarnings("unused")
	private static void newGameSetup() {

	}
}

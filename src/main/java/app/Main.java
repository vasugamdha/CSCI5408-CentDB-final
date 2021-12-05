package app;

public class Main {

	public static void main(String[] args) throws Exception {

		Input input = new Input();
		try {
			input.getInput();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

}

package ar.uba.fi.tdd.rulogic.model.builder;

public class UnknownLineFormatException extends Exception {

	private static final long serialVersionUID = -4765219615903919857L;
	public int lineNumber;
	public String lineData;

	public UnknownLineFormatException(int lineNumber, String lineData) {
		this.lineNumber = lineNumber;
		this.lineData = lineData;
	}
	
}

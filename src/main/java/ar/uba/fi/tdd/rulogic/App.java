package ar.uba.fi.tdd.rulogic;

import java.io.Console;
import java.io.IOException;

import ar.uba.fi.tdd.rulogic.model.abstractions.KnowledgeBase;
import ar.uba.fi.tdd.rulogic.model.abstractions.MalformedQueryException;
import ar.uba.fi.tdd.rulogic.model.builder.KnowledgeBaseBuilder;
import ar.uba.fi.tdd.rulogic.model.builder.UnknownLineFormatException;

/**
 * Console application.
 *
 */
public class App
{
	private static final String DEFAULT_PATH = "src/main/resources/rules.db";
	private static Console console; 
	private static KnowledgeBase knowledgeBase;
	
	private static void runQueryLoop() {
		String input;
		boolean quit = false;
		while (!quit) {
			System.out.println("Write your query (or type q|quit|exit to close the program): ");
			input = console.readLine();
			quit = input.matches("exit|quit|q");
			if (!quit) {
				try {
					boolean answer = knowledgeBase.answer(input);
					System.out.println("That's " + answer + "!");
				} catch (MalformedQueryException e) {
					System.out.println("Looks like your query wasn't formatted right, please try again.");
				}
			}
		}		
	}
	
	public static void main(String[] args) {
		System.out.println("I shall answer all your questions!");
		console = System.console();
		System.out.println("Please enter the .db file path (leave blank for default):");
		String path = console.readLine();
		path = path.length() != 0 ? path : DEFAULT_PATH;
		try {
			KnowledgeBaseBuilder builder = new KnowledgeBaseBuilder();
			knowledgeBase = builder.buildFromDBFile(path);
			runQueryLoop();
		} catch (IOException e) {
			System.out.println("Could not locate the file. Are you sure it exists, and it has the correct permissions?");
		} catch (UnknownLineFormatException e) {
			System.out.println("There was an error parsing the file. In line " + e.lineNumber + ": " + e.lineData + ".");
			System.out.println("Please check the formatting for that line and try again.");
		}
	
    }
}

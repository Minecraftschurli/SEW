package api;


import javax.swing.JOptionPane;


public class Try {
	private static Log log = new Log();
	public static void error(){
		int button=0;
		button=JOptionPane.showConfirmDialog(null, "<html><font color=#FF0000>ERROR!</font>"+"\n"+"Wollen Sie fortfahren?", "ERROR!", JOptionPane.YES_NO_OPTION, ERROR_MESSAGE);
		//System.out.print(""+button);
		if(button==1)System.exit(0);
	}
	
	public static void error(String text){
		int button=0;
		button=JOptionPane.showConfirmDialog(null, "<html><font color=#FF0000>ERROR!</font>"+"\n"+text+"\n"+"Wollen Sie fortfahren?", "ERROR!", JOptionPane.YES_NO_OPTION, ERROR_MESSAGE);
		//System.out.print(""+button);
		if(button==1)System.exit(0);
	}
	
	public static void waitM(long millisec){
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			log.log(e);
			JOptionPane.showMessageDialog(null, "Error", "Error", ERROR_MESSAGE);
		}
	}
	
	public static int toInt(String text){
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			log.log(e);
			return 0;
		}
	}
	
	public static long toLong(String text){
		try {
			return Long.parseLong(text);
		} catch (NumberFormatException e) {
			log.log(e);
			return 0;
		}
	}
	
	public static float toFloat(String text){
		try {
			return Float.parseFloat(text);
		} catch (NumberFormatException e) {
			log.log(e);
			return 0;
		}
	}
	
	public static double toDouble(String text){
		try {
			return Double.parseDouble(text);
		} catch (NumberFormatException e) {
			log.log(e);
			return 0;
		}
	}
	
	public static boolean toBoolean(String text){
        try {
            return Boolean.parseBoolean(text);
        } catch (NumberFormatException e) {
            log.log(e);
            return false;
        }
    }
	
	public static int liesZahl(String text){
		int input;
		try{
			input=Integer.parseInt(JOptionPane.showInputDialog(null,
					text,
					"liesZahl()",
					PLAIN_MESSAGE));
		}catch(NumberFormatException e){
			log.log(e);
			input=0;
		}
		return input;
	}
	
	public static int repeatedInputInt(String text, String titel, int messageType){
		int button=0;
		int input=0;
		boolean korrekt=false;
		while(!korrekt){
			try{
				input=Integer.parseInt(JOptionPane.showInputDialog(null,
						text,
						titel,
						messageType));
				korrekt=true;
			}catch(NumberFormatException e){
				log.log(e);
				button=JOptionPane.showConfirmDialog(null,
						"Das war eine Falsche Eingabe bitte nur Zahlen eingeben!",
						"Fehler",
						JOptionPane.OK_CANCEL_OPTION,
						ERROR_MESSAGE);
				korrekt=false;
				if(button==2)System.exit(0);
			}
		}
		return input;
	}
	
	public static int repeatedInputInt(String text, String titel){
		int button=0;
		int input=0;
		boolean korrekt=false;
		while(!korrekt){
			try{
				input=Integer.parseInt(JOptionPane.showInputDialog(null,
						text,
						titel,
						QUESTION_MESSAGE));
				korrekt=true;
			}catch(NumberFormatException e){
				log.log(e);
				button=JOptionPane.showConfirmDialog(null,
						"Das war eine Falsche Eingabe bitte nur Zahlen eingeben!",
						"Fehler",
						JOptionPane.OK_CANCEL_OPTION,
						ERROR_MESSAGE);
				korrekt=false;
				if(button==2)System.exit(0);
			}
		}
		return input;
	}
	
	public static int repeatedInputInt(String text){
		int button=0;
		int input=0;
		boolean korrekt=false;
		while(!korrekt){
			try{
				input=Integer.parseInt(JOptionPane.showInputDialog(null,
						text,
						"intInput()",
						QUESTION_MESSAGE));
				korrekt=true;
			}catch(NumberFormatException e){
				log.log(e);
				button=JOptionPane.showConfirmDialog(null,
						"Das war eine Falsche Eingabe bitte nur Zahlen eingeben!",
						"Fehler",
						JOptionPane.OK_CANCEL_OPTION,
						ERROR_MESSAGE);
				korrekt=false;
				if(button==2)System.exit(0);
			}
		}
		return input;
	}
	
	public static long repeatedInputLong(String text, String titel, int messageType){
		int button=0;
		long input=0;
		boolean korrekt=false;
		while(!korrekt){
			try{
				input=Long.parseLong(JOptionPane.showInputDialog(null,
						text,
						titel,
						messageType));
				korrekt=true;
			}catch(NumberFormatException e){
				log.log(e);
				button=JOptionPane.showConfirmDialog(null,
						"Das war eine Falsche Eingabe bitte nur Zahlen eingeben!",
						"Fehler",
						JOptionPane.OK_CANCEL_OPTION,
						ERROR_MESSAGE);
				korrekt=false;
				if(button==2)System.exit(0);
			}
		}
		return input;
	}
	
	public static long repeatedInputLong(String text, String titel){
		int button=0;
		long input=0;
		boolean korrekt=false;
		while(!korrekt){
			try{
				input=Long.parseLong(JOptionPane.showInputDialog(null,
						text,
						titel,
						QUESTION_MESSAGE));
				korrekt=true;
			}catch(NumberFormatException e){
				log.log(e);
				button=JOptionPane.showConfirmDialog(null,
						"Das war eine Falsche Eingabe bitte nur Zahlen eingeben!",
						"Fehler",
						JOptionPane.OK_CANCEL_OPTION,
						ERROR_MESSAGE);
				korrekt=false;
				if(button==2)System.exit(0);
			}
		}
		return input;
	}
	
	public static long repeatedInputLong(String text){
		int button=0;
		long input=0;
		boolean korrekt=false;
		while(!korrekt){
			try{
				input=Long.parseLong(JOptionPane.showInputDialog(null,
						text,
						"longInput()",
						QUESTION_MESSAGE));
				korrekt=true;
			}catch(NumberFormatException e){
				log.log(e);
				button=JOptionPane.showConfirmDialog(null,
						"Das war eine Falsche Eingabe bitte nur Zahlen eingeben!",
						"Fehler",
						JOptionPane.OK_CANCEL_OPTION,
						ERROR_MESSAGE);
				korrekt=false;
				if(button==2)System.exit(0);
			}
		}
		return input;
	}
	
	public static float repeatedInputFloat(String text, String titel, int messageType){
		int button=0;
		float input=0;
		boolean korrekt=false;
		while(!korrekt){
			try{
				input=Float.parseFloat(JOptionPane.showInputDialog(null,
						text,
						titel,
						messageType));
				korrekt=true;
			}catch(NumberFormatException e){
				log.log(e);
				button=JOptionPane.showConfirmDialog(null,
						"Das war eine Falsche Eingabe bitte nur Komma Zahlen eingeben!",
						"Fehler",
						JOptionPane.OK_CANCEL_OPTION,
						ERROR_MESSAGE);
				korrekt=false;
				if(button==2)System.exit(0);
			}
		}
		return input;
	}
	
	public static float repeatedInputFloat(String text, String titel){
		int button=0;
		float input=0;
		boolean korrekt=false;
		while(!korrekt){
			try{
				input=Float.parseFloat(JOptionPane.showInputDialog(null,
						text,
						titel,
						QUESTION_MESSAGE));
				korrekt=true;
			}catch(NumberFormatException e){
				log.log(e);
				button=JOptionPane.showConfirmDialog(null,
						"Das war eine Falsche Eingabe bitte nur Komma Zahlen eingeben!",
						"Fehler",
						JOptionPane.OK_CANCEL_OPTION,
						ERROR_MESSAGE);
				korrekt=false;
				if(button==2)System.exit(0);
			}
		}
		return input;
	}
	
	public static float repeatedInputFloat(String text){
		int button=0;
		float input=0;
		boolean korrekt=false;
		while(!korrekt){
			try{
				input=Float.parseFloat(JOptionPane.showInputDialog(null,
						text,
						"floatInput()",
						QUESTION_MESSAGE));
				korrekt=true;
			}catch(NumberFormatException e){
				log.log(e);
				button=JOptionPane.showConfirmDialog(null,
						"Das war eine Falsche Eingabe bitte nur Komma Zahlen eingeben!",
						"Fehler",
						JOptionPane.OK_CANCEL_OPTION,
						ERROR_MESSAGE);
				korrekt=false;
				if(button==2)System.exit(0);
			}
		}
		return input;
	}
	
	public static double repeatedInputDouble(String text, String titel, int messageType){
		int button=0;
		double input=0;
		boolean korrekt=false;
		while(!korrekt){
			try{
				input=Double.parseDouble(JOptionPane.showInputDialog(null,
						text,
						titel,
						messageType));
				korrekt=true;
			}catch(NumberFormatException e){
				log.log(e);
				button=JOptionPane.showConfirmDialog(null,
						"Das war eine Falsche Eingabe bitte nur Zahlen im Format double eingeben!",
						"Fehler",
						JOptionPane.OK_CANCEL_OPTION,
						ERROR_MESSAGE);
				korrekt=false;
				if(button==2)System.exit(0);
			}
		}
		return input;
	}
	
	public static double repeatedInputDouble(String text, String titel){
		int button=0;
		double input=0;
		boolean korrekt=false;
		while(!korrekt){
			try{
				input=Double.parseDouble(JOptionPane.showInputDialog(null,
						text,
						titel,
						QUESTION_MESSAGE));
				korrekt=true;
			}catch(NumberFormatException e){
				log.log(e);
				button=JOptionPane.showConfirmDialog(null,
						"Das war eine Falsche Eingabe bitte nur Zahlen im Format double eingeben!",
						"Fehler",
						JOptionPane.OK_CANCEL_OPTION,
						ERROR_MESSAGE);
				korrekt=false;
				if(button==2)System.exit(0);
			}
		}
		return input;
	}
	
	public static double repeatedInputDouble(String text){
		int button=0;
		double input=0;
		boolean korrekt=false;
		while(!korrekt){
			try{
				input=Double.parseDouble(JOptionPane.showInputDialog(null,
						text,
						"doubleInput",
						QUESTION_MESSAGE));
				korrekt=true;
			}catch(NumberFormatException e){
				log.log(e);
				button=JOptionPane.showConfirmDialog(null,
						"Das war eine Falsche Eingabe bitte nur Zahlen im Format double eingeben!",
						"Fehler",
						JOptionPane.OK_CANCEL_OPTION,
						ERROR_MESSAGE);
				korrekt=false;
				if(button==2)System.exit(0);
			}
		}
		return input;
	}
	
	public static String input(String text, String title, int messageType){
		try {
			return JOptionPane.showInputDialog(null, text, title, messageType);
		} catch (Exception e) {
			log.log(e);
			return "";
		}
	}
	
	public static String input(String text, String title){
		try {
			return JOptionPane.showInputDialog(null, text, title, PLAIN_MESSAGE);
		} catch (Exception e) {
			log.log(e);
			return "";
		}
	}
	
	public static String input(String text){
		try {
			return JOptionPane.showInputDialog(null, text, "", PLAIN_MESSAGE);
		} catch (Exception e) {
			log.log(e);
			return "";
		}
	}
	
	public static void output(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	public static void output(String message,String title){
		JOptionPane.showMessageDialog(null, message, title, PLAIN_MESSAGE);
	}
	
	public static void output(String message,String title,int massageType){
		JOptionPane.showMessageDialog(null, message, title, massageType);
	}
	
	public static final int PLAIN_MESSAGE = JOptionPane.PLAIN_MESSAGE;
	public static final int ERROR_MESSAGE = JOptionPane.ERROR_MESSAGE;
	public static final int WARNING_MESSAGE = JOptionPane.WARNING_MESSAGE;
	public static final int QUESTION_MESSAGE = JOptionPane.QUESTION_MESSAGE;
	
}

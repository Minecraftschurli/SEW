package api;

/**
 * @author georg
 *
 */
public class Log {
	
	private boolean cleared;
	private Exception[] log;
	
	public Log(){
		this.log=new Exception[0];
	}
	
	public void log(Exception e) {
		Exception[] temp;
		temp = new Exception[this.log.length+1];
		for (int i=0;i<this.log.length;i++)temp[i] = this.log[i];
		temp[this.log.length]=e;
		this.log = temp;
	}
	
	public Exception[] getLog(){
		return this.log;
	}
	
	public void clearLog(){
		Exception[] temp;
		temp = new Exception[0];
		this.log = temp;
		this.cleared = true;
		System.out.println("The Log has been cleared!");
	}
	
	public void dispLog(){
		if(this.log.length>0){
			if(this.cleared)System.out.println("Log has been cleared before!");
			System.out.println("--------Log:Start--------");
			for(int i=0;i<this.log.length;i++)System.out.println(""+this.log[i]);
			System.out.println("--------Log:End----------");
		}else{
			if(!this.cleared)System.out.println("Log is empty! No Exceptions occured.");
			else System.out.println("Log is empty! No Exceptions occured since the last time the Log has been cleared.");
		}
	}
}

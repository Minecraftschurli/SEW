package au16a;

import java.util.Date;

public class EMail 
{
	public final String senderEmail;
	public final String empfängerEmail;
	public final String betreff;
	public final String nachricht;
	public final Date sendeDatum;
	
	public EMail(String senderEmail, String empfängerEmail, String betreff, String nachricht)
	{
		this.sendeDatum = new Date(System.currentTimeMillis());
		this.senderEmail = senderEmail;
		this.empfängerEmail = empfängerEmail;
		this.betreff = betreff;
		this.nachricht = nachricht;
	}
	
	@Override
	public String toString()
	{
		char zeile = '\n';
		return this.sendeDatum.toString()+zeile+this.senderEmail+zeile+zeile+this.betreff+zeile+zeile+this.nachricht;
	}

	public String getBetreff() 
	{
		return this.betreff;
	}
}
